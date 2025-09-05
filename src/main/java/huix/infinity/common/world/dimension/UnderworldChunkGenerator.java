package huix.infinity.common.world.dimension;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.common.world.block.IFWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Underworld generator – MITE faithful upper band + noise‑driven bedrock strata.
 *
 * Fixes:
 *  - ArrayIndexOutOfBoundsException caused by strata noise arrays sized 16 while generator filled 16*16.
 *    Now uses 256-length (16x16) arrays with (x + z*16) indexing.
 *
 * Mapping:
 *  - Dimension assumed: -64 .. 319 (height 384). (Make sure DataGen dimension type matches runtime!)
 *  - MITE local 0..255 mapped to world 64..319.
 *
 * Upper band:
 *  - MITE original density interpolation.
 *  - Water zone localY < 24 (world 64..87): density<=0 => water else solid.
 *  - Stone replaced with Deepslate.
 *  - Bedrock top/bottom EXACT rule: localY >= 255 - rand(5) || localY <= rand(5).
 *
 * Lower band (-64..63):
 *  - Blackstone masses via density.
 *  - Mantle only at -64 (100%) and -63/-62 (65% mantle / 35% bedrock).
 *  - Bedrock strata (MITE style multi-noise) applied only in two bands:
 *      * Upper interface band: top 16 layers of lower zone (63 downward).
 *      * Lower strata band: from -61 upward for 24 vertical layers (configurable).
 *
 * Strata noise & thresholds adapted from earlier MITE-inspired code you provided.
 */
public class UnderworldChunkGenerator extends ChunkGenerator {

    /* ========================== Dimension / Mapping ========================== */
    private static final int DIM_MIN_Y = -64;
    private static final int DIM_HEIGHT = 384;
    private static final int DIM_TOP_Y = DIM_MIN_Y + DIM_HEIGHT - 1; // 319

    private static final int MITE_LOCAL_HEIGHT = 256;
    private static final int MITE_BAND_BASE_WORLD_Y = DIM_TOP_Y - (MITE_LOCAL_HEIGHT - 1); // 64
    private static final int MITE_BAND_TOP_WORLD_Y = DIM_TOP_Y;                             // 319

    private static final int MITE_WATER_LOCAL_THRESHOLD = 24; // localY < 24 => water zone
    private static final int MITE_LOWEST_LAND_LOCAL = 144;
    private static final int LOWEST_LAND_WORLD_Y = MITE_BAND_BASE_WORLD_Y + MITE_LOWEST_LAND_LOCAL; // 208

    /* ========================== Materials ========================== */
    private static final BlockState UPPER_SOLID = Blocks.DEEPSLATE.defaultBlockState();
    private static final BlockState LOWER_SOLID = Blocks.BLACKSTONE.defaultBlockState();

    /* ========================== Lower zone ========================== */
    private static final int LOWER_ZONE_TOP_WORLD_Y = MITE_BAND_BASE_WORLD_Y - 1; // 63
    private static final int LOWER_ZONE_BOTTOM_WORLD_Y = DIM_MIN_Y;               // -64
    private static final int LOWER_ZONE_HEIGHT = LOWER_ZONE_TOP_WORLD_Y - LOWER_ZONE_BOTTOM_WORLD_Y + 1; // 128

    /* ========================== Mantle / Bedrock Base ========================== */
    private static final int MANTLE_FULL_Y = -64;
    private static final int MANTLE_MIX_MIN_Y = -63;
    private static final int MANTLE_MIX_MAX_Y = -62;

    /* ========================== Lattice segmentation ========================== */
    private static final int H_NOISE_RES = 4;
    private static final int V_SLICE_UPPER = 8;
    private static final int V_SLICE_LOWER = 8;

    /* ========================== Bedrock random window ========================== */
    private static final int BEDROCK_RANDOM_WINDOW = 5;

    /* ========================== Strata bands config ========================== */
    private static final int LOWER_STRATA_TOP_DEPTH = 16;     // top 16 layers of lower zone (63..48)
    private static final int LOWER_STRATA_BOTTOM_HEIGHT = 24; // from -61 up to -38

    /* ========================== Strata thresholds (tuned) ========================== */
    private static final double STRATA_1A_THRESH = 0.20;
    private static final double STRATA_1B_THRESH = 0.20;
    private static final double STRATA_2_THRESH  = 0.30;
    private static final double STRATA_3_THRESH  = 0.25;
    private static final double STRATA_4_THRESH  = 0.25;
    private static final double STRATA_1C_BUMP   = 0.40;

    /* ========================== Seeds for XOR ========================== */
    private static final long SEED_COLUMN_XOR_LOWER_BEDROCK = 0x4BED0001L;
    private static final long SEED_COLUMN_XOR_MANTLE       = 0x13579BDF2468ACEFL;

    /* ========================== Terrain noise generators ========================== */
    private final MiteNoiseGeneratorOctaves netherNoiseGen1;
    private final MiteNoiseGeneratorOctaves netherNoiseGen2;
    private final MiteNoiseGeneratorOctaves netherNoiseGen3;
    private final MiteNoiseGeneratorOctaves netherNoiseGen6;
    private final MiteNoiseGeneratorOctaves netherNoiseGen7;

    private final MiteNoiseGeneratorOctaves lowerNoiseGen1;
    private final MiteNoiseGeneratorOctaves lowerNoiseGen2;
    private final MiteNoiseGeneratorOctaves lowerNoiseGen3;
    private final MiteNoiseGeneratorOctaves lowerNoiseGen6;
    private final MiteNoiseGeneratorOctaves lowerNoiseGen7;

    /* ========================== Strata noise generators ========================== */
    private final MiteNoiseGeneratorOctaves noise_strata_1a;
    private final MiteNoiseGeneratorOctaves noise_strata_1b;
    private final MiteNoiseGeneratorOctaves noise_strata_2;
    private final MiteNoiseGeneratorOctaves noise_strata_3;
    private final MiteNoiseGeneratorOctaves noise_strata_4;
    private final MiteNoiseGeneratorOctaves noise_strata_1a_bump;
    private final MiteNoiseGeneratorOctaves noise_strata_1b_bump;
    private final MiteNoiseGeneratorOctaves noise_strata_1c_bump;
    private final MiteNoiseGeneratorOctaves noise_strata_2_bump;
    private final MiteNoiseGeneratorOctaves noise_strata_3_bump;
    private final MiteNoiseGeneratorOctaves noise_strata_4_bump;

    /* ========================== Codec ========================== */
    public static final MapCodec<UnderworldChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource))
                    .apply(inst, UnderworldChunkGenerator::new)
    );

    public UnderworldChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
        long baseSeed = 2384752984L;
        RandomSource rng = RandomSource.create(baseSeed);

        this.netherNoiseGen1 = new MiteNoiseGeneratorOctaves(rng, 16);
        this.netherNoiseGen2 = new MiteNoiseGeneratorOctaves(rng, 16);
        this.netherNoiseGen3 = new MiteNoiseGeneratorOctaves(rng, 8);
        this.netherNoiseGen6 = new MiteNoiseGeneratorOctaves(rng, 10);
        this.netherNoiseGen7 = new MiteNoiseGeneratorOctaves(rng, 16);

        RandomSource rngLower = RandomSource.create(baseSeed ^ 0x5A17F00DL);
        this.lowerNoiseGen1 = new MiteNoiseGeneratorOctaves(rngLower, 16);
        this.lowerNoiseGen2 = new MiteNoiseGeneratorOctaves(rngLower, 16);
        this.lowerNoiseGen3 = new MiteNoiseGeneratorOctaves(rngLower, 8);
        this.lowerNoiseGen6 = new MiteNoiseGeneratorOctaves(rngLower, 10);
        this.lowerNoiseGen7 = new MiteNoiseGeneratorOctaves(rngLower, 16);

        // Strata noise – reuse original RNG for consistency
        this.noise_strata_1a = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_1b = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_2  = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_3  = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_4  = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_1a_bump = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_1b_bump = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_1c_bump = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_2_bump  = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_3_bump  = new MiteNoiseGeneratorOctaves(rng, 4);
        this.noise_strata_4_bump  = new MiteNoiseGeneratorOctaves(rng, 4);
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() { return CODEC; }

    /* ========================== Vanilla Hooks (unused) ========================== */
    @Override public void applyCarvers(WorldGenRegion region, long seed, RandomState randomState, BiomeManager biomeManager,
                                       StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {}
    @Override public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState randomState, ChunkAccess chunk) {}
    @Override public void spawnOriginalMobs(WorldGenRegion region) {}

    /* ========================== Height API ========================== */
    @Override public int getGenDepth() { return DIM_HEIGHT; }
    @Override public int getMinY() { return DIM_MIN_Y; }
    @Override public int getSeaLevel() { return MITE_BAND_BASE_WORLD_Y + MITE_WATER_LOCAL_THRESHOLD - 1; }
    @Override public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor accessor, RandomState randomState) {
        return LOWEST_LAND_WORLD_Y;
    }
    @Override public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor accessor, RandomState randomState) {
        BlockState[] arr = new BlockState[accessor.getHeight()];
        Arrays.fill(arr, Blocks.AIR.defaultBlockState());
        return new NoiseColumn(accessor.getMinBuildHeight(), arr);
    }
    @Override public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos pos) {
        list.add("[Underworld Gen]");
        list.add("MITE band: " + MITE_BAND_BASE_WORLD_Y + ".." + MITE_BAND_TOP_WORLD_Y);
        list.add("Lower zone: " + LOWER_ZONE_BOTTOM_WORLD_Y + ".." + LOWER_ZONE_TOP_WORLD_Y);
        list.add("Water zone local < 24 => world " + MITE_BAND_BASE_WORLD_Y + ".." + (MITE_BAND_BASE_WORLD_Y + 23));
    }

    /* ========================== Main generation ========================== */
    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(@NotNull Blender blender,
                                                                 @NotNull RandomState randomState,
                                                                 @NotNull StructureManager structureManager,
                                                                 @NotNull ChunkAccess chunk) {
        return CompletableFuture.supplyAsync(() -> {
            generateUpperMiteTerrain(chunk);
            applyMiteBedrockTopBottom(chunk);
            generateLowerBlackstoneMasses(chunk);
            generateMantleBase(chunk);
            // Strata passes (MITE-style noise) – now using correct 256-length arrays
            StrataPack strata = generateStrataNoisePack(chunk);
            generateStrataUpperInterface(chunk, strata);
            generateStrataLowerBands(chunk, strata);
            return chunk;
        }, net.minecraft.Util.backgroundExecutor());
    }

    /* ========================== Upper band (MITE faithful) ========================== */
    private void generateUpperMiteTerrain(ChunkAccess chunk) {
        int noiseSizeX = H_NOISE_RES + 1;
        int noiseSizeZ = H_NOISE_RES + 1;
        int noiseSizeY = (MITE_LOCAL_HEIGHT / V_SLICE_UPPER) + 1;

        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        double[] noiseField = buildDensityField(
                netherNoiseGen1, netherNoiseGen2, netherNoiseGen3,
                netherNoiseGen6, netherNoiseGen7,
                chunkX * H_NOISE_RES, 0, chunkZ * H_NOISE_RES,
                noiseSizeX, noiseSizeY, noiseSizeZ
        );

        for (int nx = 0; nx < H_NOISE_RES; ++nx) {
            for (int nz = 0; nz < H_NOISE_RES; ++nz) {
                for (int ny = 0; ny < MITE_LOCAL_HEIGHT / V_SLICE_UPPER; ++ny) {
                    double stepY = 0.125;
                    int i000 = ((nx) * noiseSizeZ + nz) * noiseSizeY + ny;
                    int i010 = ((nx) * noiseSizeZ + (nz + 1)) * noiseSizeY + ny;
                    int i100 = ((nx + 1) * noiseSizeZ + nz) * noiseSizeY + ny;
                    int i110 = ((nx + 1) * noiseSizeZ + (nz + 1)) * noiseSizeY + ny;

                    double n000 = noiseField[i000];
                    double n010 = noiseField[i010];
                    double n100 = noiseField[i100];
                    double n110 = noiseField[i110];

                    double d000 = (noiseField[i000 + 1] - n000) * stepY;
                    double d010 = (noiseField[i010 + 1] - n010) * stepY;
                    double d100 = (noiseField[i100 + 1] - n100) * stepY;
                    double d110 = (noiseField[i110 + 1] - n110) * stepY;

                    for (int sy = 0; sy < V_SLICE_UPPER; ++sy) {
                        double v000 = n000;
                        double v010 = n010;
                        double v100 = n100;
                        double v110 = n110;
                        double dv100 = (v100 - v000) * 0.25;
                        double dv110 = (v110 - v010) * 0.25;

                        for (int sx = 0; sx < 4; ++sx) {
                            double val = v000;
                            double dv010 = (v010 - v000) * 0.25;

                            for (int sz = 0; sz < 4; ++sz) {
                                int localY = ny * V_SLICE_UPPER + sy;
                                int worldY = MITE_BAND_BASE_WORLD_Y + localY;
                                if (worldY < chunk.getMinBuildHeight() || worldY > chunk.getMaxBuildHeight()) break;

                                BlockPos pos = new BlockPos(sx + nx * 4, worldY, sz + nz * 4);
                                BlockState state = Blocks.AIR.defaultBlockState();

                                if (localY < MITE_WATER_LOCAL_THRESHOLD) {
                                    state = (val > 0.0) ? UPPER_SOLID : Blocks.WATER.defaultBlockState();
                                } else {
                                    if (val > 0.0) state = UPPER_SOLID;
                                }
                                chunk.setBlockState(pos, state, false);
                                val += dv010;
                            }
                            v000 += dv100;
                            v010 += dv110;
                        }
                        n000 += d000; n010 += d010; n100 += d100; n110 += d110;
                    }
                }
            }
        }
    }

    /* ========================== Bedrock top/bottom (original rule) ========================== */
    private void applyMiteBedrockTopBottom(ChunkAccess chunk) {
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                RandomSource rand = RandomSource.create(columnSeed(chunk, x, z));
                rand.nextDouble(); rand.nextDouble(); rand.nextDouble();
                for (int localY = 0; localY < MITE_LOCAL_HEIGHT; ++localY) {
                    int worldY = MITE_BAND_BASE_WORLD_Y + localY;
                    if (worldY < chunk.getMinBuildHeight() || worldY > chunk.getMaxBuildHeight()) continue;
                    boolean top = localY >= (MITE_LOCAL_HEIGHT - 1) - rand.nextInt(BEDROCK_RANDOM_WINDOW);
                    boolean bottom = localY <= rand.nextInt(BEDROCK_RANDOM_WINDOW);
                    if (top || bottom) {
                        chunk.setBlockState(new BlockPos(x, worldY, z), Blocks.BEDROCK.defaultBlockState(), false);
                    }
                }
            }
        }
    }

    /* ========================== Lower blackstone masses ========================== */
    private void generateLowerBlackstoneMasses(ChunkAccess chunk) {
        int lowerHeight = LOWER_ZONE_HEIGHT;
        int noiseSizeX = H_NOISE_RES + 1;
        int noiseSizeZ = H_NOISE_RES + 1;
        int noiseSizeY = (lowerHeight / V_SLICE_LOWER) + 1;

        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        double[] density = buildDensityField(
                lowerNoiseGen1, lowerNoiseGen2, lowerNoiseGen3,
                lowerNoiseGen6, lowerNoiseGen7,
                chunkX * H_NOISE_RES, 0, chunkZ * H_NOISE_RES,
                noiseSizeX, noiseSizeY, noiseSizeZ
        );

        for (int nx = 0; nx < H_NOISE_RES; ++nx) {
            for (int nz = 0; nz < H_NOISE_RES; ++nz) {
                for (int ny = 0; ny < lowerHeight / V_SLICE_LOWER; ++ny) {
                    double stepY = 0.125;
                    int i000 = ((nx) * noiseSizeZ + nz) * noiseSizeY + ny;
                    int i010 = ((nx) * noiseSizeZ + (nz + 1)) * noiseSizeY + ny;
                    int i100 = ((nx + 1) * noiseSizeZ + nz) * noiseSizeY + ny;
                    int i110 = ((nx + 1) * noiseSizeZ + (nz + 1)) * noiseSizeY + ny;

                    double n000 = density[i000];
                    double n010 = density[i010];
                    double n100 = density[i100];
                    double n110 = density[i110];

                    double d000 = (density[i000 + 1] - n000) * stepY;
                    double d010 = (density[i010 + 1] - n010) * stepY;
                    double d100 = (density[i100 + 1] - n100) * stepY;
                    double d110 = (density[i110 + 1] - n110) * stepY;

                    for (int sy = 0; sy < V_SLICE_LOWER; ++sy) {
                        double v000 = n000;
                        double v010 = n010;
                        double v100 = n100;
                        double v110 = n110;
                        double dv100 = (v100 - v000) * 0.25;
                        double dv110 = (v110 - v010) * 0.25;

                        for (int sx = 0; sx < 4; ++sx) {
                            double val = v000;
                            double dv010 = (v010 - v000) * 0.25;
                            for (int sz = 0; sz < 4; ++sz) {
                                int localLowerY = ny * V_SLICE_LOWER + sy;
                                int worldY = LOWER_ZONE_BOTTOM_WORLD_Y + localLowerY;
                                if (worldY < chunk.getMinBuildHeight() || worldY > LOWER_ZONE_TOP_WORLD_Y) {
                                    val += dv010;
                                    continue;
                                }
                                if (worldY >= MITE_BAND_BASE_WORLD_Y) {
                                    val += dv010;
                                    continue;
                                }
                                if (val > 0.0) {
                                    chunk.setBlockState(new BlockPos(sx + nx * 4, worldY, sz + nz * 4), LOWER_SOLID, false);
                                }
                                val += dv010;
                            }
                            v000 += dv100;
                            v010 += dv110;
                        }
                        n000 += d000; n010 += d010; n100 += d100; n110 += d110;
                    }
                }
            }
        }
    }

    /* ========================== Mantle base (-64..-62) ========================== */
    private void generateMantleBase(ChunkAccess chunk) {
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                RandomSource rand = RandomSource.create(columnSeed(chunk, x, z) ^ SEED_COLUMN_XOR_MANTLE);
                for (int y = MANTLE_FULL_Y; y <= MANTLE_MIX_MAX_Y; ++y) {
                    if (y < chunk.getMinBuildHeight()) continue;
                    BlockPos pos = new BlockPos(x, y, z);
                    if (y == MANTLE_FULL_Y) {
                        chunk.setBlockState(pos, mantleBlock(), false);
                    } else {
                        if (rand.nextDouble() < 0.65)
                            chunk.setBlockState(pos, mantleBlock(), false);
                        else
                            chunk.setBlockState(pos, Blocks.BEDROCK.defaultBlockState(), false);
                    }
                }
            }
        }
    }

    /* ========================== Strata noise pack ========================== */
    private record StrataPack(
            double[] s1a, double[] s1b, double[] s2, double[] s3, double[] s4,
            double[] s1aB, double[] s1bB, double[] s1cB, double[] s2B, double[] s3B, double[] s4B
    ) {
        int idx(int x, int z) { return x + (z << 4); } // 0..255
    }

    private StrataPack generateStrataNoisePack(ChunkAccess chunk) {
        int baseX = chunk.getPos().x * 16;
        int baseZ = chunk.getPos().z * 16;

        // Each call with sizeX=16,sizeZ=16,sizeY=1 => array length 16*1*16=256
        double[] a1  = noise_strata_1a.generateNoiseOctaves(null, baseX, 0, baseZ, 16,1,16, 1.0,0.0,1.0);
        double[] b1  = noise_strata_1b.generateNoiseOctaves(null, baseX, 0, baseZ, 16,1,16, 1.0,0.0,1.0);
        double[] a2  = noise_strata_2 .generateNoiseOctaves(null, baseX, 0, baseZ, 16,1,16, 1.0,0.0,1.0);
        double[] a3  = noise_strata_3 .generateNoiseOctaves(null, baseX, 0, baseZ, 16,1,16, 1.0,0.0,1.0);
        double[] a4  = noise_strata_4 .generateNoiseOctaves(null, baseX, 0, baseZ, 16,1,16, 1.0,0.0,1.0);

        double[] a1B = noise_strata_1a_bump.generateNoiseOctaves(null, baseX,0,baseZ,16,1,16,1.0,0.0,1.0);
        double[] b1B = noise_strata_1b_bump.generateNoiseOctaves(null, baseX,0,baseZ,16,1,16,1.0,0.0,1.0);
        double[] c1B = noise_strata_1c_bump.generateNoiseOctaves(null, baseX,0,baseZ,16,1,16,1.0,0.0,1.0);
        double[] a2B = noise_strata_2_bump .generateNoiseOctaves(null, baseX,0,baseZ,16,1,16,1.0,0.0,1.0);
        double[] a3B = noise_strata_3_bump .generateNoiseOctaves(null, baseX,0,baseZ,16,1,16,1.0,0.0,1.0);
        double[] a4B = noise_strata_4_bump .generateNoiseOctaves(null, baseX,0,baseZ,16,1,16,1.0,0.0,1.0);

        return new StrataPack(a1,b1,a2,a3,a4,a1B,b1B,c1B,a2B,a3B,a4B);
    }

    /* ========================== Interface strata (top of lower zone) ========================== */
    private void generateStrataUpperInterface(ChunkAccess chunk, StrataPack pack) {
        int startY = LOWER_ZONE_TOP_WORLD_Y; // 63
        int endY = Math.max(LOWER_ZONE_TOP_WORLD_Y - LOWER_STRATA_TOP_DEPTH + 1, LOWER_ZONE_BOTTOM_WORLD_Y);
        final int zoneHeight = LOWER_ZONE_HEIGHT;

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int idx = pack.idx(x,z);
                RandomSource rand = RandomSource.create(columnSeed(chunk, x, z) ^ SEED_COLUMN_XOR_LOWER_BEDROCK);
                rand.nextDouble(); rand.nextDouble(); rand.nextDouble();

                double n1a = pack.s1a()[idx] + pack.s1aB()[idx];
                double n1b = pack.s1b()[idx] + pack.s1bB()[idx];
                double n2  = pack.s2()[idx]  + pack.s2B()[idx];
                double n3  = pack.s3()[idx]  + pack.s3B()[idx];
                double n4  = pack.s4()[idx]  + pack.s4B()[idx];
                double n1cB= pack.s1cB()[idx];

                for (int y = startY; y >= endY; --y) {
                    if (y < chunk.getMinBuildHeight()) break;
                    if (y >= MITE_BAND_BASE_WORLD_Y) continue; // do not intrude into upper band

                    int localLowerY = y - LOWER_ZONE_BOTTOM_WORLD_Y;
                    int distFromBottom = localLowerY;
                    int distFromTop    = zoneHeight - 1 - localLowerY;

                    boolean makeBedrock = false;
                    if (distFromTop < BEDROCK_RANDOM_WINDOW && rand.nextInt(BEDROCK_RANDOM_WINDOW) > distFromTop - 1)
                        makeBedrock = true;
                    if (distFromBottom < BEDROCK_RANDOM_WINDOW && rand.nextInt(BEDROCK_RANDOM_WINDOW) > distFromBottom - 1)
                        makeBedrock = true;

                    if (!makeBedrock) {
                        if (localLowerY < 6 && (n1a > STRATA_1A_THRESH || n1b > STRATA_1B_THRESH)) makeBedrock = true;
                        if (localLowerY < 10 && (n2 > STRATA_2_THRESH)) makeBedrock = true;
                        if (localLowerY > zoneHeight - 6 && (n3 > STRATA_3_THRESH || n4 > STRATA_4_THRESH)) makeBedrock = true;
                        if ((localLowerY < 16 || localLowerY > zoneHeight - 16) && n1cB > STRATA_1C_BUMP) makeBedrock = true;
                    }

                    if (makeBedrock) {
                        BlockPos pos = new BlockPos(x, y, z);
                        BlockState old = chunk.getBlockState(pos);
                        if (old.isAir() || old.is(Blocks.BLACKSTONE)) {
                            chunk.setBlockState(pos, Blocks.BEDROCK.defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }

    /* ========================== Lower strata band (above mantle) ========================== */
    private void generateStrataLowerBands(ChunkAccess chunk, StrataPack pack) {
        int strataStart = MANTLE_MIX_MAX_Y + 1; // -61
        int strataEnd   = Math.min(strataStart + LOWER_STRATA_BOTTOM_HEIGHT - 1, LOWER_ZONE_TOP_WORLD_Y);
        final int zoneHeight = LOWER_ZONE_HEIGHT;

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int idx = pack.idx(x,z);
                RandomSource rand = RandomSource.create(columnSeed(chunk, x, z) ^ (SEED_COLUMN_XOR_LOWER_BEDROCK >>> 1));
                rand.nextDouble(); rand.nextDouble(); rand.nextDouble();

                double n1a = pack.s1a()[idx] + pack.s1aB()[idx];
                double n1b = pack.s1b()[idx] + pack.s1bB()[idx];
                double n2  = pack.s2()[idx]  + pack.s2B()[idx];
                double n3  = pack.s3()[idx]  + pack.s3B()[idx];
                double n4  = pack.s4()[idx]  + pack.s4B()[idx];
                double n1cB= pack.s1cB()[idx];

                for (int y = strataStart; y <= strataEnd; ++y) {
                    if (y < chunk.getMinBuildHeight()) continue;
                    if (y >= MITE_BAND_BASE_WORLD_Y) break;

                    int localLowerY = y - LOWER_ZONE_BOTTOM_WORLD_Y;
                    int distFromBottom = localLowerY;
                    int distFromTop    = zoneHeight - 1 - localLowerY;

                    boolean makeBedrock = false;
                    if (distFromTop < BEDROCK_RANDOM_WINDOW && rand.nextInt(BEDROCK_RANDOM_WINDOW) > distFromTop - 1)
                        makeBedrock = true;
                    if (distFromBottom < BEDROCK_RANDOM_WINDOW && rand.nextInt(BEDROCK_RANDOM_WINDOW) > distFromBottom - 1)
                        makeBedrock = true;

                    if (!makeBedrock) {
                        if (localLowerY < 6 && (n1a > STRATA_1A_THRESH || n1b > STRATA_1B_THRESH)) makeBedrock = true;
                        if (localLowerY < 10 && n2 > STRATA_2_THRESH) makeBedrock = true;
                        if (localLowerY > zoneHeight - 6 && (n3 > STRATA_3_THRESH || n4 > STRATA_4_THRESH)) makeBedrock = true;
                        if ((localLowerY < 16 || localLowerY > zoneHeight - 16) && n1cB > STRATA_1C_BUMP) makeBedrock = true;
                    }

                    if (makeBedrock) {
                        BlockPos pos = new BlockPos(x, y, z);
                        BlockState old = chunk.getBlockState(pos);
                        if (old.isAir() || old.is(Blocks.BLACKSTONE) || old.is(Blocks.DEEPSLATE)) {
                            chunk.setBlockState(pos, Blocks.BEDROCK.defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }

    /* ========================== Density field (shared) ========================== */
    private double[] buildDensityField(
            MiteNoiseGeneratorOctaves gen1,
            MiteNoiseGeneratorOctaves gen2,
            MiteNoiseGeneratorOctaves gen3,
            MiteNoiseGeneratorOctaves gen6,
            MiteNoiseGeneratorOctaves gen7,
            int offX, int offY, int offZ,
            int sizeX, int sizeY, int sizeZ) {

        double base = 684.412;
        double baseY = 2053.236;

        double[] noise4 = gen6.generateNoiseOctaves(null, offX, offY, offZ, sizeX, 1, sizeZ, 1.0, 0.0, 1.0);
        double[] noise5 = gen7.generateNoiseOctaves(null, offX, offY, offZ, sizeX, 1, sizeZ, 100.0, 0.0, 100.0);
        double[] noise1 = gen3.generateNoiseOctaves(null, offX, offY, offZ, sizeX, sizeY, sizeZ,
                base / 80.0, baseY / 60.0, base / 80.0);
        double[] noise2 = gen1.generateNoiseOctaves(null, offX, offY, offZ, sizeX, sizeY, sizeZ,
                base, baseY, base);
        double[] noise3 = gen2.generateNoiseOctaves(null, offX, offY, offZ, sizeX, sizeY, sizeZ,
                base, baseY, base);

        double[] out = new double[sizeX * sizeY * sizeZ];
        int idx3D = 0;
        int idx2D = 0;

        double[] verticalShape = new double[sizeY];
        for (int y = 0; y < sizeY; ++y) {
            verticalShape[y] = Math.cos(y * Math.PI * 6.0 / sizeY) * 2.0;
            double m = y;
            if (y > sizeY / 2) m = sizeY - 1 - y;
            if (m < 4.0) {
                m = 4.0 - m;
                verticalShape[y] -= m * m * m * 10.0;
            }
        }

        for (int x = 0; x < sizeX; ++x) {
            for (int z = 0; z < sizeZ; ++z) {
                double d4 = (noise4[idx2D] + 256.0) / 512.0;
                if (d4 > 1.0) d4 = 1.0;

                double d5 = noise5[idx2D] / 8000.0;
                if (d5 < 0.0) d5 = -d5;
                d5 = d5 * 3.0 - 3.0;
                if (d5 < 0.0) {
                    d5 /= 2.0;
                    if (d5 < -1.0) d5 = -1.0;
                    d5 /= 1.4;
                    d5 /= 2.0;
                    d4 = 0.0;
                } else {
                    if (d5 > 1.0) d5 = 1.0;
                    d5 /= 6.0;
                }
                d4 += 0.5;
                ++idx2D;

                for (int y = 0; y < sizeY; ++y) {
                    double n2 = noise2[idx3D] / 512.0;
                    double n3 = noise3[idx3D] / 512.0;
                    double blend = (noise1[idx3D] / 10.0 + 1.0) / 2.0;
                    double val;
                    if (blend < 0.0) val = n2;
                    else if (blend > 1.0) val = n3;
                    else val = n2 + (n3 - n2) * blend;
                    val -= verticalShape[y];
                    if (y > sizeY - 4) {
                        double t = (y - (sizeY - 4)) / 3.0;
                        val = val * (1.0 - t) + (-10.0) * t;
                    }
                    out[idx3D++] = val;
                }
            }
        }
        return out;
    }

    /* ========================== Seeds / Mantle helper ========================== */
    private long columnSeed(ChunkAccess chunk, int x, int z) {
        long cx = chunk.getPos().x;
        long cz = chunk.getPos().z;
        return (cx * 341873128712L) ^ (cz * 132897987541L) ^ (x * 734287L) ^ (z * 912931L);
    }

    private BlockState mantleBlock() {
        try { return IFWBlocks.mantle.get().defaultBlockState(); }
        catch (Throwable t) { return Blocks.BEDROCK.defaultBlockState(); }
    }

    /* ========================== Perlin / Octaves ========================== */
    private static class MiteNoiseGeneratorOctaves {
        private final MiteNoiseGeneratorPerlin[] octaves;
        private final int count;
        MiteNoiseGeneratorOctaves(RandomSource random, int count) {
            this.count = count;
            this.octaves = new MiteNoiseGeneratorPerlin[count];
            for (int i = 0; i < count; i++) {
                this.octaves[i] = new MiteNoiseGeneratorPerlin(random);
            }
        }
        double[] generateNoiseOctaves(double[] result, int ox, int oy, int oz,
                                      int sx, int sy, int sz,
                                      double scx, double scy, double scz) {
            if (result == null) result = new double[sx * sy * sz];
            else Arrays.fill(result, 0.0);
            double amplitude = 1.0;
            for (int o = 0; o < count; o++) {
                double dx = ox * scx * amplitude;
                double dy = oy * scy * amplitude;
                double dz = oz * scz * amplitude;

                long lx = (long)Math.floor(dx);
                long lz = (long)Math.floor(dz);
                dx -= lx; dz -= lz;
                lx %= 16777216L; lz %= 16777216L;
                dx += lx; dz += lz;

                octaves[o].populate(result, dx, dy, dz, sx, sy, sz,
                        scx * amplitude, scy * amplitude, scz * amplitude, amplitude);

                amplitude /= 2.0;
            }
            return result;
        }
    }

    private static class MiteNoiseGeneratorPerlin {
        private final int[] perm;
        private final double xOff, yOff, zOff;
        MiteNoiseGeneratorPerlin(RandomSource rand) {
            perm = new int[512];
            xOff = rand.nextDouble() * 256.0;
            yOff = rand.nextDouble() * 256.0;
            zOff = rand.nextDouble() * 256.0;
            for (int i = 0; i < 256; i++) perm[i] = i;
            for (int i = 0; i < 256; i++) {
                int j = rand.nextInt(256 - i) + i;
                int tmp = perm[i];
                perm[i] = perm[j];
                perm[j] = tmp;
                perm[i + 256] = perm[i];
            }
        }
        void populate(double[] out, double ox, double oy, double oz,
                      int sx, int sy, int sz,
                      double scx, double scy, double scz,
                      double amplitude) {
            int idx = 0;
            double invAmp = 1.0 / amplitude;
            for (int x = 0; x < sx; ++x) {
                double px = ox + x * scx + xOff;
                for (int z = 0; z < sz; ++z) {
                    double pz = oz + z * scz + zOff;
                    for (int y = 0; y < sy; ++y) {
                        double py = oy + y * scy + yOff;
                        double n = perlin(px, py, pz);
                        out[idx++] += n * invAmp;
                    }
                }
            }
        }
        private double perlin(double x, double y, double z) {
            int xi = fastFloor(x) & 255;
            int yi = fastFloor(y) & 255;
            int zi = fastFloor(z) & 255;
            double xf = x - fastFloor(x);
            double yf = y - fastFloor(y);
            double zf = z - fastFloor(z);

            double u = fade(xf);
            double v = fade(yf);
            double w = fade(zf);

            int a = perm[xi] + yi;
            int aa = perm[a] + zi;
            int ab = perm[a + 1] + zi;
            int b = perm[xi + 1] + yi;
            int ba = perm[b] + zi;
            int bb = perm[b + 1] + zi;

            return lerp(w,
                    lerp(v,
                            lerp(u, grad(perm[aa], xf, yf, zf),
                                    grad(perm[ba], xf - 1, yf, zf)),
                            lerp(u, grad(perm[ab], xf, yf - 1, zf),
                                    grad(perm[bb], xf - 1, yf - 1, zf))
                    ),
                    lerp(v,
                            lerp(u, grad(perm[aa + 1], xf, yf, zf - 1),
                                    grad(perm[ba + 1], xf - 1, yf, zf - 1)),
                            lerp(u, grad(perm[ab + 1], xf, yf - 1, zf - 1),
                                    grad(perm[bb + 1], xf - 1, yf - 1, zf - 1))
                    )
            );
        }
        private int fastFloor(double d) { return d >= 0 ? (int)d : (int)d - 1; }
        private double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
        private double lerp(double t, double a, double b) { return a + t * (b - a); }
        private double grad(int hash, double x, double y, double z) {
            int h = hash & 15;
            double u = h < 8 ? x : y;
            double v = (h < 4) ? y : (h == 12 || h == 14 ? x : z);
            return ((h & 1)==0?u:-u) + ((h & 2)==0?v:-v);
        }
    }
}
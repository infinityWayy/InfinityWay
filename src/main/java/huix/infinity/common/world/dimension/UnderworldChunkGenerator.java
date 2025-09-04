package huix.infinity.common.world.dimension;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

public class UnderworldChunkGenerator extends ChunkGenerator {
    public static final MapCodec<UnderworldChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource)
            ).apply(instance, UnderworldChunkGenerator::new)
    );

    // MITE风格的噪声生成器 - 重新实现
    private final MiteNoiseGeneratorOctaves netherNoiseGen1;  // 16 octaves
    private final MiteNoiseGeneratorOctaves netherNoiseGen2;  // 16 octaves
    private final MiteNoiseGeneratorOctaves netherNoiseGen3;  // 8 octaves
    private final MiteNoiseGeneratorOctaves netherNoiseGen6;  // 10 octaves
    private final MiteNoiseGeneratorOctaves netherNoiseGen7;  // 16 octaves

    // 修正的MITE常量 - 256层！
    private static final double NOISE_SCALE_XZ = 684.412;
    private static final double NOISE_SCALE_Y = 2053.236;
    private static final int MITE_HEIGHT = 256; // 修正！MITE是256层
    private static final int MODERN_HEIGHT = 256; // 使用完整的256层
    private static final int LIQUID_LEVEL = 32;

    // 调试
    private static final boolean DEBUG_MODE = true;
    private static int debugChunkCount = 0;

    public UnderworldChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
        long baseSeed = 2384752984L;
        RandomSource hellRNG = RandomSource.create(baseSeed);

        if (DEBUG_MODE) {
            System.out.println("=== Initializing UnderworldChunkGenerator (MITE 256-layer) ===");
            System.out.println("Base seed: " + baseSeed);
        }

        // 创建MITE风格的噪声生成器
        this.netherNoiseGen1 = new MiteNoiseGeneratorOctaves(hellRNG, 16);
        this.netherNoiseGen2 = new MiteNoiseGeneratorOctaves(hellRNG, 16);
        this.netherNoiseGen3 = new MiteNoiseGeneratorOctaves(hellRNG, 8);
        this.netherNoiseGen6 = new MiteNoiseGeneratorOctaves(hellRNG, 10);
        this.netherNoiseGen7 = new MiteNoiseGeneratorOctaves(hellRNG, 16);

        if (DEBUG_MODE) {
            System.out.println("MITE-style noise generators initialized with proper octaves");
        }
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(@NotNull Blender blender, @NotNull RandomState randomState,
                                                                 @NotNull StructureManager structureManager, @NotNull ChunkAccess chunk) {
        return CompletableFuture.supplyAsync(() -> {
            generateNetherTerrain(chunk);
            replaceBlocksForBiome(chunk);
            generateCobwebs(chunk);
            return chunk;
        }, net.minecraft.Util.backgroundExecutor());
    }

    /**
     * MITE原版的generateNetherTerrain方法 - 完全重写
     */
    private void generateNetherTerrain(ChunkAccess chunk) {
        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        debugChunkCount++;
        boolean shouldDebug = DEBUG_MODE && debugChunkCount <= 2;

        if (shouldDebug) {
            System.out.println("\n=== Generating terrain for chunk [" + chunkX + ", " + chunkZ + "] ===");
        }

        int var4 = 4;  // noiseRes
        int var5 = 32; // liquidLevel
        int var6 = var4 + 1; // noiseSizeX = 5
        int var7 = 33; // noiseSizeY = 33 (for 256 layers: 256/8 + 1 = 33)
        int var8 = var4 + 1; // noiseSizeZ = 5

        // 生成噪声场
        double[] noiseField = initializeNoiseField(null, chunkX * var4, 0, chunkZ * var4, var6, var7, var8, shouldDebug);

        int worldBaseY = chunk.getMinBuildHeight() + 64;
        int maxGenerationY = worldBaseY + MODERN_HEIGHT;

        int stoneCount = 0, airCount = 0, waterCount = 0;

        // MITE原版的完整逻辑 - 但适配256层
        for (int var9 = 0; var9 < var4; ++var9) {
            for (int var10 = 0; var10 < var4; ++var10) {
                for (int var11 = 0; var11 < 32; ++var11) { // 256/8 = 32层噪声分片
                    double var12 = 0.125; // 1/8

                    // 获取8个顶点
                    double var14 = noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var18 = noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var20 = noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];

                    double var22 = (noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14) * var12;
                    double var24 = (noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    double var26 = (noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18) * var12;
                    double var28 = (noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20) * var12;

                    for (int var30 = 0; var30 < 8; ++var30) {
                        double var31 = 0.25;
                        double var33 = var14;
                        double var35 = var16;
                        double var37 = (var18 - var14) * var31;
                        double var39 = (var20 - var16) * var31;

                        for (int var41 = 0; var41 < 4; ++var41) {
                            double var44 = 0.25;
                            double var46 = var33;
                            double var48 = (var35 - var33) * var44;

                            for (int var50 = 0; var50 < 4; ++var50) {
                                int blockX = var41 + var9 * 4;
                                int miteY = var11 * 8 + var30;
                                int blockZ = var50 + var10 * 4;

                                int worldY = worldBaseY + miteY;

                                if (worldY >= worldBaseY && worldY < maxGenerationY &&
                                        worldY >= chunk.getMinBuildHeight() && worldY < chunk.getMaxBuildHeight()) {

                                    BlockPos pos = new BlockPos(blockX, worldY, blockZ);

                                    // MITE原版逻辑
                                    int var51 = 0;
                                    if (miteY < var5 - 8) { // Y < 24
                                        var51 = 8; // water
                                    }

                                    if (var46 > 0.0) {
                                        var51 = 1; // stone
                                    }

                                    if (shouldDebug && var9 == 0 && var10 == 0 && var11 < 3 && var30 < 2 && var41 < 2 && var50 < 2) {
                                        System.out.printf("Block[%d,%d,%d] (world Y=%d) density=%.6f -> blockId=%d%n",
                                                blockX, miteY, blockZ, worldY, var46, var51);
                                    }

                                    BlockState blockState = getBlockStateFromId(var51);
                                    chunk.setBlockState(pos, blockState, false);

                                    if (var51 == 1) stoneCount++;
                                    else if (var51 == 8) waterCount++;
                                    else airCount++;
                                }

                                var46 += var48;
                            }

                            var33 += var37;
                            var35 += var39;
                        }

                        var14 += var22;
                        var16 += var24;
                        var18 += var26;
                        var20 += var28;
                    }
                }
            }
        }

        if (shouldDebug) {
            System.out.printf("Block counts - Stone: %d, Air: %d, Water: %d%n", stoneCount, airCount, waterCount);
            double totalBlocks = stoneCount + airCount + waterCount;
            if (totalBlocks > 0) {
                System.out.printf("Percentages - Stone: %.1f%%, Air: %.1f%%, Water: %.1f%%%n",
                        stoneCount/totalBlocks*100, airCount/totalBlocks*100, waterCount/totalBlocks*100);
            }
        }
    }

    /**
     * MITE的initializeNoiseField - 适配256层
     */
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4,
                                          int par5, int par6, int par7, boolean debug) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        if (debug) {
            System.out.printf("initializeNoiseField: offset[%d,%d,%d] size[%d,%d,%d]%n",
                    par2, par3, par4, par5, par6, par7);
        }

        double var8 = 684.412;
        double var10 = 2053.236;

        // 生成噪声数据
        double[] noiseData4 = netherNoiseGen6.generateNoiseOctaves(null, par2, par3, par4, par5, 1, par7, 1.0, 0.0, 1.0);
        double[] noiseData5 = netherNoiseGen7.generateNoiseOctaves(null, par2, par3, par4, par5, 1, par7, 100.0, 0.0, 100.0);
        double[] noiseData1 = netherNoiseGen3.generateNoiseOctaves(null, par2, par3, par4, par5, par6, par7,
                var8 / 80.0, var10 / 60.0, var8 / 80.0);
        double[] noiseData2 = netherNoiseGen1.generateNoiseOctaves(null, par2, par3, par4, par5, par6, par7,
                var8, var10, var8);
        double[] noiseData3 = netherNoiseGen2.generateNoiseOctaves(null, par2, par3, par4, par5, par6, par7,
                var8, var10, var8);

        if (debug) {
            System.out.println("Noise data generated. Checking for variation...");
            System.out.printf("noiseData1 range: %.6f to %.6f%n", Arrays.stream(noiseData1).min().orElse(0), Arrays.stream(noiseData1).max().orElse(0));
            System.out.printf("noiseData2 range: %.6f to %.6f%n", Arrays.stream(noiseData2).min().orElse(0), Arrays.stream(noiseData2).max().orElse(0));
            System.out.printf("noiseData3 range: %.6f to %.6f%n", Arrays.stream(noiseData3).min().orElse(0), Arrays.stream(noiseData3).max().orElse(0));
        }

        int var12 = 0;
        int var13 = 0;

        // 高度衰减因子
        double[] var14 = new double[par6];
        for (int var15 = 0; var15 < par6; ++var15) {
            var14[var15] = Math.cos((double)var15 * Math.PI * 6.0 / (double)par6) * 2.0;
            double var16 = (double)var15;
            if (var15 > par6 / 2) {
                var16 = (double)(par6 - 1 - var15);
            }
            if (var16 < 4.0) {
                var16 = 4.0 - var16;
                var14[var15] -= var16 * var16 * var16 * 10.0;
            }
        }

        // 主噪声合成循环
        for (int var36 = 0; var36 < par5; ++var36) {
            for (int var37 = 0; var37 < par7; ++var37) {
                double var17 = (noiseData4[var13] + 256.0) / 512.0;
                if (var17 > 1.0) {
                    var17 = 1.0;
                }

                double var19 = 0.0;
                double var21 = noiseData5[var13] / 8000.0;
                if (var21 < 0.0) {
                    var21 = -var21;
                }

                var21 = var21 * 3.0 - 3.0;
                if (var21 < 0.0) {
                    var21 /= 2.0;
                    if (var21 < -1.0) {
                        var21 = -1.0;
                    }
                    var21 /= 1.4;
                    var21 /= 2.0;
                    var17 = 0.0;
                } else {
                    if (var21 > 1.0) {
                        var21 = 1.0;
                    }
                    var21 /= 6.0;
                }

                var17 += 0.5; // 关键偏移
                var21 = var21 * (double)par6 / 16.0;
                ++var13;

                for (int var23 = 0; var23 < par6; ++var23) {
                    double var24 = 0.0;
                    double var26 = var14[var23];
                    double var28 = noiseData2[var12] / 512.0;
                    double var30 = noiseData3[var12] / 512.0;
                    double var32 = (noiseData1[var12] / 10.0 + 1.0) / 2.0;

                    if (var32 < 0.0) {
                        var24 = var28;
                    } else if (var32 > 1.0) {
                        var24 = var30;
                    } else {
                        var24 = var28 + (var30 - var28) * var32;
                    }

                    var24 -= var26;

                    if (var23 > par6 - 4) {
                        double var34 = (double)(var23 - (par6 - 4)) / 3.0;
                        var24 = var24 * (1.0 - var34) + (-10.0) * var34;
                    }

                    if ((double)var23 < var19) {
                        double var34 = (var19 - (double)var23) / 4.0;
                        if (var34 < 0.0) {
                            var34 = 0.0;
                        }
                        if (var34 > 1.0) {
                            var34 = 1.0;
                        }
                        var24 = var24 * (1.0 - var34) + (-10.0) * var34;
                    }

                    par1ArrayOfDouble[var12] = var24;
                    ++var12;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    /**
     * MITE风格基岩层生成
     */
    private void replaceBlocksForBiome(ChunkAccess chunk) {
        RandomSource random = RandomSource.create(chunk.getPos().toLong());
        int worldBaseY = chunk.getMinBuildHeight() + 64;

        for (int var7 = 0; var7 < 16; ++var7) {
            for (int var8 = 0; var8 < 16; ++var8) {
                random.nextDouble(); // MITE的额外随机调用
                random.nextDouble();
                random.nextDouble();

                for (int var15 = 255; var15 >= 0; --var15) { // 256层：0-255
                    int worldY = worldBaseY + var15;
                    if (worldY >= chunk.getMinBuildHeight() && worldY < chunk.getMaxBuildHeight()) {
                        if (var15 >= 255 - random.nextInt(5) || var15 <= 0 + random.nextInt(5)) {
                            BlockPos pos = new BlockPos(var7, worldY, var8);
                            chunk.setBlockState(pos, Blocks.BEDROCK.defaultBlockState(), false);
                        }
                    }
                }
            }
        }
    }

    private void generateCobwebs(ChunkAccess chunk) {
        RandomSource random = RandomSource.create(chunk.getPos().toLong() + 42L);
        int worldBaseY = chunk.getMinBuildHeight() + 64;

        for (int i = 0; i < 128; i++) {
            int x = random.nextInt(16);
            int miteY = random.nextInt(246) + 5; // 256层：Y=5-250
            int z = random.nextInt(16);

            int worldY = worldBaseY + miteY;

            if (worldY >= chunk.getMinBuildHeight() && worldY < chunk.getMaxBuildHeight()) {
                BlockPos pos = new BlockPos(x, worldY, z);
                if (chunk.getBlockState(pos).isAir()) {
                    BlockPos below = pos.below();
                    if (chunk.getBlockState(below).isSolidRender(chunk, below) && random.nextInt(8) == 0) {
                        chunk.setBlockState(pos, Blocks.COBWEB.defaultBlockState(), false);
                    }
                }
            }
        }
    }

    private BlockState getBlockStateFromId(int blockId) {
        return switch (blockId) {
            case 1 -> Blocks.STONE.defaultBlockState();
            case 8 -> Blocks.WATER.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, @NotNull LevelHeightAccessor levelHeightAccessor, @NotNull RandomState randomState) {
        BlockState[] column = new BlockState[levelHeightAccessor.getHeight()];
        Arrays.fill(column, Blocks.AIR.defaultBlockState());
        return new NoiseColumn(levelHeightAccessor.getMinBuildHeight(), column);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion region, long seed, @NotNull RandomState randomState,
                             @NotNull BiomeManager biomeManager, @NotNull StructureManager structureManager,
                             @NotNull ChunkAccess chunk, @NotNull GenerationStep.Carving step) {}

    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureManager structureManager,
                             @NotNull RandomState randomState, @NotNull ChunkAccess chunk) {}

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion region) {}

    @Override
    public int getGenDepth() {
        return MODERN_HEIGHT;
    }

    @Override
    public int getSeaLevel() {
        return getMinY() + 64 + LIQUID_LEVEL - 8;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, @NotNull Heightmap.Types heightmapType,
                             @NotNull LevelHeightAccessor levelHeightAccessor, @NotNull RandomState randomState) {
        return getMinY() + 140 + 114; // 中间高度
    }

    @Override
    public void addDebugScreenInfo(@NotNull List<String> list, @NotNull RandomState randomState, @NotNull BlockPos pos) {
        list.add("[IFW] Underworld Generator (MITE 256-layer Faithful Port)");
        list.add("MITE Height: 0-256 → World Y: " + (getMinY() + 64) + "-" + (getMinY() + 64 + MODERN_HEIGHT));
        list.add("Sea Level: " + getSeaLevel());
    }

    /**
     * MITE风格的噪声生成器实现
     */
    private static class MiteNoiseGeneratorOctaves {
        private final MiteNoiseGeneratorPerlin[] generatorCollection;
        private final int octaves;

        public MiteNoiseGeneratorOctaves(RandomSource random, int octaves) {
            this.octaves = octaves;
            this.generatorCollection = new MiteNoiseGeneratorPerlin[octaves];

            for (int i = 0; i < octaves; i++) {
                this.generatorCollection[i] = new MiteNoiseGeneratorPerlin(random);
            }
        }

        public double[] generateNoiseOctaves(double[] result, int offsetX, int offsetY, int offsetZ,
                                             int sizeX, int sizeY, int sizeZ,
                                             double scaleX, double scaleY, double scaleZ) {
            if (result == null) {
                result = new double[sizeX * sizeY * sizeZ];
            } else {
                Arrays.fill(result, 0.0);
            }

            double amplitude = 1.0;

            for (int octave = 0; octave < this.octaves; octave++) {
                double currentScaleX = offsetX * amplitude * scaleX;
                double currentScaleY = offsetY * amplitude * scaleY;
                double currentScaleZ = offsetZ * amplitude * scaleZ;

                // MITE的坐标处理
                long xLong = (long) currentScaleX;
                long zLong = (long) currentScaleZ;
                currentScaleX -= xLong;
                currentScaleZ -= zLong;
                xLong %= 16777216L;
                zLong %= 16777216L;
                currentScaleX += xLong;
                currentScaleZ += zLong;

                this.generatorCollection[octave].populateNoiseArray(result, currentScaleX, currentScaleY, currentScaleZ,
                        sizeX, sizeY, sizeZ,
                        scaleX * amplitude, scaleY * amplitude, scaleZ * amplitude,
                        amplitude);
                amplitude /= 2.0;
            }

            return result;
        }
    }

    /**
     * 简化的MITE Perlin噪声实现
     */
    private static class MiteNoiseGeneratorPerlin {
        private final int[] permutations;
        private final double xCoord, yCoord, zCoord;

        public MiteNoiseGeneratorPerlin(RandomSource random) {
            this.permutations = new int[512];
            this.xCoord = random.nextDouble() * 256.0;
            this.yCoord = random.nextDouble() * 256.0;
            this.zCoord = random.nextDouble() * 256.0;

            for (int i = 0; i < 256; i++) {
                this.permutations[i] = i;
            }

            for (int i = 0; i < 256; i++) {
                int j = random.nextInt(256 - i) + i;
                int temp = this.permutations[i];
                this.permutations[i] = this.permutations[j];
                this.permutations[j] = temp;
                this.permutations[i + 256] = this.permutations[i];
            }
        }

        public void populateNoiseArray(double[] result, double offsetX, double offsetY, double offsetZ,
                                       int sizeX, int sizeY, int sizeZ,
                                       double scaleX, double scaleY, double scaleZ, double amplitude) {
            int index = 0;
            double amplitudeRecip = 1.0 / amplitude;

            for (int x = 0; x < sizeX; x++) {
                for (int z = 0; z < sizeZ; z++) {
                    for (int y = 0; y < sizeY; y++) {
                        double sampleX = offsetX + x * scaleX + this.xCoord;
                        double sampleY = offsetY + y * scaleY + this.yCoord;
                        double sampleZ = offsetZ + z * scaleZ + this.zCoord;

                        // 简化的Perlin噪声计算
                        double noise = simplexNoise(sampleX, sampleY, sampleZ);
                        result[index++] += noise * amplitudeRecip;
                    }
                }
            }
        }

        private double simplexNoise(double x, double y, double z) {
            // 这里使用一个简化的3D噪声实现
            // 实际的MITE实现更复杂，但这应该足够产生变化
            int xi = (int) Math.floor(x) & 255;
            int yi = (int) Math.floor(y) & 255;
            int zi = (int) Math.floor(z) & 255;

            double xf = x - Math.floor(x);
            double yf = y - Math.floor(y);
            double zf = z - Math.floor(z);

            double u = fade(xf);
            double v = fade(yf);
            double w = fade(zf);

            int a = permutations[xi] + yi;
            int aa = permutations[a] + zi;
            int ab = permutations[a + 1] + zi;
            int b = permutations[xi + 1] + yi;
            int ba = permutations[b] + zi;
            int bb = permutations[b + 1] + zi;

            return lerp(w, lerp(v, lerp(u, grad(permutations[aa], xf, yf, zf),
                                    grad(permutations[ba], xf - 1, yf, zf)),
                            lerp(u, grad(permutations[ab], xf, yf - 1, zf),
                                    grad(permutations[bb], xf - 1, yf - 1, zf))),
                    lerp(v, lerp(u, grad(permutations[aa + 1], xf, yf, zf - 1),
                                    grad(permutations[ba + 1], xf - 1, yf, zf - 1)),
                            lerp(u, grad(permutations[ab + 1], xf, yf - 1, zf - 1),
                                    grad(permutations[bb + 1], xf - 1, yf - 1, zf - 1))));
        }

        private double fade(double t) {
            return t * t * t * (t * (t * 6 - 15) + 10);
        }

        private double lerp(double t, double a, double b) {
            return a + t * (b - a);
        }

        private double grad(int hash, double x, double y, double z) {
            int h = hash & 15;
            double u = h < 8 ? x : y;
            double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
            return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
        }
    }
}
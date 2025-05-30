package huix.infinity.util;

/**
 * Random Generator 用于兼容1.6.4-MITE用到的的随机数生成器
 * 基于Java 1.6的java.util.Random
 */
public class LegacyRandomGen {
    private long seed;

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

    public LegacyRandomGen(long seed) {
        setSeed(seed);
    }

    public synchronized void setSeed(long seed) {
        this.seed = (seed ^ multiplier) & mask;
    }

    protected int next(int bits) {
        long oldseed, nextseed;
        do {
            oldseed = seed;
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!compareAndSet(oldseed, nextseed));
        return (int)(nextseed >>> (48 - bits));
    }

    private boolean compareAndSet(long expect, long update) {
        if (seed == expect) {
            seed = update;
            return true;
        }
        return false;
    }

    public int nextInt() {
        return next(32);
    }

    public int nextInt(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");

        if ((n & -n) == n)  // i.e., n is a power of 2
            return (int)((n * (long)next(31)) >> 31);

        int bits, val;
        do {
            bits = next(31);
            val = bits % n;
        } while (bits - val + (n-1) < 0);
        return val;
    }

    public long nextLong() {
        return ((long)(next(32)) << 32) + next(32);
    }

    public boolean nextBoolean() {
        return next(1) != 0;
    }

    public float nextFloat() {
        return next(24) / ((float)(1 << 24));
    }

    public double nextDouble() {
        return (((long)(next(26)) << 27) + next(27))
                / (double)(1L << 53);
    }
}
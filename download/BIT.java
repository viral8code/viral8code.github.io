final class BIT {
    final int size;
    final long[] tree;

    public BIT ( int n ) {
        size = n;
        tree = new long[n + 1];
    }

    public long sum ( int i ) {
        long sum = 0;
        while ( i > 0 ) {
            sum += tree[i];
            i -= i & ( -i );
        }
        return sum;
    }

    public void add ( int i, long x ) {
        while ( i <= size ) {
            tree[i] += x;
            i += i & ( -i );
        }
    }
}

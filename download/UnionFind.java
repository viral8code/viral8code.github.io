final class UnionFind {
    private final int[] par, rank, size;
    private int count;

    public UnionFind ( int N ) {
        count = N;
        par = new int[N];
        rank = new int[N];
        size = new int[N];
        java.util.Arrays.fill( par, -1 );
        java.util.Arrays.fill( size, 1 );
    }

    public int root ( int x ) {
        if ( par[x] == -1 ) {
            return x;
        }
        else {
            return par[x] = root( par[x] );
        }
    }

    public boolean isSame ( int x, int y ) {
        return root( x ) == root( y );
    }

    public boolean unite ( int x, int y ) {
        int rx = root( x );
        int ry = root( y );
        if ( rx == ry ) {
            return false;
        }
        if ( rank[rx] < rank[ry] ) {
            int temp = rx;
            rx = ry;
            ry = temp;
        }
        par[ry] = rx;
        if ( rank[rx] == rank[ry] ) {
            ++rank[rx];
        }
        size[rx] += size[ry];
        --count;
        return true;
    }

    public int groupCount () {
        return count;
    }

    public int size ( int x ) {
        return size[root( x )];
    }
}

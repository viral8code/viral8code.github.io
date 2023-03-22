abstract class SegmentTree<E> {
    int N, size;
    E def;
    Object[] node;

    public SegmentTree ( int n, E def, boolean include ) {
        N = 2;
        size = 1;
        while ( N < n << 1 ) {
            N <<= 1;
            size <<= 1;
        }
        size -= include ? 1 : 0;
        node = new Object[N];
        this.def = def;
        java.util.Arrays.fill( node, this.def );
    }

    public SegmentTree ( int n, E def ) {
        this( n, def, false );
    }

    @SuppressWarnings( "unchecked" )
    public void update ( int n, E value ) {
        n += size;
        node[n] = value;
        n >>= 1;
        while ( n > 0 ) {
            node[n] = function( ( E )node[n << 1], ( E )node[( n << 1 ) + 1] );
            n >>= 1;
        }
    }

    @SuppressWarnings( "unchecked" )
    public E get ( int a ) {
        return ( E )node[a + size];
    }

    @SuppressWarnings( "unchecked" )
    public E answer () {
        return ( E )node[1];
    }

    @SuppressWarnings( "unchecked" )
    public E query ( int l, int r ) {
        l += size;
        r += size;
        E answer = def;
        while ( l > 0 && r > 0 && l <= r ) {
            if ( l % 2 == 1 ) {
                answer = function( ( E )node[l++], answer );
            }
            l >>= 1;
            if ( r % 2 == 0 ) {
                answer = function( answer, ( E )node[r--] );
            }
            r >>= 1;
        }
        return answer;
    }

    abstract public E function ( E a, E b );
}

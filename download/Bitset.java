final class Bitset implements Cloneable {
    private final long[] bit;
    private final int size, len;
    private final long MASK;

    public Bitset ( final int len ) {
        this.len = len;
        size = ( len + 63 ) >> 6;
        bit = new long[size];
        MASK = ( -1L ) >>> ( ( size << 6 ) - len );
    }

    public void set ( final int index ) {
        if ( index >= size << 6 ) {
            throw new ArrayIndexOutOfBoundsException( index + " is out of this bitset's size " + size );
        }
        bit[index >> 6] |= ( 1L << ( index & 0b111111 ) );
    }

    public void clear ( final int index ) {
        if ( index >= size << 6 ) {
            throw new ArrayIndexOutOfBoundsException( index + " is out of this bitset's size " + size );
        }
        long m = ~( 1L << ( index & 0b111111 ) );
        bit[index >> 6] &= m;
    }

    public boolean get ( final int index ) {
        if ( index >= size << 6 ) {
            throw new ArrayIndexOutOfBoundsException( index + " is out of this bitset's size " + size );
        }
        return ( bit[index >> 6] & ( 1L << ( index & 0b111111 ) ) ) != 0;
    }

    public void shiftLeft ( int num ) {
        if ( num >= size << 6 ) {
            java.util.Arrays.fill( bit, 0L );
            return;
        }
        final int n = num >> 6;
        num &= 0b111111;
        for ( int i = size - 1; i >= n; i-- ) {
            bit[i] = ( bit[i - n] << num ) | ( i != n && num != 0 ? bit[i - n - 1] >>> ( 64 - num ) : 0L );
        }
        for ( int i = 0; i < n; i++ ) {
            bit[i] = 0L;
        }
        bit[size - 1] &= MASK;
    }

    public void shiftRight ( int num ) {
        if ( num >= size << 6 ) {
            java.util.Arrays.fill( bit, 0L );
            return;
        }
        final int n = num >> 6;
        num &= 0b111111;
        for ( int i = 0; i < size - n; i++ ) {
            bit[i] = ( bit[i + n] >>> num ) | ( i + n + 1 != size && num != 0 ? bit[i + n + 1] << ( 64 - num ) : 0L );
        }
        for ( int i = size - 1; i >= size - n; i-- ) {
            bit[i] = 0L;
        }
    }

    public long[] longValues () {
        return bit;
    }

    public long longValue () {
        return bit[0];
    }

    public void and ( final Bitset b ) {
        final long[] bit2 = b.longValues();
        final int m = Math.min( bit2.length, size );
        for ( int i = 0; i < m; i++ ) {
            bit[i] &= bit2[i];
        }
        for ( int i = m; i < size; i++ ) {
            bit[i] = 0;
        }
        bit[size - 1] &= MASK;
    }

    public void or ( final Bitset b ) {
        final long[] bit2 = b.longValues();
        final int m = Math.min( bit2.length, size );
        for ( int i = 0; i < m; i++ ) {
            bit[i] |= bit2[i];
        }
        bit[size - 1] &= MASK;
    }

    public void xor ( final Bitset b ) {
        final long[] bit2 = b.longValues();
        final int m = Math.min( bit2.length, size );
        for ( int i = 0; i < m; i++ ) {
            bit[i] ^= bit2[i];
        }
        bit[size - 1] &= MASK;
    }

    public Bitset clone () throws CloneNotSupportedException {
        super.clone();
        final Bitset b = new Bitset( len );
        b.or( this );
        return b;
    }
}

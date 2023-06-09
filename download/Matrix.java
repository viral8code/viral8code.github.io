final class Matrix {
    private final long[][] matrix;

    public Matrix ( final int H, final int W, final long def ) {
        matrix = new long[H][W];
        if ( def != 0 ) {
            for ( long[] mat: matrix ) {
                java.util.Arrays.fill( mat, def );
            }
        }
    }

    public Matrix ( final int H, final int W ) {
        this( H, W, 0 );
    }

    public Matrix ( final java.awt.Dimension d, final long def ) {
        this( d.height, d.width, def );
    }

    public Matrix ( final long[][] mat ) {
        matrix = new long[mat.length][];
        for ( int i = 0; i < mat.length; i++ ) {
            matrix[i] = java.util.Arrays.copyOf( mat[i], mat[i].length );
        }
    }

    public long get ( final int i, final int j ) {
        return matrix[i][j];
    }

    public long set ( final int i, final int j, final long value ) {
        return matrix[i][j] = value;
    }

    public Matrix copy () {
        return new Matrix( matrix );
    }

    public java.awt.Dimension size () {
        return new java.awt.Dimension( matrix[0].length, matrix.length );
    }

    public Matrix add ( final Matrix m ) {
        if ( !size().equals( m.size() ) ) {
            throw new IllegalArgumentException( "matrix size is not same" );
        }
        final Matrix ans = new Matrix( size(), 0 );
        for ( int i = 0; i < matrix.length; i++ ) {
            for ( int j = 0; j < matrix[i].length; j++ ) {
                ans.set( i, j, matrix[i][j] + m.get( i, j ) );
            }
        }
        return ans;
    }

    public Matrix subtract ( final Matrix m ) {
        if ( !size().equals( m.size() ) ) {
            throw new IllegalArgumentException( "matrix size is not same" );
        }
        final Matrix ans = new Matrix( size(), 0 );
        for ( int i = 0; i < matrix.length; i++ ) {
            for ( int j = 0; j < matrix[i].length; j++ ) {
                ans.set( i, j, matrix[i][j] - m.get( i, j ) );
            }
        }
        return ans;
    }

    public Matrix multiply ( final Matrix m ) {
        if ( size().width != m.size().height ) {
            throw new IllegalArgumentException( "matrix length is not same" );
        }
        final Matrix ans = new Matrix( size().height, m.size().width );
        final java.awt.Dimension size = ans.size();
        final int len = size().width;
        for ( int i = 0; i < size.height; i++ ) {
            for ( int j = 0; j < size.width; j++ ) {
                long sum = 0;
                for ( int k = 0; k < len; k++ ) {
                    sum += matrix[i][k] * m.get( k, j );
                }
                ans.set( i, j, sum );
            }
        }
        return ans;
    }

    public Matrix modAdd ( final Matrix m, final long mod ) {
        if ( !size().equals( m.size() ) ) {
            throw new IllegalArgumentException( "matrix size is not same" );
        }
        final Matrix ans = new Matrix( size(), 0 );
        for ( int i = 0; i < matrix.length; i++ ) {
            for ( int j = 0; j < matrix[i].length; j++ ) {
                ans.set( i, j, remainder( matrix[i][j] + m.get( i, j ), mod ) );
            }
        }
        return ans;
    }

    public Matrix modSubtract ( final Matrix m, final long mod ) {
        if ( !size().equals( m.size() ) ) {
            throw new IllegalArgumentException( "matrix size is not same" );
        }
        final Matrix ans = new Matrix( size(), 0 );
        for ( int i = 0; i < matrix.length; i++ ) {
            for ( int j = 0; j < matrix[i].length; j++ ) {
                ans.set( i, j, remainder( matrix[i][j] - m.get( i, j ), mod ) );
            }
        }
        return ans;
    }

    public Matrix modMultiply ( final Matrix m, final long mod ) {
        if ( size().width != m.size().height ) {
            throw new IllegalArgumentException( "matrix length is not same" );
        }
        final Matrix ans = new Matrix( size().height, m.size().width );
        final java.awt.Dimension size = ans.size();
        final int len = size().width;
        for ( int i = 0; i < size.height; i++ ) {
            for ( int j = 0; j < size.width; j++ ) {
                long sum = 0;
                for ( int k = 0; k < len; k++ ) {
                    sum = remainder( sum + matrix[i][k] * m.get( k, j ), mod );
                }
                ans.set( i, j, sum );
            }
        }
        return ans;
    }

    public static Matrix pow ( final Matrix original, final Matrix pw, long exp ) {
        Matrix a = original.copy();
        Matrix b = pw.copy();
        while ( 0 < exp ) {
            if ( ( exp & 1 ) == 1 ) {
                a = b.multiply( a );
            }
            b = b.multiply( b );
            exp >>= 1;
        }
        return a;
    }

    public static Matrix modPow ( final Matrix original, final Matrix pw, long exp, final long mod ) {
        Matrix a = original.copy();
        Matrix b = pw.copy();
        while ( 0 < exp ) {
            if ( ( exp & 1 ) == 1 ) {
                a = b.modMultiply( a, mod );
            }
            b = b.modMultiply( b, mod );
            exp >>= 1;
        }
        return a;
    }

    private static long remainder ( long num, final long mod ) {
        num %= mod;
        if ( num < 0 ) {
            num += mod;
        }
        return num;
    }

    @Override
    public String toString () {
        final StringBuilder ans = new StringBuilder();
        ans.append( Arrays.toString( matrix[0] ) );
        for ( int i = 1; i < matrix.length; i++ ) {
            ans.append( "\n" );
            ans.append( Arrays.toString( matrix[i] ) );
        }
        return ans.toString();
    }
}

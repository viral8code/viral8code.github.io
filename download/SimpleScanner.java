final class SimpleScanner {
    final private int buff_size = 1 << 15;
    private final java.io.InputStream is;
    private final byte[] buff;
    private int point, length;

    public SimpleScanner ( java.io.InputStream is ) {
        this.is = is;
        buff = new byte[buff_size];
        point = length = 0;
    }

    private void reload () {
        do {
            try {
                length = is.read( buff, point = 0, buff_size );
            } catch ( java.io.IOException e ) {
                e.printStackTrace();
                System.exit( 1 );
            }
        } while ( length == -1 );
    }

    private byte read () {
        if ( point == length ) {
            reload();
        }
        return buff[point++];
    }

    public byte nextByte () {
        byte c = read();
        while ( c <= ' ' ) {
            c = read();
        }
        return c;
    }

    public int nextInt () {
        int ans = 0;
        byte c = read();
        while ( c <= ' ' ) {
            c = read();
        }
        boolean negate = c == '-';
        if ( c == '-' ) {
            c = read();
        }
        while ( '0' <= c && c <= '9' ) {
            ans = ans * 10 + c - '0';
            c = read();
        }
        return negate ? -ans : ans;
    }

    public long nextLong () {
        long ans = 0;
        byte c = read();
        while ( c <= ' ' ) {
            c = read();
        }
        boolean negate = c == '-';
        if ( c == '-' ) {
            c = read();
        }
        while ( '0' <= c && c <= '9' ) {
            ans = ans * 10 + c - '0';
            c = read();
        }
        return negate ? -ans : ans;
    }

    public char nextChar () {
        byte c = read();
        while ( c <= ' ' ) {
            c = read();
        }
        return ( char )c;
    }

    public String next () {
        StringBuilder ans = new StringBuilder();
        byte c = read();
        while ( c <= ' ' ) {
            c = read();
        }
        while ( c > ' ' ) {
            ans.append( ( char )c );
            c = read();
        }
        return ans.toString();
    }

    public byte[] nextByte ( int n ) {
        byte[] ans = new byte[n];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextByte();
        }
        return ans;
    }

    public int[] nextInt ( int n ) {
        int[] ans = new int[n];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextInt();
        }
        return ans;
    }

    public long[] nextLong ( int n ) {
        long[] ans = new long[n];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextLong();
        }
        return ans;
    }

    public String[] next ( int n ) {
        String[] ans = new String[n];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = next();
        }
        return ans;
    }

    public byte[][] nextByte ( int n, int m ) {
        byte[][] ans = new byte[n][];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextByte( m );
        }
        return ans;
    }

    public int[][] nextInt ( int n, int m ) {
        int[][] ans = new int[n][];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextInt( m );
        }
        return ans;
    }

    public long[][] nextLong ( int n, int m ) {
        long[][] ans = new long[n][];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextLong( m );
        }
        return ans;
    }

    public String[][] next ( int n, int m ) {
        String[][] ans = new String[n][];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = next( m );
        }
        return ans;
    }

    public char[] nextCharArray () {
        return next().toCharArray();
    }

    public char[][] nextCharArray ( int n ) {
        char[][] ans = new char[n][];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextCharArray();
        }
        return ans;
    }

    public int[][] nextGraph ( int N, int M ) {
        if ( M == 0 ) {
            return new int[N + 1][0];
        }
        int[][] ans = new int[N + 1][];
        int[] count = new int[N + 1];
        int[][] path = nextInt( M, 2 );
        for ( int[] temp: path ) {
            count[temp[0]]++;
            count[temp[1]]++;
        }
        for ( int i = 1; i <= N; i++ ) {
            ans[i] = new int[count[i]];
        }
        for ( int[] temp: path ) {
            ans[temp[0]][--count[temp[0]]] = temp[1];
            ans[temp[1]][--count[temp[1]]] = temp[0];
        }
        ans[0] = new int[0];
        return ans;
    }

    public java.awt.Point nextPoint () {
        return new java.awt.Point( nextInt(), nextInt() );
    }

    public java.awt.Point[] nextPoint ( int n ) {
        java.awt.Point[] ans = new java.awt.Point[n];
        for ( int i = 0; i < n; i++ ) {
            ans[i] = nextPoint();
        }
        return ans;
    }

    public void close () {
        try {
            is.close();
        } catch ( java.io.IOException e ) {
            e.printStackTrace();
            System.exit( 1 );
        }
    }
}

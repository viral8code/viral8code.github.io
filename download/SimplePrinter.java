final class SimplePrinter extends java.io.PrintWriter {
    public SimplePrinter ( java.io.PrintStream os ) {
        super( os );
    }

    public SimplePrinter ( java.io.PrintStream os, boolean bool ) {
        super( os, bool );
    }

    public void println ( int[] array, String str ) {
        print( array[0] );
        for ( int i = 1; i < array.length; i++ ) {
            print( str );
            print( array[i] );
        }
        println();
    }

    public void println ( int[] array, char c ) {
        print( array[0] );
        for ( int i = 1; i < array.length; i++ ) {
            print( c );
            print( array[i] );
        }
        println();
    }

    public void println ( int[][] arrays, String str ) {
        for ( int[] array: arrays ) {
            println( array, str );
        }
    }

    public void println ( int[][] arrays, char c ) {
        for ( int[] array: arrays ) {
            println( array, c );
        }
    }

    public void println ( long[] array, String str ) {
        print( array[0] );
        for ( int i = 1; i < array.length; i++ ) {
            print( str );
            print( array[i] );
        }
        println();
    }

    public void println ( long[] array, char c ) {
        print( array[0] );
        for ( int i = 1; i < array.length; i++ ) {
            print( c );
            print( array[i] );
        }
        println();
    }

    public void println ( long[][] arrays, String str ) {
        for ( long[] array: arrays ) {
            println( array, str );
        }
    }

    public void println ( long[][] arrays, char c ) {
        for ( long[] array: arrays ) {
            println( array, c );
        }
    }

    public void println ( char[] cs, String str ) {
        print( cs[0] );
        for ( int i = 1; i < cs.length; i++ ) {
            print( str );
            print( cs[i] );
        }
        println();
    }

    public void println ( char[] cs, char c ) {
        print( cs[0] );
        for ( int i = 1; i < cs.length; i++ ) {
            print( c );
            print( cs[i] );
        }
        println();
    }

    public void println ( char[][] cs ) {
        for ( char[] c: cs ) {
            println( c );
        }
    }

    public void println ( char[][] cs, String str ) {
        print( cs[0] );
        for ( int i = 1; i < cs.length; i++ ) {
            print( str );
            print( cs[i] );
        }
        println();
    }

    public void println ( char[][] cs, char c ) {
        print( cs[0] );
        for ( int i = 1; i < cs.length; i++ ) {
            print( c );
            print( cs[i] );
        }
        println();
    }

    public <E> void println ( E[] array, String str ) {
        print( array[0] );
        for ( int i = 1; i < array.length; i++ ) {
            print( str );
            print( array[i] );
        }
        println();
    }

    public <E> void println ( E[] array, char c ) {
        print( array[0] );
        for ( int i = 1; i < array.length; i++ ) {
            print( c );
            print( array[i] );
        }
        println();
    }

    public <E> void println ( E[][] arrays, String str ) {
        for ( E[] array: arrays ) {
            println( array, str );
        }
    }

    public <E> void println ( E[][] arrays, char c ) {
        for ( E[] array: arrays ) {
            println( array, c );
        }
    }
}

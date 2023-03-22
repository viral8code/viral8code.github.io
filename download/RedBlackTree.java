final class RedBlackTree {
    private int size;
    private Entry root;

    public RedBlackTree () {
        size = 0;
        root = null;
    }

    public boolean add ( final long key ) {
        Entry t = root;
        if ( t == null ) {
            addEntryToEmptySet( key );
            return true;
        }
        Entry parent;
        do {
            parent = t;
            if ( key < parent.getValue() ) {
                t = t.left;
            }
            else if ( key > parent.getValue() ) {
                t = t.right;
            }
            else {
                return false;
            }
        } while ( t != null );
        addEntry( key, parent, key < parent.getValue() );
        return true;
    }

    public long ceiling ( final long key ) {
        Entry p = root;
        while ( p != null ) {
            if ( key < p.getValue() ) {
                if ( p.left != null ) {
                    p = p.left;
                }
                else {
                    return p.getValue();
                }
            }
            else if ( key > p.getValue() ) {
                if ( p.right != null ) {
                    p = p.right;
                }
                else {
                    Entry parent = p.parent;
                    Entry ch = p;
                    while ( parent != null && ch == parent.right ) {
                        ch = parent;
                        parent = parent.parent;
                    }
                    return parent != null ? parent.getValue() : key - 1;
                }
            }
            else {
                return key;
            }
        }
        return key - 1;
    }

    public void clear () {
        size = 0;
        root = null;
    }

    public boolean contains ( final long key ) {
        return getEntry( key ) != null;
    }

    public long first () {
        Entry p = root;
        if ( p != null ) {
            while ( p.left != null ) {
                p = p.left;
            }
        }
        else {
            throw new NullPointerException();
        }
        return p.getValue();
    }

    public long floor ( final long key ) {
        Entry p = root;
        while ( p != null ) {
            if ( key > p.getValue() ) {
                if ( p.right != null ) {
                    p = p.right;
                }
                else {
                    return p.getValue();
                }
            }
            else if ( key < p.getValue() ) {
                if ( p.left != null ) {
                    p = p.left;
                }
                else {
                    Entry parent = p.parent;
                    Entry ch = p;
                    while ( parent != null && ch == parent.left ) {
                        ch = parent;
                        parent = parent.parent;
                    }
                    return parent != null ? parent.getValue() : key + 1;
                }
            }
            else {
                return p.getValue();
            }
        }
        return key + 1;
    }

    public long higher ( final long key ) {
        Entry p = root;
        while ( p != null ) {
            if ( key < p.getValue() ) {
                if ( p.left != null ) {
                    p = p.left;
                }
                else {
                    return p.getValue();
                }
            }
            else {
                if ( p.right != null ) {
                    p = p.right;
                }
                else {
                    Entry parent = p.parent;
                    Entry ch = p;
                    while ( parent != null && ch == parent.right ) {
                        ch = parent;
                        parent = parent.parent;
                    }
                    return parent != null ? parent.getValue() : key - 1;
                }
            }
        }
        return key - 1;
    }

    public boolean isEmpty () {
        return size == 0;
    }

    public long last () {
        Entry p = root;
        if ( p != null ) {
            while ( p.right != null ) {
                p = p.right;
            }
        }
        else {
            throw new NullPointerException();
        }
        return p.getValue();
    }

    public long lower ( final long key ) {
        Entry p = root;
        while ( p != null ) {
            if ( key > p.getValue() ) {
                if ( p.right != null ) {
                    p = p.right;
                }
                else {
                    return p.getValue();
                }
            }
            else {
                if ( p.left != null ) {
                    p = p.left;
                }
                else {
                    Entry parent = p.parent;
                    Entry ch = p;
                    while ( parent != null && ch == parent.left ) {
                        ch = parent;
                        parent = parent.parent;
                    }
                    return parent != null ? parent.getValue() : key + 1;
                }
            }
        }
        return key + 1;
    }

    public boolean remove ( final long key ) {
        final Entry p = getEntry( key );
        if ( p == null ) {
            return false;
        }
        deleteEntry( p );
        return true;
    }

    public int size () {
        return size;
    }

    public long[] toArray () {
        index = 0;
        return search( new long[size], root );
    }

    @Override
    public String toString () {
        return java.util.Arrays.toString( toArray() );
    }

    /*
        以下privateメソッド
    */

    private Entry getEntry ( final long key ) {
        Entry p = root;
        while ( p != null ) {
            if ( key < p.getValue() ) {
                p = p.left;
            }
            else if ( key > p.getValue() ) {
                p = p.right;
            }
            else {
                return p;
            }
        }
        return null;
    }

    private void addEntry ( final long key, final Entry parent, final boolean addToLeft ) {
        final Entry e = new Entry( key, parent );
        if ( addToLeft ) {
            parent.left = e;
        }
        else {
            parent.right = e;
        }
        fixAfterInsertion( e );
        size++;
    }

    private void addEntryToEmptySet ( final long key ) {
        root = new Entry( key, null );
        size = 1;
    }

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private static final class Entry {
        private long value;
        private Entry left;
        private Entry right;
        private Entry parent;
        private boolean color = BLACK;

        private Entry ( final long value, final Entry parent ) {
            this.value = value;
            this.parent = parent;
        }

        private long getValue () {
            return value;
        }

        public boolean equals ( final Object o ) {
            if ( !( o instanceof Entry ) ) {
                return false;
            }
            Entry e = ( Entry )o;
            return value == e.getValue();
        }

        public int hashCode () {
            return Long.hashCode( value );
        }

        public String toString () {
            return String.valueOf( value );
        }
    }

    private static Entry successor ( final Entry t ) {
        if ( t == null ) {
            return null;
        }
        else if ( t.right != null ) {
            Entry p = t.right;
            while ( p.left != null ) {
                p = p.left;
            }
            return p;
        }
        else {
            Entry p = t.parent;
            Entry ch = t;
            while ( p != null && ch == p.right ) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private static boolean colorOf ( final Entry p ) {
        return ( p == null ? BLACK : p.color );
    }

    private static Entry parentOf ( final Entry p ) {
        return ( p == null ? null : p.parent );
    }

    private static void setColor ( final Entry p, final boolean c ) {
        if ( p != null ) {
            p.color = c;
        }
    }

    private static Entry leftOf ( final Entry p ) {
        return ( p == null ) ? null : p.left;
    }

    private static Entry rightOf ( final Entry p ) {
        return ( p == null ) ? null : p.right;
    }

    private void rotateLeft ( final Entry p ) {
        if ( p != null ) {
            final Entry r = p.right;
            p.right = r.left;
            if ( r.left != null ) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if ( p.parent == null ) {
                root = r;
            }
            else if ( p.parent.left == p ) {
                p.parent.left = r;
            }
            else {
                p.parent.right = r;
            }
            r.left = p;
            p.parent = r;
        }
    }

    private void rotateRight ( final Entry p ) {
        if ( p != null ) {
            final Entry l = p.left;
            p.left = l.right;
            if ( l.right != null ) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if ( p.parent == null ) {
                root = l;
            }
            else if ( p.parent.right == p ) {
                p.parent.right = l;
            }
            else {
                p.parent.left = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    private void fixAfterInsertion ( Entry x ) {
        x.color = RED;
        while ( x != null && x != root && x.parent.color == RED ) {
            if ( parentOf( x ) == leftOf( parentOf( parentOf( x ) ) ) ) {
                final Entry y = rightOf( parentOf( parentOf( x ) ) );
                if ( colorOf( y ) == RED ) {
                    setColor( parentOf( x ), BLACK );
                    setColor( y, BLACK );
                    setColor( parentOf( parentOf( x ) ), RED );
                    x = parentOf( parentOf( x ) );
                }
                else {
                    if ( x == rightOf( parentOf( x ) ) ) {
                        x = parentOf( x );
                        rotateLeft( x );
                    }
                    setColor( parentOf( x ), BLACK );
                    setColor( parentOf( parentOf( x ) ), RED );
                    rotateRight( parentOf( parentOf( x ) ) );
                }
            }
            else {
                final Entry y = leftOf( parentOf( parentOf( x ) ) );
                if ( colorOf( y ) == RED ) {
                    setColor( parentOf( x ), BLACK );
                    setColor( y, BLACK );
                    setColor( parentOf( parentOf( x ) ), RED );
                    x = parentOf( parentOf( x ) );
                }
                else {
                    if ( x == leftOf( parentOf( x ) ) ) {
                        x = parentOf( x );
                        rotateRight( x );
                    }
                    setColor( parentOf( x ), BLACK );
                    setColor( parentOf( parentOf( x ) ), RED );
                    rotateLeft( parentOf( parentOf( x ) ) );
                }
            }
        }
        root.color = BLACK;
    }

    private void deleteEntry ( Entry p ) {
        size--;
        if ( p.left != null && p.right != null ) {
            final Entry s = successor( p );
            p.value = s.value;
            p = s;
        }
        final Entry replacement = ( p.left != null ? p.left : p.right );
        if ( replacement != null ) {
            replacement.parent = p.parent;
            if ( p.parent == null ) {
                root = replacement;
            }
            else if ( p == p.parent.left ) {
                p.parent.left = replacement;
            }
            else {
                p.parent.right = replacement;
            }

            p.left = p.right = p.parent = null;

            if ( p.color == BLACK ) {
                fixAfterDeletion( replacement );
            }
        }
        else if ( p.parent == null ) {
            root = null;
        }
        else {
            if ( p.color == BLACK ) {
                fixAfterDeletion( p );
            }

            if ( p.parent != null ) {
                if ( p == p.parent.left ) {
                    p.parent.left = null;
                }
                else if ( p == p.parent.right ) {
                    p.parent.right = null;
                }
                p.parent = null;
            }
        }
    }

    private void fixAfterDeletion ( Entry x ) {
        while ( x != root && colorOf( x ) == BLACK ) {
            if ( x == leftOf( parentOf( x ) ) ) {
                Entry sib = rightOf( parentOf( x ) );

                if ( colorOf( sib ) == RED ) {
                    setColor( sib, BLACK );
                    setColor( parentOf( x ), RED );
                    rotateLeft( parentOf( x ) );
                    sib = rightOf( parentOf( x ) );
                }

                if ( colorOf( leftOf( sib ) ) == BLACK &&
                     colorOf( rightOf( sib ) ) == BLACK ) {
                    setColor( sib, RED );
                    x = parentOf( x );
                }
                else {
                    if ( colorOf( rightOf( sib ) ) == BLACK ) {
                        setColor( leftOf( sib ), BLACK );
                        setColor( sib, RED );
                        rotateRight( sib );
                        sib = rightOf( parentOf( x ) );
                    }
                    setColor( sib, colorOf( parentOf( x ) ) );
                    setColor( parentOf( x ), BLACK );
                    setColor( rightOf( sib ), BLACK );
                    rotateLeft( parentOf( x ) );
                    x = root;
                }
            }
            else {
                Entry sib = leftOf( parentOf( x ) );

                if ( colorOf( sib ) == RED ) {
                    setColor( sib, BLACK );
                    setColor( parentOf( x ), RED );
                    rotateRight( parentOf( x ) );
                    sib = leftOf( parentOf( x ) );
                }

                if ( colorOf( rightOf( sib ) ) == BLACK &&
                     colorOf( leftOf( sib ) ) == BLACK ) {
                    setColor( sib, RED );
                    x = parentOf( x );
                }
                else {
                    if ( colorOf( leftOf( sib ) ) == BLACK ) {
                        setColor( rightOf( sib ), BLACK );
                        setColor( sib, RED );
                        rotateLeft( sib );
                        sib = leftOf( parentOf( x ) );
                    }
                    setColor( sib, colorOf( parentOf( x ) ) );
                    setColor( parentOf( x ), BLACK );
                    setColor( leftOf( sib ), BLACK );
                    rotateRight( parentOf( x ) );
                    x = root;
                }
            }
        }
        setColor( x, BLACK );
    }

    private int index;

    private long[] search( final long[] array, final Entry now ) {
        if( now == null )
            return array;
        search( array, now.left );
        array[ index++ ] = now.getValue();
        search( array, now.right );
        return array;
    }
}

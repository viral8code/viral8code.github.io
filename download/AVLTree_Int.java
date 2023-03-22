final class AVLTree_Int {
    private Node root;
    private int size, hash;

    public AVLTree_Int () {
        size = 0;
        root = null;
        hash = 0;
    }

    static final private class Node {
        int value;
        int height,size;
        Node left, right, parent;

        public Node ( final Node p, final int v ) {
            value = v;
            parent = p;
            height = 1;
            size = 1;
        }
    }

    public boolean add ( final int x ) {
        boolean bool = true;
        if ( root == null ) {
            root = new Node( null, x );
        }
        else {
            Node par;
            Node now = root;
            do {
                par = now;
                if ( x < now.value ) {
                    now = now.left;
                }
                else if ( x > now.value ) {
                    now = now.right;
                }
                else {
                    bool = false;
                    break;
                }
            } while ( now != null );
            if ( bool ) {
                if ( x < par.value ) {
                    par.left = new Node( par, x );
                }
                else {
                    par.right = new Node( par, x );
                }
                fix( par );
            }
        }
        if ( bool ) {
            ++size;
            hash ^= ( int )x;
        }
        return bool;
    }

    public int get ( int index ) {
        if ( root == null || size <= index ) {
            throw new NullPointerException();
        }
        Node now = root;
        while ( true ) {
            int ls = now.left != null ? now.left.size : 0;
            if ( index < ls ) {
                now = now.left;
            }
            else if ( ls < index ) {
                now = now.right;
                index -= ls + 1;
            }
            else {
                break;
            }
        }
        return now.value;
    }

    public boolean remove ( final int x ) {
        Node n = getNode( x );
        if ( n == null ) {
            return false;
        }
        --size;
        delete( n );
        return true;
    }

    private void delete ( final Node node ) {
        if ( node != null ) {
            if ( node.left == null && node.right == null ) {
                if ( node.parent != null ) {
                    if ( node.parent.left == node ) {
                        node.parent.left = null;
                    }
                    else {
                        node.parent.right = null;
                    }
                    fix( node.parent );
                }
                else {
                    root = null;
                }
                node.parent = null;
            }
            else {
                if ( node.left != null && node.right != null ) {
                    Node rep = getFirstNode( node.right );
                    node.value = rep.value;
                    delete( rep );
                }
                else {
                    Node rep = node.left != null ? node.left : node.right;
                    rep.parent = node.parent;
                    if ( node.parent != null ) {
                        if ( node.parent.left == node ) {
                            node.parent.left = rep;
                        }
                        else {
                            node.parent.right = rep;
                        }
                        fix( node.parent );
                    }
                    else {
                        root = rep;
                    }
                    node.parent = null;
                }
            }
        }
    }

    private Node getNode ( final int x ) {
        Node now = root;
        while ( now != null ) {
            if ( x < now.value ) {
                now = now.left;
            }
            else if ( x > now.value ) {
                now = now.right;
            }
            else {
                break;
            }
        }
        return now;
    }

    public int first () {
        if ( root == null ) {
            throw new NullPointerException();
        }
        return getFirstNode( root ).value;
    }

    private Node getFirstNode ( Node node ) {
        assert node != null;
        Node par = null;
        while ( node != null ) {
            par = node;
            node = par.left;
        }
        return par;
    }

    public int last () {
        if ( root == null ) {
            throw new NullPointerException();
        }
        return getLastNode( root ).value;
    }

    private Node getLastNode ( Node node ) {
        assert node != null;
        Node par = null;
        while ( node != null ) {
            par = node;
            node = par.right;
        }
        return par;
    }

    public boolean contains ( final int x ) {
        if ( root == null ) {
            return false;
        }
        return getNode( x ) != null;
    }

    public int pollFirst () {
        if ( root == null ) {
            throw new NullPointerException();
        }
        --size;
        final Node min = getFirstNode( root );
        delete( min );
        hash ^= ( int )min.value;
        return min.value;
    }

    public int pollLast () {
        if ( root == null ) {
            throw new NullPointerException();
        }
        --size;
        final Node max = getLastNode( root );
        delete( max );
        hash ^= ( int )max.value;
        return max.value;
    }

    public int ceiling ( final int x ) {
        if ( root == null ) {
            throw new NullPointerException();
        }
        return ceiling( root, x );
    }

    private int ceiling ( Node node, final int x ) {
        Node ans = new Node( null, x - 1 );
        while ( node != null ) {
            if ( x < node.value ) {
                node = node.right;
            }
            else if ( x > node.value ) {
                ans = node;
                node = node.left;
            }
            else {
                return x;
            }
        }
        return ans.value;
    }

    public int higher ( final int x ) {
        if ( root == null ) {
            throw new NullPointerException();
        }
        return ceiling( root, x );
    }

    private int higher ( Node node, final int x ) {
        Node ans = new Node( null, x - 1 );
        while ( node != null ) {
            if ( x <= node.value ) {
                node = node.right;
            }
            else {
                ans = node;
                node = node.left;
            }
        }
        return ans.value;
    }

    public int floor ( final int x ) {
        if ( root == null ) {
            throw new NullPointerException();
        }
        return floor( root, x );
    }

    private int floor ( Node node, final int x ) {
        Node ans = new Node( null, x + 1 );
        while ( node != null ) {
            if ( x > node.value ) {
                node = node.left;
            }
            else if ( x < node.value ) {
                ans = node;
                node = node.right;
            }
            else {
                return x;
            }
        }
        return ans.value;
    }

    public int lower ( final int x ) {
        if ( root == null ) {
            throw new NullPointerException();
        }
        return floor( root, x );
    }

    private int lower ( Node node, final int x ) {
        Node ans = new Node( null, x + 1 );
        while ( node != null ) {
            if ( x >= node.value ) {
                node = node.left;
            }
            else {
                ans = node;
                node = node.right;
            }
        }
        return ans.value;
    }

    public void clear () {
        root = null;
        size = 0;
        hash = 0;
    }

    public boolean isEmpty () {
        return size == 0;
    }

    public int size () {
        return size;
    }

    public int[] toArray () {
        final int[] list = new int[size];
        if ( root != null ) {
            int index = 0;
            java.util.ArrayDeque<Node> deq = new java.util.ArrayDeque<>();
            deq.add( root );
            while ( !deq.isEmpty() ) {
                Node now = deq.pollLast();
                if ( index == 0 ) {
                    if ( now.left != null ) {
                        deq.add( now );
                        deq.add( now.left );
                    }
                    else {
                        list[index++] = now.value;
                        if ( now.right != null ) {
                            deq.add( now.right );
                        }
                    }
                }
                else if ( now.left != null && list[index - 1] < now.left.value ) {
                    deq.add( now );
                    deq.add( now.left );
                }
                else {
                    list[index++] = now.value;
                    if ( now.right != null ) {
                        deq.add( now.right );
                    }
                }
            }
        }
        return list;
    }

    @Override
    public String toString () {
        final int[] list = toArray();
        return java.util.Arrays.toString( list );
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( o instanceof AVLTree_Int ) {
            final AVLTree_Int tree = ( AVLTree_Int )o;
            if ( size == tree.size() ) {
                return false;
            }
            final int[] array1 = toArray();
            final int[] array2 = tree.toArray();
            for ( int i = 0; i < size; i++ ) {
                if ( array1[i] != array2[i] ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode () {
        return hash;
    }

    /*
     * 以下、平衡用メソッド
     */

    private void fix ( Node node ) {
        while ( node != null ) {
            final int lh = node.left == null ? 0 : node.left.height;
            final int rh = node.right == null ? 0 : node.right.height;
            if ( lh - rh > 1 ) {
                assert node.left != null;
                if ( node.left.right != null && node.left.right.height == lh - 1 ) {
                    rotateL( node.left );
                }
                rotateR( node );
            }
            else if ( rh - lh > 1 ) {
                assert node.right != null;
                if ( node.right.left != null && node.right.left.height == rh - 1 ) {
                    rotateR( node.right );
                }
                rotateL( node );
            }
            else {
                setStates( node );
            }
            node = node.parent;
        }
    }

    private void rotateR ( Node node ) {
        final Node temp = node.left;
        node.left = temp.right;
        if ( node.left != null ) {
            node.left.parent = node;
        }
        temp.right = node;
        temp.parent = node.parent;
        if ( node.parent != null ) {
            if ( node.parent.left == node ) {
                node.parent.left = temp;
            }
            else {
                node.parent.right = temp;
            }
        }
        else {
            root = temp;
        }
        node.parent = temp;
        setStates( node );
    }

    private void rotateL ( Node node ) {
        final Node temp = node.right;
        node.right = temp.left;
        if ( node.right != null ) {
            node.right.parent = node;
        }
        temp.left = node;
        temp.parent = node.parent;
        if ( node.parent != null ) {
            if ( node.parent.left == node ) {
                node.parent.left = temp;
            }
            else {
                node.parent.right = temp;
            }
        }
        else {
            root = temp;
        }
        node.parent = temp;
        setStates( node );
    }

    private void setStates ( Node node ) {
        int lh = node.left != null ? node.left.height : 0;
        int rh = node.right != null ? node.right.height : 0;
        node.height = Math.max( lh, rh ) + 1;
        int ls = node.left != null ? node.left.size : 0;
        int rs = node.right != null ? node.right.size : 0;
        node.size = ls + rs + 1;
    }
}

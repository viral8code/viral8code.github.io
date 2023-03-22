class Pair<K extends Comparable<K>, V extends Comparable<V>>
        implements Comparable<Pair<K, V>> {
    private java.util.AbstractMap.SimpleEntry<K, V> map;

    public Pair ( K key, V value ) {
        map = new java.util.AbstractMap.SimpleEntry<>( key, value );
    }

    public K getKey () {
        return map.getKey();
    }

    public V getValue () {
        return map.getValue();
    }

    public K setKey ( K key ) {
        K oldKey = map.getKey();
        V value = map.getValue();
        map = new java.util.AbstractMap.SimpleEntry<>( key, value );
        return oldKey;
    }

    public V setValue ( V value ) {
        return map.setValue( value );
    }

    @Override
    public int compareTo ( Pair<K, V> pair ) {
        int com = getKey().compareTo( pair.getKey() );
        return com != 0 ? com : getValue().compareTo( pair.getValue() );
    }

    @Override
    public boolean equals ( Object o ) {
        if ( o instanceof Pair<?, ?> ) {
            Pair<?, ?> pair = ( Pair<?, ?> )o;
            return getKey().equals( pair.getKey() ) && getValue().equals( pair.getValue() );
        }
        return false;
    }

    @Override
    public String toString () {
        return getKey() + "=" + getValue();
    }

    @Override
    public int hashCode () {
        return ( getKey().hashCode() << 2 ) ^ ( getValue().hashCode() );
    }
}

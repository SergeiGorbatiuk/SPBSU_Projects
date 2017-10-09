interface Tree<K : Comparable<K>, V> {
    var nil : Node<K, V>?
    fun insert(key: K, value : V)
    fun searchByKey(key: K): V?
    fun deleteByKey(key: K)
}
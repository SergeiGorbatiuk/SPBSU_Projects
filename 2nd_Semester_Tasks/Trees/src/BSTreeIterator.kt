import java.util.*

class TreeIterator<K : Comparable<K>, V>(val node: Node<K, V>, tree: Tree<K, V>) : Iterator<Node<K, V>> {
    val tree = tree
    var queue = LinkedList<Node<K, V>>()
    init {
        queue.add(node)
    }


    override fun hasNext(): Boolean {
        return queue.isNotEmpty()
    }

    override fun next(): Node<K, V> {
        var tnode = queue.poll()
        if (tnode.leftChild != null && tnode.leftChild != tree.nil) queue.add(tnode.leftChild!!)
        if (tnode.rightChild != null && tnode.rightChild != tree.nil) queue.add(tnode.rightChild!!)
        return tnode
    }

}
open class BSTree<K : Comparable<K>, V>: Tree<K, V>, Iterable<Node<K, V>>{
    override var nil: Node<K, V>? =  null
    var root: Node<K, V>? = null

    override fun iterator(): Iterator<Node<K, V>> {
        return TreeIterator(root!!, this)
    }

    override fun insert(key: K, value : V) {
        var node = Node(key, value)
        if (root == null){
            root = node
            node.height = 0
            return
        }
        var tempnode :Node<K, V> = root!!
        while (true){
            when{
                node.key > tempnode.key ->{

                    if (tempnode.rightChild != null){
                        tempnode = tempnode.rightChild!!
                        node.height++
                    }
                    else{
                        tempnode.rightChild = node
                        node.parentNode = tempnode
                        node.height++
                        return
                    }
                }
                node.key < tempnode.key ->{
                    if (tempnode.leftChild != null){
                        tempnode = tempnode.leftChild!!
                        node.height++
                    }
                    else{
                        tempnode.leftChild = node
                        node.parentNode = tempnode
                        node.height++
                        return
                    }
                }
                else ->{
                    println("Key already exist")
                    return
                }
            }
        }
    }

    private fun search(key: K) : Node<K, V>?{
        if (root == null) return null
        var tempnode : Node<K, V> = root!!
        while (true){
            when{
                key < tempnode.key ->{
                    if (tempnode.leftChild != null) tempnode = tempnode.leftChild!!
                    else return null
                }
                key > tempnode.key ->{
                    if (tempnode.rightChild != null) tempnode = tempnode.rightChild!!
                    else return null
                }
                else -> {
                    return tempnode
                }
            }
        }
    }

    override fun searchByKey(key: K): V? {

        if (root == null) return null
        var node = search(key)
        if (node != null) return node.value
        return null
    }

    private fun getMaxSubnode(node: Node<K, V>) : Node<K, V>{
        var tempnode = node
        while (tempnode.rightChild != null) tempnode = tempnode.rightChild!!
        return tempnode
    }

    private fun getMinSubnode(node: Node<K, V>) : Node<K, V>{
        var tempnode = node
        while (tempnode.leftChild != null) tempnode = tempnode.leftChild!!
        return tempnode
    }

    fun deleteNode(node: Node<K, V>){
        if (node.leftChild != null){
            var tleft = getMaxSubnode(node.leftChild!!)
            tleft.parentNode!!.rightChild = null
            tleft.parentNode = node.parentNode
            if (node.rightChild != null){
                tleft.rightChild = node.rightChild
                node.rightChild!!.parentNode = tleft
            }
            if (node == root) root = tleft
            return
        }

        if (node.rightChild != null){
            var tright = getMinSubnode(node.rightChild!!)
            tright.parentNode!!.rightChild = null
            tright.parentNode = node.parentNode
            //there is no left child in this case
            if (node == root) root = tright
            return
        }

        node.parentNode = null //root check
        if (node == root)
        {
            root = null
        }
        return
    }

    override fun deleteByKey(key: K) {
        if (root == null) return
        var tempnode : Node<K, V> = root!!
        while (true){
            when{
                key < tempnode.key ->{
                    if (tempnode.leftChild != null) tempnode = tempnode.leftChild!!
                    else return
                }
                key > tempnode.key ->{
                    if (tempnode.rightChild != null) tempnode = tempnode.rightChild!!
                    else return
                }
                else -> {
                    deleteNode(tempnode)
                }
            }
        }
    }

    fun treeHeight() : Int{
        var node : Node<K, V>
        var max = 0
        var i : Int
        for (node in this){
            if (node.leftChild == null && node.rightChild == null){
                i = 0
                var tempnode = node
                while (tempnode.parentNode != nil){
                    tempnode = tempnode.parentNode!!
                    i++
                }
                if (i > max ) max = i
            }
        }
        return max
    }
}
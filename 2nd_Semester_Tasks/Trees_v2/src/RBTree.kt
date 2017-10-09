open class RBTree<K : Comparable<K>, V >:  Tree<K, V>, Iterable<Node<K, V>>{
    var root: Node<K, V>? = null
    override var nil : Node<K, V>? = null

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

    override fun iterator(): Iterator<Node<K, V>> {
        return TreeIterator<K, V>(root!!, this)
    }


    override fun insert(key: K, value :V) {
        var node =  Node(key, value)

        if (root == null) {
            nil = Node(node.key, null)
            root = node
            root!!.parentNode = nil
            node.leftChild = nil
            node.rightChild = nil
            return
        }
        var tempnode :Node<K, V> = root!!
        while (true){
            when{
                node.key > tempnode.key ->{
                    if (tempnode.rightChild != null && tempnode.rightChild != nil) tempnode = tempnode.rightChild!!
                    else{
                        tempnode.rightChild = node
                        node.parentNode = tempnode
                        node.color = Color.red
                        node.rightChild = nil
                        node.leftChild = nil
                        insertFixup(node)
                        return
                    }
                }
                node.key < tempnode.key ->{
                    if (tempnode.leftChild != null && tempnode.leftChild != nil) tempnode = tempnode.leftChild!!
                    else{
                        tempnode.leftChild = node
                        node.parentNode = tempnode
                        node.color = Color.red
                        node.rightChild = nil
                        node.leftChild = nil
                        insertFixup(node)
                        return
                    }
                }
                else ->{
                    println("Key already exists!")
                    return
                }
            }
        }
    }

    private fun insertFixup(insertedNode: Node<K, V>){
        var node = insertedNode
        while (node != root && node.parentNode!!.color == Color.red){
            if (node.parentNode == node.parentNode!!.parentNode!!.leftChild) {
                var uncle: Node<K, V>? = node.parentNode!!.parentNode!!.rightChild
                if (uncle != null && uncle.color == Color.red) {
                    node.parentNode!!.color = Color.black
                    uncle.color = Color.black
                    node.parentNode!!.parentNode!!.color = Color.red
                    node = node.parentNode!!.parentNode!!
                } else {
                    if (node == node.parentNode!!.rightChild) {
                        node = node.parentNode!!
                        node.rotateLeft(this)
                    }
                    node.parentNode!!.color = Color.black
                    node.parentNode!!.parentNode!!.color = Color.red
                    node.parentNode!!.parentNode!!.rotateRight(this)
                }
            } else {
                var uncle: Node<K, V>? = node.parentNode!!.parentNode!!.leftChild
                if (uncle != null && uncle.color == Color.red) {
                    node.parentNode!!.color = Color.black
                    uncle.color = Color.black
                    node.parentNode!!.parentNode!!.color = Color.red
                    node = node.parentNode!!.parentNode!!
                } else {
                    if (node == node.parentNode!!.leftChild) {
                        node = node.parentNode!!
                        node.rotateRight(this)
                    }
                    node.parentNode!!.color = Color.black
                    node.parentNode!!.parentNode!!.color = Color.red
                    node.parentNode!!.parentNode!!.rotateLeft(this)
                }
            }
        }
        root!!.color = Color.black
    }

    fun treeHeight() : Int{
        var node : Node<K, V>
        var max = 0
        var i : Int
        for (node in this){
            if (node.leftChild == nil && node.rightChild == nil){
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

    private fun transplant(node1 : Node<K, V>, node2 : Node<K, V>){
        if (node1.parentNode == nil){
            node2.parentNode = nil
            root = node2
        }
        else{
            if (node1 == node1.parentNode!!.leftChild) node1.parentNode!!.leftChild = node2
            else node1.parentNode!!.rightChild = node2
            node2.parentNode = node1.parentNode
        }
    }

    override fun deleteByKey(key: K) {
        if (root == null) {
            println("Tree is empty!")
            return
        }

        var dnode = search(key)
        if (dnode == null){
            println("No node with such key")
            return
        }
            /*node actually exists*/
        var pointnode = dnode
        var x : Node<K, V>
        var original_color = pointnode.color
        if (dnode.leftChild == nil){
            x = dnode.rightChild!!
            transplant(dnode, dnode.rightChild!!)
        }
        else{
            if (dnode.rightChild == nil){
                x = dnode.leftChild!!
                transplant(dnode, dnode.leftChild!!)
            }
            else{
                pointnode = getMinSubnode(dnode.rightChild!!)
                original_color = pointnode.color
                x = pointnode.rightChild!!
                if (pointnode.parentNode!! == dnode){
                    x.parentNode = pointnode
                }
                else{
                    transplant(pointnode, pointnode.rightChild!!)
                    pointnode.rightChild = dnode.rightChild
                    pointnode.rightChild!!.parentNode = pointnode
                }
                transplant(dnode, pointnode)
                pointnode.leftChild = dnode.leftChild
                pointnode.leftChild!!.parentNode = pointnode
                pointnode.color = dnode.color
            }
        }
        if (original_color == Color.black) deleteFixup(x)
    }

    private fun deleteFixup(Fixingnode: Node<K, V>){
        var w : Node<K, V>
        var node = Fixingnode
        while (node != root && node.color == Color.black){
            if (node == node.parentNode!!.leftChild){
                w = node.parentNode!!.rightChild!!
                if (w.color ==Color.red){
                    w.color = Color.black
                    node.parentNode!!.color = Color.red
                    node.parentNode!!.rotateLeft(this)
                    w = node.parentNode!!.rightChild!!
                }
                if (w.leftChild!!.color == Color.black && w.rightChild!!.color ==Color.black){
                    w.color = Color.red
                    node = node.parentNode!!
                }
                else{
                    if (w.rightChild!!.color ==Color.black){
                        w.leftChild!!.color = Color.black
                        w.color = Color.red
                        w.rotateRight(this)
                        w = node.parentNode!!.rightChild!!
                    }
                    w.color = node.parentNode!!.color
                    node.parentNode!!.color = Color.black
                    w.rightChild!!.color = Color.black
                    node.parentNode!!.rotateLeft(this)
                    node = root!!
                }
            }

            else{
                w = node.parentNode!!.leftChild!!
                if (w.color == Color.red){
                    w.color ==Color.black
                    node.parentNode!!.color = Color.red
                    node.parentNode!!.rotateRight(this)
                    w = node.parentNode!!.leftChild!!
                }
                if (w.rightChild!!.color == Color.black && w.leftChild!!.color ==Color.black){
                    w.color = Color.red
                    node = node.parentNode!!
                }
                else{
                    if (w.leftChild!!.color == Color.black){
                        w.rightChild!!.color == Color.black
                        w.color = Color.red
                        w.rotateLeft(this)
                        w = node.parentNode!!.leftChild!!
                    }
                    w.color = node.parentNode!!.color
                    node.parentNode!!.color = Color.black
                    w.rightChild!!.color = Color.black
                    node.parentNode!!.rotateLeft(this)
                    node = root!!
                }
            }
        }
        node.color = Color.black
    }

    private fun getMinSubnode(node: Node<K, V>) : Node<K, V>{
        var tempnode = node
        while (tempnode.leftChild != nil) tempnode = tempnode.leftChild!!
        return tempnode
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RBTree<*, *>) return false


        var iterator = this.iterator()

        for (node in other){
            if (!iterator.hasNext()) return false
            if (node != iterator.next()) return false
        }
        if (iterator.hasNext()) return false
        return true
    }

}
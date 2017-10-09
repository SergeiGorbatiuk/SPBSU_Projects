enum class Color(var color : Boolean){
    red(true), black(false);
}

class Node<K : Comparable<K>, V>(val key: K, val value: V?) /*: Comparable<Node<K, V>>*/{

    var parentNode: Node<K, V>? = null
    var rightChild: Node<K, V>? = null
    var leftChild: Node<K, V>? = null
    var color = Color.black
    var height = 0




//    override fun compareTo(other: Node<K, V>): Int {
//        if (this.key < other.key) return -1
//        if (this.key > other.key) return 1
//        return 0
//    }


    override fun toString(): String {
        return value.toString()
    }

    internal fun rotateLeft(tree: RBTree<K, V>){
        var newtop = this.rightChild

        this.rightChild = newtop!!.leftChild
        if (newtop.leftChild != null){
            newtop.leftChild!!.parentNode = this
        }
        newtop.parentNode = this.parentNode
        if (this == tree.root) tree.root = newtop /*in-tree check*/
        else {
            if (this == this.parentNode!!.leftChild) this.parentNode!!.leftChild = newtop
            else
                if (this == this.parentNode!!.rightChild) this.parentNode!!.rightChild = newtop
        }
        newtop.leftChild = this
        this.parentNode = newtop
    }

    internal fun rotateRight(tree: RBTree<K, V>){
        var newtop = this.leftChild

        this.leftChild = newtop!!.rightChild
        if (newtop.rightChild != null){
            newtop.rightChild!!.parentNode = this
        }
        newtop.parentNode = this.parentNode
        if(this == tree.root) tree.root = newtop
        else{
            if (this == this.parentNode!!.leftChild) this.parentNode!!.leftChild = newtop
            else
                if (this == this.parentNode!!.rightChild) this.parentNode!!.rightChild = newtop
        }
        newtop.rightChild = this
        this.parentNode = newtop

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node<*, *>

        if (key != other.key) return false
        if (value != other.value) return false
        if (color != other.color) return false

        return true
    }
}


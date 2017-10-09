import java.util.*

/**
 * Created by sergey on 13.04.17.
 */
class BTree<K: Comparable<K>, V>(val branching : Int) :Tree<K, V>, Iterable<BNode<K,V>>{
    override var nil: Node<K, V>? = null
    var root: BNode<K, V>? = null

    override fun iterator(): Iterator<BNode<K, V>> {
        return (object : Iterator<BNode<K, V>>{
            var queue = LinkedList<BNode<K, V>>()

            init {
                if (root != null){
                    queue.add(root!!)
                }

            }

            override fun hasNext(): Boolean {
                return  queue.isNotEmpty()
            }

            override fun next(): BNode<K, V> {
                var tnode = queue.poll()
                for (i in 0..tnode.children.size-1){
                    queue.add(tnode.children[i])
                }
                return tnode
            }

        })
    }

    private fun search(key: K, node: BNode<K, V>) : BNode<K, V>?{
        var i = 0
        var size = node.keys_n_values.size
        while (i<size && key>node.keys_n_values[i].first){
            i++
        }
        if (i < size && key == node.keys_n_values[i].first) return node
        if (node.isLeaf) return null
        else{
            return search(key, node.children[i])
        }
    }

    override fun searchByKey(key: K): V?{
        if (root == null){
            println("Tree is empty!")
            return null
        }
        var tnode = search(key, root!!)
        if (tnode != null){
            for (i in 0..tnode.keys_n_values.size-1){
                if (key == tnode.keys_n_values[i].first){
                    return tnode.keys_n_values[i].second
                }
            }
        }
        return null
    }

    override fun insert(key: K, value : V){
        var pair = Pair(key, value)
        if (root == null){
            var node = BNode<K, V>()
            node.keys_n_values.add(pair)
            root = node
            return
        }
        var r = root!!
        if (root!!.keys_n_values.size == 2*branching-1){
            var s = BNode<K, V>()
            root = s
            s.isLeaf = false
            s.children.add(r)
            r.parentNode = s
            splitChild(s, 0)
            nonFullInsert(s, pair)
        }
        else{
            nonFullInsert(root!!, pair)
        }
    }

    private fun nonFullInsert(node : BNode<K, V>, pair: Pair<K, V>){
        var i = 0
        var size = node.keys_n_values.size
        if (node.isLeaf){
            while (i<size && pair.first>node.keys_n_values[i].first){
                i++
            }
            node.keys_n_values.add(i, pair)
        }
        else{
            while (i<size && pair.first > node.keys_n_values[i].first){
                i++
            }
            if (node.children[i].keys_n_values.size == 2*branching-1){
                splitChild(node, i)
                if (pair.first > node.keys_n_values[i].first){
                    i++
                }
            }
            nonFullInsert(node.children[i], pair)
        }
    }

    private fun splitChild(node : BNode<K, V>, index: Int){
        var x = node
        var y = x.children[index]
        var z = BNode<K, V>()
        z.isLeaf = y.isLeaf
        z.parentNode = y.parentNode
        for (j in 0..branching-2){
            z.keys_n_values.add(y.keys_n_values[branching])
            y.keys_n_values.removeAt(branching)
        }
        if (!y.isLeaf){
            for (j in 0..branching-1){
                y.children[branching].parentNode = z
                z.children.add(y.children[branching])
                y.children.removeAt(branching)
            }
        }

        x.keys_n_values.add(index, y.keys_n_values[branching-1])
        x.children.add(index+1, z)
        y.keys_n_values.removeAt(branching-1)
    }

    private fun pullFromNeighbour(parent : BNode<K, V>, indexTo: Int, indexFrom: Int){
        if (parent.children[indexTo].keys_n_values[0].first < parent.children[indexFrom].keys_n_values[0].first){ //pulling from right neighbour

            parent.children[indexTo].keys_n_values.add(parent.keys_n_values[indexTo])
            parent.keys_n_values[indexTo] = parent.children[indexFrom].keys_n_values[0]
            if (!parent.children[indexFrom].isLeaf){
                parent.children[indexFrom].children[0].parentNode = parent.children[indexTo]
                parent.children[indexTo].children.add(parent.children[indexFrom].children[0])
                parent.children[indexFrom].children.removeAt(0)
            }
            parent.children[indexFrom].keys_n_values.removeAt(0)
        }
        else{ //pulling from left neigbour
            parent.children[indexTo].keys_n_values.add(0, parent.keys_n_values[indexFrom])
            parent.keys_n_values[indexFrom] = parent.children[indexFrom].keys_n_values.last()
            if (!parent.children[indexFrom].isLeaf){
                parent.children[indexFrom].children.last().parentNode = parent.children[indexTo]
                parent.children[indexTo].children.add(0, parent.children[indexFrom].children.last())
                parent.children[indexFrom].children.removeAt(parent.children[indexFrom].children.size-1)
            }
            parent.children[indexFrom].keys_n_values.removeAt(parent.children[indexFrom].keys_n_values.size-1)
        }
    }

    private fun mergeNeigbours(parent: BNode<K, V>, buddy2Index : Int, buddy1Index: Int ) : BNode<K, V>{ //note that indexes are REVERSED. Returns new big node
        var left = buddy1Index
        var right  = buddy2Index

        if (buddy2Index == 0){
            left = 0
            right = 1
        }
        parent.children[left].keys_n_values.add(parent.keys_n_values[left])
        deletion(parent , parent.keys_n_values[left].first)

        for (i in 0..parent.children[right].keys_n_values.size-1){
            parent.children[left].keys_n_values.add(parent.children[right].keys_n_values[i])
        }

        for (i in 0..parent.children[right].children.size-1){
            parent.children[right].children[i].parentNode = parent.children[left]
            parent.children[left].children.add(parent.children[right].children[i])
        }
        parent.children.removeAt(right)

        return parent.children[left]
    }

    private fun deleteFromAL(node: BNode<K, V>, key: K){
//        var i = 0
//        while (i < node.keys_n_values.size && key != node.keys_n_values[i]){
//            i++
//        }
//        if (key == node.keys_n_values[i]){
//            node.keys_n_values.removeAt(i)
//        }
//        else println("No such key! ("+ key.toString() + ")")
//        return
        for (i in 0..node.keys_n_values.size-1){
            if (key == node.keys_n_values[i].first){
                node.keys_n_values.removeAt(i)
                return
            }
        }

    }

    private fun deletion(deletingNode: BNode<K, V>, key: K){
        var node = deletingNode
        if (root == null || root!!.keys_n_values.size == 0) {
            println("Tree is empty!")
            return
        }

        if (node == root){
            if (root!!.children.size > 0 && root!!.keys_n_values.size < 2 /*???*/){
                root = root!!.children.first()
            }
            deleteFromAL(node, key)
            return
        }

        if (node.keys_n_values.size > branching-1){ //possible to delete without consequenses
            deleteFromAL(node, key)
        }
        else{ //need to pull from neighbour
            var index = node.parentNode!!.children.indexOf(node)

            try {
                if (node.parentNode!!.children[index-1].keys_n_values.size > branching-1){
                    pullFromNeighbour(node.parentNode!!, index, index-1)
                    deleteFromAL(node, key)
                    return
                }
                else{
                }
            }
            catch (e : IndexOutOfBoundsException){
            }

            try {
                if (node.parentNode!!.children[index+1].keys_n_values.size > branching-1){
                    pullFromNeighbour(node.parentNode!!, index, index+1)
                    deleteFromAL(node, key)
                    return
                }
                else{
                }
            }
            catch (e : IndexOutOfBoundsException){
            }

            /*impossible to pull from neighbour*/
            if (index == 0){
                node = mergeNeigbours(node.parentNode!!, 0, 1)
            }
            else{
                node = mergeNeigbours(node.parentNode!!, index, index-1)
            }

            deleteFromAL(node, key)
            return
        }
    }

    private fun swapDeletingElement(node: BNode<K, V> , key: K) : BNode<K, V>{
        var pair : Pair<K, V>? = null
        for (i in 0 .. node.keys_n_values.size-1){
            if (node.keys_n_values[i].first == key){
                pair = node.keys_n_values[i]
            }
        }
        var index = node.keys_n_values.indexOf(pair)
        var temp = node.keys_n_values[index]
        node.keys_n_values[index] = node.children[index].keys_n_values[node.children[index].keys_n_values.size-1]
        node.children[index].keys_n_values[node.children[index].keys_n_values.size-1] = temp
        return node.children[index]
    }

    override fun deleteByKey(key: K){
        var dnode = search(key, root!!)
        if (dnode != null){
            if (dnode.isLeaf){
                deletion(dnode, key)
            }
            else{
                while (!dnode!!.isLeaf){
                    dnode = swapDeletingElement(dnode, key)
                }
                deletion(dnode, key)
            }
        }
    }

}
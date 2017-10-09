import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Created by sergey on 11.05.17.
 */
internal class RBTreeTest{
    var NullTree = RBTree<Int, Int>()

    @Test
    fun insertNullTree(){
        var tree = RBTree<Int, Int>()
        NullTree.root = Node(1,1)
        tree.insert(1, 1)
        assertEquals(NullTree, tree)
        NullTree.root = null
    }

    @Test
    fun searchNullTree(){
        assertEquals(null, NullTree.searchByKey(1))
    }

    @Test
    fun searchNormal(){
        var tree = RBTree<Int, Int>()
        for (i in 1..5){
            tree.insert(i, i)
        }
        assertEquals(3, tree.searchByKey(3))
        assertEquals(null, tree.searchByKey(0))
    }


    @Test
    fun iterationTest(){
        val node1 = Node<Int, Int>(1,1)
        val node2 = Node<Int, Int>(2,2)
        val node3 = Node<Int, Int>(3,3)
        val node4 = Node<Int, Int>(4,4)
        val node5 = Node<Int, Int>(5,5)

        var Itree = RBTree<Int, Int>()

        Itree.root = node2
        node2.color = Color.black

        node1.parentNode = node2
        node2.leftChild = node1

        node4.parentNode = node2
        node2.rightChild = node4

        node3.parentNode = node4
        node4.leftChild = node3
        node3.color = Color.red

        node5.parentNode = node4
        node4.rightChild = node5
        node5.color = Color.red

        var iter = Itree.iterator()
        val expNode1 = Node<Int, Int>(1,1)
        val expNode2 = Node<Int, Int>(2,2)
        val expNode3 = Node<Int, Int>(3,3)
        expNode3.color = Color.red
        val expNode4 = Node<Int, Int>(4,4)
        val expNode5 = Node<Int, Int>(5,5)
        expNode5.color = Color.red
        assertEquals(expNode2, iter.next())
        assertEquals(expNode1, iter.next())
        assertEquals(expNode4, iter.next())
        assertEquals(expNode3, iter.next())
        assertEquals(expNode5, iter.next())
    }

    @Test
    fun insertNormal() {

        val node1 = Node<Int, Int>(1,1)
        val node2 = Node<Int, Int>(2,2)
        val node3 = Node<Int, Int>(3,3)
        val node4 = Node<Int, Int>(4,4)
        val node5 = Node<Int, Int>(5,5)
        val node0 = Node<Int, Int>(0,0)

        var Itree = RBTree<Int, Int>()

        Itree.root = node2
        node2.color = Color.black

        node1.parentNode = node2
        node2.leftChild = node1

        node4.parentNode = node2
        node2.rightChild = node4

        node3.parentNode = node4
        node4.leftChild = node3
        node3.color = Color.red

        node5.parentNode = node4
        node4.rightChild = node5
        node5.color = Color.red


        var tree = RBTree<Int, Int>()
        for (i in 1..5){
            tree.insert(i, i)
        }
        assertEquals(Itree, tree)

        node0.parentNode = node1
        node1.leftChild = node0
        node0.color = Color.red

        tree.insert(0,0)
        assertEquals(Itree, tree)
    }

    @Test
    fun deletionCase1(){
        val node1 = Node<Int, Int>(1,1)
        val node2 = Node<Int, Int>(2,2)
        val node3 = Node<Int, Int>(3,3)
        val node4 = Node<Int, Int>(4,4)
        //val node5 = Node<Int, Int>(5,5)

        var Itree = RBTree<Int, Int>()

        Itree.root = node2
        node2.color = Color.black

        node1.parentNode = node2
        node2.leftChild = node1

        node4.parentNode = node2
        node2.rightChild = node4

        node3.parentNode = node4
        node4.leftChild = node3
        node3.color = Color.red

        var tree = RBTree<Int,Int>()
        for (i in 1..5){
            tree.insert(i,i)
        }
        tree.deleteByKey(5)

        assertEquals(Itree, tree)
    }

    @Test
    fun deletionCase2(){
        val node1 = Node<Int, Int>(1,1)
        val node2 = Node<Int, Int>(2,2)
        val node3 = Node<Int, Int>(3,3)
        //val node4 = Node<Int, Int>(4,4)
        val node5 = Node<Int, Int>(5,5)

        var Itree = RBTree<Int, Int>()

        Itree.root = node2
        node2.color = Color.black

        node1.parentNode = node2
        node2.leftChild = node1

        node5.parentNode = node2
        node2.rightChild = node5

        node3.parentNode = node5
        node5.leftChild = node3
        node3.color = Color.red

        var tree = RBTree<Int,Int>()
        for (i in 1..5){
            tree.insert(i,i)
        }
        tree.deleteByKey(4)

        assertEquals(Itree, tree)
    }

    @Test
    fun deletionCase3(){
        //val node1 = Node<Int, Int>(1,1)
        val node2 = Node<Int, Int>(2,2)
        val node3 = Node<Int, Int>(3,3)
        val node4 = Node<Int, Int>(4,4)
        val node5 = Node<Int, Int>(5,5)

        var Itree = RBTree<Int, Int>()

        Itree.root = node4

        node5.parentNode = node4
        node4.rightChild = node5

        node2.parentNode = node4
        node4.leftChild = node2

        node3.parentNode = node2
        node2.rightChild = node3
        node3.color = Color.red

        var tree = RBTree<Int,Int>()
        for (i in 1..5){
            tree.insert(i,i)
        }
        tree.deleteByKey(1)

        assertEquals(Itree, tree)
    }

    @Test
    fun deletionCase4_root(){
        val node1 = Node<Int, Int>(1,1)
        //val node2 = Node<Int, Int>(2,2)
        val node3 = Node<Int, Int>(3,3)
        val node4 = Node<Int, Int>(4,4)
        val node5 = Node<Int, Int>(5,5)

        var Itree = RBTree<Int, Int>()

        Itree.root = node3

        node1.parentNode = node3
        node3.leftChild = node1

        node4.parentNode = node3
        node3.rightChild = node4

        node5.parentNode = node4
        node4.rightChild = node5
        node5.color = Color.red


        var tree = RBTree<Int,Int>()
        for (i in 1..5){
            tree.insert(i,i)
        }
        tree.deleteByKey(2)

        assertEquals(Itree, tree)
    }

    @Test
    fun stressTest(){
        for (i in 1..20000000){
            NullTree.insert(i, i)
            if (i%100000 == 0) println(i)
        }
    }
}
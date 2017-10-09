import java.util.*

fun main(args: Array<String>){
    println("Input tree type: \n" +
            "1 - for Binary Search tree \n" +
            "2 - for Red-Black tree \n" +
            "3 - for B tree")
    var sc : Scanner = Scanner(System.`in`)
    var ver = sc.nextInt()
    when (ver){
        1 -> {
            var bstree = BSTree<Int, Int>()
            println("Input nodes quantity")
            var quant = sc.nextInt()
            for (i in 1..quant){
                bstree.insert(sc.nextInt(), sc.nextInt())
            }
        }
        2 -> {
            var rbtree = RBTree<Int, Int>()
            println("Input nodes quantity")
            var quant = sc.nextInt()
            for (i in 1..quant){
                rbtree.insert(sc.nextInt(), sc.nextInt())
            }
        }
        3 -> {
            var btree = BTree<Int, Int>(100)
            println("Input nodes quantity")
            var quant = sc.nextInt()
            for (i in 1..quant){
                btree.insert(sc.nextInt(), sc.nextInt())
            }
        }
    }

}

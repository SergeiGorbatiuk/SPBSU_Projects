/**
 * Created by sergey on 14.04.17.
 */
class BtPrinter<K :Comparable<K>, V> {
    fun printTree(tree : BTree<K, V>){
        var printuntil = 1
        var nextline = 0
        for (bnode in tree){
            if (printuntil == 0){
                printuntil = nextline
                nextline = 0
                println()
            }
            print(bnode.toString() + " ")
            nextline += bnode.children.size
            printuntil --
        }
    }
}
import java.util.*

class Printer<K : Comparable<K>, V> {
    fun printRBTree(tree: RBTree<K, V>, height : Int){

        var coord = LinkedList<Int>()
        coord.add((Math.pow(2.0, height.toDouble())).toInt())
        coord.add(-1)

        var counter : Int = 1
        var line : Int = 0

        for (node in tree){
            if (coord.peek() == -1){
                println()
                line++
                coord.poll()
                counter = 1
                coord.add(-1)
            }
            while (coord.peek() != counter ){
                print(" ")
                counter++
            }
            if (node.color == Color.red) {
                print(27.toChar() + "[31m" + node.value + 27.toChar() + "[0m")
            }
            else{
                print(node.value)
            }
            coord.poll()
            if (node.leftChild != null && node.leftChild != tree.nil) coord.add(counter - (Math.pow(2.0, (height-line-1).toDouble()).toInt()))
            if (node.rightChild != null && node.rightChild != tree.nil) coord.add(counter + (Math.pow(2.0, (height-line-1).toDouble()).toInt()))
            counter++
        }

    }

    fun printBSTree(tree: BSTree<K, V>, height : Int){

        var coord = LinkedList<Int>()
        coord.add((Math.pow(2.0, height.toDouble())).toInt())
        coord.add(-1)

        var counter : Int = 1
        var line : Int = 0

        for (node in tree){
            if (coord.peek() == -1){
                println()
                line++
                coord.poll()
                counter = 1
                coord.add(-1)
            }
            while (coord.peek() != counter ){
                print(" ")
                counter++
            }
            print(node.value)
            coord.poll()
            if (node.leftChild != null && node.leftChild != tree.nil) coord.add(counter - (Math.pow(2.0, (height-line-1).toDouble()).toInt()))
            if (node.rightChild != null && node.rightChild != tree.nil) coord.add(counter + (Math.pow(2.0, (height-line-1).toDouble()).toInt()))
            counter++
        }

    }
}

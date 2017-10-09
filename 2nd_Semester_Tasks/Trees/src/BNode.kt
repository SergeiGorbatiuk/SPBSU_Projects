import java.util.*

/**
 * Created by sergey on 13.04.17.
 */
class BNode <K : Comparable<K>, V>  {
    var isLeaf : Boolean = true
    var keys_n_values = ArrayList<Pair<K,V>>()
    var children = ArrayList<BNode<K, V>>()
    var parentNode : BNode<K, V>? = null

    override fun toString(): String {
        var s = "["
        for (i in 0..keys_n_values.size-1){
            s+=keys_n_values[i].first.toString()+","
        }
        s+="]"
        return s
    }
}
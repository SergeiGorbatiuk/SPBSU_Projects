import java.awt.Color

/**
 * Created by sergey on 27.04.17.
 */
data class targetObject(var headInfo: HashMap<String, Int>, var data: ByteArray) {
    var colorTable : Array<Byte> = emptyArray()
}
interface DataConvertable{
    fun renderImage()
    fun pushEvent()
    fun addSubscriber()
}

class BMP_Model8(targetObject: targetObject) : DataConvertable{
    var renderedImage = mutableListOf<Color>()
    var headInfo = targetObject.headInfo
    var data = targetObject.data
    var colorTable = targetObject.colorTable
    var tableNormalized = mutableListOf<Color>()
    init {
        normalizeTable()
        renderImage()
        var panel = PaenlBMP(renderedImage, headInfo)

    }

    override fun renderImage() {
        var num: Int
        for(i in headInfo.get("biHeight")!!-1 downTo 0){
            for (j in 0..headInfo.get("biWidth")!!-1){
                num = data[i*512+j].toInt()
                if (num < 0) num += 256
                renderedImage.add(tableNormalized.get(num))
            }
        }
    }

    override fun pushEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSubscriber() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun normalizeTable(){
        var r: Int
        var g: Int
        var b: Int
        var alpha: Int
        var index:Int
        for (i in 0..headInfo.get("biClrUsed")!!-1){
            b = colorTable[i*4].toInt()
            if (b < 0) b += 256
            g = colorTable[i*4+1].toInt()
            if (g < 0 ) g+=256
            r = colorTable[i*4+2].toInt()
            if (r < 0) r+= 256
            alpha = colorTable[i*4+3].toInt()
            if (alpha < 0) alpha+= 256
            tableNormalized.add(Color(r, g, b, alpha))
        }
    }

}







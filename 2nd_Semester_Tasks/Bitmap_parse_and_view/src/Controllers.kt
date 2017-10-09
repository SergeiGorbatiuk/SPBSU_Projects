
import com.sun.media.sound.InvalidFormatException
import sun.awt.image.ImageFormatException
import java.nio.file.Files
import java.nio.file.Paths


/**
 * Created by sergey on 27.04.17.
 */
interface Controller{
    fun validateFormat(filename : String)
    fun fileParse()
}

class BMP_Controller() : Controller{
    override fun validateFormat(filename: String) {
        var str = filename.substring(filename.length-4)
        if (str != ".bmp"){
            throw InvalidFormatException()
        }
    }

    override fun fileParse() {
        var path = Paths.get("/home/sergey/Downloads/lena512.bmp")
        var target = Files.readAllBytes(path)
        var bfType = target[0].toChar().toString() + target[1].toChar().toString()
        if (bfType != "BM"){
            throw ImageFormatException("U SICK MATE")
        }
        var headInfo = HashMap<String, Int>()

        var bfOffBits = readLitleEndian(target, 10, 4)//: Long = target[13].toInt().shl(24) + target[12].toInt().shl(16) + target[11].toInt().shl(8) + target[10].toInt()
        headInfo.put("bfOffBits", bfOffBits)

        var biCount : Int = target[28].toInt()
        headInfo.put("biCount", biCount)

        var biCompression: Int = target[30].toInt()
        headInfo.put("biCompression", biCompression)

        var biWidth = readLitleEndian(target, 18, 4)
        headInfo.put("biWidth", biWidth)

        var biHeight = readLitleEndian(target, 22, 4)
        headInfo.put("biHeight", biHeight)

        var biClrUsed = readLitleEndian(target, 46, 4)
        headInfo.put("biClrUsed", biClrUsed)

        var biClrImportant = readLitleEndian(target, 50, 4)
        headInfo.put("biClrImportant", biClrImportant)

        var biSizeImage = readLitleEndian(target, 34, 4)
        headInfo.put("biSizeImage", biSizeImage)

        var fileSize = readLitleEndian(target, 2, 4)
        var data = target.sliceArray(IntRange(bfOffBits.toInt(), fileSize.toInt() ))

        var colorTable = target.sliceArray(IntRange(54, 4*biClrUsed.toInt()+53))
        println(colorTable[colorTable.lastIndex-1])
        println(headInfo)

        var targetObject = targetObject(headInfo, data)
        targetObject.colorTable = colorTable.toTypedArray()

        when(biCount.toInt()){
            8 ->{
                var model = BMP_Model8(targetObject)
            }
            24 ->{
                //var model = BMP_Model24(targetObject)
            }
        }
    }

    private fun readLitleEndian(target: ByteArray, index: Int, pixels: Int): Int{
        if (pixels == 4){
            return target[index+3].toInt().shl(24) + target[index+2].toInt().shl(16) + target[index+1].toInt().shl(8) + target[index].toInt()
        }
        if (pixels == 2){
            return target[index+1].toInt().shl(8) + target[index].toInt()
        }
        return 0
    }

}
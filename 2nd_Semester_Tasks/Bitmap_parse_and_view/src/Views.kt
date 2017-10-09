
import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

/**
 * Created by sergey on 29.04.17.
 */
class PaenlBMP(var renderedImage: MutableList<Color>,var headInfo: HashMap<String, Int>, isDoubleBuffered: Boolean = true) : JPanel(isDoubleBuffered) {

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        var cur = 0
        for (i in 0..headInfo.get("biHeight")!!-1){
            for (j in 0..headInfo.get("biWidth")!!-1){
                g.color = renderedImage[cur]
                cur++
                g.drawRect(j, i, 1, 1)
            }
        }
    }

}
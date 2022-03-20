import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import javax.swing.JFrame
import kotlin.math.abs

class PlayFrame : JFrame() {
    var newPosition = 0.0 to 0.0
        set(value) {
            println(value)
            field = value
        }
    var position = 0.0 to 0.0
        set(value) {
            println(value)
            field = value
        }
    var speed = 0.5 //0.05f

    init {
        addMouseMotionListener(object : MouseMotionListener {
            override fun mouseDragged(e: MouseEvent?) {}
            override fun mouseMoved(e: MouseEvent) {
                newPosition = e.x.toDouble() to e.y.toDouble()
            }
        })
        GlobalScope.launch {
            while (true){
                yield()
                if (abs(position.first - newPosition.first) > 3.0 ||
                    abs(position.second - newPosition.second) > 3.0){

                    val sx = (newPosition.first - position.first)
                    val sy = (newPosition.second - position.second)
                    val dx = sx / speed * 0.03
                    val dy = sy / speed * 0.03
                    position = position.first + dx to position.second + dy
                    updateCoord(
                        position
                    )
                }
            }
        }
    }

    fun updateCoord(newPosition : Pair<Double,Double>){
        position = newPosition
        repaint()
    }
    var lastClear = System.currentTimeMillis()

    override fun paint(g: Graphics) {
        if (System.currentTimeMillis() - lastClear > 17 ){
           g.clearRect(0,0,800,800)
            lastClear = System.currentTimeMillis()
        }
        g.fillOval(position.first.toInt() - 50, position.second.toInt() - 50, 100, 100)
    }

}

fun main() {

    val j = PlayFrame()
    j.setSize(800,700)
    j.isVisible = true
}
import pt.isel.canvas.*

val SIDE = 10..400
val ROUND = 0..1
const val DEFAULTSIDE = 200
const val DEFAULTROUND = 0.5F
const val GRIDWIDTH = 600
const val GRIDHEIGHT = 400
const val CENTERX = GRIDWIDTH / 2
const val CENTERY = GRIDHEIGHT / 2
const val ROUNDADDER = 0.1F
const val SIDEADDER = 1

data class Position(val x: Int, val y: Int)
data class RoundSquare(val center: Position, val side: Int = 200, val round: Float, val color: Int)
data class Balls(val cx: Int, val cy: Int, val radius: Int, val color: Int)

fun RoundSquare.factorSide() = ((this.side) / 2 * this.round).toInt()


fun drawcircules(r: RoundSquare, c: Canvas): Balls {
    val f = r.factorSide()
    c.drawCircle((r.center.x - f), (r.center.y - f), f, r.color)
    c.drawCircle((r.center.x + f), (r.center.y - f), f, r.color)
    c.drawCircle((r.center.x - f), (r.center.y + f), f, r.color)
    c.drawCircle((r.center.x + f), (r.center.y + f), f, r.color)
    return Balls(CENTERX, CENTERY, (r.side / 2 * r.round).toInt(), r.color)
}


fun drawRoundSquare(r: RoundSquare, c: Canvas) {
    drawcircules(r, c)
    val f = r.factorSide()
    c.drawRect((r.center.x - 2 * f), (r.center.y - f), r.side, (r.side - 2 * f), r.color)
    c.drawRect((r.center.x - f), (r.center.y - 2 * f), (r.side - 2 * f), (r.side), r.color)

}

fun presentText(r: RoundSquare, c: Canvas) {
    val color = "0x${r.color.toString(16).padStart(6, '0').toUpperCase()}"
    val round = "${(r.round * 100).toInt()}%"
    val center = "(${r.center.x},${r.center.y})"
    val side = "${r.side}"
    c.drawText(15, GRIDHEIGHT - 10, "Center = $center Side = $side round = $round  color = $color}", BLACK, 15)
}


fun keyReceiver(rs: RoundSquare, key: Char): RoundSquare {
    return when (key) {
        'S' -> RoundSquare(rs.center, if (rs.side < 400) rs.side + SIDEADDER else rs.side, rs.round, rs.color)
        's' -> RoundSquare(rs.center, if (rs.side > 10) rs.side - SIDEADDER else rs.side, rs.round, rs.color)
        'c' -> RoundSquare(rs.center, rs.side, rs.round, (0x000000..0xFFFFFF).random())
        //'R' ->RoundSquare(rs.center, rs.side, rs.round - 1F, rs.color)
        //'r' ->RoundSquare(rs.center, rs.side, rs.round + 1F, rs.color)
        else -> RoundSquare(rs.center, rs.side, rs.round, rs.color)
    }
}


fun main() {
    onStart {
        val cv = Canvas(GRIDWIDTH, GRIDHEIGHT, WHITE)
        var rs = RoundSquare(Position(CENTERX, CENTERY), DEFAULTSIDE, DEFAULTROUND, GREEN)
        cv.onTimeProgress(20) {

            erase(cv)
            drawRoundSquare(rs, cv)
            presentText(rs, cv)
        }

        cv.onKeyPressed {
            rs = keyReceiver(rs, it.char)
        }
        cv.onMouseDown { me: MouseEvent ->
            rs = RoundSquare(Position(me.x, me.y), rs.side, rs.round, rs.color)
        }

        onFinish { println("Hasta la vista Baby!") }
    }
}
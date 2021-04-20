import pt.isel.canvas.*

val SIDE = 10..400
val ROUND = 0..1
const val DEFAULTSIDE = 200
const val DEFAULTROUND = 0.5
const val GRIDWIDTH = 600
const val GRIDHEIGHT = 400
const val CENTERX = GRIDWIDTH / 2
const val CENTERY = GRIDHEIGHT / 2
const val ROUNDADDER = 0.1
const val SIDEADDER = 1

data class Position(val x: Int, val y: Int)
data class RoundSquare(val center: Position, val side: Int = 200, val round: Double, val color: Int)
//data class Balls(val cx: Int, val cy: Int, val radius: Int, val color: Int)

fun RoundSquare.factorSide() = ((this.side) / 2 * this.round).toInt()

//fun drawcircules(r: RoundSquare, c: Canvas): Balls {
//    c.drawCircle((r.factorSide() + CENTERX), (r.factorSide() + CENTERY), r.factorSide(), r.color)
//    c.drawCircle((r.side - r.factorSide() + CENTERX), (r.factorSide() + CENTERY), r.factorSide(), r.color)
//    c.drawCircle((r.factorSide() + CENTERX), (r.side - r.factorSide() + CENTERY), r.factorSide(), r.color)
//    c.drawCircle((r.side - r.factorSide() + CENTERX), (r.side - r.factorSide() + CENTERY), r.factorSide(), r.color)
//    return Balls(CENTERX, CENTERY, (r.side / 2 * r.round).toInt(), r.color)
//}

//f = (Side/2)*round = 100*50% ->1º e 50 pixel
fun drawRoundSquare(r: RoundSquare, c: Canvas) :RoundSquare {
    //drawcircules(r, c)
    return c.drawCircle((r.factorSide() + CENTERX), (r.factorSide() + CENTERY), r.factorSide(), r.color)
    c.drawCircle((r.side - r.factorSide() + CENTERX), (r.factorSide() + CENTERY), r.factorSide(), r.color)
    c.drawCircle((r.factorSide() + CENTERX), (r.side - r.factorSide() + CENTERY), r.factorSide(), r.color)
    c.drawCircle((r.side - r.factorSide() + CENTERX), (r.side - r.factorSide() + CENTERY), r.factorSide(), r.color)
    c.drawRect(r.center.x, r.center.y+r.factorSide()/2, DEFAULTSIDE, (DEFAULTSIDE - r.factorSide()), GREEN)
    c.drawRect(r.center.x+r.factorSide()/2, r.center.y, (DEFAULTSIDE - r.factorSide()), DEFAULTSIDE, GREEN)
}

fun RoundSquare.presentText(c: Canvas) {
    c.drawText(CENTERX, GRIDWIDTH, "$this", BLACK, 20)
}

fun keyReceiver(rs: RoundSquare, key: Char) :RoundSquare {
    return when (key) {
        'S' -> RoundSquare(rs.center, if (rs.side <= SIDE.last) rs.side + SIDEADDER else rs.side, rs.round, rs.color)
        's' -> RoundSquare(rs.center, if (rs.side >= SIDE.first) rs.side - SIDEADDER else rs.side, rs.round, rs.color)
        'R' -> RoundSquare(rs.center, rs.side, if (rs.round <= ROUND.last) rs.round + ROUNDADDER else rs.round, rs.color)
        'r' -> RoundSquare(rs.center, rs.side, if (rs.round >= ROUND.first) rs.round - ROUNDADDER else rs.round, rs.color)
        'c' -> RoundSquare(rs.center, rs.side, rs.round, (0x000000..0xffffff).random())
        else -> RoundSquare(rs.center, rs.side, rs.round, rs.color)
    }
}

fun main() {
    onStart {
        val cv = Canvas(GRIDWIDTH, GRIDHEIGHT, WHITE)
        var rs = RoundSquare(Position(0,0), DEFAULTSIDE, DEFAULTROUND, GREEN)
        drawRoundSquare(rs,cv)
        rs.presentText(cv)
        cv.onKeyPressed {
            rs = keyReceiver(rs, it.char)
        }
        cv.onMouseDown {
            rs = RoundSquare(Position(it.x, it.y), rs.side, rs.round, rs.color)
        }
    }
    onFinish { println("Hasta la vista Baby!") }
}



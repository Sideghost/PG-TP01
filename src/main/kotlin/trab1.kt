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
fun drawRoundSquare(r: RoundSquare, c: Canvas) {
    //drawcircules(r, c)
    val f = r.factorSide()
    c.drawRect((r.center.x - 2 * f), (r.center.y - f), r.side, (r.side - 2 * f), r.color)
    c.drawRect((r.center.x - f), (r.center.y - 2 * f), (r.side - 2 * f), (r.side), r.color)
    c.drawCircle((r.center.x - f), (r.center.y - f), f, r.color)
    c.drawCircle((r.center.x + f), (r.center.y - f), f, r.color)
    c.drawCircle((r.center.x - f), (r.center.y + f), f, r.color)
    c.drawCircle((r.center.x + f), (r.center.y + f), f, r.color)

}



fun presentText(r: RoundSquare, c: Canvas) {
    c.drawText(15, GRIDHEIGHT - 10, "Center = (${r.center.x},${r.center.y}) Side = ${r.side} round = ${(r.round * 100).toInt()}% color = 0x${adderColorString(r)}",
        BLACK, 15)
}

fun adderColorString (r: RoundSquare) :String {
    val str =r.color.toString(16)
    return when (str.length/*size da string*/) {
        1 -> "00000$str"
        2 -> "0000$str"
        3 -> "000$str"
        4 -> "00$str"
        5 -> "0$str"
        else -> str
    }
}

/*fun keyReceiver(rs: RoundSquare, key: Char): RoundSquare {
    return when (key) {
}*/



fun main() {
    onStart {
        val cv = Canvas(GRIDWIDTH, GRIDHEIGHT, WHITE)
        var rs = RoundSquare(Position(CENTERX, CENTERY), DEFAULTSIDE, DEFAULTROUND, GREEN)
        cv.onTimeProgress(20){

            erase(cv)

            drawRoundSquare(rs, cv)

             presentText(rs , cv)
        }

        cv.onKeyPressed { ke :KeyEvent ->
            if (ke.char == 'S' && rs.side < 300) {
                rs = RoundSquare(rs.center, rs.side + 1, rs.round, rs.color)
            }
            if (ke.char == 's' && rs.side > 10) {
                rs = RoundSquare(rs.center, rs.side -1 , rs.round, rs.color)
            }
            if (ke.char == 'c'){
                rs = RoundSquare(rs.center, rs.side, rs.round, (0x000000..0xFFFFFF).random())
            }
            /*if (ke.char == 'r'){
                rs = RoundSquare(rs.center, rs.side, rs.round + 1, rs.color)
            }
            if (ke.char == 'R'){
                rs = RoundSquare(rs.center, rs.side, rs.round - 1, rs.color)
            }*/
        }
        cv.onMouseDown { me :MouseEvent ->
            rs = RoundSquare(Position(me.x,me.y), rs.side, rs.round, rs.color)
        }









      /*  cv.onKeyPressed {
            rs = keyReceiver(rs, it.char)
        }
        cv.onMouseDown {
            rs = RoundSquare(Position(it.x, it.y), rs.side, rs.round, rs.color)
        }*/
    }
    onFinish { println("Hasta la vista Baby!") }
}



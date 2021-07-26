import pt.isel.canvas.*


const val DEFAULT_SIDE = 200 //The initial Side of the RoundSquare


const val DEFAULT_ROUND = 50 //The initial percentage of how much round is the RoundSquare


const val GRID_WIDTH = 600 //The width of the canvas


const val GRID_HEIGHT = 400 //The height of the canvas


const val CENTER_X = GRID_WIDTH / 2 //The center in the X assis


const val CENTER_Y = GRID_HEIGHT / 2 //The center in the Y assis


const val ROUND_ADDER = 1 //The increment of roundness


const val SIDE_ADDER = 1 //That defines the increment to the Side


/**
 * Defines the center position of the RoundSquare
 * @property x coordinate of the center position on the canvas
 * @property y coordinate of the center position on the canvas
 */
data class Position(val x: Int, val y: Int)

/**
 * Defines the parameters required to draw a RoundSquare (center position, size, roundness and color)
 * @property center [Position]( x , y )
 * @property side size of the RoundSquare
 * @property round roundness of the RoundSquare
 */
data class RoundSquare(val center: Position, val side: Int = 200, val round: Int, val color: Int)


/**
 * Function that calculates the relation between the radius of a circle and the side of the RoundSquare
 */
fun RoundSquare.factorSide() = ((this.side) / 2 * (this.round.toFloat() / 100)).toInt()



/**
 * Draws the RoundSquare from 4 circles and 2 rectangles
 * @param r [RoundSquare] center position(x,y) , Side , Round , Color
 * @param c [Canvas] Grid
 */
fun drawRoundSquare(r: RoundSquare, c: Canvas) {
    val f = r.factorSide()
    val ro = (r.side / 2 - f)
    c.drawCircle((r.center.x - ro), (r.center.y - ro), f, r.color)                                                      // Upper left conner
    c.drawCircle((r.center.x + ro), (r.center.y - ro), f, r.color)                                                      // Upper right conner
    c.drawCircle((r.center.x + ro), (r.center.y + ro), f, r.color)                                                      // Lower left conner
    c.drawCircle((r.center.x - ro), (r.center.y + ro), f, r.color)                                                      // Lower right conner
    c.drawRect((r.center.x - r.side / 2), (r.center.y - ro), r.side, (r.side - 2 * f), r.color)                         // Horizontal rectangle
    c.drawRect((r.center.x - ro), (r.center.y - r.side / 2), (r.side - 2 * f), r.side, r.color)                         // Vertical rectangle
}

/**
 * Presents the proprieties of the RoundSquare in real time
 * @param r [RoundSquare] center position(x,y) , Side , Round , Color
 * @param c [Canvas] Grid
 */
fun presentText(r: RoundSquare, c: Canvas) {
    val color = "0x${r.color.toString(16).padStart(6, '0').toUpperCase()}"
    val round = "${r.round}%"
    val center = "(${r.center.x},${r.center.y})"
    val side = "${r.side}"
    c.drawText(15, GRID_HEIGHT - 10, "Center = $center Side = $side round = $round  color = $color", BLACK, 15)
}

/**
 * Function that receives the key from the user to modify the RoundSquare.
 * @param r [RoundSquare] center position(x,y) , Side , Round , Color.
 * @param key [Char] selected between the keys : changes the RoundSquare ( S , s , R , r , c ).
 * @return RoundSquare
 */
fun keyReceiver(r: RoundSquare, key: Char): RoundSquare {
    return when (key) {
        'S' ->  RoundSquare(r.center, if (r.side < 400) r.side + SIDE_ADDER else r.side, r.round, r.color)              // increases the size
        's' ->  RoundSquare(r.center, if (r.side > 10) r.side - SIDE_ADDER else r.side, r.round, r.color)               // decreases the size
        'c' ->  RoundSquare(r.center, r.side, r.round, (0x000000..0xFFFFFF).random())                                   // changes the color
        'r' ->  RoundSquare(r.center, r.side, if (r.round > 0) (r.round - ROUND_ADDER) else r.round, r.color)             // decreases the round factor
        'R' ->  RoundSquare(r.center, r.side, if (r.round < 100) (r.round + ROUND_ADDER) else r.round, r.color)             // increases the round factor
        else -> RoundSquare(r.center, r.side, r.round, r.color)
    }
}

/**
 * Main function of the hole program
 */
fun main() {
    onStart {
        val cv = Canvas(GRID_WIDTH, GRID_HEIGHT, WHITE)
        var rs = RoundSquare(Position(CENTER_X, CENTER_Y), DEFAULT_SIDE, DEFAULT_ROUND, GREEN)
        drawRoundSquare(rs, cv)
        presentText(rs, cv)
        cv.onKeyPressed {
            rs = keyReceiver(rs, it.char)
            erase(cv)
            drawRoundSquare(rs, cv)
            presentText(rs, cv)
        }
        cv.onMouseDown { me: MouseEvent ->
            rs = RoundSquare(Position(me.x, me.y), rs.side, rs.round, rs.color)
            erase(cv)
            drawRoundSquare(rs, cv)
            presentText(rs, cv)
        }
        onFinish { println("Hasta la vista Baby!") }
    }
}
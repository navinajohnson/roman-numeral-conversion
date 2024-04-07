import scala.util.matching.Regex

/**
 * @author Navina Johnson
 *
 */

/**
 *
 * @param number the number to convert to the corresponding roman numeral format
 */
case class convertNumber(number: Long) {

  // number to numeral conversion rules
  private val baseConversions: Map[Long, String] = Map((1, "I"), (5, "V"), (10, "X"), (50, "L"), (100, "C"),
    (500, "D"), (1000, "M"), (4, "IV"), (9, "IX"), (40, "XL"), (90, "XC"), (400, "CD"), (900, "CM"))

  /**
   * This function recursively parses the number according to the conversion rules and breaks it down into
   * a list of numbers that have representation in the roman numeral form.
   * @param first The input number to be converted (or to be broken down further)
   * @param number A list that will be recursively updated with the broken down numbers
   * @return The list of numbers that will make up the corresponding roman numeral
   */
  def parseNum(first: Long, number: List[Long] = List.empty): List[Long] = {

    if (first > 0) {
      val (next: Long, baseNumbers: List[Long]) =
        if (first >= 1000) {
          (first - 1000, List(1000))
        } else if (first >= 900 && first < 1000) {
          (first - 900, List(900))
        } else if (first >= 500 && first < 900) {
          (first - 500, List(500))
        } else if (first >= 400 && first < 500) {
          (first - 400, List(400))
        } else if (first >= 100 && first < 400) {
          (first - 100, List(100))
        } else if (first >= 90 && first < 100) {
          (first - 90, List(90))
        } else if (first >= 50 && first < 100) {
          (first - 50, List(50))
        } else if (first >= 40 && first < 50) {
          (first - 40, List(40))
        } else if (first >= 10 && first < 40) {
          (first - 10, List(10))
        } else if (first >= 9 && first < 10) {
          (first - 9, List(9))
        } else if (first >= 5 && first < 9) {
          (first - 5, List(5))
        } else if (first >= 4 && first < 5) {
          (first - 4, List(4))
        } else if (first >= 1 && first < 4) {
          (first - 1, List(1))
        } else {
          (first - 1, List.empty)
        }
      parseNum(next, number ++ baseNumbers)
    } else {
      number
    }
  }

  /**
   * This function checks if the number is valid to be converted. If the number is valid then it will be parsed to
   * get the corresponding roman numeral
   *
   * @return returns the corresponding roman numeral string
   */
  def parse(): String = {

    number match {
      case num if num == 0 => Err("0 is an invalid number")
      case num if num < 0 => Err("Enter a positive number > 0")
      case num =>
        val getNumList: List[Long] = parseNum(num)
        val size = getNumList.length

        def numeralString(numeralStr: String, idx: Int): String = {
          if (idx < size) {
            val getNumeral = baseConversions(getNumList(idx))
            numeralString(numeralStr.concat(getNumeral), idx + 1)
          } else {
            numeralStr
          }
        }

        numeralString("", 0)

      case _ => Err("invalid number")
    }

  }.toString

}


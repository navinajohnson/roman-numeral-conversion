import scala.annotation.tailrec
import scala.util.matching.Regex

/**
 * @author Navina Johnson
 *
 */

/**
 * Source: https://www.cuemath.com/numbers/roman-numerals/
 *  - Rule 1: When certain numerals are repeated, the number represented by them is their sum. For example, II = 1 + 1 = 2, or XX = 10 + 10 = 20, or, XXX = 10 + 10 + 10 = 30
 *  - Rule 2: It is to be noted that no Roman numerals can come together more than 3 times. For example, we cannot write 40 as XXXX
 *  - Rule 3: The letters V, L, and D are not repeated
 *  - Rule 4: Only I, X, and C can be used as subtractive numerals. There can be 6 combinations when we subtract. These are IV = 5 - 1 = 4; IX = 10 - 1 = 9; XL = 50 - 10 = 40; XC = 100 - 10 = 90; CD = 500 - 100 = 400; and CM = 1000 - 100 = 900
 *  - Rule 5: When a Roman numeral is placed after another Roman numeral of greater value, the result is the sum of the numerals. For example, VIII = 5 + 1 + 1 + 1 = 8, or, XV = 10 + 5 = 15,
 *  - Rule 6: When a Roman numeral is placed before another Roman numeral of greater value, the result is the difference between the numerals. For example, IV = 5 - 1 = 4, or, XL = 50 - 10 = 40, or XC = 100 - 10 = 90
 *  - Rule 7: When a Roman numeral of a smaller value is placed between two numerals of greater value, it is subtracted from the numeral on its right. For example, XIV = 10 + (5 - 1) = 14, or, XIX = 10 + (10 - 1) = 19
 *  - Rule 8: To multiply a number by a factor of 1000 a bar is placed over it.
 *  - Rule 9: Roman numerals do not follow any place value system.
 *  - Rule 10: There is no Roman numeral for zero (0).
 */

/**
 *
 * @param str Roman numeral string to convert to the corresponding numerical representation
 */
case class convertNumeral(str: String) {

  /* Modern Roman numerals use 7 letters to represent different numbers.
    These are I, V, X, L, C, D, and M which represent the numbers 1, 5, 10, 50, 100, 500, and 1000 respectively.
     */
  private val baseNumerals = Map(("I", 1), ("V", 5), ("X", 10), ("L", 50), ("C", 100), ("D", 500), ("M", 1000))

  /* Only I, X, and C can be used as subtractive numerals. There can be 6 combinations when we subtract.
  These are IV = 5 - 1 = 4; IX = 10 - 1 = 9; XL = 50 - 10 = 40; XC = 100 - 10 = 90; CD = 500 - 100 = 400; and CM = 1000 - 100 = 900
   */
  private val subtractivePair = Map(("IV", 4), ("IX", 9), ("XL", 40), ("XC", 90), ("CD", 400), ("CM", 900))

  private val countChar = (r: Regex) => r.findAllIn(str).toList.size

  private val moreThanThreeReg: Regex = ".*?(XXXX+|IIII+|CCCC+|MMMM+)".r

  /**
   * @param str    the string representing the Roman numerals
   * @param subStr list of subtractive pair strings
   * @return the string after removing the subtractive pair strings
   */
  @tailrec
  private def string(str: String, subStr: List[String]): String = {
    if (subStr.nonEmpty) {
      val h = subStr.head
      val t = subStr.tail
      string(str.replace(h, ""), t)
    }
    else {
      str
    }
  }

  /**
   * This function parses the roman numeral string according to the defined rules and converts it into its corresponding number
   *
   * @return returns the corresponding number string , or an error in case of invalid numeral string
   */
  def parse(): String = {

    // case to check that the letters V, L, and D are not repeated
    if (countChar("V".r) > 1 || countChar("L".r) > 1 || countChar("D".r) > 1) {
      Err(s"Roman numerals V,L,D should not be repeated")
    } else {
      str match {
        // case to check that no Roman numerals can come together more than 3 times
        case moreThanThreeReg(s) => Err(s"Roman numeral ${s.charAt(0)} should not come together more than 3 times")
        // case to find and add the subtractive pairs if any and add the individual numerals
        case s =>
          // create 2 lists from the string to generate the numeral pairs
          val x = s.split("").toList
          val y = x.drop(1)

          // get the list of subtractive pairs
          val maybeSubtractivePair: List[(String, String)] = x.zip(y).filter { x =>
            baseNumerals(x._1) < baseNumerals(x._2)
          }

          /* if there are no subtractive pairs then just sum up all the individual numerals in the string,
             else add up the subtractive pairs of numerals in the string
           */
          if (maybeSubtractivePair.isEmpty) {
            x.map(elem => baseNumerals(elem)).sum
          } else {
            val se: Int = maybeSubtractivePair.map {
              case (elem1, elem2) => subtractivePair(elem1 + elem2)
            }.sum

            // get the numerals to be added from the string after removing the subtractive pairs
            val adElements = {
              val subStrings = maybeSubtractivePair.map {
                case (elem1, elem2) =>
                  val elem = elem1 + elem2
                  elem
              }
              string(str, subStrings)
            }

            // add the individual items of the string without the subtractive pairs
            val ad = adElements.split("").toList.filter(_.nonEmpty).map(item => baseNumerals(item)).sum

            /* a guard condition to check if,
            in case the whole string comprises only of subtractive pairs, then just return the added subtractive pairs
            else add both the added individual items and the added subtractive pairs.
            */
            if (maybeSubtractivePair.length * 2 == str.length) {
              se
            } else {
              ad + se
            }

          }

        case _ => Err("invalid numeral")
      }
    }

  }.toString

}

case class Err(str: String)
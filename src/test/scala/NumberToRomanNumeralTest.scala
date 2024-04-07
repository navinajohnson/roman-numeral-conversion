import org.scalatest.featurespec.AnyFeatureSpec

import scala.io.Source

class NumberToRomanNumeralTest extends AnyFeatureSpec{

  Feature("test conversion of numbers to roman numerals") {

    Scenario("Check happy paths - 1 to 3999") {

      val lines:Iterator[List[String]] = Source
        .fromFile("src/test/numerals-numbers.csv")
        .getLines
        .map(str =>
          str.split("=").toList)

      lines.foreach {
        case List(number, expectedRomanNumeral) =>
          val actualNumber: String = convertNumber(number.trim.toInt).parse()
          withClue (number) {assert(actualNumber == expectedRomanNumeral.trim)}
      }
  }

    Scenario("test that number 0 is invalid") {
      val actual: String = convertNumber(0).parse()
      assert(actual == "Err(0 is an invalid number)")
    }

    Scenario("test that negative number is invalid") {
      val actual: String = convertNumber(-1).parse()
      assert(actual == "Err(Enter a positive number > 0)")
    }

  }

}

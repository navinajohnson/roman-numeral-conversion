import org.scalatest.featurespec.AnyFeatureSpec

import scala.io.Source

class RomanNumeralToNumberTest extends AnyFeatureSpec{

  Feature("test conversion of roman numerals to numbers") {

    Scenario("Check happy paths - I to MMMCMXCIX") {

      val lines:Iterator[List[String]] = Source
        .fromFile("src/test/numerals-numbers.csv")
        .getLines
        .map(str =>
          str.split("=").toList)

      lines.foreach {
        case List(expectedNumber, romanNumeral) =>
          val actualNumber: String = convertNumeral(romanNumeral.trim).parse()
          withClue (romanNumeral) {assert(actualNumber == expectedNumber.trim)}
      }
  }

    Scenario("test that I, X, C, and M don't occur more than 3 times") {
      val x_actual: String = convertNumeral("XXXX").parse()
      val i_actual: String = convertNumeral("IIIII").parse()
      val cm_actual: String = convertNumeral("MMMCCCCC").parse()
      val cm_actual_2: String = convertNumeral("CCCCCMMMMM").parse()
      assert(x_actual == "Err(Roman numeral X should not come together more than 3 times)")
      assert(i_actual == "Err(Roman numeral I should not come together more than 3 times)")
      assert(cm_actual == "Err(Roman numeral C should not come together more than 3 times)")
      assert(cm_actual_2 == "Err(Roman numeral M should not come together more than 3 times)")
    }

    Scenario("test that V, L, D don't occur more than 1 times") {
      val v_actual: String = convertNumeral("VVIX").parse()
      val l_actual: String = convertNumeral("IILLD").parse()
      val cm_actual: String = convertNumeral("LDVIV").parse()
      val cm_actual_2: String = convertNumeral("CCDD").parse()
      val error = "Err(Roman numerals V,L,D should not be repeated)"
      assert(v_actual == error)
      assert(l_actual == error)
      assert(cm_actual == error)
      assert(cm_actual_2 == error)
    }

  }

}

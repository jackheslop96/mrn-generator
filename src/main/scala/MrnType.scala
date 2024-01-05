import wolfendale.scalacheck.regexp.RegexpGen

import scala.annotation.tailrec
import scala.util.matching.Regex

sealed trait MrnType {

  val regex: Regex

  def generate(number: Int): List[String] = {
    @tailrec
    def rec(acc: List[String]): List[String] = {
      if (acc.size < number) {
        RegexpGen.from(regex.toString()).sample match {
          case Some(value) => fixCheckCharacter(value) match {
            case Some(value) => rec(acc :+ value)
            case None => rec(acc)
          }
          case None => rec(acc)
        }
      } else {
        acc
      }
    }
    rec(Nil)
  }

  private def fixCheckCharacter(mrn: String): Option[String] = {
    mrn match {
      case regex(year, countryCode, serial, _) =>
        val str = s"$year$countryCode$serial"
        val checkCharacter = getCheckCharacter(str)
        Some(s"$str$checkCharacter")
      case _ =>
        None
    }
  }

  private def getCheckCharacter(str: String): String = {
    val multiplicationFactors = str.zipWithIndex.map {
      case (character, index) =>
        characterWeights.apply(character) * Math.pow(2, index).toInt
    }

    val remainder = multiplicationFactors.sum % 11

    (remainder % 10).toString
  }

  private val characterWeights = Map(
    '0' -> 0,
    '1' -> 1,
    '2' -> 2,
    '3' -> 3,
    '4' -> 4,
    '5' -> 5,
    '6' -> 6,
    '7' -> 7,
    '8' -> 8,
    '9' -> 9,
    'A' -> 10,
    'B' -> 12,
    'C' -> 13,
    'D' -> 14,
    'E' -> 15,
    'F' -> 16,
    'G' -> 17,
    'H' -> 18,
    'I' -> 19,
    'J' -> 20,
    'K' -> 21,
    'L' -> 23,
    'M' -> 24,
    'N' -> 25,
    'O' -> 26,
    'P' -> 27,
    'Q' -> 28,
    'R' -> 29,
    'S' -> 30,
    'T' -> 31,
    'U' -> 32,
    'V' -> 34,
    'W' -> 35,
    'X' -> 36,
    'Y' -> 37,
    'Z' -> 38
  )
}

object MrnType {

  def apply(arg: String): MrnType = arg match {
    case "P4" =>
      P4Mrn
    case "P5T" =>
      P5TransitionMrn
    case "P5F" =>
      P5FinalMrn
    case x =>
      throw new IllegalArgumentException(s"$x must be one of P4, P5T or P5F")
  }

  private case object P4Mrn extends MrnType {
    override val regex: Regex = "([0-9]{2})([A-Z]{2})([A-Z0-9]{13})([0-9])".r
  }

  private case object P5TransitionMrn extends MrnType {
    override val regex: Regex = "([0-1][0-9]|[2][0-4])([A-Z]{2})([A-Z0-9]{13})([0-9])".r
  }

  private case object P5FinalMrn extends MrnType {
    override val regex: Regex = "([2][4-9]|[3-9][0-9])([A-Z]{2})([A-Z0-9]{12}[J-M])([0-9])".r
  }
}

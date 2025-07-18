// Generated by Claude Sonnet 4 (claude-sonnet-4-20250514)
package com.formalcalculation.arithmetic.impl

import com.formalcalculation.arithmetic.{Natural, NaturalOps, NaturalPositive, NaturalZero, INaturalPositive, Repr}
import cats.data.NonEmptyList

import scala.annotation.tailrec

object NaturalOpsImpl extends NaturalOps {

  given naturalRepr: Repr[Natural] = new Repr[Natural] {
    type ReprType = List[Boolean]

    override def repr: Natural => ReprType = {
      case NaturalZero        => List(false)
      case p: INaturalPositive => p.bits.toList
    }
    override def from: List[Boolean] => Natural = {
      case List(false) => NaturalZero
      case Nil => NaturalZero
      case bits => NonEmptyList.fromList(bits) match {
        case Some(nel) => INaturalPositive(nel)
        case None => NaturalZero
      }
    }
  }
  
  // Helper method to get bits from Natural
  def getBits(n: Natural): List[Boolean] = n match {
    case NaturalZero => List(false)
    case p: INaturalPositive => p.bits.toList
  }
  
  // Helper method to create Natural from bits
  def fromBits(bits: List[Boolean]): Natural = bits match {
    case List(false) => NaturalZero
    case Nil => NaturalZero
    case bits => NonEmptyList.fromList(bits) match {
      case Some(nel) => INaturalPositive(nel)
      case None => NaturalZero
    }
  }

  def fromInt(n: Int): Natural = {
    require(n >= 0, "Natural number must be non-negative")
    if (n == 0) NaturalZero
    else
      toBinaryList(n) match {
        case Nil     => NaturalZero
        case x :: xs => Natural.positiveFromBits(NonEmptyList(x, xs))
      }
  }

  /** 整数を二進リストに変換（先頭が最下位ビット） */
  private def toBinaryList(n: Int): List[Boolean] = {
    @tailrec
    def loop(value: Int, acc: List[Boolean]): List[Boolean] = {
      if (value == 0) acc
      else loop(value / 2, (value % 2 == 1) :: acc)
    }
    loop(n, Nil).reverse
  }

  def add(a: Natural, b: Natural): Natural = (a, b) match {
    case (NaturalZero, x) => x
    case (x, NaturalZero) => x
    case (a: NaturalPositive, b: NaturalPositive) =>
      val aBits = getBits(a)
      val bBits = getBits(b)
      addBits(aBits, bBits) match {
        case Nil     => NaturalZero
        case x :: xs => Natural.positiveFromBits(NonEmptyList(x, xs))
      }
  }

  /** ビットリストの加算（キャリー付き） */
  private def addBits(a: List[Boolean], b: List[Boolean]): List[Boolean] = {
    @tailrec
    def loop(a: List[Boolean], b: List[Boolean], carry: Boolean, acc: List[Boolean]): List[Boolean] = {
      (a, b) match {
        case (Nil, Nil) => if (carry) acc :+ true else acc
        case (Nil, bb)  => loop(List(false), bb, carry, acc)
        case (aa, Nil)  => loop(aa, List(false), carry, acc)
        case (aHead :: aTail, bHead :: bTail) =>
          val sum       = (if (aHead) 1 else 0) + (if (bHead) 1 else 0) + (if (carry) 1 else 0)
          val resultBit = sum % 2 == 1
          val newCarry  = sum >= 2
          loop(aTail, bTail, newCarry, acc :+ resultBit)
      }
    }
    loop(a, b, false, Nil)
  }

  def multiply(a: Natural, b: Natural): Natural = (a, b) match {
    case (NaturalZero, _) | (_, NaturalZero) => NaturalZero
    case (x, b: NaturalPositive) if getBits(b) == List(true) => x // b == 1
    case (a: NaturalPositive, x) if getBits(a) == List(true) => x // a == 1
    case (a: NaturalPositive, b: NaturalPositive) =>
      val aBits = getBits(a)
      val bBits = getBits(b)
      multiplyBits(aBits, bBits)
  }

  /** ビットリストの乗算 */
  private def multiplyBits(a: List[Boolean], b: List[Boolean]): Natural = {
    @tailrec
    def loop(multiplicand: List[Boolean], multiplier: List[Boolean], position: Int, acc: Natural): Natural =
      multiplier match {
        case Nil => acc
        case false :: rest =>
          loop(multiplicand, rest, position + 1, acc)
        case true :: rest =>
          val newAcc = add(
            acc,
            shiftLeft(multiplicand, position) match {
              case Nil     => NaturalZero
              case x :: xs => Natural.positiveFromBits(NonEmptyList(x, xs))
            }
          )
          loop(multiplicand, rest, position + 1, newAcc)
      }

    loop(a, b, 0, NaturalZero)
  }

  /** ビットリストを左にnビットシフト */
  private def shiftLeft(bits: List[Boolean], n: Int): List[Boolean] = {
    if (n <= 0) bits
    else List.fill(n)(false) ++ bits
  }

  def compare(a: Natural, b: Natural): Int = (a, b) match {
    case (NaturalZero, NaturalZero) => 0
    case (NaturalZero, _)           => -1
    case (_, NaturalZero)           => 1
    case (a: NaturalPositive, b: NaturalPositive) =>
      val aList = getBits(a)
      val bList = getBits(b)
      if (aList.length != bList.length) aList.length.compare(bList.length)
      else compareBits(aList.reverse, bList.reverse) // 最上位ビットから比較
  }

  /** ビットリストの比較（最上位から） */
  @tailrec
  private def compareBits(a: List[Boolean], b: List[Boolean]): Int = (a, b) match {
    case (Nil, Nil)               => 0
    case (true :: _, false :: _)  => 1
    case (false :: _, true :: _)  => -1
    case (_ :: aTail, _ :: bTail) => compareBits(aTail, bTail)
    case _                        => assert(false, "同じ長さなのでここには来ない"); 0
  }

  def subtract(a: Natural, b: Natural): Option[Natural] = {
    if (compare(a, b) < 0) None
    else Some(subtractUnsafe(a, b))
  }

  def subtractUnsafe(a: Natural, b: Natural): Natural = (a, b) match {
    case (x, NaturalZero) => x
    case (a: NaturalPositive, b: NaturalPositive) =>
      val aBits = getBits(a)
      val bBits = getBits(b)
      val result = subtractBits(aBits, bBits)
      result.reverse.dropWhile(!_).reverse match {
        case Nil     => NaturalZero
        case x :: xs => Natural.positiveFromBits(NonEmptyList(x, xs))
      }
    case (NaturalZero, _) => assert(false, "実際には呼ばれない"); NaturalZero
  }

  /** ビットリストの減算（ボロー付き） */
  private def subtractBits(a: List[Boolean], b: List[Boolean]): List[Boolean] = {
    @tailrec
    def loop(a: List[Boolean], b: List[Boolean], borrow: Boolean, acc: List[Boolean]): List[Boolean] = {
      (a, b) match {
        case (Nil, Nil) => if (borrow) (acc :+ true) else acc
        case (aa, Nil)  => loop(aa, List(false), borrow, acc)
        case (Nil, _)   => assert(false, "実際には呼ばれない"); acc // a >= b が保証されているため
        case (aHead :: aTail, bHead :: bTail) =>
          val aVal                   = if (aHead) 1 else 0
          val bVal                   = if (bHead) 1 else 0
          val borrowVal              = if (borrow) 1 else 0
          val diff                   = aVal - bVal - borrowVal
          val (resultBit, newBorrow) = if (diff >= 0) (diff == 1, false) else (true, true)
          loop(aTail, bTail, newBorrow, acc :+ resultBit)
      }
    }
    loop(a, b, false, Nil)
  }

  def equal(a: Natural, b: Natural): Boolean = compare(a, b) == 0

  /** 自然数を文字列に変換 */
  def naturalToString(n: Natural): String =
    if (n.isZero) "0"
    else {
      // 2進数から10進数への変換
      bitsToDecimalString(getBits(n))
    }

  /** ビット列を10進文字列に変換 */
  private def bitsToDecimalString(bits: List[Boolean]): String = {
    // 2進数から10進数への変換
    def bitsToNat(bs: List[Boolean]): Natural = bs match {
      case Nil => NaturalZero
      case head :: tail =>
        val tailValue = bitsToNat(tail)
        val doubled   = multiply(tailValue, fromInt(2))
        if (head) add(doubled, fromInt(1)) else doubled
    }

    val nat = bitsToNat(bits)
    natToDecimalString(nat)
  }

  /** 自然数を10進文字列に変換（簡易実装） */
  private def natToDecimalString(n: Natural): String = {
    @tailrec
    def loop(current: Natural, acc: String): String =
      if (current.isZero) if (acc.isEmpty) "0" else acc
      else {
        val ten = fromInt(10)
        subtract(current, ten) match {
          case Some(_) =>
            // current >= 10 の場合
            val quotient = divideByTen(current)
            val digit    = subtractionsToGetRemainder(current, ten)
            loop(quotient, digit.toString + acc)
          case None =>
            // current < 10 の場合
            val digit = naturalToSingleDigit(current)
            digit.toString + acc
        }
      }
    loop(n, "")
  }

  /** 自然数を10で割った商を計算（簡易実装） */
  private def divideByTen(n: Natural): Natural = {
    val ten               = fromInt(10)
    var quotient: Natural = NaturalZero
    val one               = fromInt(1)

    // 10 * quotient + remainder = n となるquotientを見つける
    while (compare(add(multiply(quotient, ten), ten), n) <= 0) {
      quotient = add(quotient, one)
    }
    quotient
  }

  /** 自然数から単一桁の数字を取得 */
  private def naturalToSingleDigit(n: Natural): Int = {
    val values = (0 to 9).map(fromInt)
    values.indexWhere(equal(n, _)) match {
      case -1    => throw new IllegalArgumentException(s"Natural number is not a single digit: $n")
      case digit => digit
    }
  }

  /** nからmを何回引けるかを計算 */
  private def subtractionsToGetRemainder(n: Natural, m: Natural): Int = {
    @tailrec
    def loop(current: Natural, count: Int): Int =
      subtract(current, m) match {
        case Some(remainder) => loop(remainder, count + 1)
        case None            => count
      }
    loop(n, 0)
  }

  /** 自然数をIntに変換 */
  def naturalToInt(n: Natural): Int =
    if (n.isZero) 0
    else {
      getBits(n).zipWithIndex.foldLeft(0) { case (acc, (bit, index)) =>
        if (bit) acc + (1 << index) else acc
      }
    }

  /** 偶数判定 */
  def isEven(n: Natural): Boolean = n match {
    case NaturalZero => true
    case p: NaturalPositive =>
      !getBits(p).head // 最下位ビット（LSB）が0なら偶数
  }
}

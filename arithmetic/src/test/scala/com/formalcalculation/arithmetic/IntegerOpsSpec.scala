// Generated by Claude Sonnet 4 (claude-sonnet-4-20250514)
package com.formalcalculation.arithmetic

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import IntegerOps.*
import com.formalcalculation.arithmetic.IntegerOps.Constants.*

class IntegerOpsSpec extends AnyFlatSpec with Matchers {

  "Natural operators" should "work correctly with infix notation" in {
    val one   = Natural.fromInt(1)
    val two   = Natural.fromInt(2)
    val three = Natural.fromInt(3)
    val five  = Natural.fromInt(5)

    // 加算
    val _ = (one + two).shouldBe(three)
    val _ = (two + three).shouldBe(five)

    // 乗算
    val _ = (two * three).shouldBe(Natural.fromInt(6))
    val _ = (one * five).shouldBe(five)

    // 減算
    val _ = (five - two).shouldBe(Some(three))
    val _ = (two - five).shouldBe(None)

    // 比較
    val _ = (one == one).shouldBe(true)
    val _ = (one != two).shouldBe(true)
    val _ = (one < two).shouldBe(true)
    val _ = (two > one).shouldBe(true)
    val _ = (one <= one).shouldBe(true)
    val _ = (two >= one).shouldBe(true)
  }

  "Integer operators" should "work correctly with infix notation" in {
    val zero     = Integer.Zero
    val one      = Integer.fromInt(1)
    val minusOne = Integer.fromInt(-1)
    val two      = Integer.fromInt(2)
    val three    = Integer.fromInt(3)
    val five     = Integer.fromInt(5)
    val six      = Integer.fromInt(6)
    val eight    = Integer.fromInt(8)

    // 加算
    val _ = (one + two).shouldBe(three)
    val _ = (one + minusOne).shouldBe(zero)

    // 減算
    val _ = (five - two).shouldBe(three)
    val _ = (two - five).shouldBe(Integer.fromInt(-3))

    // 乗算
    val _ = (two * three).shouldBe(six)
    val _ = (two * minusOne).shouldBe(Integer.fromInt(-2))

    // 除算
    val _ = (six / two).shouldBe(Some(three))
    val _ = (five / two).shouldBe(Some(two))
    val _ = (one / zero).shouldBe(None)

    // 余り
    val _ = (five % two).shouldBe(Some(one))
    val _ = (six  % three).shouldBe(Some(zero))

    // べき乗
    val _ = (two ** Natural.fromInt(3)).shouldBe(eight)
    val _ = (minusOne ** Natural.fromInt(2)).shouldBe(one)

    // 単項マイナス
    val _ = (-one).shouldBe(minusOne)
    val _ = (-minusOne).shouldBe(one)
    val _ = (-zero).shouldBe(zero)

    // 比較
    val _ = (one == one).shouldBe(true)
    val _ = (one != two).shouldBe(true)
    val _ = (one < two).shouldBe(true)
    val _ = (two > one).shouldBe(true)
    val _ = (minusOne < zero).shouldBe(true)
    val _ = (zero > minusOne).shouldBe(true)

    // 絶対値
    val _ = one.abs.shouldBe(Natural.fromInt(1))
    val _ = minusOne.abs.shouldBe(Natural.fromInt(1))
    val _ = zero.abs.shouldBe(Natural.Zero)
  }

  "Implicit conversions" should "work correctly" in {
    // Int から Integer への変換
    val intOne: Integer      = 1
    val intMinusOne: Integer = -1
    val intZero: Integer     = 0

    val _ = intOne.shouldBe(Integer.fromInt(1))
    val _ = intMinusOne.shouldBe(Integer.fromInt(-1))
    val _ = intZero.shouldBe(Integer.Zero)

    // Int から Natural への変換（非負の場合）
    val natOne: Natural  = 1
    val natTwo: Natural  = 2
    val natZero: Natural = 0

    val _ = natOne.shouldBe(Natural.fromInt(1))
    val _ = natTwo.shouldBe(Natural.fromInt(2))
    val _ = natZero.shouldBe(Natural.Zero)

    // 負数は変換できない
    an[IllegalArgumentException] should be thrownBy {
      val _: Natural = -1
    }
  }

  "Constants" should "be accessible" in {
    val _ = Zero.shouldBe(Integer.Zero)
    val _ = One.shouldBe(Integer.fromInt(1))
    val _ = MinusOne.shouldBe(Integer.fromInt(-1))
    val _ = Two.shouldBe(Integer.fromInt(2))

    val _ = NaturalZero.shouldBe(Natural.Zero)
    val _ = NaturalOne.shouldBe(Natural.fromInt(1))
    val _ = NaturalTwo.shouldBe(Natural.fromInt(2))
  }

  "Mixed operations" should "work with implicit conversions" in {
    // Integer と Int の混合演算
    val result1 = Integer.fromInt(5) + 3
    val _ = result1.shouldBe(Integer.fromInt(8))

    val result2 = Integer.fromInt(10) - 4
    val _ = result2.shouldBe(Integer.fromInt(6))

    val result3 = Integer.fromInt(3) * 4
    val _ = result3.shouldBe(Integer.fromInt(12))

    // Natural と Int の混合演算
    val result4 = Natural.fromInt(5) + 3
    val _ = result4.shouldBe(Natural.fromInt(8))

    val result5 = Natural.fromInt(3) * 4
    val _ = result5.shouldBe(Natural.fromInt(12))
  }

  "String representation" should "work correctly" in {
    // Natural の文字列表現
    val _ = Natural.Zero.show.shouldBe("0")
    val _ = Natural.fromInt(1).show.shouldBe("1")
    val _ = Natural.fromInt(5).show.shouldBe("5")
    val _ = Natural.fromInt(10).show.shouldBe("10")
    val _ = Natural.fromInt(123).show.shouldBe("123")

    // Integer の文字列表現
    val _ = Integer.Zero.show.shouldBe("0")
    val _ = Integer.fromInt(1).show.shouldBe("1")
    val _ = Integer.fromInt(-1).show.shouldBe("-1")
    val _ = Integer.fromInt(5).show.shouldBe("5")
    val _ = Integer.fromInt(-5).show.shouldBe("-5")
    val _ = Integer.fromInt(42).show.shouldBe("42")
    val _ = Integer.fromInt(-42).show.shouldBe("-42")
  }
}

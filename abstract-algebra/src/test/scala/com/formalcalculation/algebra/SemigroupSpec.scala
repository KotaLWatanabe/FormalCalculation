// Generated by Claude Sonnet 4 (claude-sonnet-4-20250514)
package com.formalcalculation.algebra

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import org.scalacheck.Gen

class SemigroupSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks {
  
  /**
    * Property-based test for semigroup laws
    */
  def testSemigroupLaws[A](gen: Gen[A])(using S: Semigroup[A]): Unit = {
     it should "satisfy associativity law" in {
      forAll(gen, gen, gen) { (a, b, c) =>
        val _ = S.combine(S.combine(a, b), c).shouldBe(S.combine(a, S.combine(b, c)))
      }
     }
  }
  
  "String Semigroup" should behave like {
     given S: Semigroup[String] = Monoid.stringMonoid
     testSemigroupLaws(Gen.alphaStr)
  }
  
  "Int Addition Semigroup" should behave like {
     given S: Semigroup[Int] = Monoid.intAdditionMonoid
     testSemigroupLaws(Gen.choose(-1000, 1000))
  }
  
  "List Semigroup" should behave like {
     given S: Semigroup[List[Int]] = summon[Monoid[List[Int]]]
     testSemigroupLaws(Gen.listOf(Gen.choose(1, 100)))
  }
}
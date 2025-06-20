// Generated by Claude Sonnet 4 (claude-sonnet-4-20250514)
package com.formalcalculation.arithmetic

/** 基本的な数論関数の実装 */
object NumberTheory {
  
  /** 最大公約数（ユークリッドの互除法） */
  def gcd(a: Natural, b: Natural): Natural = (a, b) match {
    case (Natural.Zero, x) => x
    case (x, Natural.Zero) => x
    case _ =>
      val comparison = Natural.compare(a, b)
      if (comparison == 0) a
      else if (comparison > 0) {
        Natural.subtract(a, b) match {
          case Some(diff) => gcd(diff, b)
          case None => b // これは実際には起こらない
        }
      } else {
        Natural.subtract(b, a) match {
          case Some(diff) => gcd(a, diff)
          case None => a // これは実際には起こらない
        }
      }
  }
  
  /** 最大公約数（整数版） */
  def gcd(a: Integer, b: Integer): Natural = gcd(Integer.abs(a), Integer.abs(b))
  
  /** 最小公倍数 */
  def lcm(a: Natural, b: Natural): Natural = (a, b) match {
    case (Natural.Zero, _) | (_, Natural.Zero) => Natural.Zero
    case _ =>
      val gcdValue = gcd(a, b)
      val product = Natural.multiply(a, b)
      quotient(product, gcdValue) match {
        case Some(result) => result
        case None => Natural.Zero // これは実際には起こらない（gcdは積の約数）
      }
  }
  
  /** 最小公倍数（整数版） */
  def lcm(a: Integer, b: Integer): Natural = lcm(Integer.abs(a), Integer.abs(b))
  
  /** 自然数の除算（商のみ、余りは捨てる） */
  private def quotient(dividend: Natural, divisor: Natural): Option[Natural] = 
    (dividend, divisor) match {
      case (_, Natural.Zero) => None
      case (Natural.Zero, _) => Some(Natural.Zero)
      case _ =>
        // 簡易実装：減算を繰り返して商を求める
        def loop(remainder: Natural, quotient: Natural): Natural = 
          Natural.subtract(remainder, divisor) match {
            case Some(newRemainder) => 
              loop(newRemainder, Natural.add(quotient, Natural.fromInt(1)))
            case None => quotient
          }
        Some(loop(dividend, Natural.Zero))
    }
  
  /** 素数判定（試し割り法） */
  def isPrime(n: Natural): Boolean = n match {
    case Natural.Zero => false
    case Natural.Positive(bits) if bits.toList == List(true) => false // 1は素数ではない
    case Natural.Positive(bits) if bits.toList == List(false, true) => true // 2は素数
    case _ =>
      // 2で割り切れるかチェック
      if (isEven(n)) false
      else {
        // 3以上の奇数で√nまで試し割り
        val sqrtN = approximateSqrt(n)
        trialDivision(n, Natural.fromInt(3), sqrtN)
      }
  }
  
  /** 偶数判定 */
  def isEven(n: Natural): Boolean = n match {
    case Natural.Zero => true
    case Natural.Positive(bits) => !bits.head // 最下位ビットが0なら偶数
  }
  
  /** 奇数判定 */
  def isOdd(n: Natural): Boolean = !isEven(n)
  
  /** 試し割り法による素数判定の補助関数 */
  private def trialDivision(n: Natural, divisor: Natural, limit: Natural): Boolean = {
    if (Natural.compare(divisor, limit) > 0) true // √nを超えたら素数
    else {
      if (isDivisibleBy(n, divisor)) false // 割り切れたら合成数
      else {
        // 次の奇数でチェック
        val nextDivisor = Natural.add(divisor, Natural.fromInt(2))
        trialDivision(n, nextDivisor, limit)
      }
    }
  }
  
  /** aがbで割り切れるかどうか */
  def isDivisibleBy(a: Natural, b: Natural): Boolean = (a, b) match {
    case (_, Natural.Zero) => false // ゼロ除算
    case (Natural.Zero, _) => true // 0は任意の数で割り切れる
    case _ =>
      def loop(remainder: Natural): Boolean = 
        Natural.subtract(remainder, b) match {
          case Some(Natural.Zero) => true // ちょうど割り切れた
          case Some(newRemainder) => loop(newRemainder) // 続行
          case None => false // 割り切れない
        }
      loop(a)
  }
  
  /** 平方根の近似値（ニュートン法の簡易版） */
  private def approximateSqrt(n: Natural): Natural = n match {
    case Natural.Zero => Natural.Zero
    case Natural.Positive(bits) if bits.toList == List(true) => Natural.fromInt(1)
    case _ =>
      // 初期値として n/2 を使用
      val two = Natural.fromInt(2)
      val initialGuess = quotient(n, two).getOrElse(Natural.fromInt(1))
      newtonSqrt(n, initialGuess, Natural.fromInt(10)) // 最大10回反復
  }
  
  /** ニュートン法による平方根計算 */
  private def newtonSqrt(n: Natural, guess: Natural, maxIterations: Natural): Natural = 
    maxIterations match {
      case Natural.Zero => guess
      case _ =>
        // newGuess = (guess + n/guess) / 2
        quotient(n, guess) match {
          case Some(quotientValue) =>
            val sum = Natural.add(guess, quotientValue)
            val newGuess = quotient(sum, Natural.fromInt(2)).getOrElse(guess)
            
            // 収束判定（簡易）
            if (Natural.equal(guess, newGuess)) guess
            else {
              val nextIterations = Natural.subtract(maxIterations, Natural.fromInt(1))
                .getOrElse(Natural.Zero)
              newtonSqrt(n, newGuess, nextIterations)
            }
          case None => guess // 除算できない場合は現在の推定値を返す
        }
    }
  
  /** 階乗 */
  def factorial(n: Natural): Natural = n match {
    case Natural.Zero => Natural.fromInt(1)
    case Natural.Positive(bits) if bits.toList == List(true) => Natural.fromInt(1) // 1! = 1
    case _ =>
      def loop(current: Natural, acc: Natural): Natural = current match {
        case Natural.Zero => acc
        case _ =>
          val next = Natural.subtract(current, Natural.fromInt(1))
            .getOrElse(Natural.Zero)
          loop(next, Natural.multiply(acc, current))
      }
      loop(n, Natural.fromInt(1))
  }
  
  /** フィボナッチ数列のn番目 */
  def fibonacci(n: Natural): Natural = n match {
    case Natural.Zero => Natural.Zero
    case Natural.Positive(bits) if bits.toList == List(true) => Natural.fromInt(1) // F(1) = 1
    case _ =>
      def loop(i: Natural, prev: Natural, current: Natural): Natural = 
        if (Natural.equal(i, n)) current
        else {
          val next = Natural.add(prev, current)
          val nextI = Natural.add(i, Natural.fromInt(1))
          loop(nextI, current, next)
        }
      loop(Natural.fromInt(1), Natural.Zero, Natural.fromInt(1))
  }
  
  /** 二項係数 C(n, k) = n! / (k! * (n-k)!) */
  def binomialCoefficient(n: Natural, k: Natural): Option[Natural] = {
    val comparison = Natural.compare(k, n)
    if (comparison > 0) Some(Natural.Zero) // k > n の場合は 0
    else if (comparison == 0) Some(Natural.fromInt(1)) // k == n の場合は 1
    else {
      // 効率的な計算: C(n,k) = C(n,n-k) で小さい方を使う
      val nMinusK = Natural.subtract(n, k).getOrElse(Natural.Zero)
      val (smallerK, _) = if (Natural.compare(k, nMinusK) <= 0) (k, nMinusK) else (nMinusK, k)
      
      // C(n,k) = n * (n-1) * ... * (n-k+1) / (k * (k-1) * ... * 1)
      computeBinomialCoefficient(n, smallerK)
    }
  }
  
  /** 二項係数の実際の計算 */
  private def computeBinomialCoefficient(n: Natural, k: Natural): Option[Natural] = {
    def loop(i: Natural, numerator: Natural, denominator: Natural): Option[Natural] = 
      if (Natural.equal(i, k)) {
        quotient(numerator, denominator)
      } else {
        val nextI = Natural.add(i, Natural.fromInt(1))
        val nMinusI = Natural.subtract(n, i).getOrElse(Natural.Zero)
        val newNumerator = Natural.multiply(numerator, nMinusI)
        val newDenominator = Natural.multiply(denominator, nextI)
        loop(nextI, newNumerator, newDenominator)
      }
    
    if (Natural.equal(k, Natural.Zero)) Some(Natural.fromInt(1))
    else loop(Natural.Zero, Natural.fromInt(1), Natural.fromInt(1))
  }
}
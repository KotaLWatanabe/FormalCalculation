package com.formalcalculation.backend

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.*
import com.formalcalculation.arithmetic.{Integer, Natural}
import com.formalcalculation.arithmetic.IntegerOps.{NaturalOps, IntegerOps}

object JsonCodecs:

  given Encoder[Natural] = Encoder.encodeInt.contramap(_.toInt)
  given Decoder[Natural] = Decoder.decodeInt.map(Natural.fromInt)

  given Encoder[Integer] = Encoder.encodeInt.contramap(_.toInt) 
  given Decoder[Integer] = Decoder.decodeInt.map(Integer.fromInt)

  case class ArithmeticRequest(a: Int, b: Int)
  case class ArithmeticResponse(
    operation: String,
    operands: List[Int], 
    result: Int
  )
  case class DivisionResponse(
    operation: String,
    operands: List[Int],
    quotient: Int,
    remainder: Int
  )
  case class FactorialRequest(n: Int)
  case class FactorialResponse(
    operation: String,
    operand: Int,
    result: Int
  )
  case class ErrorResponse(error: String)

  given Encoder[ArithmeticRequest] = deriveEncoder
  given Decoder[ArithmeticRequest] = deriveDecoder
  given Encoder[ArithmeticResponse] = deriveEncoder
  given Decoder[ArithmeticResponse] = deriveDecoder
  given Encoder[DivisionResponse] = deriveEncoder
  given Decoder[DivisionResponse] = deriveDecoder
  given Encoder[FactorialRequest] = deriveEncoder
  given Decoder[FactorialRequest] = deriveDecoder
  given Encoder[FactorialResponse] = deriveEncoder
  given Decoder[FactorialResponse] = deriveDecoder
  given Encoder[ErrorResponse] = deriveEncoder
  given Decoder[ErrorResponse] = deriveDecoder
package com.formalcalculation.backend

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io.*
import org.http4s.implicits.*
import org.http4s.circe.CirceEntityCodec.*
import com.formalcalculation.arithmetic.{Integer, Natural}
import com.formalcalculation.arithmetic.IntegerOps.{NaturalOps, IntegerOps}
import JsonCodecs.{ArithmeticResponse, DivisionResponse, FactorialResponse, ErrorResponse}

object Routes:

  val arithmeticRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    
    case GET -> Root / "health" =>
      Ok("OK")

    case GET -> Root / "arithmetic" / "add" / IntVar(a) / IntVar(b) =>
      val result = Integer.fromInt(a) + Integer.fromInt(b)
      Ok(ArithmeticResponse("add", List(a, b), result.toInt))

    case GET -> Root / "arithmetic" / "multiply" / IntVar(a) / IntVar(b) =>
      val result = Integer.fromInt(a) * Integer.fromInt(b)
      Ok(ArithmeticResponse("multiply", List(a, b), result.toInt))

    case GET -> Root / "arithmetic" / "subtract" / IntVar(a) / IntVar(b) =>
      val result = Integer.fromInt(a) - Integer.fromInt(b)
      Ok(ArithmeticResponse("subtract", List(a, b), result.toInt))

    case GET -> Root / "arithmetic" / "divide" / IntVar(a) / IntVar(b) =>
      if b == 0 then
        BadRequest(ErrorResponse("Division by zero"))
      else
        Integer.fromInt(a).divide(Integer.fromInt(b)) match
          case Some((quotient, remainder)) =>
            Ok(DivisionResponse("divide", List(a, b), quotient.toInt, remainder.toInt))
          case None =>
            BadRequest(ErrorResponse("Division failed"))

    case GET -> Root / "natural" / "factorial" / IntVar(n) =>
      if n < 0 then
        BadRequest(ErrorResponse("Factorial not defined for negative numbers"))
      else
        val result = if n == 0 then Natural.fromInt(1) 
                     else (1 to n).foldLeft(Natural.fromInt(1))((acc, i) => 
                       acc * Natural.fromInt(i)
                     )
        Ok(FactorialResponse("factorial", n, result.toInt))
  }

  val httpApp = arithmeticRoutes.orNotFound
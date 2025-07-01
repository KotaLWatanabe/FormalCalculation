package com.formalcalculation.backend

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.http4s.*
import org.http4s.implicits.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RoutesSpec extends AnyFlatSpec with Matchers {

  "Routes" should "respond to health check" in {
    val request = Request[IO](Method.GET, uri"/health")
    val response = Routes.httpApp(request).unsafeRunSync()
    val _ = response.status.shouldBe(Status.Ok)
  }

  it should "add two integers" in {
    val request = Request[IO](Method.GET, uri"/arithmetic/add/5/3")
    val response = Routes.httpApp(request).unsafeRunSync()
    val _ = response.status.shouldBe(Status.Ok)
  }

  it should "multiply two integers" in {
    val request = Request[IO](Method.GET, uri"/arithmetic/multiply/4/6")
    val response = Routes.httpApp(request).unsafeRunSync()
    val _ = response.status.shouldBe(Status.Ok)
  }

  it should "calculate factorial" in {
    val request = Request[IO](Method.GET, uri"/natural/factorial/5")
    val response = Routes.httpApp(request).unsafeRunSync()
    val _ = response.status.shouldBe(Status.Ok)
  }

  it should "handle division by zero" in {
    val request = Request[IO](Method.GET, uri"/arithmetic/divide/10/0")
    val response = Routes.httpApp(request).unsafeRunSync()
    val _ = response.status.shouldBe(Status.BadRequest)
  }
}
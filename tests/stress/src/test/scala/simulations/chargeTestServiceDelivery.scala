package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class chargeTestServiceDelivery extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9100/delivery-service-document/delivery")
  /*
  val stressSample =
    scenario("Deliver")
      .repeat(10)
      {
        exec(session =>
          session.set("id", UUID.randomUUID().toString)
        )
          .exec(
            http("Create delivery")
              .post("delivery")
              .body(StringBody(session => buildDelivery(session)))
              .check(status.is(200))
          )
          .pause(1 seconds)
          .exec(
            http("The restaurant consult ")
              .post("ordersFood")
              .body(StringBody(session => buildValidate(session)))
              .check(status.is(200))
          )
          .pause(1 seconds)
          .exec(
            http("Consult orders")
              .post("ordersFood")
              .body(StringBody(session => buildConsult(session)))
              .check(status.is(200))
          )
      }

  def buildOrder(session: Session): String = {
    val id = session("id").as[String]
    raw"""{
      "event": "ORDER",
      "orderFood": {
        "id":"$id",
        "nameOfFood":"Hot Wings 30 Bucket",
        "nameOfClient":"Aurélien",
        "addressDestination":"3 avenue promenade des anglais"
      }
    }""""
  }

  def buildValidate(session: Session): String = {
    val id = session("id").as[String]
    raw"""{
      "event": "VALIDATE",
      "id": "$id",
      "validate": "false"
    }""""
  }

  def buildConsult(session: Session): String = {
    raw"""{
      "event": "CONSULT"
    }""""
  }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
  */
}

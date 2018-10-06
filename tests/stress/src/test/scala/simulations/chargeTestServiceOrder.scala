
package simulations

import java.util.UUID

import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class chargeTestServiceOrder extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9080/order-service-document/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Order")
        .repeat(10)
        {
          exec(session =>
            session.set("id", UUID.randomUUID().toString)
          )
            .exec(
              http("Make a meal order")
                .post("ordersFood")
                .body(StringBody(session => buildOrder(session)))
                .check(status.is(200))
            )
            .pause(1 seconds)
            .exec(
              http("The customer validate the order")
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
}
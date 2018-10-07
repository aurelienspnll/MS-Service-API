package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps

class chargeTestServiceDelivery extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9100/delivery-service-document/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

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
            http("The restaurant consult the delivery")
              .post("delivery")
              .body(StringBody(session => buildConsult(session)))
              .check(status.is(200))
          )
          .pause(1 seconds)
          .exec(
            http("The delivery man has finished")
              .post("delivery")
              .body(StringBody(session => buildComplete(session)))
              .check(status.is(200))
          )
      }

  def buildDelivery(session: Session): String = {
    val id = session("id").as[String]
    raw"""{
         "event" : "DELIVER",
         "delivery" : {
            "id": "$id",
            "idOrder": "34",
            "deliveryMan": "Pablo",
            "delivered": false
         }
    }""""
  }

  def buildConsult(session: Session): String = {
    val id = session("id").as[String]
    raw"""{"event": "CONSULT", "id":"$id"}""""
  }

  def buildComplete(session: Session): String = {
    val id = session("id").as[String]
    raw"""{"event": "COMPLETE", "id":"$id"}""""
  }



  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))

}

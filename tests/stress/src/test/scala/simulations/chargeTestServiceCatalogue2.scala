package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps


class chargeTestServiceCatalogue2 extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9090/catalogue-service-rest/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Add meal in catalogue")
        .repeat(10)
        {
          exec(session =>
            session.set("id", UUID.randomUUID().toString)
          )
            .exec(
            http("Add meal in catalogue")
              .post("meals")
              .body(StringBody(session => addMeal(session)))
              .check(status.is(200))
            )
        }


  def addMeal(session: Session): String = {
    raw"""{
      "name": "tarte aux pommes",
      "description": "Tarte sucrée, faite d'une pâte feuilletée garnie de pommes émincées.",
      "price": "3"
    }""""
  }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
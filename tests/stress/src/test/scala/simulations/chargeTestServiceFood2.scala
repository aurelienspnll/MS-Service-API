package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps


class chargeTestServiceFood2 extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9090/food-service-rest/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Add food in BD")
        .repeat(10)
        {
          exec(session =>
            session.set("id", UUID.randomUUID().toString)
          )
            .exec(
            http("Add food in BD")
              .post("foods")
              .body(StringBody(session => addFood(session)))
              .check(status.is(200))
            )
        }


  def addFood(session: Session): String = {
    raw"""
    {
    	"food":
    		{
    		  "name": "tarte aux pommes",
              "description": "Tarte sucrée, faite d'une pâte feuilletée garnie de pommes émincées.",
              "price": 3.15
    		}
    }""""
  }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
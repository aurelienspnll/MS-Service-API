
package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps


class chargeTestServiceFood extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9090/food-service-rest/foods")

  val stressSample =
    scenario("Consult food")
        .repeat(10)
        {
          exec(
            http("Consult All Catalogue")
              .get("")
              .check(status.is(200))
          )
        }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
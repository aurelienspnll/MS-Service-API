
package simulations

import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps


class chargeTestServiceCatalogue extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9090/catalogue-service-rest/meals")

  val stressSample =
    scenario("Consult catalogue")
        .repeat(10)
        {
          exec(
            http("Consult All Catalogue")
              .get("")
              .check(status.is(200))
          ).pause(1 seconds)
           .exec(
              http("Consult Calalogue with query parameter : maxPrice")
                .get("?maxPrice=30")
                .check(status.is(200))
           )
        }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}
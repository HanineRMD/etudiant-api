package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EtudiantSimpleSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")  // API Gateway ou service direct
            .acceptHeader("application/json")
            .check(status().is(200));

    ScenarioBuilder scn = scenario("GET /api/etudiants - Test de charge")
            .exec(http("Requête GET /api/etudiants")
                    .get("/api/etudiants")
                    .check(status().is(200))
            )
            .pause(Duration.ofMillis(500));  // Pause de 500ms

    {
        setUp(
                scn.injectOpen(
                        // Monter progressivement jusqu'à 50 utilisateurs en 30 secondes
                        rampUsers(50).during(Duration.ofSeconds(30))
                ).protocols(httpProtocol)
        )
                .assertions(
                        global().responseTime().max().lt(1000),     // Max < 1s
                        global().responseTime().percentile(95).lt(500),  // 95% < 500ms
                        global().failedRequests().count().lt(5L)    // < 5 échecs
                );
    }
}
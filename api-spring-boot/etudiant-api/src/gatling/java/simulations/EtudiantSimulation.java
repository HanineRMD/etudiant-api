package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EtudiantSimulation extends Simulation {

    // Configuration HTTP
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")  // Port du service étudiant
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .userAgentHeader("Gatling/Performance Test")
            .check(status().is(200));  // Vérifier que la réponse est 200

    // Scénario 1: Liste des étudiants
    ScenarioBuilder scnGetEtudiants = scenario("Liste des étudiants")
            .exec(http("GET /api/etudiants")
                    .get("/api/etudiants")
                    .check(status().is(200))
                    .check(jsonPath("$").exists())
            )
            .pause(1);  // Pause de 1 seconde entre les requêtes

    // Scénario 2: Récupération d'un étudiant par ID
    ScenarioBuilder scnGetEtudiantById = scenario("Récupération étudiant par ID")
            .exec(http("GET /api/etudiants/1")
                    .get("/api/etudiants/1")
                    .check(status().is(200))
                    .check(jsonPath("$.id").is("1"))
            )
            .pause(1);

    // Scénario 3: Création d'un étudiant
    ScenarioBuilder scnCreateEtudiant = scenario("Création d'un étudiant")
            .exec(http("POST /api/etudiants")
                    .post("/api/etudiants")
                    .body(StringBody("""
                {
                    "cin": "GAT123456",
                    "nom": "Test Gatling",
                    "dateNaissance": "2000-01-01",
                    "email": "gatling@test.com",
                    "anneePremiereInscription": 2024,
                    "departementId": 1
                }
            """)).asJson()
                    .check(status().is(201))
            );

    // Scénario 4: Mélange d'opérations
    ScenarioBuilder scnMixte = scenario("Mixte (80% GET, 20% POST)")
            .randomSwitch().on(
                    Choice.withWeight(80, exec(http("GET /api/etudiants")
                            .get("/api/etudiants")
                            .check(status().is(200))
                    )),
                    Choice.withWeight(20, exec(http("POST /api/etudiants")
                            .post("/api/etudiants")
                            .body(StringBody("""
                    {
                        "cin": "MIX${counter}",
                        "nom": "Mixte ${counter}",
                        "dateNaissance": "1995-01-01",
                        "email": "mixte${counter}@test.com",
                        "anneePremiereInscription": 2023,
                        "departementId": 1
                    }
                """)).asJson()
                            .check(status().is(201))
                    ))
            )
            .pause(1, 2);  // Pause aléatoire entre 1 et 2 secondes

    {
        // Configuration des injections de charge
        setUp(
                // Test 1: Montée en charge progressive
                scnGetEtudiants.injectOpen(
                        rampUsers(10).during(Duration.ofSeconds(10)),   // 10 utilisateurs en 10s
                        rampUsers(50).during(Duration.ofSeconds(30)),   // 50 utilisateurs en 30s
                        constantUsersPerSec(20).during(Duration.ofSeconds(60)), // 20 req/sec constant
                        rampUsers(10).during(Duration.ofSeconds(10))    // Descente progressive
                ).protocols(httpProtocol)
        );
    }
}
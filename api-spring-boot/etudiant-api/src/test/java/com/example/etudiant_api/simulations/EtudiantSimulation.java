package com.example.etudiant_api.simulations; // Vérifiez que ce package correspond bien à votre dossier

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.time.Duration;

// 1. AJOUT de "extends Simulation"
public class EtudiantSimulation extends Simulation {

    // 2. AJOUT du type HttpProtocolBuilder et correction du nom
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8888")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // 3. Définition du Scénario
    ScenarioBuilder scn = scenario("Test de charge - Liste Etudiants")
            .exec(http("GET /api/etudiants")
                    .get("/api/etudiants")
                    .check(status().is(200)));

    // 4. Bloc d'initialisation
    {
        setUp(
                scn.injectOpen(rampUsers(50).during(Duration.ofSeconds(30)))
        ).protocols(httpProtocol);
    }
}
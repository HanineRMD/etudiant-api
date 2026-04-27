package com.example.etudiant_api.steps;

import com.example.etudiant_api.entity.Etudiant;
import io.cucumber.java.en.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtudiantSteps {
    private Etudiant etudiant;
    private int age;

    @Given("un étudiant avec la date de naissance {string}")
    public void unEtudiantAvecDateNaissance(String date) {
        etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.parse(date));
    }

    @When("on calcule son âge")
    public void onCalculeSonAge() {
        age = etudiant.age();
    }

    @Then("l'âge retourné doit être {int}")
    public void lAgeRetourneDoit(int expected) {
        assertEquals(expected, age);
    }
}
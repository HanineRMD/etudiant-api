package com.example.etudiant_api.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import com.example.etudiant_api.entity.Etudiant;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtudiantSteps {
    private Etudiant etudiant;
    private int age;

    @Given("un étudiant avec la date de naissance {string}")
    public void un_etudiant_avec_la_date_de_naissance(String dateNaissance) {
        etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.parse(dateNaissance));
    }

    @When("on calcule son âge")
    public void on_calcule_son_age() {
        age = etudiant.age();
    }

    @Then("l'âge retourné doit être {int}")
    public void l_age_retourne_doit_etre(Integer ageAttendu) {
        assertEquals(ageAttendu, age);
    }
}
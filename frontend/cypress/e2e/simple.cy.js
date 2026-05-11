describe("Test d'accès", () => {
  it("devrait charger la page d'accueil", () => {
    cy.visit("http://localhost:3000");
    cy.get("body").should("be.visible");
  });
});

describe("Page des étudiants", () => {
  it("devrait charger la liste des étudiants", () => {
    cy.visit("http://localhost:3000/etudiants");
    cy.get("body").should("be.visible");
  });
});

const { defineConfig } = require("cypress");

module.exports = defineConfig({
    e2e: {
        baseUrl: "http://localhost:3000",
        specPattern: "cypress/e2e/**/*.cy.{js,jsx}",
        supportFile: false,  // Désactiver le support file pour éviter l'erreur
        viewportWidth: 1280,
        viewportHeight: 720,
        defaultCommandTimeout: 10000,
        video: false,
        screenshotOnRunFailure: true,
        setupNodeEvents(on, config) {
            return config;
        },
    },
});
const { defineConfig } = require("cypress");

module.exports = defineConfig({
    e2e: {
        baseUrl: "http://localhost:3001",
        specPattern: "cypress/e2e/**/*.cy.{js,jsx}",
        viewportWidth: 1280,
        viewportHeight: 720,
        defaultCommandTimeout: 10000,
        video: false,
        screenshotOnRunFailure: false,
        setupNodeEvents(on, config) {
            return config;
        },
    },
});
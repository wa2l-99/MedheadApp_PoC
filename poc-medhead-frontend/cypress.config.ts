import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:4200',
    defaultCommandTimeout: 30000, // Augmenté à 30 secondes
    pageLoadTimeout: 120000, // Augmenté à 2 minutes
  },

  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },
    specPattern: '**/*.cy.ts',
  },

  retries: {
    runMode: 2, // Ajoute 2 tentatives en mode run
    openMode: 0,
  },
});

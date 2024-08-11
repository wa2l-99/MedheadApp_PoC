declare namespace Cypress {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  interface Chainable<Subject> {
    mockLoginPatient(): Chainable<void>;
    mockLoginAdmin(): Chainable<void>;
  }
}

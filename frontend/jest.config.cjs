module.exports = {
  preset: "ts-jest",
  testEnvironment: "jsdom",
  transform: {
    "^.+\\.(ts|tsx|js|jsx)$": "babel-jest",
  },
  moduleNameMapper: {
    "\\.(css|less|scss|sass)$": "identity-obj-proxy",
  },
  // Dodajemy setupFiles, ktore beda wykonane przed testami
  setupFilesAfterEnv: ["<rootDir>/src/tests/setupTests.ts"],

  // Wyciszanie ostrzeżeń deprecacji (np. dla punycode)
  testRunner: "jest-circus/runner",
  // setupFiles: ['<rootDir>/src/tests/silenceWarnings.js']
};

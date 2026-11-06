module.exports = {
  preset: "ts-jest",
  testEnvironment: "jsdom",
  transform: {
    "^.+\\.(ts|tsx|js|jsx)$": "babel-jest",
  },
  moduleNameMapper: {
    "\\.(css|less|scss|sass)$": "identity-obj-proxy",
  },
  /*
   * Add setup files that run before the tests
   */
  setupFilesAfterEnv: ["<rootDir>/src/tests/setupTests.ts"],

  /*
   * Silence deprecation warnings (e.g. for punycode)
   */
  testRunner: "jest-circus/runner",
  // setupFiles: ['<rootDir>/src/tests/silenceWarnings.js']
};

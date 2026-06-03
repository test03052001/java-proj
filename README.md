# java-proj
java security vulnerabilities

## Configuration

The application reads its database settings from environment variables when present:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

For local development, the Spring Boot defaults in `src/main/resources/application.yml` keep the app runnable if these variables are not set. If you run the app from IntelliJ or another IDE, set the variables in the run configuration or rely on the built-in defaults.

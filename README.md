# java-proj
java security vulnerabilities

## Database configuration

Copy `.env.example` to `.env` or set the following environment variables in your deployment:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

The application reads these values at runtime and does not store database credentials in source control.

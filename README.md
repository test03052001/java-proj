# java-proj

java security vulnerabilities

## Environment variables

Spring Boot reads these environment variables automatically:

- `SPRING_DATASOURCE_URL` — JDBC URL for the MySQL database.
- `SPRING_DATASOURCE_USERNAME` — database user name.
- `SPRING_DATASOURCE_PASSWORD` — database password.

Example for local shells:

```bash
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/enterprise_platform?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='your-user'
export SPRING_DATASOURCE_PASSWORD='your-password'
```

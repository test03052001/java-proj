# java-proj
java security vulnerabilities

## Required environment variables

Set these before running the application:

- `DB_USERNAME` - database username
- `DB_PASSWORD` - database password

## Local run example

```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
mvn spring-boot:run
```

## Docker run example

```bash
docker build -t java-proj .
docker run -p 8080:8080 \
  -e DB_USERNAME=your_username \
  -e DB_PASSWORD=your_password \
  java-proj
```

Do not commit real credentials; use environment variables or platform secret injection.

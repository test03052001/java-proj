# java-proj
java security vulnerabilities

## Production / GitHub Actions

Set these repository secrets after merge:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

The deploy pipeline should pass them from GitHub Secrets into the job environment and then into the container runtime, for example:

```yaml
env:
  SPRING_DATASOURCE_URL: ${{ secrets.SPRING_DATASOURCE_URL }}
  SPRING_DATASOURCE_USERNAME: ${{ secrets.SPRING_DATASOURCE_USERNAME }}
  SPRING_DATASOURCE_PASSWORD: ${{ secrets.SPRING_DATASOURCE_PASSWORD }}
```

```bash
docker run -e SPRING_DATASOURCE_URL -e SPRING_DATASOURCE_USERNAME -e SPRING_DATASOURCE_PASSWORD your-image
```

Do not commit datasource credentials to Git. Spring Boot reads the values from environment variables at runtime; local IDE usage can rely on the checked-in defaults in `application.yml`.

# After-merge CI Compose setup

After this MR merges, create `.github/workflows/ci-compose.yml` in GitHub and paste:

```yaml
name: CI Compose
on:
  push:
    branches: [main, master]
  workflow_dispatch:
jobs:
  compose:
    runs-on: ubuntu-latest
    env:
      SPRING_DATASOURCE_URL: ${{ secrets.SPRING_DATASOURCE_URL }}
      SPRING_DATASOURCE_USERNAME: ${{ secrets.SPRING_DATASOURCE_USERNAME }}
      SPRING_DATASOURCE_PASSWORD: ${{ secrets.SPRING_DATASOURCE_PASSWORD }}
    services:
      mysql:
        image: mysql:8
        env:
          MYSQL_ROOT_PASSWORD: ${{ secrets.SPRING_DATASOURCE_PASSWORD }}
          MYSQL_DATABASE: enterprise_platform
        ports:
          - 3306:3306
        options: >-
          --health-cmd="mysqladmin ping -h localhost"
          --health-interval=10s
          --health-timeout=5s
          --health-retries=5
    steps:
      - uses: actions/checkout@v4
      - run: docker compose up --build -d
      - run: sleep 45
      - run: curl -sf http://localhost:8080/actuator/health || curl -sf http://localhost:8080/ || exit 1
      - if: always()
        run: docker compose down -v
```

Add GitHub Actions secrets for `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD`. Use a JDBC URL whose host is `mysql` for CI; for local development, copy `.env.example` to `.env` and set localhost credentials.

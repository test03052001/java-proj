# java-proj
java security vulnerabilities

## Database configuration

Set the MySQL credentials in the process environment before starting the app.

Linux/macOS:
```bash
export SPRING_DATASOURCE_USERNAME="your_mysql_username"
export SPRING_DATASOURCE_PASSWORD="your_mysql_password"
java -jar target/enterprise-platform.jar
```

Windows PowerShell:
```powershell
$env:SPRING_DATASOURCE_USERNAME="your_mysql_username"
$env:SPRING_DATASOURCE_PASSWORD="your_mysql_password"
java -jar target/enterprise-platform.jar
```

The application uses the datasource URL configured in `src/main/resources/application.yml`.

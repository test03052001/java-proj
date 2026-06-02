# java-proj
java security vulnerabilities

## Runtime configuration

Set the MySQL credentials as environment variables before starting the app.

### Linux/macOS

```bash
export SPRING_DATASOURCE_USERNAME="<mysql-username>"
export SPRING_DATASOURCE_PASSWORD="<mysql-password>"
java -jar target/platform-1.0.0-SNAPSHOT.jar
```

### Windows PowerShell

```powershell
$env:SPRING_DATASOURCE_USERNAME="<mysql-username>"
$env:SPRING_DATASOURCE_PASSWORD="<mysql-password>"
java -jar target/platform-1.0.0-SNAPSHOT.jar
```

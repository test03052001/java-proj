# java-proj
java security vulnerabilities

## Runtime configuration

Set the database credentials before starting the application. You can copy `.env.example` to `.env` or export the variables manually:

- `MYSQL_USERNAME`
- `MYSQL_PASSWORD`

Linux/macOS:

```bash
export MYSQL_USERNAME=your-mysql-username
export MYSQL_PASSWORD=your-mysql-password
mvn spring-boot:run
```

Windows PowerShell:

```powershell
$env:MYSQL_USERNAME="your-mysql-username"
$env:MYSQL_PASSWORD="your-mysql-password"
mvn spring-boot:run
```

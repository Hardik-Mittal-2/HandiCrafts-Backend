# Spring Boot Backend Setup Guide

## Quick Start Guide for Tribal Handicrafts Platform Backend

This guide will help you set up and run the Spring Boot backend application.

## Prerequisites

Ensure you have the following installed:
- Java 17 (JDK 17+)
- Maven 3.8+
- MySQL 8.0+
- Git (optional, for cloning repository)
- Postman (optional, for API testing)

## Step 1: Install Java 17

### Windows
```bash
# Download from https://www.oracle.com/java/technologies/downloads/#java17
# Follow installation instructions
# Verify installation
java -version
```

### macOS
```bash
brew install openjdk@17
java -version
```

### Linux
```bash
sudo apt-get install openjdk-17-jdk
java -version
```

## Step 2: Install Maven

### Windows
```bash
# Download from https://maven.apache.org/download.cgi
# Extract to C:\Program Files\Apache\maven
# Add M2_HOME to Environment Variables
# Verify installation
mvn --version
```

### macOS
```bash
brew install maven
mvn --version
```

### Linux
```bash
sudo apt-get install maven
mvn --version
```

## Step 3: Install MySQL Database

### Windows
1. Download from https://dev.mysql.com/downloads/mysql/
2. Run installer and follow instructions
3. Set root password
4. Start MySQL service

### macOS
```bash
brew install mysql
# Start MySQL
brew services start mysql
```

### Linux
```bash
sudo apt-get install mysql-server
sudo service mysql start
```

### Create Database
```sql
# Connect to MySQL
mysql -u root -p

# Create database
CREATE DATABASE handicrafts_db;

# Verify creation
SHOW DATABASES;

# Exit
EXIT;
```

## Step 4: Configure Application

### Update application.properties

Edit `src/main/resources/application.properties`:

```properties
# Update MySQL connection
spring.datasource.url=jdbc:mysql://localhost:3306/handicrafts_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password

# JWT Secret (Generate a strong one)
jwt.secret=your-very-long-secure-secret-key-minimum-256-bits-recommended-for-hs512-algorithm

# Other settings remain as default
```

### Generate Secure JWT Secret

```bash
# Using OpenSSL
openssl rand -base64 172

# Or using Python
python -c "import secrets; print(secrets.token_urlsafe(144))"
```

## Step 5: Build the Application

```bash
# Navigate to project root
cd backend

# Clean previous builds
mvn clean

# Build project
mvn install

# Or skip tests for faster build
mvn install -DskipTests
```

## Step 6: Run the Application

```bash
# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using JAR file
java -jar target/handicrafts-backend-1.0.0.jar

# Option 3: Using IDE (IntelliJ IDEA or Eclipse)
# Right-click TribalHandicraftsApplication.java → Run
```

## Expected Output

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_|\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v3.2.0)

2024-01-15 10:30:45.123  INFO 12345 --- [main] c.p.h.b.TribalHandicraftsApplication : Starting TribalHandicraftsApplication v1.0.0
...
2024-01-15 10:30:50.456  INFO 12345 --- [main] c.p.h.b.TribalHandicraftsApplication : Started TribalHandicraftsApplication in 5.123 seconds (JVM running for 6.234)
```

## Step 7: Verify Application is Running

```bash
# Test endpoint
curl http://localhost:8080/api/swagger-ui.html

# Or open in browser
http://localhost:8080/api/swagger-ui.html
```

## Step 8: Test APIs

### Using Postman

1. **Register User**
```
POST http://localhost:8080/api/auth/register

Body (JSON):
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "CUSTOMER"
}
```

2. **Login**
```
POST http://localhost:8080/api/auth/login

Body (JSON):
{
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "userId": 1,
  "role": "CUSTOMER"
}
```

3. **Get All Products**
```
GET http://localhost:8080/api/products
```

### Using cURL

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com","password":"pass123","role":"CUSTOMER"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"pass123"}'

# Get Products
curl http://localhost:8080/api/products
```

## Troubleshooting

### Issue: Port 8080 already in use

**Solution:**
```bash
# Option 1: Change port in application.properties
server.port=8081

# Option 2: Kill process using port 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :8080
kill -9 <PID>
```

### Issue: MySQL connection refused

**Solution:**
```bash
# Verify MySQL is running
# Check mysql service status

# Windows
sc query MySQL80  # or MySQL version number

# macOS
brew services list

# Linux
sudo service mysql status

# Start MySQL if not running
# Windows: Services → Start MySQL
# macOS: brew services start mysql
# Linux: sudo service mysql start
```

### Issue: JWT secret is too short

**Error:** Signature verification failed
**Solution:** Generate a longer secret (minimum 256 bits for HS512)

### Issue: Build fails with compilation errors

**Solution:**
```bash
# Ensure Java 17 is set
java -version

# Clean Maven cache
mvn clean

# Update dependencies
mvn dependency:resolve

# Rebuild
mvn install
```

### Issue: Database migration errors

**Solution:**
```bash
# Drop and recreate database
mysql -u root -p
DROP DATABASE handicrafts_db;
CREATE DATABASE handicrafts_db;
EXIT;

# Restart application (Hibernate will recreate tables)
```

## Useful Commands

```bash
# Build
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Run tests
mvn test

# Run specific test class
mvn test -Dtest=YourTestClass

# Check dependencies
mvn dependency:tree

# Check for vulnerable dependencies
mvn dependency-check:check

# Format code
mvn spotless:apply

# Generate coverage report
mvn clean test jacoco:report

# Deploy to local repository
mvn deploy -DaltDeploymentRepository=local::file://~/.m2/repository
```

## Development Tips

### IDE Setup (IntelliJ IDEA)

1. Open project: File → Open → Select backend folder
2. Configure Java: File → Project Structure → Project → Set SDK to Java 17
3. Configure Maven: Settings → Build, Execution, Deployment → Build Tools → Maven → Maven home path
4. Install Lombok: File → Settings → Plugins → Search "Lombok" → Install
5. Enable annotation processing: File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing

### IDE Setup (Eclipse)

1. File → Import → Existing Maven Projects
2. Select backend folder
3. Install Lombok: Help → Eclipse Marketplace → Search "Lombok" → Install

### Enable Hot Reload

In `pom.xml`, devtools is already included. Add to IDE:
- IntelliJ: Build → Compile
- Eclipse: Project → Build Project (Auto-build enabled)

## Common Issues and Solutions

| Issue | Solution |
|-------|----------|
| Can't find Java 17 | Make sure JAVA_HOME is set correctly |
| Maven command not found | Add Maven to PATH environment variable |
| Database connection refused | Verify MySQL is running and credentials are correct |
| Port already in use | Change server.port in application.properties |
| JWT errors | Check jwt.secret length (minimum 256 bits) |
| CORS errors from frontend | Verify frontend URL in CorsConfig.java |
| File upload not working | Create uploads/products/ directory or ensure write permissions |

## Next Steps

1. Set up the React frontend
2. Configure environment variables
3. Set up continuous integration (CI/CD)
4. Configure SSL/TLS for production
5. Set up monitoring and logging
6. Configure automated backups for database

## Documentation

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JPA/Hibernate Documentation](https://hibernate.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## Support

For issues and questions:
- Check logs in `logs/application.log`
- Review error messages carefully
- Check GitHub issues
- Contact development team

## Version History

- v1.0.0 - Initial Release

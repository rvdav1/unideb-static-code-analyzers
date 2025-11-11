# Static Code Analysis Demo Project

This project is a demonstration application designed to showcase various static code analysis and quality assurance tools commonly used in Java projects. The application intentionally contains code quality issues that these tools can detect, making it an excellent learning resource for understanding how static analysis tools work.

## Overview

This is a simple Spring Boot web application with user login functionality. The application demonstrates the integration and usage of:

- **JaCoCo** - Code coverage analysis
- **Checkstyle** - Code style checking
- **Maven Surefire** - Test execution
- **SonarQube** - Comprehensive code quality analysis
- **OWASP Dependency-Check** - Security vulnerability scanning

## Project Structure

```
.
├── src/
│   ├── main/
│   │   ├── java/com/epam/example/
│   │   │   ├── StaticCodeAnalysisDemoApplication.java
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── domain/
│   │   │   ├── config/
│   │   │   └── util/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   └── test/
│       └── java/com/epam/example/
├── .github/workflows/
│   └── ci.yml
├── checkstyle.xml
├── sonar-project.properties
└── pom.xml
```

## Prerequisites

- JDK 21 or higher
- Maven 3.6 or higher
- (Optional) SonarQube server for SonarQube analysis

## Getting Started

### 1. Clone and Build

```bash
git clone <repository-url>
cd unideb-static-code-analyzers
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

**Demo Credentials:**
- Username: `admin`
- Password: `admin123`

### 3. Access the Application

- Login page: http://localhost:8080/
- Dashboard: http://localhost:8080/dashboard
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (empty)

## Running Static Analysis Tools

### Checkstyle

Checkstyle analyzes code style and formatting issues.

```bash
# Run Checkstyle check
mvn checkstyle:check

# Generate Checkstyle report
mvn checkstyle:checkstyle
```

**Report Location:** `target/checkstyle-result.xml`

**Expected Issues:**
- Missing whitespace around operators
- Missing braces in if statements
- Missing default case in switch statements
- Code style violations in `UserService.methodWithStyleIssues()`

### JaCoCo (Code Coverage)

JaCoCo measures test coverage and generates coverage reports.

```bash
# Run tests with coverage
mvn clean test jacoco:report

# Check coverage thresholds
mvn jacoco:check
```

**Report Location:** `target/site/jacoco/index.html`

**Coverage Threshold:** Minimum 50% instruction coverage

**Expected Issues:**
- Low coverage in service classes
- Uncovered methods in `UserService`
- Uncovered utility classes

### Maven Surefire (Test Execution)

Surefire runs unit tests during the build process.

```bash
# Run tests
mvn test

# Run tests with detailed output
mvn surefire:test
```

**Report Location:** `target/surefire-reports/`

**Expected Issues:**
- Some tests may fail due to intentional issues (missing mock injection)
- Test coverage gaps

### SonarQube

SonarQube provides comprehensive code quality analysis including bugs, vulnerabilities, code smells, and security hotspots.

**📖 For detailed setup instructions, see [SONARQUBE_SETUP.md](SONARQUBE_SETUP.md)**

**Quick Start:**

1. **SonarCloud (Recommended - Free for public repos):**
   - Sign up at [sonarcloud.io](https://sonarcloud.io)
   - Create a project and get your token
   - Set GitHub secrets: `SONAR_TOKEN`, `SONAR_HOST_URL`, `SONAR_ORGANIZATION`

2. **Local SonarQube:**
   - Install and start SonarQube server
   - Create project and generate token
   - Set environment variables:
     ```bash
     export SONAR_TOKEN=your-token
     export SONAR_HOST_URL=http://localhost:9000
     ```

**Run Analysis:**

```bash
mvn clean verify sonar:sonar
```

**Expected Issues:**
- Security vulnerabilities (plain text passwords, SQL injection risks)
- Code smells (unused methods, missing validation)
- Duplicated code
- Complexity issues

### OWASP Dependency-Check

OWASP Dependency-Check scans project dependencies for known security vulnerabilities.

```bash
# Run dependency check
mvn org.owasp:dependency-check-maven:check

# Generate report only (without failing build)
mvn org.owasp:dependency-check-maven:aggregate
```

**Report Location:** `target/dependency-check-report.html`

**Configuration:**
- Fails build if CVSS score >= 7.0
- Generates HTML and JSON reports

**Expected Issues:**
- Vulnerabilities in dependencies (if any exist)
- Outdated dependencies with known CVEs

## CI/CD Pipeline

The project includes a GitHub Actions workflow (`.github/workflows/ci.yml`) that automatically runs all static analysis tools on:

- Push to `main` or `develop` branches
- Pull requests to `main` or `develop` branches

### Pipeline Jobs

1. **Build and Test** - Compiles code and runs tests
2. **Checkstyle** - Validates code style
3. **JaCoCo** - Measures code coverage
4. **SonarQube** - Performs comprehensive code analysis (requires configuration)
5. **OWASP Dependency-Check** - Scans for dependency vulnerabilities
6. **Quality Gate** - Summarizes all analysis results

### Viewing Results

- Go to the "Actions" tab in GitHub
- Click on a workflow run
- View individual job logs and artifacts
- Download reports from the artifacts section

## Intentionally Included Issues

This project intentionally includes various code quality issues for demonstration purposes:

### Security Issues
- Plain text password storage
- Missing password hashing
- Potential SQL injection risks (though mitigated by JPA)
- Missing input validation

### Code Style Issues
- Missing whitespace around operators
- Missing braces in control structures
- Missing default case in switch statements
- Inconsistent formatting

### Code Quality Issues
- Unused methods
- Missing validation
- Poor error handling
- Missing documentation

### Test Coverage Issues
- Incomplete test coverage
- Missing test cases for some methods
- Improperly configured mocks in tests

## Use Case Scenarios

### Scenario 1: Local Development

1. **Before Committing:**
   ```bash
   # Run all checks locally
   mvn clean test checkstyle:check jacoco:check
   ```

2. **Fix Issues:**
   - Review Checkstyle violations
   - Improve test coverage
   - Fix security issues

3. **Commit and Push:**
   - CI/CD pipeline will automatically run all checks
   - Review pipeline results before merging

### Scenario 2: Pull Request Review

1. **Create Feature Branch:**
   ```bash
   git checkout -b feature/new-feature
   ```

2. **Make Changes and Push:**
   ```bash
   git push origin feature/new-feature
   ```

3. **Create Pull Request:**
   - GitHub Actions will run all quality checks
   - Review automated reports
   - Address any blocking issues

4. **Merge After Approval:**
   - All quality gates must pass
   - Code coverage threshold met
   - No critical security vulnerabilities

### Scenario 3: Continuous Monitoring

1. **Regular Dependency Updates:**
   ```bash
   mvn org.owasp:dependency-check-maven:check
   ```

2. **Monitor Coverage Trends:**
   - Track coverage reports over time
   - Ensure coverage doesn't decrease

3. **SonarQube Dashboard:**
   - Monitor code quality metrics
   - Track technical debt
   - Review security hotspots

## Interpreting Results

### Checkstyle Results

- **Severity Levels:** Error, Warning
- **Common Issues:**
  - Line length violations
  - Import order issues
  - Missing Javadoc
  - Whitespace violations

### JaCoCo Results

- **Coverage Metrics:**
  - Instruction coverage
  - Branch coverage
  - Line coverage
  - Method coverage
- **Threshold:** 50% minimum instruction coverage

### SonarQube Results

- **Issue Types:**
  - Bugs
  - Vulnerabilities
  - Code Smells
  - Security Hotspots
- **Severity:** Blocker, Critical, Major, Minor, Info

### OWASP Dependency-Check Results

- **CVSS Scores:** 0.0 - 10.0
- **Severity Levels:**
  - Critical (9.0-10.0)
  - High (7.0-8.9)
  - Medium (4.0-6.9)
  - Low (0.1-3.9)
- **Build Fails:** If CVSS >= 7.0

## Best Practices

1. **Run Checks Locally First:**
   - Don't rely solely on CI/CD
   - Fix issues before pushing

2. **Review Reports Regularly:**
   - Don't ignore warnings
   - Address technical debt incrementally

3. **Maintain Coverage:**
   - Write tests for new code
   - Aim for high coverage on critical paths

4. **Update Dependencies:**
   - Regularly check for vulnerabilities
   - Keep dependencies up to date

5. **Configure Tools Appropriately:**
   - Adjust rules to match team standards
   - Don't disable important checks

## Troubleshooting

### Checkstyle Fails

- Review `checkstyle.xml` configuration
- Fix style violations or adjust rules
- Use `mvn checkstyle:checkstyle` to see detailed report

### JaCoCo Coverage Too Low

- Write more tests
- Adjust coverage thresholds if needed
- Review excluded packages in `pom.xml`

### SonarQube Not Running

- Verify SonarQube server is running
- Check `SONAR_TOKEN` and `SONAR_HOST_URL` are set
- Review `sonar-project.properties` configuration

### OWASP Dependency-Check Takes Too Long

- First run downloads vulnerability database (can take time)
- Subsequent runs are faster
- Consider running in CI/CD only

## Resources

- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Checkstyle Documentation](https://checkstyle.sourceforge.io/)
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

## License

See LICENSE file for details.

## Contributing

This is a demonstration project. Feel free to use it as a template for your own projects or as a learning resource.

---

**Note:** This project intentionally contains code quality issues for educational purposes. In a production environment, all identified issues should be resolved.


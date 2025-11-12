# Quick Start Guide

## Run All Static Analysis Tools

Execute all quality checks in one command:

```bash
mvn clean verify checkstyle:check org.owasp:dependency-check-maven:check
```

This will:
1. Clean and compile the project
2. Run tests with JaCoCo coverage
3. Check code coverage thresholds
4. Run Checkstyle validation
5. Run OWASP Dependency-Check

## Individual Tool Commands

### Build and Test
```bash
mvn clean test
```

### Checkstyle
```bash
mvn checkstyle:check
```

### Code Coverage
```bash
mvn clean test jacoco:report jacoco:check
```

### OWASP Dependency-Check
```bash
mvn org.owasp:dependency-check-maven:check
```

### SonarQube (requires server setup)
```bash
mvn sonar:sonar -Dskip.owasp=true 
```

## View Reports

After running the tools, view reports at:

- **JaCoCo Coverage:** `target/site/jacoco/index.html`
- **Checkstyle:** `target/checkstyle-result.xml`
- **OWASP Dependency-Check:** `target/dependency-check-report.html`
- **Test Reports:** `target/surefire-reports/`

## Expected Results

This project intentionally contains issues that tools will detect:

✅ **Checkstyle** will find style violations  
✅ **JaCoCo** will show coverage below threshold  
✅ **SonarQube** will identify security issues and code smells  
✅ **OWASP** will scan dependencies for vulnerabilities  

These are intentional for demonstration purposes!


## Task Description for AI Agent: Java Project for Static Code Analysis Demo

### Overview
You are tasked with creating the majority of a small Java project designed specifically to showcase various static code analysis and quality tools. The goal is to provide a simple, runnable Java application (e.g., with a basic login and static page) that demonstrates the integration and utility of common, free tools used for ensuring code quality in Java projects. This project will serve as a demo for running static code analysis in CI/CD pipelines.

### Instructions

#### 1. Java Project Setup
- Use the provided initial Maven `pom.xml` (with Java version and listed dependencies).
- Create a minimal but complete Java application, for example:
  - Simple user login.
  - Display a static page upon login.
- Structure the project with common Maven conventions and packages.

#### 2. Integrate Static Code Quality and Related Tools
Integrate the following free tools via Maven plugins/configuration, and ensure their usage is reflected in the project:
| Tool                | Type     | What it Analyzes           | Cost      |
|---------------------|----------|----------------------------|-----------|
| JaCoCo              | Coverage | Test execution             | Free      |
| Checkstyle          | Static   | Code style                 | Free      |
| Surefire            | Test     | Runs tests                 | Free      |
| SonarQube           | Static + Platform | Code quality, bugs, security | Free/Paid |
| OWASP Dependency-Check | Dependency | Vulnerabilities         | Free      |

- Configure each tool in the `pom.xml` or appropriate configuration files.
- Add configuration files (e.g., `checkstyle.xml`, SonarQube config, OWASP Dependency-Check config).
- Make sure each tool can be run locally through Maven and produces useful reports.

#### 3. Codebase Tailoring for Tool Reports
- Intentionally include some code patterns or issues so that:
  - Each tool is able to detect/report at least one relevant warning (e.g., style issue for Checkstyle, uncovered test code for JaCoCo, usage of a known vulnerable dependency for OWASP, etc).
- Ensure the code is still runnable, but imperfect for demonstration purposes.

#### 4. CI/CD Pipeline and Quality Guardrails
- Create a GitHub Actions workflow (in `.github/workflows/`) with steps to:
  - Build and test the application.
  - Run static analysis with the above tools.
  - Fail the pipeline or add PR comments if a tool reports issues (e.g., Checkstyle fails, coverage too low, SonarQube blocking).
  - Publish reports/artifacts from each tool in the pipeline.
  - Integrate SonarQube and OWASP Dependency-Check with PR checks.
- Add clear documentation/examples of expected results and how to interpret failures.

#### 5. Use Case Scenarios
- Outline a generic CI/CD workflow describing how developers would:
  - Run these checks locally and in CI.
  - Detect and resolve reported issues.
  - Benefit from automated code quality reports and security checks before merging code.

### Constraints
- Do not add paid dependencies or require infrastructure beyond freely available local or cloud setups.
- Application and pipeline configs must be easily usable for demonstration and onboarding.
- Project must be runnable on a standard developer machine with JDK and Maven.

### Deliverables
- Complete Java project repository.
- All configuration files for tools and CI/CD (`pom.xml`, `checkstyle.xml`, SonarQube, OWASP, etc.).
- Example GitHub Actions YAML workflow.
- Documentation for setup and understanding tool usage/results.

### Reference
This task is intended for use as a demo to teach, showcase, and evaluate static code analysis practices in Java using free, well-known tools in a CI/CD pipeline.
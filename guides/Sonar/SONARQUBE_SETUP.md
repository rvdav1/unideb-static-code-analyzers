# SonarQube Setup Guide

This guide provides step-by-step instructions for setting up SonarQube analysis for this project. You have two options:

1. **SonarCloud** (Recommended - Free for public repositories)
2. **Local SonarQube Server** (Requires installation)

## Option 1: SonarCloud Setup (Recommended)

SonarCloud is the cloud-hosted version of SonarQube, free for public repositories.

### Step 1: Create SonarCloud Account

1. Go to [https://sonarcloud.io](https://sonarcloud.io)
2. Click "Log in" and sign in with your GitHub account
3. Authorize SonarCloud to access your GitHub account

### Step 2: Create a New Project

1. After logging in, click "Create Project"
2. Select "Analyze a new project"
3. Choose your organization (or create one if needed)
4. Select "GitHub" as the source
5. Find and select your repository: `unideb-static-code-analyzers`
6. Click "Set Up"

### Step 3: Get Your Token

1. Go to your account settings (click your avatar → "My Account")
2. Go to "Security" tab
3. Generate a new token (give it a name like "GitHub Actions")
4. **Copy the token immediately** - you won't be able to see it again!

### Step 4: Configure GitHub Secrets

1. Go to your GitHub repository
2. Navigate to: **Settings** → **Secrets and variables** → **Actions**
3. Click "New repository secret"
4. Add the following secrets:

   - **Name:** `SONAR_TOKEN`
   - **Value:** (paste the token from Step 3)

   - **Name:** `SONAR_HOST_URL`
   - **Value:** `https://sonarcloud.io`

   - **Name:** `SONAR_ORGANIZATION`
   - **Value:** (your SonarCloud organization key - found in project settings)

### Step 5: Update Project Key

1. In SonarCloud, go to your project settings
2. Note the **Project Key** (e.g., `your-org_static-code-analysis-demo`)
3. Update `sonar-project.properties` with the correct project key:

```properties
sonar.projectKey=your-org_static-code-analysis-demo
```

### Step 6: Enable SonarQube in GitHub Actions

Edit `.github/workflows/ci.yml` and update the sonarqube job:

```yaml
sonarqube:
  name: SonarQube Analysis
  runs-on: ubuntu-latest
  
  steps:
  - name: Checkout code
    uses: actions/checkout@v4
    with:
      fetch-depth: 0

  - name: Set up JDK 21
    uses: actions/setup-java@v4
    with:
      java-version: '21'
      distribution: 'temurin'
      cache: maven

  - name: Cache SonarQube packages
    uses: actions/cache@v4
    with:
      path: ~/.sonar/cache
      key: ${{ runner.os }}-sonar
      restore-keys: ${{ runner.os }}-sonar

  - name: Build and analyze
    env:
      GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
      SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
      SONAR_HOST_URL: ${{ secrets.SONAR_HOST_URL }}
      SONAR_ORGANIZATION: ${{ secrets.SONAR_ORGANIZATION }}
    run: mvn clean verify sonar:sonar
```

### Step 7: Run Analysis

**Locally:**
```bash
export SONAR_TOKEN=your-token-here
export SONAR_HOST_URL=https://sonarcloud.io
export SONAR_ORGANIZATION=your-org-key

mvn clean verify sonar:sonar
```

**In CI/CD:**
- Push your changes to GitHub
- The workflow will automatically run SonarQube analysis
- View results at: https://sonarcloud.io

---

## Option 2: Local SonarQube Server Setup

If you prefer to run SonarQube locally, follow these steps:

### Step 1: Install SonarQube

**Using Docker (Easiest):**
```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

**Using Homebrew (macOS):**
```bash
brew install sonarqube
brew services start sonarqube
```

**Manual Installation:**
1. Download from [https://www.sonarqube.org/downloads/](https://www.sonarqube.org/downloads/)
2. Extract the archive
3. Navigate to `bin/[OS]` directory
4. Run `sonar.sh start` (Linux/Mac) or `StartSonar.bat` (Windows)

### Step 2: Access SonarQube

1. Open browser: http://localhost:9000
2. Default credentials:
   - Username: `admin`
   - Password: `admin`
3. You'll be prompted to change the password on first login

### Step 3: Create a Project

1. Click "Create Project" → "Manually"
2. Enter:
   - **Project Key:** `static-code-analysis-demo`
   - **Display Name:** `Static Code Analysis Demo`
3. Click "Set Up"

### Step 4: Generate Token

1. Go to: **My Account** → **Security** → **Generate Token**
2. Name it: "Maven Analysis"
3. **Copy the token** - you won't see it again!

### Step 5: Configure Local Analysis

**Update `sonar-project.properties`:**
```properties
sonar.projectKey=static-code-analysis-demo
sonar.host.url=http://localhost:9000
```

**Run analysis:**
```bash
export SONAR_TOKEN=your-local-token-here

mvn clean verify sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=$SONAR_TOKEN
```

### Step 6: Configure GitHub Actions (Optional)

If you want to use local SonarQube in CI/CD, you'll need to:

1. Set up a self-hosted runner, OR
2. Use a tunnel service (like ngrok) to expose your local server, OR
3. Deploy SonarQube to a cloud instance

For most use cases, **SonarCloud is recommended** for CI/CD.

---

## Verification

### Check if SonarQube is Working

1. **Run analysis:**
   ```bash
   mvn clean verify sonar:sonar
   ```

2. **View results:**
   - SonarCloud: https://sonarcloud.io
   - Local: http://localhost:9000

3. **Expected findings:**
   - Security vulnerabilities (plain text passwords)
   - Code smells (unused methods, missing validation)
   - Coverage information
   - Duplicated code
   - Code complexity issues

### Troubleshooting

**Error: "Unable to execute SonarQube"**
- Check if SonarQube server is running
- Verify `SONAR_HOST_URL` is correct
- Check network connectivity

**Error: "Authentication failed"**
- Verify `SONAR_TOKEN` is correct
- Token might have expired - generate a new one
- Check token has proper permissions

**Error: "Project key already exists"**
- Update `sonar-projectKey` in `sonar-project.properties`
- Or delete the existing project in SonarQube

**Analysis takes too long**
- First analysis is slower (downloads analyzers)
- Subsequent analyses are faster
- Large projects take more time

---

## Integration with GitHub Actions

The workflow file (`.github/workflows/ci.yml`) already includes a SonarQube job. To enable it:

1. Set up secrets as described above
2. The job will run automatically on push/PR
3. SonarQube will post PR comments with issues found
4. Quality gates can block merges if configured

### Quality Gate Configuration

In SonarQube/SonarCloud:
1. Go to **Quality Gates**
2. Configure rules (e.g., "No new issues", "Coverage > 50%")
3. The gate will pass/fail based on these rules

---

## Additional Resources

- [SonarCloud Documentation](https://docs.sonarcloud.io/)
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Maven SonarQube Plugin](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/)

---

## Quick Reference

### SonarCloud
```bash
# Set environment variables
export SONAR_TOKEN=your-token
export SONAR_HOST_URL=https://sonarcloud.io
export SONAR_ORGANIZATION=your-org

# Run analysis
mvn clean verify sonar:sonar
```

### Local SonarQube
```bash
# Set environment variables
export SONAR_TOKEN=your-token
export SONAR_HOST_URL=http://localhost:9000

# Run analysis
mvn clean verify sonar:sonar
```

---

**Note:** For this demo project, SonarCloud is recommended as it's free, requires no infrastructure, and integrates seamlessly with GitHub Actions.


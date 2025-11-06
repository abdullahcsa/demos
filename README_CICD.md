# CI/CD Configuration Guide

This project uses GitHub Actions for continuous integration and code quality analysis.

## GitHub Actions Workflows

### 1. Main CI Pipeline (`.github/workflows/ci.yml`)

**Triggers:**
- Push to `master`, `main`, or `develop` branches
- Pull requests to these branches

**Steps:**
1. **Build** - Compiles the project with Maven
2. **Test** - Runs all unit tests
3. **JaCoCo Coverage** - Generates code coverage reports
4. **Codecov Upload** - Uploads coverage to Codecov (optional)
5. **SonarQube Analysis** - Analyzes code quality (if configured)
6. **PR Comments** - Posts coverage report on PRs

**Coverage Threshold:** 80% (configurable)

### 2. SonarCloud Analysis (`.github/workflows/sonarcloud.yml`)

**Triggers:**
- Push to `master`/`main`
- Pull requests

**Requirements:**
- SonarCloud account
- `SONAR_TOKEN` secret configured

## Required GitHub Secrets

Configure these in your repository settings (`Settings > Secrets and variables > Actions`):

### For SonarQube (Self-hosted)
```
SONAR_TOKEN=<your-sonarqube-token>
SONAR_HOST_URL=<your-sonarqube-url>
```

### For SonarCloud
```
SONAR_TOKEN=<your-sonarcloud-token>
```

## Setup Instructions

### 1. SonarCloud Setup

1. Go to [SonarCloud.io](https://sonarcloud.io)
2. Sign in with GitHub
3. Click "+" → "Analyze new project"
4. Select your repository
5. Copy the project key and organization
6. Generate a token: `My Account > Security > Generate Token`
7. Add token as GitHub secret: `SONAR_TOKEN`

Update `.github/workflows/sonarcloud.yml`:
```yaml
-Dsonar.projectKey=YOUR_PROJECT_KEY \
-Dsonar.organization=YOUR_ORGANIZATION \
```

### 2. Self-hosted SonarQube Setup

1. Install SonarQube server
2. Create a project
3. Generate authentication token
4. Add secrets to GitHub:
   - `SONAR_TOKEN`
   - `SONAR_HOST_URL`

### 3. Codecov Setup (Optional)

1. Go to [Codecov.io](https://codecov.io)
2. Sign in with GitHub
3. Enable your repository
4. No token needed for public repos!

## Maven Configuration

The `pom.xml` includes:

```xml
<!-- JaCoCo for code coverage -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
</plugin>

<!-- SonarQube plugin -->
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.10.0.2594</version>
</plugin>
```

## Running Locally

### Run tests with coverage
```bash
mvn clean test jacoco:report
```

### View coverage report
```bash
open target/site/jacoco/index.html
```

### Run SonarQube analysis locally
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=demoBritishSpokenTime \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_TOKEN
```

## Code Quality Gates

### Coverage Requirements
- Overall: 80%
- Changed files: 80%

### SonarCloud Quality Gate
- No new bugs
- No new vulnerabilities
- No new code smells (maintainability rating A)
- Code coverage on new code > 80%
- Duplication < 3%

## Badges

Add these to your README.md:

```markdown
[![CI Pipeline](https://github.com/abd/demoBritSpokTime/actions/workflows/ci.yml/badge.svg)](https://github.com/abd/demoBritSpokTime/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/abd/demoBritSpokTime/branch/master/graph/badge.svg)](https://codecov.io/gh/abd/demoBritSpokTime)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=abd_demoBritSpokTime&metric=alert_status)](https://sonarcloud.io/dashboard?id=abd_demoBritSpokTime)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=abd_demoBritSpokTime&metric=coverage)](https://sonarcloud.io/dashboard?id=abd_demoBritSpokTime)
```

## Troubleshooting

### SonarQube fails with "shallow clone"
- The workflow uses `fetch-depth: 0` to disable shallow clones

### Coverage not uploading
- Check `GITHUB_TOKEN` permissions
- Verify JaCoCo report path: `target/site/jacoco/jacoco.xml`

### Tests fail in CI but pass locally
- Check Java version (CI uses Java 17)
- Check timezone settings
- Review test logs in GitHub Actions

## Additional Features

### Dependabot
- Configured in `.github/dependabot.yml`
- Automatically updates dependencies weekly
- Updates both Maven and GitHub Actions

### Code Owners
- Configured in `.github/CODEOWNERS`
- Auto-assigns reviewers for PRs

## Support

For issues with CI/CD setup, check:
1. GitHub Actions logs
2. SonarCloud/SonarQube dashboard
3. Maven build output

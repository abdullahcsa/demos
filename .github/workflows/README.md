# GitHub Actions Workflows

This directory contains CI/CD workflows for the project.

## Available Workflows

### 1. `ci.yml` - Main CI Pipeline
**Purpose**: Complete CI pipeline with testing, coverage, and code quality

**Runs on**:
- Push to `master`, `main`, `develop`
- Pull requests to these branches

**Features**:
- ✅ Unit tests
- ✅ JaCoCo code coverage (80% threshold)
- ✅ SonarQube/SonarCloud analysis
- ✅ Codecov integration
- ✅ PR comments with coverage report
- ✅ Test result publishing

### 2. `sonarcloud.yml` - SonarCloud Analysis
**Purpose**: Dedicated SonarCloud code quality analysis

**Runs on**:
- Push to `master`, `main`
- Pull requests

**Features**:
- Deep code quality analysis
- Security vulnerability scanning
- Code smell detection
- Technical debt tracking

## Setup Required

### Secrets to Configure

Add these in: `Repository Settings > Secrets and variables > Actions`

#### For SonarCloud (Recommended)
```
SONAR_TOKEN=<your-sonarcloud-token>
```

#### For Self-hosted SonarQube
```
SONAR_TOKEN=<your-sonarqube-token>
SONAR_HOST_URL=<your-sonarqube-url>
```

### Quick Start

1. **Push to GitHub**
   ```bash
   git add .
   git commit -m "Add CI/CD workflows"
   git push origin master
   ```

2. **Configure SonarCloud** (if using)
   - Visit https://sonarcloud.io
   - Import your repository
   - Copy the token
   - Add to GitHub secrets

3. **View Results**
   - Actions tab: See workflow runs
   - Pull Requests: See coverage comments
   - SonarCloud dashboard: See code quality

## Local Testing

Test the workflow locally before pushing:

```bash
# Run tests
mvn clean test

# Generate coverage report
mvn jacoco:report

# View coverage report
open target/site/jacoco/index.html

# Run SonarQube (if configured locally)
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=YOUR_TOKEN
```

## Workflow Status Badges

Add to your README.md:

```markdown
![CI](https://github.com/YOUR_USERNAME/demoBritSpokTime/actions/workflows/ci.yml/badge.svg)
![SonarCloud](https://github.com/YOUR_USERNAME/demoBritSpokTime/actions/workflows/sonarcloud.yml/badge.svg)
```

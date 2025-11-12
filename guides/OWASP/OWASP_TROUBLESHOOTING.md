# OWASP Dependency-Check Troubleshooting

## Known Issue: CVSS v4 Parsing Error

If you encounter this error:
```
Cannot construct instance of CvssV4Data$ModifiedCiaType, problem: SAFETY
```

This is a **known bug in OWASP Dependency-Check 11.1.0** when parsing CVSS v4 data from the NVD API.

## Solutions

### Solution 1: Use NVD API Key (Recommended)

An NVD API key can help because:
1. It increases rate limits (from 5 requests/30 seconds to 50 requests/30 seconds)
2. It may use a different data format that avoids the parsing issue
3. It's free and easy to get

**Get an NVD API Key:**
1. Go to https://nvd.nist.gov/developers/request-an-api-key
2. Fill out the form (it's free)
3. You'll receive an API key via email

**Configure it:**

**Locally:**
```bash
export NVD_API_KEY=your-api-key-here
mvn clean verify
```

**In GitHub Actions:**
- Already configured! Just make sure `NVD_API_KEY` is set as a repository secret.

### Solution 2: Skip OWASP Check Temporarily

If you need to build without OWASP:

```bash
mvn clean verify -Dskip.owasp=true
```

### Solution 3: Run OWASP Separately

Run OWASP Dependency-Check as a separate command:

```bash
# With API key
export NVD_API_KEY=your-key
mvn org.owasp:dependency-check-maven:check

# Or skip it in the main build
mvn clean verify -Dskip.owasp=true
mvn org.owasp:dependency-check-maven:check
```

### Solution 4: Update OWASP Dependency-Check Version

Check if a newer version fixes the issue:

```xml
<owasp-dependency-check.version>11.1.1</owasp-dependency-check.version>
```

Or check the latest version at: https://github.com/jeremylong/DependencyCheck/releases

## Current Configuration

The project is configured to:
- ✅ Use NVD API key if available (`NVD_API_KEY` environment variable)
- ✅ Not fail the build on update errors (`failOnError=false`)
- ✅ Retry up to 3 times on failure
- ✅ Continue on error in CI/CD pipeline

## Testing Your Setup

1. **Check if API key is set:**
   ```bash
   echo $NVD_API_KEY
   ```

2. **Test OWASP with API key:**
   ```bash
   export NVD_API_KEY=your-key
   mvn org.owasp:dependency-check-maven:check
   ```

3. **If it still fails, skip it:**
   ```bash
   mvn clean verify -Dskip.owasp=true
   ```

## GitHub Actions

The workflow is already configured to:
- Use the `NVD_API_KEY` secret
- Continue on error (won't block the pipeline)
- Upload reports as artifacts

Make sure your GitHub repository has the `NVD_API_KEY` secret set:
1. Go to Settings → Secrets and variables → Actions
2. Add `NVD_API_KEY` with your API key value

## Additional Resources

- [OWASP Dependency-Check Documentation](https://jeremylong.github.io/DependencyCheck/)
- [NVD API Documentation](https://nvd.nist.gov/developers/vulnerabilities)
- [Known Issues](https://github.com/jeremylong/DependencyCheck/issues)


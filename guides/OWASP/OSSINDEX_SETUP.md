# OSS Index Analyzer Setup

OSS Index is an additional vulnerability database that complements the NVD database used by OWASP Dependency-Check. It's optional but provides additional coverage.

## Getting OSS Index Credentials

1. **Sign up for a free account:**
   - Go to https://ossindex.sonatype.org/
   - Click "Sign In" or "Register"
   - Sign in with your GitHub, Google, or create a new account

2. **Get your API token:**
   - After logging in, go to your profile/settings
   - Navigate to "API Tokens" or "Credentials"
   - Generate a new token (or use your username and token)

## Configuration

### Local Development

Set environment variables:

```bash
export OSSINDEX_USERNAME=your-username
export OSSINDEX_TOKEN=your-api-token
```

Then run:
```bash
mvn org.owasp:dependency-check-maven:check
```

### Make it Permanent

Add to your `~/.zshrc` (or `~/.bashrc`):

```bash
export OSSINDEX_USERNAME=your-username
export OSSINDEX_TOKEN=your-api-token
```

Then reload:
```bash
source ~/.zshrc
```

### GitHub Actions

The workflow is already configured to use secrets. Add these secrets to your repository:

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Add the following secrets:
   - `OSSINDEX_USERNAME` - Your OSS Index username
   - `OSSINDEX_TOKEN` - Your OSS Index API token

## Verification

To verify it's working, run:

```bash
export OSSINDEX_USERNAME=your-username
export OSSINDEX_TOKEN=your-token
mvn org.owasp:dependency-check-maven:check
```

You should **not** see the warning:
```
[WARNING] Disabling OSS Index analyzer due to missing user/password credentials
```

## Benefits

- ✅ Additional vulnerability coverage beyond NVD
- ✅ Free to use
- ✅ Complements NVD database
- ✅ No rate limits for authenticated users

## Troubleshooting

**Still seeing the warning?**
- Verify environment variables are set: `echo $OSSINDEX_USERNAME`
- Check credentials are correct
- Ensure no typos in variable names

**Want to disable it?**
Set in `pom.xml`:
```xml
<ossIndexAnalyzerEnabled>false</ossIndexAnalyzerEnabled>
```

## Resources

- [OSS Index Documentation](https://ossindex.sonatype.org/doc/auth-required)
- [OSS Index API](https://ossindex.sonatype.org/rest)


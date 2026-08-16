# Docker Setup Guide

## 🔐 Security First

This project includes Docker configuration. **Never commit files with real credentials to Git.**

### Setup Steps:

1. **Copy example files:**
   ```bash
   cp docker-compose.example.yml docker-compose.yml
   cp .env.example .env
   ```

2. **Edit with your credentials:**
   - Open `docker-compose.yml` and replace `your_password_here`
   - Open `.env` and set your database credentials

3. **Start services:**
   ```bash
   docker-compose up --build
   ```

### Files in .gitignore:
- `.env` - Local environment variables (secrets)
- `docker-compose.override.yml` - Local overrides
- Your real `docker-compose.yml` (if using local credentials)

### What's safe to push:
- ✅ `Dockerfile` - No secrets
- ✅ `docker-compose.example.yml` - Template only
- ✅ `.env.example` - Template only
- ✅ `.dockerignore` - Build rules

### What's NOT safe:
- ❌ `.env` - Contains passwords
- ❌ Real `docker-compose.yml` with credentials

## Best Practices:

1. **Never commit `.env` files**
2. **Use `.example` files as templates**
3. **Rotate credentials** if accidentally committed
4. For production, use secret management (Docker Secrets, AWS Secrets Manager, etc.)

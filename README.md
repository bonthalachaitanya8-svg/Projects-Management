# Project Management Phase 4 - PostgreSQL + JPA

This version adds:
- PostgreSQL database
- Spring Data JPA entities
- repositories
- persistent users and projects
- persistent project file metadata
- persistent GitHub links
- login and register starter
- profile update
- role-based visibility

## Demo users
After first run:
- admin@example.com / Password@123
- user@example.com / Password@123

## 1. Create local database
Run this in PostgreSQL:
```sql
CREATE DATABASE project_management_db;
```

## 2. Run backend
```bash
cd backend
mvn clean
mvn spring-boot:run
```

Default local DB config:
- DB URL: jdbc:postgresql://localhost:5432/project_management_db
- username: postgres
- password: postgres

If your password is different, update:
`backend/src/main/resources/application.properties`

## 3. Run frontend
```bash
cd frontend
npm install
npm run dev
```

Frontend:
- http://localhost:5173

## 4. Supabase / Neon later
You can replace DB settings with env vars:
- DB_URL
- DB_USERNAME
- DB_PASSWORD

## Important note
This version keeps auth tokens in memory for simplicity.
So:
- users/projects/files/links are persistent in PostgreSQL
- token sessions reset when backend restarts

## Next recommended phase
- real JWT
- Spring Security
- Supabase storage upload
- folder upload UI

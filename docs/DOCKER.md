# Docker Setup

## Prerequisites
- Docker and Docker Compose installed on your machine.

## Running the Database
This project uses MySQL 8 as its database. We have provided a `docker-compose.yml` file to quickly spin up a local database instance.

### Commands
1. **Start the database**:
   ```bash
   docker-compose up -d
   ```
   This will start MySQL in the background. It may take a few moments for the database to become fully healthy.

2. **Stop the database**:
   ```bash
   docker-compose down
   ```

3. **View logs**:
   ```bash
   docker-compose logs -f
   ```

## Application Configuration
Ensure your `application-local.properties` or `.env` file matches the credentials defined in the `docker-compose.yml` file (default user: `root`, default password: `password`, database: `student_db`).

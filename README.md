# **Elmenus Lite - Backend (Spring Boot + PostgreSQL + pgAdmin)**

This project is a Spring Boot application with PostgreSQL as the database and pgAdmin for database management. It uses Docker for containerization.

---

## **Prerequisites**
- **Docker** ([Install Docker](https://docs.docker.com/get-docker/))
- **Docker Compose** ([Install Docker Compose](https://docs.docker.com/compose/install/))
- **Java 17+** (for local development without Docker)

---

## **Setup & Run**

### **1. Clone the Repository**
```bash
git clone <repository-url>
cd elmenus-lite
```

### **2. Configure Environment Variables**
Create a `.env` file in the root directory with the following variables:
```env
# PostgreSQL
POSTGRES_VERSION=15
DB_NAME=elmenusdb
DB_USER=postgres
DB_PASSWORD=secretpassword

# Spring Boot (optional, can override in docker-compose)
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/${DB_NAME}
SPRING_DATASOURCE_USERNAME=${DB_USER}
SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
```

### **3. Build & Run with Docker Compose**
```bash
docker-compose up --build
```
- The app will be available at: **`http://localhost:8080`**
- pgAdmin will be available at: **`http://localhost:9090`**

#### **Optional Flags**
- Run in detached mode (background):
  ```bash
  docker-compose up -d
  ```
- Stop containers:
  ```bash
  docker-compose down
  ```
- Remove volumes (resets database):
  ```bash
  docker-compose down -v
  ```

---

## **Accessing Services**

### **1. PostgreSQL Database**
- **Host:** `postgres` (inside Docker network)
- **Port:** `5432`
- **Default Database:** `elmenusdb`
- **Username:** `postgres`
- **Password:** `secretpassword`

### **2. pgAdmin (Database GUI)**
- **URL:** `http://localhost:9090`
- **Login Email:** `admin@admin.com`
- **Login Password:** `admin`

#### **Auto-Configured Server in pgAdmin**
- **Name:** `PostgreSQL (Docker)`
- **Host:** `postgres`
- **Port:** `5432`
- **Maintenance DB:** `elmenusdb`
- **Username:** `postgres`
- **Password:** `secretpassword`

---

## **Development (Without Docker)**

### **1. Run PostgreSQL Locally**
Install PostgreSQL and create a database:
```bash
createdb elmenusdb -U postgres
```

### **2. Run Spring Boot App**
```bash
./mvnw spring-boot:run
```
- The app will run on `http://localhost:8080`.

---

## **Troubleshooting**

### **1. Database Connection Issues**
- Ensure PostgreSQL is running (`docker-compose logs postgres`).
- Check `.env` variables match in `docker-compose.yml`.

### **2. pgAdmin Not Connecting**
- Verify `postgres` is healthy:
  ```bash
  docker-compose ps
  ```
- Manually add the server in pgAdmin if auto-config fails.

### **3. Rebuilding the App**
If you make code changes:
```bash
docker-compose up --build
```

---

## **Project Structure**
```
.
├── src/                  # Spring Boot source code
├── .mvn/                 # Maven wrapper
├── mvnw                  # Maven wrapper script
├── pom.xml               # Maven dependencies
├── Dockerfile            # Spring Boot container setup
└── compose.yml    # Defines PostgreSQL, pgAdmin, and app services
```

---

## **License**
[GNU v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)



---

🚀 **Happy Coding!** Let us know if you need help.
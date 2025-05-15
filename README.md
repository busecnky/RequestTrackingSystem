# 🎟️ Request Tracking System

A full-stack request tracking system where users can create support tickets and admins can view, filter, and respond to them.

Built with:
- **Spring Boot (Java 17+)**
- **React 18+**
- **JWT Authentication**
- **Role-based Access Control (Admin/User)**

---

## 🔧 Technologies Used

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Security (JWT-based)
- PostgreSQL
- Spring Data JPA
- Global Exception Handling
- Swagger UI for API documentation

### [Frontend](https://github.com/busecnky/RequestTrackingSystemFrontend)
- React 18+
- React Router DOM
- Axios
- Formik & Yup (form handling and validation)
- Material UI

---

## 🚀 Getting Started

### 1. Backend Setup

#### ✅ Prerequisites
- Java 17+
- PostgreSQL
- Gradle

#### 📦 Environment Variables

Create a `.env` file or configure environment variables (or use `application.yml`) with the following:

```env
# PostgreSQL Database
DB_URL=jdbc:postgresql://localhost:5432/RequestTrackingDB
PGUSER=your_postgres_username
PGPASS=your_postgres_password

# JWT Secret
SECRET=your_very_secret_key

### 🔑 Demo Users
####👨‍💼 Admin User
Username: admin1
Password: admin123
Role: ADMIN
Permissions: View all tickets, filter by status, respond to tickets

Admin User is created when the project start.

####👤 Regular User
Username: user1
Password: password123
Role: USER
Permissions: Create & view personal tickets



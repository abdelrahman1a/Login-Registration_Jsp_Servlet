# Login and Registration Web Application (Servlet + JSP + JDBC + PostgreSQL)

A simple and secure web application built using **Java Servlets**, **JSP**, and **JDBC** for PostgreSQL database integration.  
This project implements **user registration, login, and password recovery via OTP sent to email**.  

---

## 🛠️ Features

### 1. User Registration
- Users can create an account with:
  - **Username**
  - **Email**
  - **Password**
  - **Phone number**
- Server-side validation ensures:
  - All fields are filled
  - Password and repeated password match
  - Password length is at least 8 characters
- Data is stored securely in a **PostgreSQL database** using **JDBC** with **Prepared Statements** to prevent SQL injection.

### 2. User Login
- Users can log in using **email** and **password**.
- Implements **session management** to keep the user logged in.
- Secure authentication using **Prepared Statements**.

### 3. Forgot Password with OTP
- Users can reset their password if forgotten.
- **OTP (One-Time Password)** is generated and sent to the user's email.
- OTP is stored in the **HTTP session** and validated before allowing password change.
- Email sending implemented using **JavaMail (jakarta.mail) API** with SMTP configuration.
- Users can set a new password after OTP verification.

### 4. Database Integration
- Uses **PostgreSQL** as backend database.
- JDBC used for database connectivity.
- Tables used:
  - `users` (id , username, email, password, phone)

### 5. Security
- **Prepared Statements** prevent SQL injection attacks.
- Server-side validation ensures data integrity.

---

## 💻 Tech Stack
- Java **Servlets** and **JSP**
- **JDBC** for database connectivity
- **PostgreSQL** as the database
- **JavaMail API** for sending OTP emails
- **HTML / CSS / Bootstrap / Js** for frontend

---


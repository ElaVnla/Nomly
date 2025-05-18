
![image](https://github.com/user-attachments/assets/54876391-1bde-45f6-838c-a26b71af1af3)

---
<br>

## 📝 About the Project
<br>

**Nomly** is a social-based food discovery and decision-making platform that simplifies group dining experiences. The app helps users discover nearby restaurants, vote on food choices via a swipe-based interface, and coordinate meetups with friends effortlessly. Designed for spontaneity and inclusiveness, Nomly turns the question “Where should we eat?” into a fun and interactive group activity.

🚀 **Award-Winning Project**: 2nd Place @ *Singtel Information System and Programming 2025*

---
<br>

## 🧩 Key Features
<br>

- 🔐 Secure Login & Signup System  
- ✅ Email OTP Account Verification  
- 🎴 Tinder-style Restaurant Swiping  
- 📍 Location-based Restaurant Retrieval 
- 👥 Group Session Creation with Unique Join Codes  
- 🗳️ Real-Time Group Voting to Decide Where to Eat  
- 🔄 Backend API for Full Session and Restaurant Handling  
<br>

---
<br>

## 🛠️ Tech Stack
<br>

- **Frontend**: Java (Android Studio), Material Design Components  
- **Backend**: Spring Boot (Java), MySQL  
- **Database**: SQL with image blob support  
- **API Services**: REST API with Postman Documentation  
- **Authentication**: Email OTP using custom backend logic  
<br>

---
<br>

## 📸 Project Screenshots

(Coming soon...)

---

## 🧑‍💻 Installation & Setup Instructions
<br>

### 1. 📦 Database Setup
<br>

```bash
cd database/
```

- Run `create database with images.sql` to initialize the full database (includes restaurant images).
- Run the optional dummy values SQL script to populate test data.
- Start your local MySQL server.
<br>

### 2. 🔧 Backend Configuration
<br>

```bash
cd nomlybackend/
```

- Open in IntelliJ (recommended for best experience).
- Navigate to:
  ```
  target/classes/application.properties
  ```
- Edit **lines 4–6** with your MySQL DB connection info:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/nomly
  spring.datasource.username=yourUsername
  spring.datasource.password=yourPassword
  ```
<br>

### 3. ▶️ Running the Backend
<br>

- In IntelliJ, go to:
  ```
  src/main/java/
  ```
- Right-click and run:
  ```
  NomlyBackendApplication.java
  ```

---
<br>

## 🔌 API Reference

Explore and test the API with full documentation:

📄 [Postman API Docs](https://documenter.getpostman.com/view/9125226/2sB2cX7LRf)

---

## 🤝 Team Credits

- 👨‍💻 **Backend Lead**: *Junzhi, Gynn, Qin Xin, Toni, Erika*  
  Handled all backend development, database integration, OTP verification, and API handling.

- 🎨 **Frontend & Design**: *Toni, Erika, Shonim, Jing Yu*  
  Led UI/UX design and Android frontend implementation, API handling, ensuring timely milestones and smooth styling.

- 🧩 **Integration & QA**: *Erika, Junzhi, Toni, Shonim, Gynn*  
  Oversaw frontend-backend integration, conducted functional testing, managed team communication and Git.

---
<br>

## 🏆 Recognition

- 🥈 2nd Place @ Singtel Information System and Programming 2025  
- 🎓 Built for SUTD Information Systems and Programming Module
- 🌟 Showcased at Final Demo Day Event  
<br>

---

## 📫 Contact
<br>

For inquiries, issues or contributions, feel free to reach out via GitHub or LinkedIn!

---

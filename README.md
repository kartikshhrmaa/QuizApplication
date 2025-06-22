# 🧠 Quiz Web Application

A full-stack Spring Boot application that allows users to take dynamically generated quizzes based on a pool of questions stored in a MySQL database. The system supports quiz creation, question management, and real-time evaluation — making it a scalable and modular backend solution for assessment platforms.

> Built using RESTful API architecture and clean MVC layering, this project demonstrates strong backend development skills, database integration, and production-level coding practices.

---

## 🚀 Features

- 📋 **Create, Read, Update, Delete (CRUD)** operations for quiz questions
- 🎯 **Random quiz generation** from a stored set of questions
- 🗂 **Filter questions** by category or difficulty level
- 🧮 **Evaluate submitted answers** in real-time with scoring logic
- 🛠️ **Spring Boot MVC architecture** with RESTful endpoints
- ✅ **Input validation**, **error handling**, and clean code structure

---

## 🛠️ Tech Stack

| Layer        | Technology                             |
|--------------|-----------------------------------------|
| **Backend**  | Java 17, Spring Boot, Spring MVC        |
| **Database** | MySQL, Spring Data JPA                  |
| **Tools**    | Maven, Postman (API testing), Eclipse/STS |

---

## 📁 Folder Structure

src/
└── main/
├── java/com/example/quiz/
│ ├── controller/ # API endpoints
│ ├── model/ # Entity classes (e.g., Question)
│ ├── repository/ # JPA interfaces
│ ├── service/ # Business logic
│ └── dto/ # Request/response wrappers
└── resources/
├── application.properties

## ⚙️ Getting Started

1. **Clone the repo**:
   ```bash
   git clone https://github.com/your-username/quiz-app.git
   cd quiz-app

2. **Configure MySQL in application.properties**:
spring.datasource.url=jdbc:mysql://localhost:3306/quizdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

3. **Run the applicaiton **
./mvnw spring-boot:run

📊 Sample Question Object
{
  "question": "What is JPA in Spring Boot?",
  "option1": "Java Persistence API",
  "option2": "Java Programming Architecture",
  "option3": "Java Platform Adapter",
  "option4": "None of the above",
  "correctAnswer": "Java Persistence API",
  "category": "springboot",
  "difficulty": "medium"
}

## 🙋‍♂️ Author

**Kartik Sharma**  
Java Developer | Spring Boot Enthusiast

[🔗 LinkedIn]([https://www.linkedin.com/in/your-profile](https://www.linkedin.com/in/kartiksharma-tech-enthusiast/))


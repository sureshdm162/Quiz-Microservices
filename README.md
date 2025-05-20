# Quiz-Microservices

This is a **microservices-based quiz application** that allows users to create and attempt quizzes. It is built using **Spring Boot**, utilizes **Eureka Service Discovery**, and is routed through an **API Gateway**. The services communicate internally using **OpenFeign**.

---

##  Architecture Overview

This project is composed of the following components:

1. **Service Registry** - Eureka Server for service discovery
2. **API Gateway** - Routing and load balancing using Spring Cloud Gateway
3. **Quiz-Service** - Core service for creating and managing quizzes
4. **Question-Service** - Internal service for managing questions

---

##  Databases Used

**MySQL** is used as the database system, with two separate databases for modularity:

### 1. `questiondb`
Contains the `question` table with the following schema:

+-----------------+--------------+------+-----+---------+----------------+
| Field           | Type         | Null | Key | Default | Extra          |
+-----------------+--------------+------+-----+---------+----------------+
| ID              | int          | NO   | PRI | NULL    | auto_increment |
| category        | varchar(255) | YES  |     | NULL    |                |
| difficultyLevel | varchar(20)  | YES  |     | NULL    |                |
| option1         | varchar(255) | YES  |     | NULL    |                |
| option2         | varchar(255) | YES  |     | NULL    |                |
| option3         | varchar(255) | YES  |     | NULL    |                |
| option4         | varchar(255) | YES  |     | NULL    |                |
| question_title  | varchar(255) | YES  |     | NULL    |                |
| right_answer    | varchar(255) | YES  |     | NULL    |                


### 2. `quizdb`
Contains two tables:

#### `quiz` Table
+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | int          | NO   | PRI | NULL    | auto_increment |
| title | varchar(255) | YES  |     | NULL    |                |
+-------+--------------+------+-----+---------+----------------+



#### `quiz_question_ids` Table
+--------------+------+------+-----+---------+-------+
| Field        | Type | Null | Key | Default | Extra |
+--------------+------+------+-----+---------+-------+
| quiz_id      | int  | NO   | MUL | NULL    |       |
| question_ids | int  | YES  |     | NULL    |       |
+--------------+------+------+-----+---------+-------+


##  Microservices Overview

### 1. Service Registry (`service-registry`)
- Built using `spring-cloud-starter-netflix-eureka-server`
- Manages service discovery across microservices

### 2. API Gateway (`api-gateway`)
- Built using `spring-cloud-starter-gateway`
- Routes incoming requests to the appropriate microservice
- Acts as the single entry point for all external requests

### 3. Quiz Service (`quiz-service`)
- Public-facing service that:
  - Creates quizzes
  - Retrieves quizzes by ID
  - Submits quizzes for evaluation
  - Communicates with `question-service` using **OpenFeign**

### 4. Question Service (`question-service`)
- Internal-only service that:
  - Manages questions
  - Performs CRUD operations on the question table
- Not exposed directly to users; only accessed by `quiz-service`









##  Technologies Used

- **Spring Boot**
- **Spring Cloud Eureka**
- **Spring Cloud Gateway**
- **OpenFeign**
- **MySQL**
- **Maven**
- **Java JDK-23**

---

##  Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- MySQL Server
- IntelliJ IDEA or any IDE

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone  https://github.com/sureshdm162/Quiz-Microservices.git

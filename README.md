# Fitness Tracker REST API

A fully relational backend application built to manage user workout data, track progressive overload, and serve a comprehensive catalog of exercises.

## 🚀 Core Features

* **Relational Database Architecture:** Fully normalized PostgreSQL database linking Users, Workouts, Sets, and a master Exercise Catalog.
* **Session Management:** Users can create isolated workout "folders" to group their sets by date or routine (e.g., "Heavy Push Day").
* **Granular Metric Tracking:** Logs specific reps and weights (supporting fractional plates) for every single set performed.
* **Master ETL Catalog:** Integrated a custom ETL pipeline using Python to clean and load thousands of exercises into the database for rapid querying.

## 🛠️ Tech Stack

* **Backend:** Java 17, Spring Boot, Spring Web
* **Data Access:** Hibernate, Spring Data JPA
* **Database:** PostgreSQL
* **Build Tool:** Maven
* **Testing:** Postman

## 🗄️ Database Schema Blueprint

The application utilizes a strict standard relational model to ensure data integrity:
1.  **`users`**: The top-level entity owning all subsequent workout data.
2.  **`workouts`**: A One-to-Many relationship from Users, acting as a folder for a specific gym session.
3.  **`workout_sets`**: The core join table. Contains a Many-to-One relationship to `workouts` (when it happened) and a Many-to-One to `exercises` (what movement was performed), along with the reps and weight data.
4.  **`exercises`**: A master dictionary of movements, mechanics, and targeted muscle groups.

## ⚙️ How to Run Locally

1. Clone the repository: `git clone https://github.com/das622/Fitness-Data-App.git`
2. Create a PostgreSQL database named `gym_exercises`.
3. Update the `application.properties` file with your specific PostgreSQL credentials.
4. Run the Spring Boot application. The server will start on `localhost:8080`.
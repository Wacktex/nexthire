# NEXTHIRE – Career Guidance Web Application

A web application that analyzes your CV and GitHub profile to provide personalized career guidance using Google Gemini AI.

---

## Project Overview

NEXTHIRE accepts a user's CV text, GitHub profile URL, and target job role. It then performs analysis using simple Java logic and calls the Google Gemini API to generate career recommendations. Results are stored in an SQLite database and displayed on a results page.

---

## Folder Structure

```
nexthire/
├── src/main/java/com/nexthire/
│   ├── model/
│   │   ├── UserProfile.java       - Holds all user data together
│   │   ├── CVData.java            - Stores CV text and extracted info
│   │   ├── GitHubProfile.java     - Stores GitHub profile data
│   │   ├── TargetRole.java        - Stores role name and required skills
│   │   ├── Finding.java           - Stores one analysis finding
│   │   └── Recommendation.java    - Stores one recommendation
│   ├── repository/
│   │   ├── RoleRepository.java    - Stores predefined roles in memory
│   │   └── UserRepository.java    - Saves results to SQLite database
│   ├── analysis/
│   │   ├── CVAnalyzer.java        - Checks CV for missing sections
│   │   ├── GitHubAnalyzer.java    - Checks GitHub activity
│   │   ├── SkillGapAnalyzer.java  - Compares user skills to role
│   │   └── RecommendationEngine.java - Converts findings to recommendations
│   ├── service/
│   │   ├── LLMService.java        - Calls Google Gemini API
│   │   └── UserService.java       - Handles user profile building
│   ├── controller/
│   │   └── WebController.java     - Handles web routes (GET / and POST /analyze)
│   └── app/
│       └── NexthireApplication.java - Main Spring Boot entry point
├── src/main/resources/
│   ├── templates/
│   │   ├── index.html             - Input form page
│   │   └── result.html            - Results display page
│   ├── static/
│   │   ├── style.css              - All CSS styling
│   │   └── script.js              - All JavaScript
│   └── application.properties     - App configuration
├── database/
│   └── nexthire.db                - SQLite database (auto-created on first run)
├── diagrams/
│   ├── uml_class_diagram.png      - UML class diagram
│   └── use_case_diagram.png       - Use case diagram
├── pom.xml                        - Maven build configuration
└── README.md                      - This file
```

---

## Setup Instructions

### Prerequisites

- Java 17 or higher installed
- Maven 3.6 or higher installed
- Internet connection (for Gemini API calls)

---

### Step 1 – Get a Free Gemini API Key

1. Go to [https://aistudio.google.com/](https://aistudio.google.com/)
2. Sign in with your Google account
3. Click **"Get API Key"**
4. Copy the generated API key

---

### Step 2 – Add Your API Key

Open this file:

```
src/main/resources/application.properties
```

Find this line:

```
gemini.api.key=YOUR_GEMINI_API_KEY_HERE
```

Replace `YOUR_GEMINI_API_KEY_HERE` with your actual Gemini API key.

---

### Step 3 – Create the Database Folder

Run this command in the project root folder:

```bash
mkdir database
```

The SQLite database file (`nexthire.db`) will be created automatically when the app starts.

---

### Step 4 – Run the Application

```bash
mvn spring-boot:run
```

---

### Step 5 – Open in Browser

Go to: [http://localhost:8080](http://localhost:8080)

---

## How to Use

1. Paste your CV text into the textarea
2. Enter your GitHub profile URL (e.g. `https://github.com/yourusername`)
3. Select your target role from the dropdown
4. Enter your GitHub repository count and contribution count
5. Click **"Run Analysis"**
6. Wait 10-20 seconds for the AI to process
7. View your findings, recommendations, and AI-generated advice on the results page

---

## How the System Works (for viva explanation)

| Step | Component | What it does |
|------|-----------|--------------|
| 1 | `WebController` | Receives form data from the browser |
| 2 | `UserService` | Builds the `UserProfile` object |
| 3 | `CVAnalyzer` | Checks for missing sections in the CV |
| 4 | `GitHubAnalyzer` | Checks repository and contribution counts |
| 5 | `SkillGapAnalyzer` | Compares user skills to role requirements |
| 6 | `RecommendationEngine` | Converts findings into recommendations |
| 7 | `LLMService` | Calls Google Gemini API for AI advice |
| 8 | `UserRepository` | Saves results to the SQLite database |
| 9 | `WebController` | Sends everything to `result.html` for display |

---

## Tech Stack

| Layer      | Technology              |
|------------|-------------------------|
| Backend    | Java 17, Spring Boot 3  |
| Frontend   | HTML, CSS, JavaScript   |
| Templates  | Thymeleaf               |
| Database   | SQLite (raw JDBC)       |
| AI / LLM   | Google Gemini API (free)|
| Build Tool | Maven                   |

---

## Supported Target Roles

- DevOps Engineer
- Backend Developer
- Frontend Developer
- Data Scientist
- Full Stack Developer

---

## Database Schema

Table name: `user_results`

| Column          | Type    | Description                    |
|-----------------|---------|--------------------------------|
| id              | INTEGER | Auto-incremented primary key   |
| cv_text         | TEXT    | The CV text submitted by user  |
| github_url      | TEXT    | GitHub profile URL             |
| target_role     | TEXT    | Selected target role           |
| recommendations | TEXT    | Generated recommendations text |
| created_at      | TEXT    | Date and time of submission    |

---

## Common Issues and Fixes

**Problem:** Application fails to start  
**Fix:** Make sure Java 17+ is installed. Run `java -version` to check.

**Problem:** AI recommendations say "unavailable"  
**Fix:** Check that your Gemini API key is correctly set in `application.properties`.

**Problem:** Database error on startup  
**Fix:** Make sure the `database/` folder exists in the project root. Run `mkdir database`.

**Problem:** Port 8080 already in use  
**Fix:** Change the port in `application.properties`: `server.port=9090`

---

## Academic Project Information

**Project Name:** NEXTHIRE – Career Guidance Web Application  
**Technology:** Java, Spring Boot, HTML, CSS, JavaScript, SQLite, Google Gemini API  
**Purpose:** Final Year B.Tech Academic Project  

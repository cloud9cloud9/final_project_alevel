# final_project_alevel

**Description:**
final-project-alevel is a REST API project with focus on security and using external API
It features user authentication and authorization using JWT tokens, access control based on user roles,
and a relational database for persistent data storage. The application is designed with a clean architecture,
separating concerns into repository, service, and controller layers, alongside utility classes for common functions.

**Features:**

1.REST API: Exposes endpoints for managing resources, including CRUD operations.
2.Authentication and Authorization: Implements JWT-based authentication and role-based access control to endpoints.
3.Relational Database Integration: Utilizes a relational database to store and manage application data.
# Project Structure

## Repository
Data access layer for interacting with the database.

## Service
Contains business logic and data processing.

## Util
Utility classes and common functions.

## Controller
REST controllers to handle API requests.

# API Endpoints

| Endpoint                    | Method | Description                               | Required Role | Parameters/Body |
|-----------------------------|--------|-------------------------------------------|---------------|-----------------|
| /api/v1/auth/registration   | POST   | Registration users                        | None          |                 |
| /api/v1/auth/login          | POST   | Login users                               | None          |                 |
| /api/v1/auth/refresh        | PUT    | Update a unique Token                     | USER, ADMIN   |                 |
| /api/v1/user/{id}           | GET    | Get user by id                            | USER, ADMIN   | User id         |
| /api/v1/users               | GET    | Get all user                              | ADMIN         |                 |
| /api/v1/user                | PUT    | Update a user                             | USER, ADMIN   |                 |
| /api/v1/user                | DELETE | DELETE a user                             | USER, ADMIN   |                 |
| /test                       | GET    | Just for test authority                   | USER, ADMIN   |                 |
| /api/v1/movie/results       | GET    | Get all movie from external API           | None          |                 |
| /api/v1/movie/result        | GET    | GET one movie by imdbId from external API | None          |                 |
| /api/v1/movie/{imdbId}      | PUT    | Update a movie                            | ADMIN         | Movie imdb_id   |
| /api/v1/movie/{imdbId}      | DELETE | DELETE a movie                            | ADMIN         | Movie imdb_id   |
| /api/v1/comment/{imdbId}    | POST   | CREATE a comment                          | USER, ADMIN   | Movie imdb_id   |
| /api/v1/comment/{commentId} | PUT    | UPDATE a comment                          | USER, ADMIN   | Comment id      |
| /api/v1/comment/{commentId} | DELETE | DELETE one comment                        | USER, ADMIN   | Comment id      |
| /api/v1/favorite/{imdbId}   | DELETE | DELETE a movie from favorite              | USER, ADMIN   | Movie imdb_id   |
| /api/v1/favorite            | GET    | GET all favorite movie this user          | USER, ADMIN   |                 |
| /api/v1/favorite/{imdbId}   | GET    | Get favorite movie by imdbId              | USER, ADMIN   | Movie imdb_id   |
| /api/v1/favorite            | POST   | ADD movie to favorite                     | USER, ADMIN   |                 |

## Parameters and Body Details

We utilize Swagger for documenting the parameters and body details of each API endpoint. 
Swagger provides a comprehensive way to describe and visualize API specifications, making it easier to understand the request payloads and parameters required for each endpoint.
For detailed information on the parameters and body details of each endpoint, please refer to our Swagger documentation.

## Getting Started

### Prerequisites
- JDK 17 or later
- Maven
- A relational database (PostgreSQL)

### Setup
1. Clone the repository to your local machine.
2. Configure the database connection in `src/main/resources/application.properties`.
3. Run the database migration scripts located in `src/main/resources/db/migration` to set up your database schema.
4. Build the project using Maven: `mvn clean install`.
5. Run the application: `java -jar target/your-application.jar`.

## Usage

After starting the application, you can interact with the API using tools like Postman or cURL. Below are some example requests:

### Authenticate

To authenticate, send a POST request to the following endpoint:

```bash
curl -X POST \
  http://localhost:8080/api/v1/auth/registration \
  -H 'Content-Type: application/json' \
  -d '{
    "userName": "vlad",
    "password": "password",
    "email": "vlad@gmail.com"
}'
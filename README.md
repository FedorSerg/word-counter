## Demo CRUD project (PostgreSQL, Hibernate) 

This is a simple demonstration project to show some coding best practise.
It has a __RESTful API__ for a simple social media application. The project uses __Spring Boot__, __Hibernate__, and __PostgreSQL__.
The application allows users to create and view posts, follow other users, and like posts (title, body, author).
The __Hibernate__ is used to persist the post and user data in the database.

## How to run:

### Prepare database
- If you have a stand with an empty DB (PSQL):
  - add these environment variables for the application, changing the XXXs to real values:
  `POSTGRESQL_HOST=XXX;POSTGRESQL_PORT=XXX;POSTGRESQL_DATABASE=XXX;POSTGRESQL_USER=XXX;POSTGRESQL_PASSWORD=XXX`
- If you want to run database locally (you need to have Docker installed):
  - go to the project root in terminal (src\main\java\com\example\generativeai) and run `docker-compose up`. You can check the file docker-compose.yml and update the ports or the database name, password and login there
  - if you didn't change the docker-compose.yml, then add these environment variables for the application:
  `POSTGRESQL_HOST=localhost;POSTGRESQL_PORT=5000;POSTGRESQL_DATABASE=genai;POSTGRESQL_PASSWORD=genai;POSTGRESQL_USER=genai`

### Generate code coverage report
Run `mvn clean install` command. The report will be generated in 'target/site/jacoco'.

### Code checks report
Run `mvn checkstyle:check` command: all the failed checks will be shown in console.

### Code complexity metrics
Code complexity metrics can be found in 'target/site/jacoco/index.html' after generating code coverage reports.

Cyclomatic Complexity (`cxty` column) is the minimum number of paths that can, in (linear) combination, generate all possible paths through a method.

## Tasks with low level complexity

### Task 3

Build a __RESTful API__ for a simple social media application using __Spring Boot__, __Hibernate__, and __PostgreSQL__.
The application should allow users to create and view posts, follow other users, and like posts.
Each post should have a title, body, and author.
Use __Hibernate__ to persist the post and user data in the database.

### Acceptance Criteria:
- There are all the required codes and configs in the repository to run the application.
- There is a readme.md file with the application description and instructions on how to run it.
- Code is of good quality and easy to read and understand.
- There are unit tests in place, coverage >80%
- There are quality checks (coverage, complexity, check style)
- ChatGPT conversation logs are attached in the file chat.log
- Short feedback for each task added to readme.md in the following format:
  - Was it easy to complete the task using AI?
  - How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics)
  - Was the code ready to run after generation? What did you have to change to make it usable?
  - Which challenges did you face during completion of the task?
  - Which specific prompts you learned as a good practice to complete the task?

## How to run:

### Prepare database
- If you have a stand with empty DB:
  - add these environment variables for the application, changing the XXXs to real values:
  `POSTGRESQL_HOST=XXX;POSTGRESQL_PORT=XXX;POSTGRESQL_DATABASE=XXX;POSTGRESQL_USER=XXX;POSTGRESQL_PASSWORD=XXX`
- If you want to run database locally:
  - install Docker, if you don't have it yet
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

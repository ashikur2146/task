Basic Task Management Project

Features:
---------
Token based stateless user authentication\
Endpoint security\
CRUD and Search operations on task and projects

Admin and user operations:
--------------------------
• Create project\
• Get all projects\
• Delete project\
• Create task\
• Edit task (change description, status, due date). Closed tasks cannot be edited.\
• Get task\
• Search tasks\
◦ Get all by project\
◦ Get expired tasks (due date in the past)\
◦ By status

ADMIN additionally can do:
-------------------------
• Get all tasks by user\
• Get all projects by user

Built With:
-----------
Java 11\
Spring Boot\
JWT Spring Security\
MySQL\
Maven

TEST USERS:
-----------
ADMIN / ADMIN,\
user1 / user1,\
user2 / user2,\
user3 / user3

How to run the project locally:-
-------------------------------
Required tools:
---------------
# Java 11 jdk
# Git client
# mysql client
# Maven
# REST Client (for example, Postman)

Follow the steps below to execute the application:-
-------------------------------------------------

**Step 1:** Clone the project using git client from git repository with the following command (**git clone https://github.com/ashikur2146/task.git**) in your local machine.	
**Step 2:** navigate to **../task/Task-Management/src/main/resources** directory

**step 3:** open the **application.properties** in a text editor and update the following credentials:
            **spring.datasource.username**=<<my client root username>>
	    **spring.datasource.password**=<<my client root password>>

**step 4:** navigate to **task/Task-Management/** where **pom.xml** is located\
**step 5:** now open the **cmd terminal** in that path and execute command **mvn package -Dmaven.test.skip=true**\
**step 6:** if build is successful, navigate to **/target** directory and open cmd terminal in that path
**step 7:** execute command "java -jar Task-Management-0.0.1-SNAPSHOT.war"

if messages like the following are shown in the terminal, then the application is running on port 8080.\

Tomcat started on port(s): 8080 (http) with context path ''\
Started TaskManagementApplication in 18.773 seconds (JVM running for 20.146)



Endpoints with sample request and response:-
-------------------------------------------
POST: http://localhost:8080/authenticate\
POST: http://localhost:8080/projects/create\
POST: http://localhost:8080/tasks/create\

GET: http://localhost:8080/projects/all\
GET: http://localhost:8080/projects/user/username\
GET: http://localhost:8080/projects/project-id\

GET: http://localhost:8080/tasks/all\
GET: http://localhost:8080/tasks/user/username\
GET: http://localhost:8080/tasks/task-id\
GET: http://localhost:8080/tasks/project/project-id\
GET: http://localhost:8080/tasks/status/status\
GET: http://localhost:8080/tasks/expired\

PUT: http://localhost:8080/tasks/update/task-id\

DELETE: http://localhost:8080/projects/delete/project-id\

User Authentication:-
---------------------
POST: http://localhost:8080/authenticate
Request Body:
-------------
{
	"username":"ADMIN",
	"password":"ADMIN"
}

{
	"username":"user1",
	"password":"user1"
}

Response:
----------
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBRE1JTiIsImV4cCI6MTYxMzc1NjgzMSwiaWF0IjoxNjEzNzQ5NjMxfQ.6bIHK6zED3r8JVz4dQrC7xLh17_lP6-V68yCErDbog2hoflCcyXB2EwpNkr9B9XY_nkUJDQSx5GMoeo3yiPbvQ"
}

Project Creation & Retrieve:-
------------------------------
POST: http://localhost:8080/projects/create
GET: http://localhost:8080/projects/all
Request Body:
------------
{
	"name":"project 01"
}

Possible Responses:
------------------
{
    "message": "Project creation successful!"
}

{
    "message": "Project already exists."
}

task creation:-
-------------
POST: http://localhost:8080/tasks/create
Request Body:
------------
{
	"title":"task01 of project 01",
	"description":"task01 of project 01",
	"status":"OPEN",
	"project":{
		"name":"project 01"
	},
	"endDate":"2021-02-16" <-- (YYYY-MM-DD)
}

Response Body:
-------------
{
    "message": "Task creation is successful!"
}

{
    "message": "Project does not exist."
}


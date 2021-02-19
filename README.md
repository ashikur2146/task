Basic Task Management Project

Features:
---------
Token based stateless user authentication
Endpoint security
CRUD and Search operations on task and projects

Admin and user operations:
--------------------------
• Create project
• Get all projects
• Delete project
• Create task
• Edit task (change description, status, due date). Closed tasks cannot be edited.
• Get task
• Search tasks
◦ Get all by project
◦ Get expired tasks (due date in the past)
◦ By status

ADMIN additionally can do:
-------------------------
• Get all tasks by user
• Get all projects by user

Built With:
-----------
Spring Boot
Spring Security
MySQL
Maven
JWT

TEST USERS:
-----------
ADMIN / ADMIN,
user1 / user1,
user2 / user2,
user3 / user3

How to run the project:-
-----------------------
**Step 1:** git clone 
**Step 2:** mvn clean install
**step 3:** run using IDE / directly on apache tomcat app server

<<-- Heroku java deployment has to be added -->>


Endpoints with sample request and response:-
-------------------------------------------
<< List will be added >>

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


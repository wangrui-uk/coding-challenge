================================
|   GoHenry Coding Challenge   | 
================================
--------------
| Objective  |
--------------

Create a RESTful service using Spring Boot which shall do the following:
	* Provide a service to create a parent with zero or more children using an endpoint of http://localhost:8080/parents
	* Provide a service to retrieve a parent including their children using an endpoint of http://localhost:8080/parents/{id}
	* Use an embedded database such as H2 via JPA as a backing store

You may optionally include the following:
	* Provide a service to update a parent using an endpoint of http://localhost:8080/parents/{id}
	* Provide a service to update a child http://localhost:8080/children/{id}

An example JSON object showing the required attributes when retrieving a parent and their children via http://localhost:8080/parents/{id}:
```
{  
   "id":1,
   "title":"Mrs",
   "firstName":"Jane",
   "lastName":"Doe",
   "emailAddress":"jane.doe@gohenry.co.uk",
   "dateOfBirth":"1990-06-03",
   "gender":"female",
   "secondName":"",
   "children":[  
      {  
         "id":2,
         "firstName":"Janet",
         "lastName":"Doe",
         "emailAddress":"janet.doe@gohenry.co.uk",
         "dateOfBirth":"2010-05-22",
         "gender":"female",
         "secondName":""
      },
      {  
         "id":3,
         "firstName":"Jason",
         "lastName":"Doe",
         "emailAddress":"jason.doe@gohenry.co.uk",
         "dateOfBirth":"2008-12-05",
         "gender":"male",
         "secondName":""
      }
   ]
}
```
------------------------------------------
| Technical Requirements and Guidelines  |
------------------------------------------
	* Deliver source code as a git public/private repo and most importantly with appropriately timed commits so we can see you are following a TDD process
	* Use Java version 8 or above
	* Build using either maven or gradle
	* Include a README
	* Be pragmatic and do not over-engineer, clean and maintainable code is very important
	* Bear in mind we will use either "mvn clean test" or "gradlew clean test" as appropriate to execute your code
	* Spend as much time as you feel appropriate but as a guide, allow 2-3 hours. What you deliver and the quality of what you deliver is more important than the amount of time spent

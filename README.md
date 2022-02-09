# gltest

Requirements for run by console:

Java
maven

Command for run:
In the project folder "restful-user-service":
mvn spring-boot:run


Example requests:


http://localhost:8080/sign-up

POST 

{
	"name": "persona",
	"email": "persona@gmail.com",
	"password": "a2asfGfdfdf4",
	"phones": [
		{
			"number": 12345678,
			"citycode": 210,
			"contrycode": "USA"
		}
	]
}

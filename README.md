#Test

#Repository Github

https://github.com/breuer/gltest.git

#Clone from repository

...
$ git clone https://github.com/breuer/gltest.git
...

#Requirements (run by console)

...
Java
Gradle
...

#Run the aplicacion

```
$ gradle bootRun
```

#Run test

```
$ gradle test
```



Requirements for run by console:

Java
maven

Command for run:

In the project folder "restful-user-service":
mvn spring-boot:run


Example requests:

Request "sign-up":

POST 
http://localhost:8080/sign-up

Header:
Content-Type: application/json

Body:
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

response "sign-up":



Request "login":

POST
http://localhost:8080/login

Header:
Content-Type: application/json
Authorization: token generated in "sign-up":
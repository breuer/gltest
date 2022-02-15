#test

#Repository

```
$ https://github.com/breuer/gltest.git
```

#Clone from repository


```
$ git clone https://github.com/breuer/gltest.git
```

#Requirements (run by console)

```
Java
Gradle
```

#Run the aplicacion
_from project folder:_

```
$ gradle bootRun
```

#Run unit test
_from project folder: user-microservice_

```
$ gradle test
```
_Path to report test:_


```
$ user-microservice/build/reports/tests/test/index.html
```

#h2-console to check the database:

```
http://localhost:8080/h2-console
```


#Example requests with Postman:

Request "sign-up":

```
POST 
http://localhost:8080/sign-up

Header:
Content-Type: application/json

Body:
{
	"name": "nombre apellido",
	"email": "nombre01@gmail.com",
	"password": "a2asfGfdfdf4",
	"phones": [
		{
			"number": 12345678,
			"citycode": 210,
			"contrycode": "USA"
		}
	]
}
```


Response example "sign-up":

```
{
    "id": "2832f174-8344-44d7-9219-c2500941f3e4",
    "name": "nombre apellido",
    "email": "nombre01@gmail.com",
    "password": "$2a$10$mmr5YolM6PfYbsolMjlkROhGVIsvFyIcsHFKXZFk8vXvHesAcpoQ2",
    "phones": [
        {
            "number": 12345678,
            "citycode": 210,
            "contrycode": "USA"
        }
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Im5vbWJyZTAxQGdtYWlsLmNvbSIsImV4cCI6MTY0NDkwMTQxMn0.21JOZvsLxo1be1ZWsZ3HbBEh3CwQbG5VXcN4RXXwZzM",
    "active": true
}
```


Request "login" with token from response sign-up:

```
POST
http://localhost:8080/login

Header:
Content-Type: application/json
Authorization: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Im5vbWJyZTAxQGdtYWlsLmNvbSIsImV4cCI6MTY0NDkwMTQxMn0.21JOZvsLxo1be1ZWsZ3HbBEh3CwQbG5VXcN4RXXwZzM

Body: Empty
```

Response "login":

```
{
    "id": "2832f174-8344-44d7-9219-c2500941f3e4",
    "created": "2022-02-15T01:48:32.628209",
    "lastLogin": "2022-02-15T01:50:36.4184054",
    "name": "nombre apellido",
    "email": "nombre01@gmail.com",
    "password": "$2a$10$mmr5YolM6PfYbsolMjlkROhGVIsvFyIcsHFKXZFk8vXvHesAcpoQ2",
    "phones": [
        {
            "number": 12345678,
            "citycode": 210,
            "contrycode": "USA"
        }
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Im5vbWJyZTAxQGdtYWlsLmNvbSIsImV4cCI6MTY0NDkwMTUzNn0.Ko4BvixSEU-v67VusRHjqlBeqGCqKR3-Z-cca3shWRI",
    "active": true
}
```
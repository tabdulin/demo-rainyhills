# RainyHills Test Task Application 

This application was developed for CRX Markets AG by Talgat Abdulin.

## Description

Write an application which takes an array of integers as an input representing surface, and calculates the volume 
of water which remained after the rain, in units.

The application shall be deployable in a EJB container of your choice
(preferably either JBoss, Wildfly, Glassfish, or TomEE).

We also accept Spring Boot solutions as an alternative to a JEE conform packaging.
Make a statement on complexity of your solution (time and memory).


## Assumptions taken

1. As input can be an array of any integers, and negatives as well, the following assumption is taken - 
there is flat surface with height of 0 around passed surface. So negative height are supposed to be holes in such surrounding surface. 
It seems to be natural and a little bit more difficult ;-)

2. As array in Java can be initialized with a length up to Integer.MAX_VALUE - 5 (depends on JVM used) the following assumption is taken -
user is supposed to run application with enough memory. There is no processing of this limitation 
inside the application (was not tested  on my laptop, since lack of enough memory)

## Implementation

### Algorithm 

Result is returned as long since sum of integers can overflow integer size.
Starting from both ends algorithm passes array from the lowest hill to the highest 
and changes direction if the heights comparison result is changed. This makes possible to parse array just once 
and collect water volume in holes while parsing.
   
The time complexity is O(n), where n is a size of the array.
The space complexity is O(1). Only several variables are necessary for the algorithm.

### Tools

- OpenJDK 11
- Apache Maven 3.6
- Spring Boot 2.1.2 
- Docker 18.06 (optional)

## Run application

Default application port is 8080.

### Standalone java application

You need **OpenJDK 11** and **Maven 3.6** installed to run application.

To build an application run
`mvn clean package`

To run an application run 
`java -jar target/demo-rainyhills.jar`

### Docker
You need the docker of version 17.05 and higher since Dockerfile contains multistage build.

`docker build -t tabdulin/demo-rainyhills . && docker run -p 8080:8080 tabdulin/demo-rainyhills`

### REST API

The application provides following REST API:

POST `/surface` with json array of numbers `[]` as a request body.

Possible response types:

- `200 OK` non-negative integer of the calculated water volume. 0 if there is no water.
- `400 Bad Request` if request body of incorrect format (e.g. string instead of array).
- `500 Internal Server` Error if some other error occurs.

To call an API linux curl command can be used, e.g.

`curl -X POST http://localhost:8080/surface -d "[1,0,2]" --header "Content-Type: application/json"` 

Swagger documentation is published on [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui) 
after build and run.
###############################################################################
# @Author: mailto:@mark.ocallaghan1@gmail.com

# Hello and welcome to the Simple Lottery App
###############################################################################

Some basic instructions below:

The Application is a Spring Boot application  

To Build use:
mvn clean install

To compile and just run test use:
mvn clean test

to run there is wrapping command on maven:
./mvnw spring-boot:run

To package with docker use:
docker build -t springio/gs-spring-boot-docker .

To run with Docker use:
docker run -p 8080:8080 -t springio/gs-spring-boot-docker

#########Business Usage============================================================================
To Post a new ticket just use a tool like Postman or maybe curl and send a request without a body to
e.g. curl --request POST http://127.0.0.1:8090/lottery/ticket

Sample output in JSON format would be the following for a Positive result with HTTP status 201 for Created:
{
    "lines": [
        {
            "numberOne": 1,
            "numberTwo": 2,
            "numberThree": 1,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 2,
            "numberThree": 1,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 1,
            "numberTwo": 1,
            "numberThree": 0,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 1,
            "numberThree": 2,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 2,
            "numberTwo": 2,
            "numberThree": 2,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 2,
            "numberTwo": 1,
            "numberThree": 0,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 1,
            "numberTwo": 1,
            "numberThree": 2,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 2,
            "numberTwo": 1,
            "numberThree": 2,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 1,
            "numberTwo": 1,
            "numberThree": 1,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 0,
            "numberThree": 2,
            "outcome": 0,
            "result": 0
        }
    ],
    "statusChecked": false
}

The following is a sample of checking status:
localhost:8080/lottery/status/1
{
    "lines": [
        {
            "numberOne": 0,
            "numberTwo": 1,
            "numberThree": 0,
            "outcome": 0,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 2,
            "numberThree": 2,
            "outcome": 1,
            "result": 0
        },
        {
            "numberOne": 2,
            "numberTwo": 1,
            "numberThree": 0,
            "outcome": 1,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 1,
            "numberThree": 2,
            "outcome": 1,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 0,
            "numberThree": 0,
            "outcome": 5,
            "result": 0
        },
        {
            "numberOne": 1,
            "numberTwo": 1,
            "numberThree": 1,
            "outcome": 5,
            "result": 0
        },
        {
            "numberOne": 1,
            "numberTwo": 0,
            "numberThree": 1,
            "outcome": 10,
            "result": 0
        },
        {
            "numberOne": 1,
            "numberTwo": 0,
            "numberThree": 1,
            "outcome": 10,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 1,
            "numberThree": 1,
            "outcome": 10,
            "result": 0
        },
        {
            "numberOne": 0,
            "numberTwo": 1,
            "numberThree": 1,
            "outcome": 10,
            "result": 0
        }
    ],
    "statusChecked": true
}
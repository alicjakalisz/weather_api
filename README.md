# Weather API
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [API Contract](#api-contract)
* [Setup](#setup)

## General info
This project is a Spring API that connects to Current Weather Information API available at https://openweathermap.org/current.
It returns to the client basic weather details for the chosen city at the current date.

The search results are firstly checked if there are available in the project's database and if so, they are retrieved through repository.
In case this is a new search, the info is collected from Current Weather Information Api and persisted in the database before the result is sent back to the client.

## Public Endpoint
This Rest API has been deployed in AWS in an EC2 instance and using a Postgres RDS database.

```http://ec2-18-130-4-145.eu-west-2.compute.amazonaws.com:8080/weather/london```
## Technologies/Main Libraries
Project is created with:

* Maven
* Maven Profiles for local and production deployments
* Java 1.11
* Spring Boot
* Json version: 20210307
* Junit 
* Gson version: 2.8.2
* Postgressql db
* Mockito
* AWS: ec2, RDS and security groups

## API contract
### HTTP GET /weather/{city}
Get the day weather for a city.
#### HTTP responses:
##### HTTP 200
```
{
"city": "London",
"description": "scattered clouds",
"temperature": 19.420000000000016,
"humidity": 82.0,
"country": "\"GB\"",
"date": "2021-06-27"
}
```
##### HTTP 204 (No Content)

## Setup
To run this project:
* install locally postgressql db
* get APIKey for Current Weather Data from https://openweathermap.org/api
* Modify application.properties with the apiKey and the Database credentials
* Run it from IntellIJ or from command line:
`mvn clean install spring-boot:run`
  
## Deployment to Production
* Modify application.properties with the correct database connection
* `m̀vn clean install -P prod`
* Upload jar to remote machine:
`scp -i ${location_pem} ${WORKSPACE_PATH}/weather_api/target/weather_api-0.0.1-SNAPSHOT.jar ec2-user@ec2-18-130-4-145.eu-west-2.compute.amazonaws.com:/home/ec2-user
  weather_api-0.0.1-SNAPSHOT.jar`
* Connect to Remote machine: 
  `ssh -i ${location_pem}  ec2-user@ec2-18-130-4-145.eu-west-2.compute.amazonaws.com`
  
* Run spring boot app in a remote machine:

  A.The process will be automatically killed after ending connection with EC2

  `java -jar weather_api-0.0.1-SNAPSHOT.jar > output.txt &`
  * to kill it manually:

    `ps`

    `ps | grep java`

    `kill -9 process ref`

  B. Keep the process as a daemon (even after ending connection):
  * create a screen named restaurants and run the program

    `screen -dmS weather java -jar weather_api-0.0.1-SNAPSHOT.jar > restaurants.log &
    `
  * to kill the process, list all the running process and kill the screen:

    `screen -ls
    `  
    `screen -X -S weather quit`

* Ready to use GET endpoints


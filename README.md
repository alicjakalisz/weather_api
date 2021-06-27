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


## Technologies/Main Libraries
Project is created with:

* Maven
* Java 1.11
* springframework.boot
* json version: 20210307
* junit 
* gson version: 2.8.2
* postgressql db
* mockito

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



# Project overview

This project involves the development of a RESTful API using Spring Boot 3.2.0, using an H2 in-memory database.

The API allows users to check whether a specific movie has won the Oscar for Best Picture, give a rate to movies
and check the list of 10 top-rated movies ordered by box office value.

## Requirements

For building and running the application:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3.5+](https://maven.apache.org)

## Prerequisites

You need to get a key from [OMDb API](http://www.omdbapi.com) to perform request to get the box office.
Once you get the key you need to put it in ```application.properties``` file in the property:

```properties
omdb.api.key=[your-OMDb-API-key]
```

## Usage

It is possible to run the application directly from the IDE, or using the Spring Boot Maven plugin:

```bash
./mvnw spring-boot:run
```

# How to test

When the application is running you can use the following endpoints to test the API,
for this you can use any client like Postman or cURL. I will include the cURL for each endpoint.

## Authenticate

You need to have a valid JWT Token in order to perform requests to the API.

To get a token you need to authenticate yourself with one of the 3 users hard coded in the application:

- user1 / password
- user2 / password
- user3 / password


[http://localhost:8080/authenticate](http://localhost:8080/authenticate)

```bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"user1", "password":"password"}' http://localhost:8080/authenticate
```

This will return a ```jwtToken``` that you need to include in your requests to the API.

## Use the API

Once you have a valid JWT Token you can use the API providing it in each request

### Check if a movie has won the Best Picture Oscar

[http://localhost:8080/api/movies/isBestPicture={movie-title}](http://localhost:8080/api/movies/isBestPicture={movie-title})

```bash
curl -X GET -H "Authorization: Bearer REPLACE-WITH-TOKEN" http://localhost:8080/api/movies/isBestPicture=Avatar
```

### Get movie data by title

[http://localhost:8080/api/movies/title={movie-title}](http://localhost:8080/api/movies/title={movie-title})

```bash
curl -X GET -H "Authorization: Bearer REPLACE-WITH-TOKEN" http://localhost:8080/api/movies/title=Avatar
```

## Get top-rated movies ordered by box office

[http://localhost:8080/api/movies/topRated](http://localhost:8080/api/movies/topRated)

```bash
curl -X GET -H "Authorization: Bearer REPLACE-WITH-TOKEN" http://localhost:8080/api/movies/topRated
```

### Get ratings per movie

You need to provide the movie id to get the ratings, you can retrieve it with [Get movie data by title](#Get movie data by title)

[http://localhost:8080/api/movies/{movie-id}/ratings](http://localhost:8080/api/movies/{movie-id}/ratings)

```bash
curl -X GET -H "Authorization: Bearer REPLACE-WITH-TOKEN" http://localhost:8080/api/movies/80/ratings
```

### Rate a movie

You need to provide the movie id to get the ratings, you can retrieve it with [Get movie data by title](#Get movie data by title)

[http://localhost:8080/api/movies/{movie-id}/ratings](http://localhost:8080/api/movies/{movie-id}/ratings)

```bash
curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer REPLACE-WITH-TOKEN" -d '{"rate":"5"}' http://localhost:8080/api/movies/80/ratings
```

## H2 console (optional)

As the application is using an H2 in-memory database, you can access the console to check the data directly through:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

    user: sa
    password:

# Scale

Depending on an external API to retrieve information could cause a bottleneck when accessing the data, at this moment
the application needs to perform a request per movie to get the box-office when retrieving the top 10 rated ones,
this is 10 queries in total, although we can cache the information (as it is something that is not going to change),
it will be better if we can perform this in batch with just one query for the whole process.

# TO-DO

## Replace database

The application is using an in-memory database, it should be replaced with one that persist data between application
resets.

## Remove CsvLoaderService

The CsvLoaderService loads the data from the CSV to the database with every restart of the application.
This should be replaced with a one-time process to dump the information. Perhaps provide an endpoint to allow users to
update the data.

## User management / authorization

The application has the users hard coded, this should be obviously avoided in a production environment.
Some alternatives could be to relay in an external application to perform the management and authentication of users
or create it in the database.

## Reduce external API calls

The application does not store the box office information from the movies, that makes it necessary to request it to the
external API whenever needed. A better approach will be to cache the request making it quicker but also could be
interesting to store this information in the database so in case it is there, it will not be needed to call the external
API.

## Validations

The application barely validate fields, it should validate every field that goes to the application.

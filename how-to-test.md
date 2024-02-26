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
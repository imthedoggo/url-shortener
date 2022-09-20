# Read Me First
This project provides an API to manage shortened links for long URLs.
The user can create and delete a link, get all the stored short-long URL pairs and also be redirected to a page, by calling the short link.

The goal of this project is to build production-ready MVP of a URL shortener. Therefore, it must be:
* Well-documented
* Well-tested
* Designed simply yet flexible enough for future scaling

## Tech stack:
* Java 11 (original project programming language)
* Kotlin 1.7.0 (language the project was migrated into)
* Docker to build image & run the project from anywhere
* Docker-compose to start the DB in any environment
* Redis as a key-value DB
* Lettuce as a Redis client

## How to run this project:
Create a local Redis DB
```
docker-compose up -d
```
Start the spring project
```
./gradlew bootRun
```
The project has 2 env variables. Default values are already in place.
```
REDIS_HOST=localhost
REDIS_PORT=6380
```

### Deploy:
Build Docker image
```
docker build -t imthedoggo/url-shortener .
```
Run the image locally
```
docker run -p 8081:8080 -d imthedoggo/url-shortener 
```

### Redis ops
```
redis-cli -h localhost -p 6380 //connect to the DB
KEYS "*" //get all keys
FLUSHDB //clean up DB in case of app restarts
```

## Assumptions:
1. Let's assume we expect way more READs than WRITEs
2. Let's assume we want a fast redirection (fast lookup for the short link given the long URL)
3. Let's assume we want our link as short as possible (considering ability to serve the expected amount of URL generations)

## Endpoints:

| Method | Resource      | Description                               |
|--------|---------------|-------------------------------------------|
| POST   | /v1/urls      | Creates a short link for a given long url |
| GET    | /v1/urls/{id} | Redirect user to the long URL address     |
| GET    | /v1/urls      | Returns all the existing short URL IDs    |
| DELETE | /v1/urls/{id} | Deletes the short+long url pairs          |

### Examples

#### POST /v1/urls
Request:
```
curl --location --request POST 'http://localhost:8080/v1/urls' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "https://en.wikipedia.org/wiki/Doge_(meme)"
}'
```
Response:
```
{
    "id": "2TI2yg",
    "url": "https://en.wikipedia.org/wiki/Doge_(meme)"
}
```

#### GET /v1/urls/{id}
For the redirection the request and response are slightly different and should be tested as if the end user clicks on the redirection link.
Paste the URL pointing to localhost + GET /v1/urls/ endpoint, followed by the ID you received in the previous POST request.
e.g.
```
http://localhost:8080/v1/urls/2TI2yg
```
As a 'response', you will then be immediately redirected to the original page.

#### GET /v1/urls
Request:
```
curl --location --request GET 'http://localhost:8080/v1/urls'
```
Response:
```
[
    {
        "id": "1DQJyh",
        "url": "https://en.wikipedia.org/wiki/Doge_(meme)"
    },
    {
        "id": "2TI2yg",
        "url": "https://en.wikipedia.org/wiki/Doge_(meme)"
    }
]
```

#### DELETE /v1/urls/{id}
Request:
```
curl --location --request DELETE 'http://localhost:8080/v1/urls/2TI2yg'
```
A simple 200 OK response is expected, without a body.

## Future improvements:
Assuming we don't want to over-engineer the MVP, only the basic requirements have been implemented.
But what can be improved in the future?
* Once implementing registration, add an API rate limit for security/monetization reasons
* Enable expiration of links after a certain time period
* Add a counter for the amount of redirections for tracking, monetization and additional business value
* Enable a customizable link as an upselling feature

## Tutorials and sources
* [Spring boot - Kotlin tutorial](https://spring.io/guides/tutorials/spring-boot-kotlin/)
* [Spring RESTful web service - Kotlin tutorial](https://kotlinlang.org/docs/jvm-spring-boot-restful.html#add-database-support)
* [Java vs. Kotlin, annotations and patterns](https://levelup.gitconnected.com/kotlin-makes-lombok-obsolete-9ed3318596cb)
* [Java vs. Kotlin, static, objects](https://proandroiddev.com/utils-class-in-kotlin-387a09b8d495)
* [Redis hashes](https://redis.io/docs/data-types/hashes/)
* [Base62 encoding library (deprecated from implementation)](https://github.com/seruco/base62)

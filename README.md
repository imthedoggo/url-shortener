# Read Me First
The goal of this project is to build production-ready MVP of a URL shortener. Therefore, it must be:
* Well-documented
* Well-tested
* Designed simply yet flexible enough for future scaling

## Tech stack:
* Java 11
* Lombok annotations to avoid boilerplate code
* Docker to build image & run the project from anywhere
* Docker-compose to start the DB in any environment
* Redis as a key-value DB

## How to run this project:
```
docker-compose up -d
```
```
docker build -t imthedoggo/url-shortener .
```

```
docker run -p 8081:8080 -d imthedoggo/url-shortener 
```

### Redis ops
```
redis-cli -h localhost -p 6380 //connect to the DB
KEYS "*" //get all keys
```

## Assumptions:
1. We expect way more READs than WRITEs

## Endpoints:

| Method | Resource      | Description                              |
|--------|---------------|------------------------------------------|
| POST   | /v1/urls      | Creates a short url for a given long url |
| GET    | /v1/urls/{id} | Redirect user to the long URL address    |
| GET    | /v1/urls      | Returns all the existing short URL IDs   |
| DELETE | /v1/urls/{id} | Deletes the short+long url pair          |


## Future improvements:
Assuming we don't want to over-engineer the MVP, only the basic requirements have been implemented.
But what can be improved in the future?
* Add a counter for the amount of redirections for dashboarding, monetization and additional business value
* Enable a customizable link as an upselling feature
* Once implementing registration, add protection against high volume of requests

## Tutorials and sources
[Redis hashes](https://redis.io/docs/data-types/hashes/)

# Read Me First
The goal of this project is to build production-ready MVP of a URL shortener. Therefore, it must be:
* Well-documented
* Well-tested
* Designed simply yet flexible enough for future scaling

## Tech stack:
* Java 11
* Lombok annotations to avoid boilerplate code

## Assumptions:
1. We expect way more READs than WRITEs

## Endpoints:

| Method | Resource            | Description                                     |
|--------|---------------------|-------------------------------------------------|
| POST   | /v1/short-urls      | Creates a short url for a given long url        |
| GET    | /v1/short-urls/{id} | Returns a long url based on the (short url) key |
| GET    | /v1/short-urls      | Returns all the existing short URL IDs          |
| DELETE | /v1/short-urls/{id} | Deletes the short+long url pair                 |


## Future improvements:
Assuming we don't want to over-engineer the MVP, only the basic requirements have been implemented.
But what can be improved in the future?
* Add a counter for the amount of redirections for dashboarding, monetization and additional business value
* Enable a customizable link as an upselling feature

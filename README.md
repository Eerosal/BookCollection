# Book Collection App
Simple web app for managing a collection of books.

## How to run
```
docker-compose --env-file=<env file> up
```

H2 in-memory database example with HTTP port 8123:
```
docker-compose --env-file=.env.h2.example up
```

## Frontend

Uses React and is made with TypeScript.

## Backend

Rest API made with Spring. Includes drivers for H2 and MySQL. 

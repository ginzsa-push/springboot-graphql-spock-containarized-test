# springboot-graphql-spock-containarized-test
springboot-graphql-spock-contain-containerized-test

This Spring boot application has a graphql endpoint, jpa starters and includes containarized test and docker executions.

The application will pull data about the repositories created in the last week from the git api search endpoint and save it in a database (Using Spring template, 'simulating' a Kafka connector poll implementation)

The service will pull the top ten repos (order by stars).


## Getting Started

```
./gradlew clean build
./gradlew docker
```
Docker compose execution 
```
docker-compose up --build
```

Local execution if you have the local 'test' postgres db running

```
java -DHOST_DB=localhost -jar ./build/libs/job-1.0-SNAPSHOT.jar
```

### Prerequisites

Java 8, Docker and Postgres if you want to exectute it locally


### Installing

You will need to set up the db_url enviroment variable if you want to run locally

How to install java? 
https://java.com/en/download/help/download_options.xml

How to install gradle
https://gradle.org/install/

How to install postgress
http://www.postgresqltutorial.com/install-postgresql/

How to install docker
https://docs.docker.com/install/

### The application

The mock application pulls (7 days old) data from github api from and save it into database, the service query the database and get the last top 10 repository order by stars.

You can use the graphiql ui to make a query

http://localhost:8080/graphiql
```
query Item {
    topTenStars {
      id
      name
      stars
      language
    }
}
```


## Running the tests

### Test will include containarized execution (not excluded), do you have to have docker running

Test suit includes samples of:
	- Unit test
	- Mvc integration test
	- Containerized integration test

```
./gradlew test
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [springboot](https://spring.io/projects/spring-boot) - Springboot framework
* [gradle](https://gradle.org/) - Gradle Dependency Management
* [graphql](https://graphql.org/) - Graphql API schema and query
* [spock](http://spockframework.org/) - Spock testing framework
* [docker](https://www.docker.com/) - Docker containers
* [testcontainers](https://www.testcontainers.org/) - containerized tests
* [postgres](https://www.postgresql.org/) - database
* [flyway](https://flywaydb.org/getstarted/java) - flyway data manager and migration


## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Santiago Ginzburg** - *Initial work* - [ginzsa-push](https://github.com/ginzsa-push)


## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Thank you to all those technical test interviews without them, I am nothing.
* Inspiration and expiration is alway a good thing.

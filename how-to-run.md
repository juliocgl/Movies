# How to run

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
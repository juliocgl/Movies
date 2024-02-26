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
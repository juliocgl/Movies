# Scale

Depending on an external API to retrieve information could cause a bottleneck when accessing the data, at this moment
the application needs to perform a request per movie to get the box-office when retrieving the top 10 rated ones,
this is 10 queries in total, although we can cache the information (as it is something that is not going to change),
it will be better if we can perform this in batch with just one query for the whole process.
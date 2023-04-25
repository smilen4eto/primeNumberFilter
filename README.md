## Micronaut Kafka Producer and Consumer for Prime Numbers

This is a Micronaut project that implements a Kafka producer that generates a stream of random numbers and sends them to a Kafka topic, and a Kafka consumer that consumes messages from the same topic and identifies prime numbers from the stream.

## Prerequisites

To run this project, you'll need the following:

   - Java Development Kit (JDK) 17 or higher
   - Docker Desktop or Rancher Desktop
   
## Running Kafka

From the /docker repository execute the following command:

`nerdctl compose up` or `docker-compose up`

## Running the application

To get all the dependencies and generate the files you will need:

`./gradlew build `

To run the application you will need to execute:

`./gradlew run`

This will start the consumer service and consume messages from the Kafka topic "random-numbers", identify prime numbers from the stream, and write them to a CSV file named "primes.csv" in the project's root directory.

To run the application tests execute

`./gradlew test`

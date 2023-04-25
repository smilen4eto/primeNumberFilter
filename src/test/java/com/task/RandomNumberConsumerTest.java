package com.task;


import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@MicronautTest
public class RandomNumberConsumerTest {

    @Inject
    RandomNumberConsumer consumer;

    @Test
    void shouldIdentifyPrimeNumbers() throws IOException, InterruptedException {
        // send 10 messages to the Kafka topic with random numbers
        for (int i = 0; i < 10; i++) {
            consumer.receive("random-numbers", String.valueOf(i));
            Thread.sleep(1000);
        }

        // wait for a few seconds for the consumer to write the prime-numbers.csv file
        Thread.sleep(5000);


        // read the prime-numbers.csv file
        File file = new File("prime_numbers.csv");
        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));

        // check that the file contains the expected prime numbers
        Assertions.assertTrue(content.contains("2"));
        Assertions.assertTrue(content.contains("3"));
        Assertions.assertTrue(content.contains("5"));
        Assertions.assertTrue(content.contains("7"));
    }

}


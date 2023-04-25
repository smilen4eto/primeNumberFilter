package com.task;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Value;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

@KafkaListener
public class RandomNumberConsumer {
    private final ConcurrentLinkedQueue<Integer> primes = new ConcurrentLinkedQueue<>();
    private final int maxPrimes;
    private final PrintWriter writer;

    public RandomNumberConsumer(
            @Value("${app.max-primes:4}") int maxPrimes,
            @Value("${app.primes-file: prime_numbers.csv}") String primesFile
    ) throws IOException {
        this.maxPrimes = maxPrimes;
        this.writer = new PrintWriter(new FileWriter(primesFile));
    }

    public void receive(@Topic String topic, String message){
        receive(message);
    }

    @Topic("random-number")
    public void receive(String message) {
        int number = NumberUtils.toInt(message);
        if (isPrime(number)) {
            primes.add(number);
        }

        if (primes.size() > maxPrimes) {
            writePrimesToFile();
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private void writePrimesToFile() {
        String line = String.join(",", primes.stream().map(Object::toString).toArray(String[]::new));
        writer.println(line);
        writer.flush();
        primes.clear();
    }

}

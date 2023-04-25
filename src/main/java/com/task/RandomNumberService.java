package com.task;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import io.micronaut.context.annotation.Value;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;

@Singleton
public class RandomNumberService {

    private final RandomNumberProducer producer;
    private final FileWriter csvWriter;

    public RandomNumberService(@Value("${app.random-numbers-file:random_numbers.csv}") String fileName,
                               RandomNumberProducer producer) throws IOException {
        this.producer = producer;
        this.csvWriter = new FileWriter(fileName);
        csvWriter.append("Timestamp,Numbers\n");
    }

    @Scheduled(fixedRate = "1000ms")
    public void generateRandomNumbers() {
        int count = new Random().nextInt(5) + 1;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(new Random().nextInt(100)).append(",");
        }
        String message = sb.deleteCharAt(sb.length() - 1).toString();
        try {
            csvWriter.append(LocalDateTime.now().toString()).append(",").append(message).append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        producer.send(message);
    }
}
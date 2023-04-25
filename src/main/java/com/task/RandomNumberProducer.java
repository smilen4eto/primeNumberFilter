package com.task;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface RandomNumberProducer {

    @Topic("random-number")
    void send(String message);
}
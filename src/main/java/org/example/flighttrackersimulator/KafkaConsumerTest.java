package org.example.flighttrackersimulator;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.flighttrackersimulator.AdditionalSources.KafkaMessageConsumer;

public class KafkaConsumerTest {
    public static void main(String[] args) {
        try (KafkaConsumer<String, String> consumer = KafkaMessageConsumer.createKafkaConsumer()) {
            KafkaMessageConsumer.consumeMessages(consumer);
        }

    }
}

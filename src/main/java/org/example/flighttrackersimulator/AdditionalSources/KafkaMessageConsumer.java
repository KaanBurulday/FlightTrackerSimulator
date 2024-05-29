package org.example.flighttrackersimulator.AdditionalSources;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaMessageConsumer {

    private static final String TOPIC_NAME = "Flight-Info"; // Replace with your Kafka topic name
    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092"; // Replace with your Kafka bootstrap servers

    public static void consumeMessages(KafkaConsumer<String, String> consumer) {
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.toString());
            }
        }
    }

    public static void consumeMessage(KafkaConsumer<String, String> consumer) {
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.toString());
        }
    }

    public static KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer"); // No consumer group specified
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // Set key deserializer
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // Set value deserializer

        return new KafkaConsumer<>(props);
    }
}

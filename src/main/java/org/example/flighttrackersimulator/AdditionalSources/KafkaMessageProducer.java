package org.example.flighttrackersimulator.AdditionalSources;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaMessageProducer {

    private static final String TOPIC_NAME = "Flight-Info";
    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";

    public static void sendMessage(KafkaProducer<String, String> producer, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);
        producer.send(record);
    }

    public static KafkaProducer<String, String> createKafkaProducer()
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
}

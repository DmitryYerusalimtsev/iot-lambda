package com.iotlambda.generator.publishing;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;

public class KafkaDataPublisher<T> implements DataPublisher<T> {

    private Properties props;
    private String topicName;

    public KafkaDataPublisher(String server, String topicName) {
        this.topicName = topicName;

        this.props = new Properties();
        props.put("bootstrap.servers", server);
        props.put("acks", "all");
        props.put("retries", 1);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    @Override
    public void publish(List<T> data) {
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            for (T item : data) {
                producer.send(new ProducerRecord<>(topicName, null, item.toString()));
            }
        }
    }
}

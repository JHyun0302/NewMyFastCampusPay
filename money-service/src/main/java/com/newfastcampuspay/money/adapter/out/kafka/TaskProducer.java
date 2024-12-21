package com.newfastcampuspay.money.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.common.RechargingMoneyTask;
import com.newfastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}") String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {
        this.sendMessage(task.getTaskID(), task);
    }

    public void sendMessage(String key, RechargingMoneyTask value) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProducer;
        //jsonString
        try {
            jsonStringToProducer = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProducer);
        producer.send(record, ((metadata, exception) -> {
            if (exception == null) {
//                System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
//                System.out.println("Failed to send message: " + exception.getMessage());
            }
        }));
    }
}

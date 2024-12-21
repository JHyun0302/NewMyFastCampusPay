package com.newfastcampuspay.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newfastcampuspay.common.RechargingMoneyTask;
import com.newfastcampuspay.common.SubTask;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskConsumer {

    private final KafkaConsumer<String, String> consumer;

    private final TaskResultProducer taskResultProducer;

    public TaskConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}")String topic, TaskResultProducer taskResultProducer) {
        this.taskResultProducer = taskResultProducer;

        Properties props = new Properties();

        props.put("bootstrap.servers", bootstrapServers);

        // consumer group
        props.put("group.id", "my-group");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(topic));
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    ObjectMapper mapper = new ObjectMapper();
                    for (ConsumerRecord<String, String> record : records) {
                        // record : RechaingMoneyTask (jsonString)
                        RechargingMoneyTask task;

                        // task run
                        try {
                            task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        // task result
                        for (SubTask subTask : task.getSubTaskList()) {
                            // what subtask, membership, banking 파악 -> external port, adapter (hexagonal architecture)

                            // 임시로 status = 'success' 가정한다.
                            subTask.setStatus("success");
                        }

                        // produce TaskResult
                        this.taskResultProducer.sendTaskResult(record.key(),  task);
                    }
                }
            } finally {
                consumer.close();
            }
        });
        consumerThread.start();
    }
}
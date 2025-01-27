package com.ordercrud.adapter.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordercrud.adapter.messaging.MessagingData;
import com.ordercrud.adapter.messaging.MessagingProducer;
import com.ordercrud.util.enums.Topic;

@Primary
@Component
public class KafkaProducer implements MessagingProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T extends MessagingData> void send(Topic topic, T data) {
        try {
            String message = objectMapper.writeValueAsString(data);
            kafkaTemplate.send(topic.toString(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.ordercrud.adapter.messaging.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordercrud.adapter.messaging.MessagingData;
import com.ordercrud.adapter.messaging.MessagingProducer;
import com.ordercrud.util.enums.Topic;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

@Primary
@Component
public class KafkaProducer implements MessagingProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Tracer tracer = GlobalTracer.get();

    @Override
    public <T extends MessagingData> void send(Topic topic, T data) {
        Span span = tracer.buildSpan("KafkaProducer.send").start();

        try {
            String message = objectMapper.writeValueAsString(data);

            span.setTag("kafka.topic", topic.toString());
            span.setTag("kafka.message_size", message.length());

            kafkaTemplate.send(topic.toString(), message);

            span.setTag("kafka.status", "success");
        } catch (Exception e) {
            span.setTag("error", true);
            span.log("Error sending message: " + e.getMessage());
        } finally {
            span.finish();
        }
    }

    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setTraer(Tracer tracer) {
        this.tracer = tracer;
    }
}

package com.ordercrud.adapter.messaging.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordercrud.adapter.messaging.MessagingData;
import com.ordercrud.util.enums.Topic;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Span mockSpan;

    @Mock
    private Tracer mockTracer;

    @Mock
    private Tracer.SpanBuilder spanBuilder;

    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        try (MockedStatic<GlobalTracer> mockedGlobalTracer = mockStatic(GlobalTracer.class)) {
            when(GlobalTracer.get()).thenReturn(mockTracer);
            when(mockTracer.buildSpan(anyString())).thenReturn(spanBuilder);
            when(spanBuilder.start()).thenReturn(mockSpan);

            kafkaProducer = new KafkaProducer();
            kafkaProducer.setKafkaTemplate(kafkaTemplate);
            kafkaProducer.setObjectMapper(objectMapper);
            kafkaProducer.setTraer(mockTracer); // Certifique-se de que o tracer é corretamente injetado
        }
    }

    @Test
    void testSendMessageSuccess() throws Exception {
        MessagingData data = mock(MessagingData.class);
        Topic topic = Topic.ORDER_CREATE;

        String jsonMessage = "{\"id\":\"123\"}";
        when(objectMapper.writeValueAsString(data)).thenReturn(jsonMessage);

        kafkaProducer.send(topic, data);

        verify(kafkaTemplate, times(1)).send(topic.toString(), jsonMessage);
        verify(mockSpan, times(1)).setTag("kafka.status", "success");
        verify(mockSpan, times(1)).finish();
    }

    @Test
    void testSendMessageError() throws Exception {
        MessagingData data = mock(MessagingData.class);
        Topic topic = Topic.ORDER_CREATE;

        when(objectMapper.writeValueAsString(data)).thenThrow(new RuntimeException("Serialization error"));

        kafkaProducer.send(topic, data);

        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
        verify(mockSpan, times(1)).setTag("error", true);
        verify(mockSpan, times(1)).log("Error sending message: Serialization error");
        verify(mockSpan, times(1)).finish();
    }
}

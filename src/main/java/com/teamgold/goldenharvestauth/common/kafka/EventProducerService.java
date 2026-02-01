package com.teamgold.goldenharvestauth.common.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object payload) {
        log.info("Sending Kafka message to topic: {}", topic);
        kafkaTemplate.send(topic, payload).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to send message to Kafka", ex);
            } else {
                log.info("Successfully sent message to Kafka with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}

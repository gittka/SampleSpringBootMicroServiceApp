package org.alxtek;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
    @KafkaListener(topics = "notification-topic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        // Send email notification
        log.info("Received Order notification placed event received: {}", orderPlacedEvent.getOrderNumber());
    }
}


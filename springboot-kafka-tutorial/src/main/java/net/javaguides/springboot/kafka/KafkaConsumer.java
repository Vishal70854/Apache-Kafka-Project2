package net.javaguides.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    // we will create annotation of KafkaListener to subscribe/Listen to the kafka topic(i.e javaguides)

    private static final Logger LOGGER  = LoggerFactory.getLogger(KafkaConsumer.class);

    //consume method acts as a subscriber where any message published/sent to kafka topic("javaguides") will be consumed by
    // consume() method because we have added @KafkaListener(topics="javaguides") annotation
    // topics="javaguides" which is the name of our topic
    // groupId="myGroup" which is defined in application.properties as the name of consumer groupId
    @KafkaListener(topics = "javaguides", groupId = "myGroup")
    public void consume(String message){
        LOGGER.info("Message received -> {}", message);

    }

}

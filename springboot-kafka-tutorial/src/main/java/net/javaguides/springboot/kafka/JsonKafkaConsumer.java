package net.javaguides.springboot.kafka;

import net.javaguides.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);


    //consume method acts as a subscriber where any message published/sent to kafka topic("javaguides_json") will be consumed by
    // consume() method because we have added @KafkaListener(topics="javaguides_json") annotation
    // topics="javaguides_json" which is the name of our topic for sending and giving json objects
    // groupId="myGroup" which is defined in application.properties as the name of consumer groupId
    // jsonDeserializer will convert json object to java User object
    @KafkaListener(topics = "javaguides_json",groupId = "myGroup")
    public void consume(User user){
        LOGGER.info(String.format("Json Message Received -> %s", user.toString()));
    }

}

package net.javaguides.springboot.kafka;

import net.javaguides.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    @Autowired  // inject dependency of KafkaTemplate<String, User> since we want to return value as object i.e User object
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User data){
        LOGGER.info(String.format("Message Sent -> %s", data.toString())); // get output in the console

        // create a message of User object including our topic i.e javaguides and finally
        // send the message to kafka topic using KafkaTemplate.send(message).
        // jsonSerializer will convert java User object to json object
        Message<User> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC,"javaguides_json")
                .build();

        kafkaTemplate.send(message);

    }

}

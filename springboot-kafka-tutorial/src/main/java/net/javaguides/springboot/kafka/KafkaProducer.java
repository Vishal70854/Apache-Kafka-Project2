package net.javaguides.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service    // Service class containing all business functionality
public class KafkaProducer {
    // inorder to producer messages and send to topic we have to create bean
    // of spring provided Kafka Template<Key, value>

    // import Loggger of <org.slf4j.Logger;> class
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    // constructor based dependency injection of KafkaTemplate<Key, value>
    // we can use @Autowired keyword in the above statement to use KafkaTemplate<> as dependency injection
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // sendMessage(message) will be used by KafkaTemplate<> to send messages to
    // the topic which we created in KafkaTopicConfig class i.e(topicname = "javaguides")
    public void sendMessage(String message){
        // message will be sent to the topic "javaguides" by kafkatemplate
        LOGGER.info("Message sent : {}", message);  // get output in the console/terminal
        kafkaTemplate.send("javaguides",message); // message will be sent to topic javaguides
    }



}

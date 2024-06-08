package net.javaguides.springboot.controller;

import net.javaguides.springboot.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")    // the path for our kafka application
public class MessageController {
    @Autowired  // dependency injection of KafkaProducer using @Autowired keyword
    private KafkaProducer kafkaProducer;

    // here in publish(message) method we want our message to be sent in the url.
    // so we are using @RequestParam("messageName) annotation. messageName should match the parameter name i.e message

    //http://localhost:8080/api/v1/kafka/publish?message=hello vishal
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message); // call the sendMessage(msg) method of KafkaProducer class where kafkaTemplate will send the message to kafka topic
        return ResponseEntity.ok("Message sent to the topic");
    }
}

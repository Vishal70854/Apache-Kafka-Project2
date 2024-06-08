package net.javaguides.springboot.controller;

import net.javaguides.springboot.kafka.JsonKafkaProducer;
import net.javaguides.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// this class will produce java object into json object via kafkatemplate to kafkatopic
@RestController
@RequestMapping("/api/v1/kafka")
public class JsonMessageController {
    @Autowired  // inject dependencies of JsonKafkaProducer
    private JsonKafkaProducer jsonKafkaProducer;

    // create a new user object and produce it as a message from JsonkafkaProducer and sent it to kafkatopic
    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user){
        jsonKafkaProducer.sendMessage(user);
        return ResponseEntity.ok("JSON Message sent to Kafka Topic");
    }




}

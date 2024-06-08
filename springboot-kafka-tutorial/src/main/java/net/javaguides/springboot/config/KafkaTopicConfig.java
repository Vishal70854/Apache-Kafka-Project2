package net.javaguides.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    // we need to create seperate topic for sending and receiving separate data. ex : String, Integer, Float, Long, Character, Object etc.
    @Bean   // create a new topic using TopicBuilder for sending and receiving String values in topic
    public NewTopic javaGuidesTopic(){
        return TopicBuilder.name("javaguides")  // javaguides is the name of the topic
                .build();
    }

    @Bean   // create a new topic using TopicBuilder for sending and receiving json objects
    public NewTopic javaGuidesJsonTopic(){
        return TopicBuilder.name("javaguides_json")  // javaguides is the name of the topic
                .build();
    }
}

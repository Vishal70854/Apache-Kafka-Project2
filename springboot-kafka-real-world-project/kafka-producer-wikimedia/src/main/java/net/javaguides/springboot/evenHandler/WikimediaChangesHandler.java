package net.javaguides.springboot.evenHandler;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

// this class is for getting the real time event streams from wikimedia.
// we have defined onMessage() method after implementing BackgroundEventHandler interface which will always give
// any new messages coming to wikimedia so that the kafkatemplate could send it to kafka topic
public class WikimediaChangesHandler implements BackgroundEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;
    // parameterized constructor/  a form of dependency injection in spring boot using constructor
    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }


    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    // whenever any new message will be available on wikimedia then this onMessage() method will be triggered and we will get real time event data
    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info(String.format("Event Data -> %s", messageEvent.getData()));
        // send message event to kafka topic mentioned below
        kafkaTemplate.send(topic, messageEvent.getData());

    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

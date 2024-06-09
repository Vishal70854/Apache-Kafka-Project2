package net.javaguides.springboot.kafka;

import net.javaguides.springboot.model.WikimediaData;
import net.javaguides.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    @Autowired  // inject dependency of WikimediaDataRepository so that we can save the real time wikimedia data in database from kafka consumer
    private WikimediaDataRepository dataRepository;
    @KafkaListener(
            topics = "wikimedia_recentchange",
            groupId = "myGroup"
    )
    public void consume(String eventMessage){

        LOGGER.info(String.format("Event message Received -> %s", eventMessage));


        WikimediaData data = new WikimediaData(); // create object of wikimediaData
        data.setWikiEventData(eventMessage);// set the WikiEventData with the eventMessage

        dataRepository.save(data);   // save the wikimedia data in database

    }
}

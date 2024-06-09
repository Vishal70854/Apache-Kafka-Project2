package net.javaguides.springboot.kafka;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import net.javaguides.springboot.evenHandler.WikimediaChangesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    // inject bean of KafkaTemplate<key, value> using @Autowired(i.e dependency injection)
    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage() throws IllegalArgumentException, NullPointerException, InterruptedException, URISyntaxException {
        String topic = "wikimedia_recentchange";

        // to read real time stream data from wikimedia, we use event source
        // we have to add dependency of okhttp-evensource, jackson-core, jackson-databind in pom.xml of this sub-module project
        // okhttp-evensource is used to read real time stream data(here we will read real time event stream data from wikimedia)

        // create EventHandler object and call the onMessage method to read real time event data from wikimedia and send it to kafka topic
        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate,topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // Create an EventSource.Builder (created to convert String url to EventSource.Builder type)
        // we have to pass it to BackgroundEventSource.Builder(eventHandler, eventSourceBuilder) as parameters
        // so that we could get real time data from the wikimedia url to kafka producer

        EventSource.Builder eventSourceBuilder = new EventSource.Builder(URI.create(url));
// Create the BackgroundEventSource.Builder
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder);

        BackgroundEventSource eventSource = builder.build();

        eventSource.start();    // it will start the thread as eventsource is also implementing Executor service

        TimeUnit.MINUTES.sleep(10); // after reading real time event, sleep for 10 minutes

    }

}

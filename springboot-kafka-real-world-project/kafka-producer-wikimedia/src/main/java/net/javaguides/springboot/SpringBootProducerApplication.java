package net.javaguides.springboot;

import net.javaguides.springboot.kafka.WikimediaChangesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootProducerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProducerApplication.class);
    }


    // we have to call the sendMessage() method of wikimediachangesproducer inorder to get real time json data from wikimedia
    @Autowired  // inject dependency of WikimediaChangesProducer
    private WikimediaChangesProducer wikimediaChangesProducer;
    @Override   // CommandLineRunner run method(it will be called by spring boot at start of application and wikimediaChangesProducer.sendMessage() will be instantiated)
    public void run(String... args) throws Exception {
        wikimediaChangesProducer.sendMessage(); // call the send message as soon as the spring boot application runs to get real time data from wikimedia
    }
}

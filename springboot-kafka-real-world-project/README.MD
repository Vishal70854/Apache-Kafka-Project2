## Read Real time event data from Wikimedia
# Overview:
    -- Get the data from Wikimedia to Kafka Producer
    -- send the data from Kafka Producer to Kafka Topic
    -- Consume the data from Kafka Topic to the Kafka Consumer
    -- store the data of wikimedia from Kafka Consumer to Database

## Get the data from Wikimedia to Kafka Producer:
   Create a submodule of the project by importing a maven module named(kafka-producer-wikimedia)
   Add net.javaguides.springboot directory under java directory and add SpringbootProducerApplication class as main class
   Annotate the class with @SpringBootApplication
   
    Now create a KafkaTopicConfig class under config package where you will define
    a NewTopic using TopicBuilder named("wikimedia_recentchange")
    
    Now we have to create a KafkaProducer which will read real time event stream/data from wikimedia site.
    Create a producer class named("WikimediaChangesProducer") and @Autowired KafkaTemplate<String, String>
    Create a method sendMessage() which will read real time event data from wikimedia and send to kafka topic from kafkaproducer

    Inorder to read real time event data from wikimedia, we are going to use :
        okhttp-evensource, jackson-core, jackson-databind in pom.xml of this sub-module project(kafka-producer-wikimedia) and add in pom.xml
    okhttp-eventsource dependency - https://mvnrepository.com/artifact/com.launchdarkly/okhttp-eventsource
    add okhttp maven dependency needed for reading real time even data from wikimedia - https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    jackson json maven dependency - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    jackson bind maven dependency - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
------------------------------------------------------------------------------------

Now add a event handler class which will read real time event stream data from wikimedia
Create a class WikimediaChangesHandler under eventHandler package and implement BackgroundEventHandler interface
override all unimplemented methods.
   Note here we will just implement onMessage(s, messageEvent) method as this method will be trigger when any new msg/event occurs in wikimedia
Add kafkaTemplate and Topic to this class via constructor dependency injection.
now call the kafkaTemplate.send(topic, messageEvent.getData()) to send real time data to kafka topic

------------------------------------------------------------------------------------

Now goto WikimediaChangesProducer and in sendMessage() method we have to call the event handler to get real time data from wikimedia and send it to apache kafka topic

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
our Kafka producer is ready to take real time events/msgs from wikimedia and send it to kafka topic.

------------------------------------------------------------------------------------

Create a submodule project name kafka-consume-database
Configure the main application in the project
Create a class KafkaConsumerDatabase and create a method consume(message) which will 
consume the message from the kafka topic
Annotate the method with @KafkaListener(topics = "wikimedia_recentchange", groupId="myGroup")

------------------------------------------------------------------------------------

Create a MYSQL database to store the real time data from kafka consumer to database
Flow is like :
   Wikimedia real time data -> kafka producer -> kafka topic -> kafka consumer -> database

Add the following spring data jpa and mysql jdbc connect dependencies in pom.xml of Kafka-consumer-database submodule project
   
      <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <scope>runtime</scope>
    </dependency>

Create a database named wikimedia(example database name)
set the mysql and hibernate configurations in application.properties 
--------------------------------------------------------------------------
Now create a Entity named WikimediaData with field id, wikiEventData(it will store wikimedia data in database)
--------------------------------------------------------------------------
Create an Interface WikimediaDataRepository which implements JPARepository<WikemediaData, Long>
--------------------------------------------------------------------------
Now run the Kafka consumer project and kafka producer project and you will be able to store
real time event stream data of wikimedia.
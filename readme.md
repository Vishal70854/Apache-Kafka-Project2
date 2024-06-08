## Apache Kafka with Spring Boot
1. create a spring boot project and add dependencies of Spring Web and Spring for Apache Kafka
   2. Now go to application.properties and add configurations for kafka consumer and kafka consumer
      # kafka consumer configuration
      spring.kafka.consumer.bootstrap-servers=localhost:9092
      spring.kafka.consumer.group-id=myGroup
      spring.kafka.consumer.auto-offset-reset=earliest
      spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
      spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
   
      #kafka producer configuration
      spring.kafka.producer.bootstrap-servers=localhost:9092
      spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
      spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
      3. Now we have to create a topic in kafka Broker so that producer can produce messages to topic
         -- create a package config in your project and create a KafkaTopicConfig class(add @Configuration annotation so that you can create bean of Topic)
            the syntax is as follows where we have just given name to the topic and created the bean
         (we can also give partions, replication etc. but for now we are only creating bean of Topic using TopicBuilder)

         @Configuration
         public class KafkaTopicConfig {
         @Bean   // create a new topic using TopicBuilder
         public NewTopic javaGuidesTopic(){
               return TopicBuilder.name("javaguides")
                                    .build();
               }
         }
5. Create Kafka Producer in (kafka package)
    create a KafkaProducer class and inject dependency of KafkaTemplate<key, value>.
    create a method sendMessage(String message) which will be used by kafkatemplate to send
    messages to the topic{name="javaguides"}
    Run the application to check if everything is fine.
6. Create REST API to send message
    Create a Controller class where we will create a method publish(message)
    the publish method will call the sendMessage(message) of KafkaProducer to send message to kafka topic
    the message will be sent to kafka topic("javaguides" in our project) via kafkatemplate managed by spring boot
    now hit the url of the publish() method and check whether kafkatemplate is sending message to kafka topic or not
    ---- you can also consume message from the topic(i.e javaguides) via terminal/cmd also
    type : .\bin\windows\kafka-console-consumer.bat --topic javaguides --from-beginning --bootstrap-server localhost:9092

7. Create Kafka Consumer
    Create a class KafkaConsumer and annotate it with @Service. 
    Now create a method consume(message) and annotate it with @kafkaListener(topics="javaguides", groupId="myGroup") 
     which will consume message sent to kafka topic(i.e javaguides)
    syntax:
       @KafkaListener(topics = "javaguides", groupId = "myGroup")
       public void consume(String message){
       LOGGER.info("Message received -> {}", message);
       }
8. Configure Kafka Producer and Consumer for JSON Message
    Goto application.properties and comment value-deserializer property from Consumer configuration
    We have to add JSONDeserializer configuration for Kafka Consumer configuration
   syntax: spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
    
    Similarly, we have to change the value-serializer configuration of Kafka producer
    comment out value-serializer configuration and add the below configuration
    syntax: spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
    
    Note: we just have to change the value-serializer and value-deserializer configuration from both
        kafka producer and kafka consumer in application.properties to change java object to JSON objects.

    You can also add
   spring.kafka.consumer.properties.spring.json.trusted.packages=*
    in kafka consumer configuration so that kafka consumer can deserialize all java objects into json.
      
    Note: we are just changing value-serializer and value-deserializer bcoz we will send the object(ex User object) as value
            to kafka topic from producer and kafka consumer will consume/get the User object as value(in form of JSON)
    
    Create a class JsonKafkaProducer where you inject the dependencies of KafkaTemplate<String, User> since we want to return
    value as User object in Json format
    write a sendMessage(User user) method and send the message using kafkatemplate.send(message)
    which will send message to the kafkatopic

    Note: after doing changes in application.properties for json-serializer and json-deserialize, run the application
        and spring will automatically create a offset with name (javaguides-0) i.e the name of your offset and also partitions will be 
        created by spring automatically.
9. Create REST Api to store json objects in kafka topic
    create a controller class JsonMessageController and create PostMapping method publish(user) taking User object as request body
    call the jsonkafkaProducer.send(user) it will publish the json object to the topic javaguides.
    You will get error in console when you hit the api of publish for JsonMessageController because
   we have created topic(javaguides) which stores values as String type.
    So create another Topic named="javaguides_json" iin KafkaTopicConfig file which will store json objects
    in kafka broker.
    Now the error will be gone if we hit the url for creating a json object and storing it in javaguides_json topic.
10. Create a consumer named JsonKafkaConsumer
    Create a class JsonKafkaConsumer and annotate it with @Service.
    Now create a method consume(user) and annotate it with @kafkaListener(topics="javaguides_json", groupId="myGroup")
    which will consume json object sent to kafka topic(i.e javaguides_json)
    JsonKafkaConsumer will consume json object from "javaguides_json" topic which is created to take and give json object
    syntax:
    @KafkaListener(topics = "javaguides_json",groupId = "myGroup")
    public void consume(User user){
    LOGGER.info(String.format("Json Message Received -> %s", user.toString()));
    }
11. 


 
=========================================================================

##Apache Kafka run on windows(Local machine using command prompt)
{Note :
Before starting kafka zookeeper and kafka server(broker) if you delete all the kafka-logs and zookeeper logs that was created by kafka earlier when you used it. then it will be very helpful to run kafka without error

Goto C://tmp/kafka and delete kafka-logs and zookeeper folder.
After that run kafka zookeeper in step 1: zookeeper folder and logs will be created by kafka
-- Now you run Kafka server mentioned in step 2: kafka-logs folder and the logs will be created by kafka.
}

Steps:
1. Start Zookeeper(open cmd go to kafka folder under bin under windows i.e kafka/bin/windows) and type:
   zookeeper-server-start.bat ..\..\config\zookeeper.properties
   {                             OR
   YOU can go to just kafka folder i.e in my system it is :    C:\kafka_2.13-3.7.0

   TYPE : .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
   both will work fine.
   }

2. Start Kafka server(or kafka broker)(open cmd go to kafka folder under bin under windows i.e kafka/bin/windows) and type:
   .\kafka-server-start.bat ..\..\config\server.properties
   {                             OR
   YOU can go to just kafka folder i.e in my system it is :     C:\kafka_2.13-3.7.0

   TYPE : .\bin\windows\kafka-server-start.bat .\config\server.properties
   both will work fine.
   }

3. Create new topics with kafka-topics
   .\kafka-topics.bat --create --topic user-topic --bootstrap-server localhost:9092
   (Note : here user-topic is the name of your topic which you created)

   {To check details about a topic you can type:
   .\kafka-topics.bat --describe --topic <give-topic-name> --bootstrap-server localhost:9092
   }

( we have to create topics using kafka-topics.bat file followed by --create --topic for creating a topic and followed by topic name..
here user-topic is the name of the topic we have created for storing user related messages and boostrap server is running on port 9092.)


4. Produce example message with kafka-console-producer
   .\kafka-console-producer.bat --topic user-topic --bootstrap-server localhost:9092

(after writing the above line in cmd (under bin/windows) we can write messages from kafka-console-producer and that message will be consumed by kafka-console-consumer.)


5. Consume the message with kafka-console-consumer
   .\kafka-console-consumer.bat --topic user-topic --from-beginning --bootstrap-server localhost:9092

as you can see we have used kafka-console-consumer to consume messages from kafka-console-producer.
we will mention kafka-console-consumer followed by --topic and our topic name which we want to consume. After that we have to mention whether we want to read messages from beginning or from some point (mention like --beginning to read messages of kafka producer from beginning) and then followed by --bootstrap-server localhost:9092(here 9092 is our default port for kafka. we can change it as per our convenience)

Note: we can create as many consumers as per our requirement.




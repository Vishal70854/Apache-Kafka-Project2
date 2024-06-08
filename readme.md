Apache Kafka run on windows(Local machine using command prompt)
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




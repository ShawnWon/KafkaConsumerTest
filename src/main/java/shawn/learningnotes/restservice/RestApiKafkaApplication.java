package shawn.learningnotes.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import java.io.*;


@SpringBootApplication
public class RestApiKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiKafkaApplication.class, args);

                Properties properties = new Properties();
                properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
                properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
                properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
                properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
                properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
                properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
                properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
                properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 100000);

                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
                consumer.subscribe(Collections.singletonList("my-topic"));
                
                                
                while(true){
                ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
                consumerRecords.forEach(record -> {
                  System.out.println(record.value());
                  try {
                    FileWriter writer = new FileWriter("src/main/resources/temp.txt", true);
                    BufferedWriter buffer = new BufferedWriter(writer);
                    buffer.write(record.value());
                    buffer.newLine();
                    buffer.close();
                  }
                  catch(Exception e){
                    System.out.println(e);  
                  }
                });
                }
	}

}

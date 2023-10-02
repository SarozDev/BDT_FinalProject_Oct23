package Twitterkafkaproducersvc;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageSender {

    private final static String BOOTSTRAP_SERVERS ="localhost:9092";
    private final static String TOPIC = "tweet-topic";
    
    public static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
    
    public static void sendTweetsToKafka(String id, String text) throws Exception {
        final Producer<String, String> producer = createProducer();
        final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, id, text);
//        System.out.println(record);
        producer.send(record).get();
    }
}
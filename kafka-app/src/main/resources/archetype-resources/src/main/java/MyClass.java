#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class MyClass {
    public static void main(String[] args) throws Exception {
        // Producer
        final Producer<String, String> producer = new KafkaProducer<>(
                Map.of(BOOTSTRAP_SERVERS, System.getenv(BOOTSTRAP_SERVERS)),
                new StringSerializer(), new StringSerializer());
                
        producer.send(new ProducerRecord<>(TOPIC,"DUMMY",null)).get(20, TimeUnit.SECONDS); // Force topic creation
        
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            producer.send(new ProducerRecord<>(TOPIC,
                    "key-"+ThreadLocalRandom.current().nextInt(10),
                    "val-"+ThreadLocalRandom.current().nextInt()));
        }, 0, 100, TimeUnit.MILLISECONDS);
        
        // Consumer
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(
                Map.of(BOOTSTRAP_SERVERS, System.getenv(BOOTSTRAP_SERVERS),
                        GROUP_ID, System.getenv(GROUP_ID),
                        AUTO_OFFSET_RESET, "earliest"),
                new StringDeserializer(), new StringDeserializer());

        consumer.subscribe(List.of(TOPIC));
        while (true) {
            consumer.poll(1000).spliterator().forEachRemaining(System.out::println);
        }
    }
    
    public static final String TOPIC = System.getenv("TOPIC");
    public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
    public static final String GROUP_ID = "group.id";
    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
}

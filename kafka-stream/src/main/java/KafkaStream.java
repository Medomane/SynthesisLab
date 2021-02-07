import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaStream {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-consumer");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,1000);

        var sb = new StreamsBuilder();
        var kStream = sb.stream("StreamTopic", Consumed.with(Serdes.String(),Serdes.String()));
        kStream
                .map((k,v)->{
                    try {
                        var sum = (long)(Arrays.stream(new ObjectMapper().readValue(v,Order[].class)).mapToDouble(Order::getQuantity).sum());
                        return KeyValue.pair(k,sum);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        return KeyValue.pair(k, 0L);
                    }
                })
                .groupByKey(Grouped.with(Serdes.String(),Serdes.Long()))
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                .reduce(Long::sum)
                .toStream()
                .map((k,v) -> KeyValue.pair(k.key(),v.toString()))
                .peek((k,v)-> System.out.println(k+" --> "+v))
                .to("streamKafkaTopic", Produced.with(Serdes.String(),Serdes.String()));

        var kStreams = new KafkaStreams(sb.build(),properties);
        kStreams.start();
    }
}

@Data
@NoArgsConstructor
class Order {
    private double quantity;
    private double productId;
    private Object product;
}

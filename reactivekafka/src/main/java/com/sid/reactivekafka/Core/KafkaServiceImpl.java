package com.sid.reactivekafka.Core;

import com.sid.reactivekafka.Model.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaServiceImpl implements KafkaService {
    private final Flux<ReceiverRecord<String, String>> testTopicStream;
    KafkaServiceImpl() {

        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "reactive-consumer");
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(kafkaProperties);

        testTopicStream = createTopicCache(receiverOptions);
    }

    public Flux<ReceiverRecord<String, String>> getTestTopicFlux() {
        return testTopicStream;
    }

    private <T, G> Flux<ReceiverRecord<T, G>> createTopicCache(ReceiverOptions<T, G> receiverOptions) {
        ReceiverOptions<T, G> options = receiverOptions.subscription(Collections.singleton("streamKafkaTopic"));
        return KafkaReceiver.create(options).receive().cache();
    }
}

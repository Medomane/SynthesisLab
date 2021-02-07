package com.sid.kafka.Core;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.sid.kafka.Model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    final KafkaTemplate<String, String> kafkaTemplate;

    public Consumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = MyGlobals.TOPIC,groupId = "group1")
    public void onMessage(ConsumerRecord<String,String> message) throws Exception {
        var customerId = Long.parseLong(message.key().replace('0',' ').replace('.',' ').trim());
        var orders = new JsonMapper().readValue(message.value(), Order[].class);
        System.out.println("***********Buy("+customerId+")***********");
        MyGlobals.buy(orders,customerId);
        kafkaTemplate.send("StreamTopic",String.valueOf(customerId),message.value());
    }
}

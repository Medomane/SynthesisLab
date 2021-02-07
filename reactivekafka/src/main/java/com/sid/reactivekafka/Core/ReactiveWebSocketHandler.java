package com.sid.reactivekafka.Core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.reactivekafka.Model.KafkaService;
import com.sid.reactivekafka.Model.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private static final ObjectMapper json = new ObjectMapper();

    private final KafkaService kafkaService;

    public ReactiveWebSocketHandler(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(kafkaService.getTestTopicFlux()
                .map(record -> {
                    Message message = new Message("kafkaStream", record.key()+"|"+record.value());
                    System.out.println(record.key()+" --> "+record.value());
                    try {
                        return json.writeValueAsString(message);
                    } catch (JsonProcessingException e) {
                        return "Error while serializing to JSON";
                    }
                })
                .map(webSocketSession::textMessage))
                .and(webSocketSession.receive().map(WebSocketMessage::getPayloadAsText).log());
    }
}

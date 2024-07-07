package com.bootcamp.ms.orchestrator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	@Value(value = "${KAFKA_CONSUMER_TOPIC}")
	private String topicName;
	
	@Bean
    public NewTopic jsonTopic() {
        return TopicBuilder.name(topicName)
//                .partitions(10)
                .replicas(1)
                .build();
    }
}

package com.bootcamp.ms.order.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.bootcamp.ms.common.dto.OrderRequest;
import com.bootcamp.ms.common.dto.OrderResponse;
import com.bootcamp.ms.common.status.OrderStatus;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
	private KafkaTemplate<String, OrderRequest> template;
	
	@Value(value = "${KAFKA_PRODUCER_TOPIC}")
	private String topicName;

	public OrderServiceImpl(KafkaTemplate<String, OrderRequest> template) {
		super();
		this.template = template;
	}
	
	

	@Override
	public Flux<OrderResponse> getAll() {
		log.info("Order Service:getAll");
		return Flux.empty();
	}

	@Override
	public Mono<OrderResponse> getById(Long id) {
		log.info("Order Service:get by Id={}", id);
		return Mono.just(OrderResponse.builder().orderId(id).amount(1000d).customerId(1L).productId(1L).quantity(5).status(OrderStatus.COMPLETED).build());
	}

	@Override
	public Mono<OrderResponse> createOrder(OrderRequest request) {
		log.info("Order Service:create Order");
		// call orderRepository.save here
		// return orderRepository.save
		return Mono.just(OrderResponse.builder()
				.orderId(1L)
				.amount(request.getAmount())
				.customerId(request.getCustomerId())
				.productId(request.getProductId())
				.quantity(request.getQuantity())
				.status(OrderStatus.CREATED).build())
				.doOnNext(response -> this.sendOrderRequestEvent(request));
	}

	@Override
	public Mono<OrderResponse> updateOrder(Long id, OrderRequest request) {
		log.info("update Order id={}", id);
		return Mono.just(OrderResponse.builder()
				.orderId(id)
				.amount(request.getAmount())
				.customerId(request.getCustomerId())
				.productId(request.getProductId())
				.quantity(request.getQuantity())
				.status(request.getStatus()).build());
	}
	
	private void sendOrderRequestEvent(OrderRequest orderRequest) {
		log.info("OrderRequest sent: {}", orderRequest);
		Message<OrderRequest> message = MessageBuilder
				.withPayload(orderRequest)
				.setHeader(KafkaHeaders.TOPIC, topicName)
				.build();
				
		template.send(message);
	}
}

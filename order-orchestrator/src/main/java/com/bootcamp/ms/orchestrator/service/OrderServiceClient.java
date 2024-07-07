package com.bootcamp.ms.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.ms.common.dto.OrderRequest;
import com.bootcamp.ms.common.dto.OrderResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrderServiceClient {
	@Autowired
    @Qualifier("order")
    private WebClient webClient;
	
	public Mono<OrderResponse> updateOrder(final OrderRequest request){
		String uriUpdateOrder = String.format("/api/order/update/%s", request.getOrderId());
		return this.webClient
                .put()
                .uri(uriUpdateOrder)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OrderResponse.class)
                .doOnNext(orderResponse -> {
                	log.info("call {}, response:{}", uriUpdateOrder, orderResponse);
                });
	}
}

package com.bootcamp.ms.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.ms.common.dto.InventoryRequest;
import com.bootcamp.ms.common.dto.InventoryResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class InventoryServiceClient {
	@Autowired
    @Qualifier("inventory")
    private WebClient webClient;
	
	public Mono<InventoryResponse> deduct(final InventoryRequest request){
		String uriPath = String.format("/api/inventory/deduct/%s", request.getProductId());
		return this.webClient
                .put()
                .uri(builder -> builder.path(uriPath).queryParam("quantity", request.getQuantity()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .doOnNext(inventoryResponse -> {
                	log.info("call {}, response:{}", uriPath, inventoryResponse);
                });
	}
	
	public Mono<InventoryResponse> add(final InventoryRequest request){
		String uriPath = String.format("/api/inventory/add/%s", request.getProductId());
		return this.webClient
                .put()
                .uri(builder -> builder.path(uriPath).queryParam("quantity", request.getQuantity()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .doOnNext(inventoryResponse -> {
                	log.info("call {}, response:{}", uriPath, inventoryResponse);
                });
	}
}

package com.bootcamp.ms.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.ms.common.dto.PaymentRequest;
import com.bootcamp.ms.common.dto.PaymentResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PaymentServiceClient {
	@Autowired
    @Qualifier("payment")
    private WebClient webClient;
	
	public Mono<PaymentResponse> debit(final PaymentRequest request){
		String uriPath = String.format("/api/payment/debit/%s", request.getCustomerId());
		return this.webClient
                .put()
                .uri(builder -> builder.path(uriPath).queryParam("amount", request.getAmount()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .doOnNext(paymentResponse -> {
                	log.info("call {}, response:{}", uriPath, paymentResponse);
                });
	}
	
	public Mono<PaymentResponse> credit(final PaymentRequest request){
		String uriPath = String.format("/api/payment/credit/%s", request.getCustomerId());
		return this.webClient
                .put()
                .uri(builder -> builder.path(uriPath).queryParam("amount", request.getAmount()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)                
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .doOnNext(paymentResponse -> {
                	log.info("call {}, response:{}", uriPath, paymentResponse);
                });
	}
}

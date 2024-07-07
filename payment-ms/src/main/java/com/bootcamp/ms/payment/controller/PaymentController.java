package com.bootcamp.ms.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.ms.common.dto.PaymentRequest;
import com.bootcamp.ms.common.dto.PaymentResponse;
import com.bootcamp.ms.common.status.PaymentStatus;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public Flux<ResponseEntity<PaymentResponse>> getAll() {
		log.info("get All");
		return Flux.empty();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<PaymentResponse>> getById(@PathVariable("id") Long id) {
		log.info("get by Id");
		return Mono.just(ResponseEntity.ok(PaymentResponse.builder().customerId(id).amount(1000d).status(PaymentStatus.APPROVED).build()));
	}
	

	@PutMapping("/debit/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<PaymentResponse>> debit(@PathVariable("id") Long id, @RequestParam Double amount) {
		log.info("debit balance for customerId={}, amount={}", id, amount);
		if(id <= 10) {
			return Mono.just(ResponseEntity.ok(PaymentResponse.builder()				
					.customerId(id)				
					.amount(amount)
					.status(PaymentStatus.APPROVED).build()));
		} else {
			return Mono.just(ResponseEntity.ok(PaymentResponse.builder()				
					.customerId(id)				
					.amount(amount)
					.status(PaymentStatus.REJECTED).build()));
		}
	}
	
	@PutMapping("/credit/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<PaymentResponse>> credit(@PathVariable("id") Long id, @RequestBody Double amount) {
		log.info("credit balance for customerId={}, amount={}", id, amount);
		return Mono.just(ResponseEntity.ok(PaymentResponse.builder()				
				.customerId(id)				
				.amount(amount)
				.status(PaymentStatus.APPROVED).build()));
	}
}

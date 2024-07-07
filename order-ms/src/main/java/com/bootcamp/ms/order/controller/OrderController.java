package com.bootcamp.ms.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.ms.common.dto.OrderRequest;
import com.bootcamp.ms.common.dto.OrderResponse;
import com.bootcamp.ms.common.status.OrderStatus;
import com.bootcamp.ms.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {	
	private OrderService orderService;	
	
	
	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public Flux<ResponseEntity<OrderResponse>> getAll() {
		log.info("get All");
		return orderService.getAll().map(response -> ResponseEntity.ok(response));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<OrderResponse>> getById(@PathVariable("id") Long id) {
		log.info("get by Id");
		return orderService.getById(id).map(response -> ResponseEntity.ok(response));
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<OrderResponse>> createOrder(@RequestBody OrderRequest request) {
		log.info("controller: create Order");
		return orderService.createOrder(request)
				.map(response -> ResponseEntity.ok(response));
	}

	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<OrderResponse>> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequest request) {
		log.info("update Order id={}, status={}", id, request.getStatus());
		return orderService.updateOrder(id, request).map(response -> ResponseEntity.ok(response));
	}
}

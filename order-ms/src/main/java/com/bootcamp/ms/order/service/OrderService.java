package com.bootcamp.ms.order.service;

import com.bootcamp.ms.common.dto.OrderRequest;
import com.bootcamp.ms.common.dto.OrderResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
	public Flux<OrderResponse> getAll();
	public Mono<OrderResponse> getById(Long id);
	public Mono<OrderResponse> createOrder(OrderRequest request);
	public Mono<OrderResponse> updateOrder(Long id, OrderRequest request);
}

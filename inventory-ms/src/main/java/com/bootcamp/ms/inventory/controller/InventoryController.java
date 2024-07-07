package com.bootcamp.ms.inventory.controller;

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

import com.bootcamp.ms.common.dto.InventoryRequest;
import com.bootcamp.ms.common.dto.InventoryResponse;
import com.bootcamp.ms.common.status.InventoryStatus;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	public Flux<ResponseEntity<InventoryResponse>> getAll() {
		log.info("get All");
		return Flux.empty();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<InventoryResponse>> getById(@PathVariable("id") Long id) {
		log.info("get by Id");
		return Mono.just(ResponseEntity.ok(InventoryResponse.builder().orderId(id).productId(1L).quantity(5).status(InventoryStatus.INSTOCK).build()));
	}
	

	@PutMapping("/deduct/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<InventoryResponse>> deduct(@PathVariable("id") Long id, @RequestParam Integer quantity) {
		log.info("deduct product id={}", id);
		if(id <= 10) {
			return Mono.just(ResponseEntity.ok(InventoryResponse.builder()
					.productId(id)
					.orderId(1L)				
					.quantity(quantity)
					.status(InventoryStatus.INSTOCK).build()));
		} else {
			return Mono.just(ResponseEntity.ok(InventoryResponse.builder()
					.productId(id)
					.orderId(1L)				
					.quantity(quantity)
					.status(InventoryStatus.OUTOFSTOCK).build()));
		}
	}
	
	@PutMapping("/add/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<InventoryResponse>> add(@PathVariable("id") Long id, @RequestParam Integer quantity) {
		log.info("add product id={}", id);
		return Mono.just(ResponseEntity.ok(InventoryResponse.builder()
				.productId(id)
				.orderId(1L)				
				.quantity(quantity)
				.status(InventoryStatus.INSTOCK).build()));
	}
}

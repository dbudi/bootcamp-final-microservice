package com.bootcamp.ms.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bootcamp.ms.common.dto.InventoryRequest;
import com.bootcamp.ms.common.dto.OrderRequest;
import com.bootcamp.ms.common.dto.OrderResponse;
import com.bootcamp.ms.common.dto.PaymentRequest;
import com.bootcamp.ms.common.status.InventoryStatus;
import com.bootcamp.ms.common.status.OrderStatus;
import com.bootcamp.ms.common.status.PaymentStatus;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrchestratorService {
    
    @Autowired
    private InventoryServiceClient inventoryServiceClient;
    
    @Autowired
    private PaymentServiceClient paymentServiceClient;
    
    @Autowired
    private OrderServiceClient orderServiceClient;
	
	@KafkaListener(topics = "${KAFKA_CONSUMER_TOPIC}", groupId = "order-group")
	public void consume(OrderRequest orderRequest) {
		log.info("order event received : {}", orderRequest);
		
		this.orderProduct(orderRequest).subscribe(orderResponse -> {
				log.info("orderProduct response: {}", orderResponse);
			}
		);

	}
	
	private Mono<OrderResponse> orderProduct(final OrderRequest orderRequest){
		
		return inventoryServiceClient.deduct(getInventoryRequest(orderRequest))
				.flatMap(deduct -> {
					log.info("inventory status deduct={}", deduct.getStatus());
					if(deduct.getStatus() == InventoryStatus.INSTOCK) {
						return paymentServiceClient.debit(getPaymentRequest(orderRequest))
								.flatMap(debit -> {
									log.info("payment status debit={}", debit.getStatus());
									if(debit.getStatus() == PaymentStatus.APPROVED) {
										// payment status APPROVED
										// - update Order status to COMPLETE -> call /api/inventory/add
										log.info("update order COMPLETE");
										orderRequest.setStatus(OrderStatus.COMPLETED);
										return orderServiceClient.updateOrder(orderRequest);
									} else { 
										// payment status REJECTED
										// - ROLLBACK Inventory -> call /api/inventory/add
										// - update Order status to FAILED -.CALL /api/order/update/{orderId}
										log.info("payment REJECTED, ROLLBACK inventory");
										return inventoryServiceClient.add(getInventoryRequest(orderRequest))
												.flatMap(addInventory -> {
													log.info("payment REJECTED, update order status FAILED");
													orderRequest.setStatus(OrderStatus.FAILED);
													return orderServiceClient.updateOrder(orderRequest);
												});
									}									
								});
					} else {
						// inventory status OUTOFSTOCK
						// - update Order status to FAILED -.CALL /api/order/update/{orderId}
						log.info("inventory OUTOFSTOCK, update order status FAILED");
						orderRequest.setStatus(OrderStatus.FAILED);
						return orderServiceClient.updateOrder(orderRequest);
					}
				});
    }
	
	private PaymentRequest getPaymentRequest(OrderRequest orderRequest) {
		return PaymentRequest.builder().customerId(orderRequest.getCustomerId()).amount(orderRequest.getAmount()).build();
	}
	
	private InventoryRequest getInventoryRequest(OrderRequest orderRequest) {
		return InventoryRequest.builder().orderId(orderRequest.getOrderId()).productId(orderRequest.getProductId()).quantity(orderRequest.getQuantity()).build();
	}

	
	
}

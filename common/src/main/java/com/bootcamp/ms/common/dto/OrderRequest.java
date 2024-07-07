package com.bootcamp.ms.common.dto;

import com.bootcamp.ms.common.status.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	Long orderId;
	Long productId;
	Integer quantity;
	Double amount;	
	Long customerId;
	OrderStatus status;
}

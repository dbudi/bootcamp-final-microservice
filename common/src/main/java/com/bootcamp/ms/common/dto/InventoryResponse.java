package com.bootcamp.ms.common.dto;

import com.bootcamp.ms.common.status.InventoryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
	Long productId;
	Long orderId;
	Integer quantity;
	InventoryStatus status;
}

package com.bootcamp.ms.common.dto;

import com.bootcamp.ms.common.status.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
	Double amount;	
	Long customerId;
	PaymentStatus status;
}

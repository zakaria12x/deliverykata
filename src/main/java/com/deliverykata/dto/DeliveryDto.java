package com.deliverykata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryDto {
    private String id;
    private String customerId;
    private String deliveryModeId;
    private LocalDateTime deliveryTimeSlot;

}

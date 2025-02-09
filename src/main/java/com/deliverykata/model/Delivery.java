package com.deliverykata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "deliveries")
public class Delivery {
    @Id
    private String id;
    private String customerId;
    private String deliveryModeId;
    private LocalDateTime deliveryTimeSlot;
}
package com.deliverykata.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private List<String> deliveryIds;
}

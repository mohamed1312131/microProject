package com.example.planservice;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {
    @Id
    private String id;

    private String name;
    private String description;
    private double price;
    private String createdByAdminId; // who created it
    private String assignedToUserId; // who it is assigned to
}


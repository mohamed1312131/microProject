package com.example.planservice;



public record PlanDTO(
        String id,
        String name,
        String description,
        double price,
        String createdByAdminId,
        String assignedToUserId
) {}



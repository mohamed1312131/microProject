package com.example.planservice;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    private final PlanRepository repo;
    private final PlanMessageSender messageSender;

    public PlanService(PlanRepository repo, PlanMessageSender messageSender) {
        this.repo = repo;
        this.messageSender = messageSender;
    }

    public List<Plan> getAll() {
        return repo.findAll();
    }

    public Plan getById(String id) {
        return repo.findById(id).orElseThrow();
    }

    public Plan save(Plan plan) {
        return repo.save(plan);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
    public Plan createByAdmin(Plan plan, String adminId) {
        plan.setCreatedByAdminId(adminId);
        Plan saved = repo.save(plan);

        // Send async messages
        messageSender.sendStringMessage("New plan created: " + saved.getName());
        messageSender.sendObjectMessage(saved);

        return saved;
    }


    public Plan assignToUser(String planId, String userId) {
        Plan plan = repo.findById(planId).orElseThrow(() -> new RuntimeException("Plan not found"));
        plan.setAssignedToUserId(userId);
        return repo.save(plan);
    }



}

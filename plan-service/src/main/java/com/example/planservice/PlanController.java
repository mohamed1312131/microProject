package com.example.planservice;


import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    private final PlanService service;
    private final UserClient userClient;
    private final UserServiceFallbackWrapper userServiceWrapper;

    public PlanController(PlanService service, UserClient userClient,UserServiceFallbackWrapper userServiceWrapper) {
        this.service = service;
        this.userClient = userClient;
        this.userServiceWrapper = userServiceWrapper;
    }

    @GetMapping
    public List<PlanDTO> getAll() {
        return service.getAll().stream().map(this::toDTO).toList();
    }

    @GetMapping("/{id}")
    public PlanDTO getById(@PathVariable String id) {
        return toDTO(service.getById(id));
    }

    @PostMapping
    public PlanDTO create(@RequestBody PlanDTO dto) {
        return toDTO(service.save(fromDTO(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    // Manual mapping
    private PlanDTO toDTO(Plan p) {
        return new PlanDTO(p.getId(), p.getName(), p.getDescription(), p.getPrice(),p.getCreatedByAdminId(),p.getAssignedToUserId());
    }

    private Plan fromDTO(PlanDTO dto) {
        return Plan.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .build();
    }
    @GetMapping("/with-users")
    public Map<String, Object> getPlansWithUsers() {
        List<Plan> plans = service.getAll();
        List<UserDTO> users = userServiceWrapper.getAllUsers(); // now with circuit breaker
        return Map.of(
                "plans", plans,
                "users", users
        );
    }
    @PostMapping("/admin/{adminId}")
    public PlanDTO createByAdmin(
            @PathVariable String adminId,
            @RequestBody PlanDTO dto
    ) {
        // Optional: check that adminId refers to a user with role ADMIN via Feign
        Plan plan = fromDTO(dto);
        return toDTO(service.createByAdmin(plan, adminId));
    }

    @PutMapping("/{planId}/assign/{userId}")
    public PlanDTO assignPlanToUser(
            @PathVariable String planId,
            @PathVariable String userId
    ) {
        // Optional: check that userId exists via Feign
        return toDTO(service.assignToUser(planId, userId));
    }


}

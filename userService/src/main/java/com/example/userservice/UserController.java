package com.example.userservice;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAllUsers().stream().map(this::toDTO).toList();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable String id) {
        return toDTO(service.getUserById(id));
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        User user = fromDTO(dto);
        return toDTO(service.createUser(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteUser(id);
    }

    // Manual Mapper (instead of MapStruct)
    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    private User fromDTO(UserDTO dto) {
        return User.builder()
                .id(dto.id()) // or null if auto-generated
                .name(dto.name())
                .email(dto.email())
                .role(dto.role())
                .build();
    }
}

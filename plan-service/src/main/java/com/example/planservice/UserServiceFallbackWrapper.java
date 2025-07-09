package com.example.planservice;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceFallbackWrapper {

    private final UserClient userClient;

    public UserServiceFallbackWrapper(UserClient userClient) {
        this.userClient = userClient;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetAllUsers")
    public List<UserDTO> getAllUsers() {
        return userClient.getAllUsers();
    }

    public List<UserDTO> fallbackGetAllUsers(Throwable ex) {
        System.out.println("Fallback activated due to: " + ex.getMessage());
        return Collections.emptyList(); // Or add dummy data
    }
}

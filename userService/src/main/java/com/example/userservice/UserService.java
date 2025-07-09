package com.example.userservice;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(String id) {
        return repo.findById(id).orElseThrow();
    }

    public User createUser(User user) {
        return repo.save(user);
    }

    public void deleteUser(String id) {
        repo.deleteById(id);
    }
}

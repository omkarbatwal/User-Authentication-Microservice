package com.springboot.softwaremanagement.repository;

import com.springboot.softwaremanagement.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private Map<String, User> users;

    public UserRepository() {
        // Initialize user repository with dummy data (replace with your actual data source)
        users = new HashMap<>();
        users.put("john", new User("john", "password123"));
        users.put("mary", new User("mary", "secret456"));
    }

    public User findByUsername(String username) {
        return users.get(username);
    }
}

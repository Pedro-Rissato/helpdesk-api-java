package br.com.rissato.helpdesk.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.rissato.helpdesk.model.User;

public class UserRepository {
    Map<Long, User> users = new HashMap<>();
    Long nextId = 1L;
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
    public void save(User user) {
      if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
    if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
    if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }
    if (user.getRole() == null) {
            throw new IllegalArgumentException("User role cannot be null.");
        }
    if (user.getCreatedAt() == null) {
            throw new IllegalArgumentException("User createdAt cannot be null.");
        }
    if (user.getRole() == null) {
            throw new IllegalArgumentException("User role cannot be null.");
        }
        Long id = nextId++;
        users.put(id, user);
    }
    public void update(User user) {
        users.put(user.getId(), user);
    }
    public void deleteById(Long id) {
        users.remove(id);
    }
    public Map<Long, User> findAll() {
        return users;
    }
    public boolean existsById(Long id) {
        return users.containsKey(id);
    }
    public Optional <User> findByEmail (String email){
        return users.values().stream()
            .filter(user ->  user.getCreatedAt().equals(email))
            .findFirst();
    }
}
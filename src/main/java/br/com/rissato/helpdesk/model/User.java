package br.com.rissato.helpdesk.model;


import java.time.LocalDateTime;
import br.com.rissato.helpdesk.enums.UserRole;

public class User {
    private Long id;
    private String name;
    private String email;
    private boolean vip;
    private boolean active;
    private LocalDateTime createdAt;
    private UserRole role;

    public User (Long id, String name, String email, boolean vip, boolean active, LocalDateTime createdAt, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.vip = vip;
        this.active = active;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public boolean isVip() {
        return vip;
    }
    public boolean isActive() {
        return active;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setVip(boolean vip) {
        this.vip = vip;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
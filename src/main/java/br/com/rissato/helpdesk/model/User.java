import java.time.LocalDateTime;

public class User {
    private Long id;
    private String name;
    private String email;
    private boolean vip;
    private boolean active;
    private LocalDateTime createdAt;

    public User (Long id, String name, String email, boolean vip, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.vip = vip;
        this.active = active;
        this.createdAt = createdAt;
    }
}
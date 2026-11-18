import java.time.LocalDateTime;
import main.java.br.com.rissato.helpdesk.service.Closeable;
import main.java.br.com.rissato.helpdesk.model.User;
import main.java.br.com.rissato.helpdesk.model.Category;
import main.java.br.com.rissato.helpdesk.model.TicketStatus;
import main.java.br.com.rissato.helpdesk.model.Priority;


public class Ticket implements Closeable {
    private long id;
    private String title;
    private String description;
    private TicketStatus status;
    private Priority priority;
    private User requester;
    private Category category;
    private final LocalDateTime openedAt;
    private final LocalDateTime closedAt;

    boolean isOpen(){
        return status.isOpen();
        
    }
    
    private void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public TicketStatus getStatus(){
        return status;
    }
    public Priority getPriority(){
        return priority;
    }
    public User getRequester(){
        return requester;
    }
    public Category getCategory(){
        return category;
    }
    public LocalDateTime getOpenedAt(){
        return openedAt;
    }
    public LocalDateTime getClosedAt(){
        return closedAt;
    }
    public Ticket(long id, String title, String description, TicketStatus status, Priority priority, User requester, Category category, LocalDateTime openedAt, LocalDateTime closedAt){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.requester = requester;
        this.category = category;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }
    public void close() {
        if (!isOpen()) {
            throw new IllegalStateException("Ticket is already closed.");
        }
        this.status = TicketStatus.CLOSED;
    }
    public void cancel() {
        if (!isOpen()) {
            throw new IllegalStateException("Ticket is already closed.");
        }
        this.status = TicketStatus.CANCELED;
    }
    public void resolve() {
        if (!isOpen()) {
            throw new IllegalStateException("Ticket is already closed.");
        }
        this.status = TicketStatus.RESOLVED;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setRequester(User requester) {
        this.requester = requester;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
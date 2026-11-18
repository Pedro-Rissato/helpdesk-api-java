package br.com.rissato.helpdesk.model;

import java.time.LocalDateTime;
import br.com.rissato.helpdesk.service.Closeable;




public class Ticket implements Closeable {
    private long id;
    private String title;
    private String description;
    private TicketStatus status;
    private Priority priority;
    private User requester;
    private Category category;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    public boolean isOpen(){
        return status.isOpen();
        
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
    public Ticket(long id, String title, String description, Priority priority, User requester, Category category){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.priority = priority;
        this.requester = requester;
        this.category = category;
        this.openedAt = LocalDateTime.now();
    }
    @Override 
    public void close() {
        if (this.status != TicketStatus.RESOLVED) {
            throw new IllegalStateException("Cannot close a ticket that is not resolved.");
        }
        this.status = TicketStatus.CLOSED;
    }
    public void cancel() {
        if (!isOpen()) {
            throw new IllegalStateException("Ticket is already closed.");
        }
        this.status = TicketStatus.CANCELED;
        this.closedAt = LocalDateTime.now();
    }
    public void resolve() {
        if (!isOpen()) {
            throw new IllegalStateException("Ticket is already closed.");
        }
        this.status = TicketStatus.RESOLVED;
        this.closedAt = LocalDateTime.now();
    }

    public void changeStatus(TicketStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        if (status == TicketStatus.REOPENED && (this.status == TicketStatus.CANCELED || this.status == TicketStatus.RESOLVED || this.status == TicketStatus.CLOSED)) {
            this.status = TicketStatus.REOPENED;
            this.closedAt = null;
            return;
        }
        if (status == TicketStatus.REOPENED && this.status != TicketStatus.CANCELED && this.status != TicketStatus.RESOLVED && this.status != TicketStatus.CLOSED) {
            throw new IllegalStateException("Cannot reopen a ticket that is not closed.");
        }
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change status of a closed ticket.");
        }
        if (status == TicketStatus.CLOSED || status == TicketStatus.CANCELED || status == TicketStatus.RESOLVED) {
            throw new IllegalStateException("Cannot change status to a closed state.");
        }
    
            this.status = status;   
    }

    public void setPriority(Priority priority) {
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change priority of a closed ticket.");
        }
        this.priority = priority;
    }
    public void setRequester(User requester) {
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change requester of a closed ticket.");
        }
        this.requester = requester;
    }
    public void setCategory(Category category) {
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change category of a closed ticket.");
        }
        this.category = category;
    }
    public void setTitle(String title) {
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change title of a closed ticket.");
        }
        this.title = title;
    }
    public void setDescription(String description) {
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change description of a closed ticket.");
        }
        this.description = description;
    }
}
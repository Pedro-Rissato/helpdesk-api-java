package br.com.rissato.helpdesk.model;

import java.time.LocalDateTime;

import br.com.rissato.helpdesk.enums.TicketPriority;
import br.com.rissato.helpdesk.enums.TicketStatus;
import br.com.rissato.helpdesk.exception.BusinessRuleException;




public class Ticket{
    private long id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private User requester;
    private Category category;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private LocalDateTime resolvedAt;
    private User assignedAgent;

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
    public TicketPriority getPriority(){
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
    public Ticket(Long id, String title, String description, TicketPriority priority, User requester, Category category){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.priority = priority;
        this.requester = requester;
        this.assignedAgent = null;
        this.category = category;
        this.openedAt = LocalDateTime.now();
    }
    public void closeTicket() {
        if(this.status != TicketStatus.RESOLVED){
            throw new BusinessRuleException("Only resolved tickets can be closed.");
        }
        this.status = TicketStatus.CLOSED;
    }
    public void cancelTicket() {
        if(!this.isOpen()){
            throw new BusinessRuleException("Only open tickets can be cancel.");
        }
        this.closedAt = LocalDateTime.now();
        this.status = TicketStatus.CANCELED;
        
    }
    public void resolveTicket() {
        if(!this.isOpen()){
            throw new BusinessRuleException("Only open tickets can be resolved.");
        }
        this.resolvedAt = LocalDateTime.now();
        this.status = TicketStatus.RESOLVED;
        
    }

    public void changeStatus(TicketStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        if (status == TicketStatus.REOPENED && (this.status == TicketStatus.CANCELED || this.status == TicketStatus.RESOLVED)) {
            this.status = TicketStatus.REOPENED;
            this.closedAt = null;
            return;
        }
        if (status == TicketStatus.REOPENED && this.status != TicketStatus.CANCELED && this.status != TicketStatus.RESOLVED) {
            throw new IllegalStateException("Cannot reopen a ticket that is not open.");
        }
        if (!isOpen()) {
            throw new IllegalStateException("Cannot change status of a ticket that is not open.");
        }
        if (status == TicketStatus.CLOSED || status == TicketStatus.CANCELED || status == TicketStatus.RESOLVED) {
            throw new IllegalStateException("Cannot use this method to change the status of the ticket.");
        }
    
            this.status = status;   
    }

    public void setPriority(TicketPriority priority) {
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
    public void assignAgent(User agent) {
        this.assignedAgent = agent;
    }
    public User getAssignedAgent() {
        return assignedAgent;
    }

    public void unassignAgent() {
        if (!isOpen()) {
            throw new BusinessRuleException("Cannot unassign an agent from a closed ticket.");
        }
        this.assignedAgent = null;
    }

    public Ticket(Long id, String title, String description, TicketPriority priority, User requester, Category category, LocalDateTime openedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.priority = priority;
        this.requester = requester;
        this.assignedAgent = null;
        this.category = category;
        this.openedAt = openedAt;
    }

    public Ticket(Long id, String title, String description, TicketPriority priority, Category category, User requester, LocalDateTime openedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.priority = priority;
        this.requester = requester;
        this.assignedAgent = null;
        this.category = category;
        this.openedAt = openedAt;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    public Ticket(Long id, String title, String description, TicketStatus status, TicketPriority priority, Category category, User requester, User agentUser){
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.priority = priority;
        this.requester = requester;
        this.assignedAgent = agentUser;
        this.openedAt = LocalDateTime.now();
        this.closedAt = null;
        this.resolvedAt = null;
    }
    
}
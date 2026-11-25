package br.com.rissato.helpdesk.service;


import br.com.rissato.helpdesk.enums.TicketPriority;
import br.com.rissato.helpdesk.enums.TicketStatus;
import br.com.rissato.helpdesk.enums.UserRole;
import br.com.rissato.helpdesk.exception.BusinessRuleException;
import br.com.rissato.helpdesk.exception.TicketNotFoundException;
import br.com.rissato.helpdesk.model.Category;
import br.com.rissato.helpdesk.model.Ticket;
import br.com.rissato.helpdesk.model.User;
import br.com.rissato.helpdesk.repository.TicketRepository;

public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TicketService(TicketRepository ticketRepository, UserService userService, CategoryService categoryService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }
   
    public void checkTicketExists(Long id) {
         if(id == null){
            throw new BusinessRuleException("Ticket ID cannot be null");
        }
        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException(id);
        }
    }
    public void checkTicketBusinessRules(Ticket ticket) {
        if (ticket == null) {
            throw new BusinessRuleException("Ticket cannot be null.");
        }
        validateTicketTitle(ticket.getTitle()); 
        validateTicketDescription(ticket.getDescription());
        validateTicketRequester(ticket.getRequester());
        if(ticket.getAssignedAgent() != null){
            validateAssignAgent(ticket.getAssignedAgent());
        }
        if(ticket.getPriority() != null){
            validateTicketPriority(ticket.getPriority());
        }
        if(ticket.getCategory() != null){
            validateTicketCategory(ticket.getCategory());
        }
    }
    public void validateTicketTitle(String title){
        if (title == null || title.isBlank()) {
            throw new BusinessRuleException("Ticket title cannot be null or empty.");
        }
        if (title.strip().length() < 6){
            throw new BusinessRuleException("Ticket title needs to be greater than 5 characters");
        }
    }
    public void validateTicketDescription(String description){
        if (description == null || description.isBlank()) {
            throw new BusinessRuleException("Ticket description cannot be null or empty.");
        }
        if(description.strip().length() < 6){
            throw new BusinessRuleException("Ticket description needs to be greater than 5 characters");
        }
    }
    public void validateTicketRequester(User requester){
        userService.getActiveUser(requester.getId());
    }
    public void validateAssignAgent(User agent){
        userService.getActiveUser(agent.getId());
        if(agent.getRole() != UserRole.AGENT && agent.getRole() != UserRole.ADMIN){
            throw new BusinessRuleException("Only agents and Admins can be assigned to tickets.");
        }
    }
    public void validateTicketPriority(TicketPriority priority){
        if(priority == null){
            throw new BusinessRuleException("The new priority cannot be null");
        }
    }
    public void validateTicketCategory(Category category){
        categoryService.getActiveCategory(category.getId());
    }
    public void checkTicketIsOpen(Long id) {
        if(id == null){
            throw new BusinessRuleException("Ticket ID cannot be null");
        }
        Ticket ticket = getExistingTicket(id);
        if (!ticket.isOpen()) {
            throw new BusinessRuleException("Ticket with ID " + ticket.getId() + " is not open and cannot be modified.");
        }
    }

    public void saveTicket(Ticket ticket) {
        checkTicketBusinessRules(ticket);
        ticketRepository.save(ticket);
    }
    
    public Ticket getTicketById(Long id) {
        return getExistingTicket(id);
    }
    
    public void deleteTicket(Long id) {
        checkTicketIsOpen(id);
        ticketRepository.deleteById(id);
    }
   
    public void assignAgentToTicket(Long ticketId, Long agentId) {
        if (agentId == null) {
            throw new BusinessRuleException("Agent ID cannot be null.");
        }
        checkTicketIsOpen(ticketId);
        User agent = userService.getActiveUser(agentId);
        
        Ticket ticket = getExistingTicket(ticketId);
        validateAssignAgent(agent);
         if(ticket.getAssignedAgent() != null && ticket.getAssignedAgent().getId().equals(agentId)){
            throw new BusinessRuleException("This agent is already assigned to this ticket");
        }
        ticket.assignAgent(agent);
        ticketRepository.update(ticket);
    }

    public Ticket getExistingTicket(Long id){
        checkTicketExists(id);
        return ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public void adjustTitle(Long id, String title){
        checkTicketIsOpen(id);
        Ticket ticket = getExistingTicket(id);
        validateTicketTitle(title);
        ticket.setTitle(title);
        ticketRepository.update(ticket);

    }

    public void adjustDescription(Long id, String description){
        checkTicketIsOpen(id);
        Ticket ticket = getExistingTicket(id);
        validateTicketDescription(description);
        ticket.setDescription(description);
        ticketRepository.update(ticket);
    }
    public void adjustRequester(Long ticketId, Long requesterId ){
        checkTicketIsOpen(ticketId);
        Ticket ticket =  getExistingTicket(ticketId);
        User requester = userService.getActiveUser(requesterId);
        validateTicketRequester(requester);
        ticket.setRequester(requester);
        ticketRepository.update(ticket);
    }
    public void adjustPriority(Long id, TicketPriority priority){
        checkTicketIsOpen(id);
        validateTicketPriority(priority);
        Ticket ticket = getExistingTicket(id);
        ticket.setPriority(priority);
        ticketRepository.update(ticket);
    }

    public void adjustCategory(Long ticketId, Long categoryId){
        checkTicketIsOpen(ticketId);
        Ticket ticket = getExistingTicket(ticketId);
        Category category = categoryService.getActiveCategory(categoryId);
        ticket.setCategory(category);
        ticketRepository.update(ticket);
    }

    public void closeTicket(Long ticketId, Long userId){
        userService.getActiveUser(userId);
        Ticket ticket = getExistingTicket(ticketId);
        if(ticket.getStatus() != TicketStatus.RESOLVED){
            throw new BusinessRuleException("Only resolved tickets can be closed.");
        }
        if(!ticket.getRequester().getId().equals(userId)){
            throw new BusinessRuleException("Only the requester can close this ticket.");
        }
        ticket.closeTicket();
        ticketRepository.update(ticket);
    }
    public void resolveTicket(Long ticketId, Long userId){
        Ticket ticket = getExistingTicket(ticketId);
        checkTicketIsOpen(ticketId);
        userService.getActiveUser(userId);
        
        if(ticket.getAssignedAgent() == null){
            throw new BusinessRuleException("The ticket must have a agent assigned before it can be resolved. ");
        }
        if(!ticket.getAssignedAgent().getId().equals(userId)){
            throw new BusinessRuleException("Only the assigned agent can resolve this ticket.");
        }
        ticket.resolveTicket();
        ticketRepository.update(ticket);
    }
    
    public void cancelTicket(Long ticketId, Long userId) {
    Ticket ticket = getExistingTicket(ticketId);
    checkTicketIsOpen(ticketId);
    userService.getActiveUser(userId);

    boolean isAssignedAgent =
            ticket.getAssignedAgent() != null
            && ticket.getAssignedAgent().getId().equals(userId);

    boolean isRequester =
            ticket.getRequester().getId().equals(userId);

    if (!isAssignedAgent && !isRequester) {
        throw new BusinessRuleException("Only the assigned agent or the requester can cancel this ticket.");
    }

    ticket.cancelTicket();
    ticketRepository.update(ticket);
}

    public void unassignAgent(Long ticketId){
        checkTicketIsOpen(ticketId);
        Ticket ticket = getExistingTicket(ticketId);
        if(ticket.getAssignedAgent() == null){
            throw new BusinessRuleException("Can't unassign a agent from a ticket that doesn't have one.");
        }
        ticket.unassignAgent();
        ticket.setStatus(TicketStatus.WAITING_IN_QUEUE);
        ticketRepository.update(ticket);
    }
    public void changeStatus(Long id, TicketStatus status){
        checkTicketIsOpen(id);
        Ticket ticket = getExistingTicket(id);
        if(status == null){
            throw new BusinessRuleException("New status can't be null");
        }
        if(status == TicketStatus.CANCELED){
            throw new BusinessRuleException("Can't change the status to canceled.");
        }
        if(status == TicketStatus.CLOSED){
            throw new BusinessRuleException("Can't change the status to closed.");
        }
        if(status == TicketStatus.RESOLVED){
            throw new BusinessRuleException("Can't change the status to resolved.");
        }
        ticket.setStatus(status);
        ticketRepository.update(ticket);
    }
}

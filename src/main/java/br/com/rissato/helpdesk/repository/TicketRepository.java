package br.com.rissato.helpdesk.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.rissato.helpdesk.model.Ticket;

public class TicketRepository {
    private final Map<Long, Ticket> tickets = new HashMap<>();
    private long nextId = 1L;

    public Ticket save(Ticket ticket){
        long id = nextId++;
        Ticket newTicket = new Ticket(
            id, 
            ticket.getTitle(), 
            ticket.getDescription(), 
            ticket.getPriority(), 
            ticket.getCategory(), 
            ticket.getRequester(),
            ticket.getOpenedAt()
        );
    
        tickets.put(id, newTicket);
        return newTicket;
    }
    public Optional<Ticket> findById(long id){
        return Optional.ofNullable(tickets.get(id)); 
    }
    public void update(Ticket ticket){
        tickets.put(ticket.getId(), ticket);
    }
    public void deleteById(long id){
        tickets.remove(id);
    }
    public Map<Long, Ticket> findAll(){
        return tickets;
    }
    public boolean existsById(long id){
        return tickets.containsKey(id);
    }
}

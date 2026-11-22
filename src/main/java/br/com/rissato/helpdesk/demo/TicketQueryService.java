package br.com.rissato.helpdesk.demo;

import br.com.rissato.helpdesk.Enum.TicketPriority;
import br.com.rissato.helpdesk.Enum.TicketStatus;
import br.com.rissato.helpdesk.dto.TicketSummary;
import br.com.rissato.helpdesk.exception.TicketNotFoundException;
import br.com.rissato.helpdesk.model.Agent;
import br.com.rissato.helpdesk.model.Category;
import br.com.rissato.helpdesk.model.Ticket;
import br.com.rissato.helpdesk.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketQueryService {
    public List <Ticket> findOpenTickets(List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(Ticket::isOpen)
                            .toList();
    }

    public List<String> listTicketTitles(List<Ticket> ticketList){
        return ticketList.stream()
                            .map(Ticket::getTitle)
                            .toList();
    }
    public List<Ticket> findOpenCriticalTickets (List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket -> ticket.isOpen() && ticket.getPriority() == TicketPriority.URGENT)
                            .toList();
    }
    public long countInProgress(List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket -> ticket.getStatus() == TicketStatus.IN_PROGRESS)
                            .count();
    }
    public boolean hasOpenCriticalTickets(List<Ticket> ticketList){
        return ticketList.stream()
                            .anyMatch(ticket -> ticket.isOpen() && ticket.getPriority() == TicketPriority.URGENT);
    }


    public boolean allTicketsHaveAgents(List<Ticket> ticketList){
        return ticketList.stream()
                            .allMatch(ticket -> ticket.getAssignedAgent() != null);
    }

    public Optional<Ticket> findOldestOpenTicket(List<Ticket> ticketList){
        return ticketList.stream() 
                         .filter(ticket -> ticket.isOpen())
                         .min(Comparator.comparing(Ticket::getOpenedAt));
    }

    public Optional<Ticket> findNewestOpenTicket(List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket -> ticket.isOpen())
                            .max(Comparator.comparing(Ticket::getOpenedAt));
    }

    public Optional<Ticket> findMostRecentlyClosedTicket(List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket ->ticket.getClosedAt() != null)
                            .max(Comparator.comparing(Ticket::getClosedAt));        
    }

    public Map<TicketStatus, List<Ticket>> groupByStatus(List<Ticket> ticketList){
        return ticketList.stream()
                            .collect(Collectors.groupingBy(Ticket::getStatus));
    }

    public Map<TicketPriority, List<Ticket>> groupByPriority(List<Ticket> ticketList){
        return ticketList.stream()
                            .collect(Collectors.groupingBy(Ticket::getPriority));
    }
    public Map<Agent, List<Ticket>> groupByAgent(List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket -> ticket.getAssignedAgent() != null)
                            .collect(Collectors.groupingBy(Ticket::getAssignedAgent));
    }
    public Optional<Ticket> findById(Long id, List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket -> ticket.getId().equals(id))
                            .findFirst();
    }
    public Ticket findByIdOrThrow(Long id, List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(ticket -> ticket.getId().equals(id))
                            .findFirst()
                            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + id));
    }

    public List<TicketSummary> listOpenTicketsByPriority(List<Ticket> ticketList){
        return ticketList.stream()
                            .filter(Ticket::isOpen)
                            .sorted(Comparator.comparing(Ticket::getPriority).reversed())
                            .map(this::toSummary)
                            .toList();
    }

    public TicketSummary toSummary(Ticket ticket){
        return new TicketSummary(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getStatus(),
            ticket.getPriority(),
            ticket.getOpenedAt(),
            ticket.getClosedAt(),
            ticket.getRequester().getName(),
            ticket.getAssignedAgent() != null ? ticket.getAssignedAgent().getName() : null,
            ticket.getCategory().getName()
        );
    }
    public static void main (String[] args){
        LocalDateTime now1 = LocalDateTime.now();
    User user1 = new User(1L, "User 1", "user1@example.com", true, true, null);
    Category category1 = new Category(1L, "Category 1", "Description 1");
    Ticket ticket1 = new Ticket(1L, "Ticket 1", "Description 1", TicketPriority.HIGH, user1, category1, now1.minusDays(10));
    Ticket ticket2 = new Ticket(2L, "Ticket 2", "Description 2", TicketPriority.MEDIUM, user1, category1, now1.minusDays(5));
    Ticket ticket3 = new Ticket(3L, "Ticket 3", "Description 3", TicketPriority.LOW, user1, category1, now1.minusDays(2));
    Ticket ticket4 = new Ticket(4L, "Ticket 4", "Description 4", TicketPriority.URGENT, user1, category1, now1.minusDays(1));
    Ticket ticket5 = new Ticket(5L, "Ticket 5", "Description 5", TicketPriority.HIGH, user1, category1, now1.minusDays(3));
    }
}

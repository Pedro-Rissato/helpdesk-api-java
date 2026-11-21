package br.com.rissato.helpdesk.demo;

import br.com.rissato.helpdesk.model.Ticket;
import br.com.rissato.helpdesk.model.User;
import br.com.rissato.helpdesk.model.Priority;
import br.com.rissato.helpdesk.model.Category;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import br.com.rissato.helpdesk.model.TicketStatus;
import br.com.rissato.helpdesk.model.Agent;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;

public class TicketCollectionDemo {
    private static List<Ticket> findOldestOpenTicket(List<Ticket> ticketList){
        List<Ticket> openTickets = new ArrayList<>();

        for (Ticket ticket : ticketList){
            if (ticket.isOpen()){
                openTickets.add(ticket);
            }
        }
        openTickets.sort(Comparator.comparing(Ticket::getOpenedAt));

        return openTickets;
    }
    public static void main(String[] args) {
        LocalDateTime now1 = LocalDateTime.now();
        User user1 = new User(1L, "User 1", "user1@example.com", true, true, null);
        Category category1 = new Category(1L, "Category 1", "Description 1");
        List <Ticket> ticketList = new ArrayList<>();
        Ticket ticket1 = new Ticket(1L, "Ticket 1", "Description 1", Priority.HIGH, user1, category1, now1.minusDays(10));
        Ticket ticket2 = new Ticket(2L, "Ticket 2", "Description 2", Priority.MEDIUM, user1, category1, now1.minusDays(5));
        Ticket ticket3 = new Ticket(3L, "Ticket 3", "Description 3", Priority.LOW, user1, category1, now1.minusDays(2));
        Ticket ticket4 = new Ticket(4L, "Ticket 4", "Description 4", Priority.URGENT, user1, category1, now1.minusDays(1));
        Ticket ticket5 = new Ticket(5L, "Ticket 5", "Description 5", Priority.HIGH, user1, category1, now1.minusDays(3));

        

        Agent agent1 = new Agent(1L, "Agent 1", "agent1@example.com", true);
        Agent agent2 = new Agent(2L, "Agent 2", "agent2@example.com", true);
        Agent agent3 = new Agent(3L, "Agent 3", "agent3@example.com", true);
        
        ticket3.assignAgent(agent3);
        ticket1.assignAgent(agent2);
        ticket2.assignAgent(agent2);
        ticket4.assignAgent(agent1);
        ticket5.assignAgent(agent1);

        ticket4.resolve();
        ticket5.resolve();

        ticketList.add(ticket1);
        ticketList.add(ticket2);
        ticketList.add(ticket3);
        ticketList.add(ticket4);
        ticketList.add(ticket5);

        List<Ticket> openTickets = new ArrayList<>();
        for(Ticket ticket : ticketList){
            if(ticket.isOpen()){
                openTickets.add(ticket);
            }
        }
         List<Ticket> urgentTickets = new ArrayList<>();
        for(Ticket ticket : ticketList){
            if(ticket.getPriority() == Priority.URGENT || ticket.getPriority() == Priority.HIGH){
                urgentTickets.add(ticket);
            }
        }
        
        System.out.println("Ticket Collection Demo");
        System.out.println("----------------------");
        System.out.println("All Tickets:");
        System.out.println(ticketList);
        System.out.println("----------------------");
        System.out.println("Open Tickets:");
        System.out.println(openTickets);
        System.out.println("----------------------");
        System.out.println("Urgent Tickets:");
        System.out.println(urgentTickets);

        ticketList.sort(Comparator.comparing(Ticket::getPriority).reversed());
        System.out.println("----------------------");
        System.out.println("Tickets sorted by priority:");
        System.out.println(ticketList);
        ticketList.sort(Comparator.comparing(Ticket::getOpenedAt));
        System.out.println("----------------------");
        System.out.println("Tickets sorted by opened date:");
        System.out.println(ticketList);

        Map<Long, Ticket> ticketMap = new HashMap<>();
        for (Ticket ticket : ticketList) {
            ticketMap.put(ticket.getId(), ticket);
        }
        System.out.println("----------------------");
        System.out.println("Tickets in a Map:");
        System.out.println(ticketMap);

        System.out.println("----------------------");
        System.out.println("Ticket found by Id");
        System.out.println(ticketMap.get(1L));

        ticketMap.remove(1L);
        System.out.println("----------------------");
        System.out.println("TicketMap after removing ticket 1:");
        System.out.println(ticketMap);
        
        Map<TicketStatus, Long> statusCountMap = new HashMap<>();
        for (Ticket ticket : ticketList) {
            TicketStatus status = ticket.getStatus();
            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0L) + 1);
        }
        System.out.println("----------------------");
        System.out.println("Ticket Status Counts:");
        System.out.println(statusCountMap);

        Map<Agent, Long> agentTicketCountMap = new HashMap<>();
        for (Ticket ticket : ticketList) {
            Agent agent = ticket.getAssignedAgent();
            if (agent != null) {
                agentTicketCountMap.put(agent, agentTicketCountMap.getOrDefault(agent, 0L) + 1);
            }
        }
        System.out.println("----------------------");
        System.out.println("Agent Ticket Counts:");
        System.out.println(agentTicketCountMap);

        Set<TicketStatus> uniqueStatuses = new HashSet<>();
        for (Ticket ticket : ticketList) {
            uniqueStatuses.add(ticket.getStatus());
        }
        System.out.println("----------------------");
        System.out.println("Unique Ticket Statuses:");
        System.out.println(uniqueStatuses);

        System.out.println(findOldestOpenTicket(ticketList));
        
    }
}


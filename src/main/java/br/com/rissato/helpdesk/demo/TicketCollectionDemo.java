package br.com.rissato.helpdesk.demo;

import br.com.rissato.helpdesk.model.Ticket;
import br.com.rissato.helpdesk.model.User;
import br.com.rissato.helpdesk.model.Priority;
import br.com.rissato.helpdesk.model.Category;
import java.util.Comparator;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class TicketCollectionDemo {
    public static void main(String[] args) {
        User user1 = new User(1L, "User 1", "user1@example.com", true, true, null);
        Category category1 = new Category(1L, "Category 1", "Description 1");
        List <Ticket> ticketList = new ArrayList<>();
        Ticket ticket1 = new Ticket(1, "Ticket 1", "Description 1", Priority.HIGH, user1, category1);
        Ticket ticket2 = new Ticket(2, "Ticket 2", "Description 2", Priority.MEDIUM, user1, category1);
        Ticket ticket3 = new Ticket(3, "Ticket 3", "Description 3", Priority.LOW, user1, category1);
        Ticket ticket4 = new Ticket(4, "Ticket 4", "Description 4", Priority.URGENT, user1, category1);
        Ticket ticket5 = new Ticket(5, "Ticket 5", "Description 5", Priority.HIGH, user1, category1);

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

        ticketList.sort(Comparator.comparing(Ticket::getPriority));
        System.out.println("----------------------");
        System.out.println("Tickets sorted by priority:");
        System.out.println(ticketList);
        
    }
}


package br.com.rissato.helpdesk.model;

import java.time.LocalDateTime;


public record TicketSummary (
    Long id,
    String title,
    TicketStatus status,
    Priority priority,
    LocalDateTime openedAt,
    LocalDateTime closedAt,
    String requesterName,
    String agentName,
    String category                                             

){} 

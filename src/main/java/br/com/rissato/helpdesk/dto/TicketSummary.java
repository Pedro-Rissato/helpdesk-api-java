package br.com.rissato.helpdesk.dto;

import java.time.LocalDateTime;

import br.com.rissato.helpdesk.Enum.TicketPriority;
import br.com.rissato.helpdesk.Enum.TicketStatus;


public record TicketSummary (
    Long id,
    String title,
    TicketStatus status,
    TicketPriority priority,
    LocalDateTime openedAt,
    LocalDateTime closedAt,
    String requesterName,
    String agentName,
    String category                                             

){} 

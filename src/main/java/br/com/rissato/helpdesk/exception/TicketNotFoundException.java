package br.com.rissato.helpdesk.exception;

public class TicketNotFoundException extends HelpdeskException {
    public TicketNotFoundException(Long id) {
        super("Ticket not found with ID: " + id);
    }
}

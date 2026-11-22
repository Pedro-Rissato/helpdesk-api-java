package br.com.rissato.helpdesk.exception;

public class UserNotFoundException extends HelpdeskException {
    public UserNotFoundException(Long id) {
        super("User not found with ID: " + id);
    }
}

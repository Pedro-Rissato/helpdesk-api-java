package br.com.rissato.helpdesk.exception;

public class CategoryNotFoundException extends HelpdeskException {
    public CategoryNotFoundException(Long id) {
        super("Category not found with ID: " + id);
    }
    
}

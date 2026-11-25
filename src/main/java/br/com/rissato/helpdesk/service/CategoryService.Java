package br.com.rissato.helpdesk.service;

import br.com.rissato.helpdesk.exception.BusinessRuleException;
import br.com.rissato.helpdesk.exception.CategoryNotFoundException;
import br.com.rissato.helpdesk.model.Category;
import br.com.rissato.helpdesk.repository.CategoryRepository;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public void validateCategoryCreation(Category category){
        if(category == null){
            throw new BusinessRuleException("Category cannot be null.");
        }
       validateDescription(category.getDescription());
       validateName(category.getName());
    }
    public Category getExistingCategory(Long id){
        if (id == null) {
        throw new BusinessRuleException("Category ID cannot be null.");
        }
        return categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    }
    public void checkCategoryExists(Long id){
        if(id == null){
            throw new BusinessRuleException("Category ID cannot be null");
        }
        if(!categoryRepository.existsById(id)){
            throw new CategoryNotFoundException(id);
        }

    }
    public Category getCategoryById(Long id){
        return getExistingCategory(id);
    }
    public void saveCategory(Category category){
        validateCategoryCreation(category);

        categoryRepository.save(category);
    }
    public void updateCategory(Long id, String name, String description){
        Category category = getExistingCategory(id);
        validateName(name);
        validateDescription(description);
        category.setName(name);
        category.setDescription(description);
        categoryRepository.update(category);
    }
    public void deleteCategoryById(Long id){
        checkCategoryExists(id);
        categoryRepository.deleteById(id);
    }
    public void adjustName(Long id, String name){
        Category category = getExistingCategory(id);
        validateName(name);
        category.setName(name);
        categoryRepository.update(category);
    }
    public void adjustDescription(Long id, String description){
        Category category = getExistingCategory(id);
        validateDescription(description);
        category.setDescription(description);
        categoryRepository.update(category);
    }
    public void validateName(String name){
        if(name == null || name.isBlank()){
            throw new BusinessRuleException("Category name cannot be null or empty");
        }
    }
    public void validateDescription(String description){
        if(description == null || description.isBlank()){
           throw new BusinessRuleException("Category description cannot be null or empty"); 
        }
        if(description.strip().length() <6){
            throw new BusinessRuleException("Category description needs to be greater than 5 characters"); 
        }
    }
    public void deactivateCategory(Long id) {
    Category category = getExistingCategory(id);

    category.deactivate();
    categoryRepository.update(category);
    }   

    public void activateCategory(Long id) {
        Category category = getExistingCategory(id);

        category.activate();
        categoryRepository.update(category);
    }
    public Category getActiveCategory(Long id) {
    Category category = getExistingCategory(id);

    if (!category.isActive()) {
        throw new BusinessRuleException("Inactive categories cannot be assigned to tickets.");
    }

    return category;
    }
    
}
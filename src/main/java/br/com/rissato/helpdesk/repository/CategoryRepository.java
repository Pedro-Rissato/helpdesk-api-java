package br.com.rissato.helpdesk.repository;

import br.com.rissato.helpdesk.model.Category;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class CategoryRepository {
    private final Map<Long, Category> categories = new HashMap<>();
    private long nextId = 1L;

    public void save(Category category){
        long id = nextId++;

        Category newCategory = new Category(id, category.getName(), category.getDescription());
        categories.put(id, newCategory);
    }
    public Optional<Category> findById(Long id){
        return Optional.ofNullable(categories.get(id));
    }
    public void update(Category category){
        categories.put(category.getId(), category);
    }
    public void deleteById(Long id){
        categories.remove(id);
    }
    public Map<Long, Category> findAll(){
        return categories;
    }
    public boolean existsById(Long id){
        return categories.containsKey(id);
    }
}

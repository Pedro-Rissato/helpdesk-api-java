package br.com.rissato.helpdesk.model;

public class Category{
    private long id;
    private String name;
    private String description;
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Category(long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = true;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setId(long id){
        this.id = id;
    }
}
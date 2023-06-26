package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents category of the question
 */
public class Category {
    @JsonProperty("id")
    private int id = -1; //categories with unknown ID will show id = -1
    @JsonProperty("name")
    private String name;

    /**
     * For serialization
     */
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Method tries to define category id for category based on given collection;
     * Category will have the same id as the category with the same name in the collection;
     * If there are several categories, any of the Id will be used
     * @param categories to search category id
     * @return true if id was found and changed (however it could much previous one), false otherwise
     */
    public boolean defineId(Collection<Category> categories){
        Optional<Category> foundCategory = categories
                .stream()
                .filter(cat -> Objects.equals(cat.name,this.name))
                .findAny();

        foundCategory.ifPresent(cat -> this.id = cat.id );
        return foundCategory.isPresent();
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

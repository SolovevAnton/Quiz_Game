package com.solovev.quiz_game.repositories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solovev.quiz_game.model.Category;
import com.solovev.quiz_game.util.URLs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Repository by default takes all available categories from: Util.URLs.AVAILABLE_CATEGORIES, or takes File and creates list of all available categories from file
 */

public class AvailableCategoriesRepository implements Repository<Collection<Category>> {
    private Set<Category> categories = new HashSet<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Class represents container object of categories, called "trivia_categories"
     */
    static class ContainerForCategories {
        @JsonProperty("trivia_categories")
        public Set<Category> trivia_categories;

        public ContainerForCategories() {
        }

        public ContainerForCategories(Set<Category> trivia_categories) {
            this.trivia_categories = trivia_categories;
        }

        public void setTrivia_categories(Set<Category> trivia_categories) {
            this.trivia_categories = trivia_categories;
        }

        public Set<Category> getTrivia_categories() {
            return trivia_categories;
        }
    }

    /**
     * Creates category set from URL
     */
    public AvailableCategoriesRepository() throws IOException {
        URL url = new URL(URLs.AVAILABLE_CATEGORIES.getValue());
        this.categories = objectMapper.readValue(url, ContainerForCategories.class).getTrivia_categories();
    }

    @Override
    public Collection<Category> takeData() {
        return categories;
    }

    @Override
    public String toString() {
        return "AvailableCategoriesRepository{" +
                "categories=" + categories +
                '}';
    }
}

package com.solovev.quiz_game.util;

/**
 * Enum with important urls of the <a href="https://opentdb.com/api_config.php">...</a>
 */
public enum URLs {
    AVAILABLE_CATEGORIES("https://opentdb.com/api_category.php"), //Returns the entire list of categories and ids in the database.
    CATEGORY_QUESTIONS_COUNT("https://opentdb.com/api_count.php?category="), //Returns the number of questions in the database, in a specific category.

    BASE_FOR_URL_Creator("https://opentdb.com/api.php?amount="); //used to build url to api for quiz creation
    private final String value;

    URLs(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.solovev.quiz_game.util;

import java.net.URL;

/**
 * Enum with important urls of the <a href="https://opentdb.com/api_config.php">...</a>
 */
public enum URLs {
    AVAILABLE_CATEGORIES("https://opentdb.com/api_category.php");

    private final String value;

    URLs(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

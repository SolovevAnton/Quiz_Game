package com.solovev.quiz_game.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class URLDataGetterTest {

    @Test
    public void checkURLTest() throws IOException {
        URL validUrl = new URL(URLs.CATEGORY_QUESTIONS_COUNT.getValue() + 9);
        URL invalidUrl = new URL(URLs.CATEGORY_QUESTIONS_COUNT.getValue() + -1);

        Assertions.assertTrue(URLDataGetter.getDataFromURL(validUrl).length > 0);
        Assertions.assertThrows(MalformedURLException.class, () -> URLDataGetter.getDataFromURL(invalidUrl));
    }
}
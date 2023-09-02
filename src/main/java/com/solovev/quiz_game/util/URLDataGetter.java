package com.solovev.quiz_game.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class with static methods to check if URL has ok response code, and contains any data, and returns the data is it does
 */
public class URLDataGetter {
    /**
     * Method to check if url is formed incorrectly: does not have response code HTTP_OK, and contains some data
     *
     * @param url to check
     * @return data from this url
     * @throws IOException if url does not response correct code, if URL is empty and does not contain any data or IOException occurs
     */
    public static byte[] getDataFromURL(URL url) throws IOException {
        byte[] bytes;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream())) {
            bytes = bufferedInputStream.readAllBytes();
            if (bytes.length == 0) {
                throw new MalformedURLException("URL content is empty");
            }
        }
        return bytes;
    }
}

package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Request;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Supplier;

/**
 * Class is used to create url from the request. It doesn't check the logical correctness of request values;
 * Url has structure : https://opentdb.com/api.php?amount=AMOUNT&category=CATEGORY_ID&difficulty=DIFFUCULTY&type=QUESTION_TYPE
 */
public class URLCreator {
    private final StringBuilder urlString = new StringBuilder(URLs.BASE_FOR_URL_Creator.getValue());
    private final char delimiter = '&';

    /**
     * Adds all necessary parts to urlString based on the request. The order of internal methods is important
     * @param request to build url from
     */
    public URLCreator(Request request){
        urlString.append(request.getNumberOfQuestions());

        //add parts order matters
        adder("category",request::getCategory, () -> request.getCategory().getId());
        adder("difficulty",request::getDifficulty, () -> request.getDifficulty().toString().toLowerCase());
        adder("type",request::getQuestionType, () -> request.getQuestionType().toString().toLowerCase());

    }

    /**
     * Checks if parameter is not null in request and adds it if it is not null
     * @param paramName name of the parameter in URL
     * @param requestParamGetter get request field to sure its non-null
     * @param value value to append to url if param in request is not null
     */

    private void adder(String paramName, Supplier requestParamGetter, Supplier value){
        String parameterName = paramName + "=";
        if(requestParamGetter.get() != null) {
            urlString
                    .append(delimiter)
                    .append(parameterName)
                    .append(value.get());
        }
    }

    /**
     * Creates url from
     * @return url created for request in constructor
     * @throws MalformedURLException if url is malformed
     */
    public URL getURL() throws MalformedURLException {
        return new URL(urlString.toString());
    }

}

package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class to describe instance of the quiz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quiz {
    private RESPONSE_CODE responseCode;
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();
    public enum RESPONSE_CODE{
        SUCCESS, // Returned results successfully.
        NO_RESULTS,// Could not return results. The API doesn't have enough questions for your query. (Ex. Asking for 50 Questions in a Category that only has 20.)
        INVALID_PARAMETER, //Contains an invalid parameter. Arguements passed in aren't valid. (Ex. Amount = Five)
        TOKEN_NOT_FOUND, // Session Token does not exist.
        TOKEN_EMPTY// Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.
    }

    /**
     * No args constructor for serialization
     */
    public Quiz() {
    }

    public Quiz(RESPONSE_CODE responseCode, Set<Question> questions) {
        this.responseCode = responseCode;
        this.questions = questions;
    }

    public RESPONSE_CODE getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(RESPONSE_CODE responseCode) {
        this.responseCode = responseCode;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        if (responseCode != quiz.responseCode) return false;
        return Objects.equals(questions, quiz.questions);
    }

    @Override
    public int hashCode() {
        int result = responseCode != null ? responseCode.hashCode() : 0;
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "responseCode=" + responseCode +
                ", questions=" + questions +
                '}';
    }
}

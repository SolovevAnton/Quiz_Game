package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class to describe instance of the quiz
 */
public class Quiz {
    @JsonProperty("response_code")
     private ResponseCode responseCode;
    @JsonProperty("results")
    private Set<Question> questions = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public enum ResponseCode {
        SUCCESS("Returned results successfully"),
        NO_RESULTS("Could not return results. The Database doesn't have enough questions for your query. (Ex. Asking for 50 Questions in a Category that only has 20.)"),
        INVALID_PARAMETER("Contains an invalid parameter. Arguments passed in aren't valid. (Ex. Amount = Five)"),
        TOKEN_NOT_FOUND("Session Token does not exist."),
        TOKEN_EMPTY("Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.");
        private final String message;
        ResponseCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    /**
     * No args constructor for serialization
     */
    public Quiz() {
    }

    public Quiz(ResponseCode responseCode, Set<Question> questions) {
        this.responseCode = responseCode;
        this.questions = questions;
    }

    /**
     * To get number of questions
     * @return sumber of questions in this quiz
     */
    public int size(){
        return questions.size();
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
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

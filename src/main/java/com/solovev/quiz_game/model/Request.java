package com.solovev.quiz_game.model;

import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;

import java.util.Objects;

/**
 * Class to present request created by user in the loading form to form quiz with API
 */
public class Request {
    private Category category;
    private int numberOfQuestions;
    private Difficulty difficulty;
    private QuestionType questionType;

    public Request(Category category, int numberOfQuestions, Difficulty difficulty, QuestionType questionType) {
        this.category = category;
        this.numberOfQuestions = numberOfQuestions;
        this.difficulty = difficulty;
        this.questionType = questionType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (numberOfQuestions != request.numberOfQuestions) return false;
        if (!Objects.equals(category, request.category)) return false;
        if (difficulty != request.difficulty) return false;
        return questionType == request.questionType;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + numberOfQuestions;
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "category=" + category +
                ", numberOfQuestions=" + numberOfQuestions +
                ", difficulty=" + difficulty +
                ", questionType=" + questionType +
                '}';
    }
}

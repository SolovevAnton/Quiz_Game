package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents question object
 */
public class Question {
    private final Category category;
    @JsonProperty("type")
    private final boolean isMultipleChoice;
    private final Difficulty difficulty;
    private final String question;
    @JsonProperty("correct_answer")
    private final String correctAnswer;
    @JsonProperty("incorrect_answers")
    private Set<String> incorrectAnswers = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    public enum Difficulty{
        EASY,
        MEDIUM,
        HARD
    }
    public Question(Category category, boolean isMultipleChoice, Difficulty difficulty, String question, String correctAnswer, Set<String> incorrectAnswers) {
        this.category = category;
        this.isMultipleChoice = isMultipleChoice;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Set<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(Set<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        if (isMultipleChoice != question1.isMultipleChoice) return false;
        if (!Objects.equals(category, question1.category)) return false;
        if (difficulty != question1.difficulty) return false;
        if (!Objects.equals(question, question1.question)) return false;
        if (!Objects.equals(correctAnswer, question1.correctAnswer))
            return false;
        return Objects.equals(incorrectAnswers, question1.incorrectAnswers);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (isMultipleChoice ? 1 : 0);
        result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (correctAnswer != null ? correctAnswer.hashCode() : 0);
        result = 31 * result + (incorrectAnswers != null ? incorrectAnswers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "category=" + category +
                ", isMultipleChoice=" + isMultipleChoice +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", inCorrectAnswers=" + incorrectAnswers +
                '}';
    }

}

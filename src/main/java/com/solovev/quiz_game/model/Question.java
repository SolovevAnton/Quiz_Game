package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.Type;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents question object
 */
public class Question {
    @JsonProperty("category")
    private Category category;
    private Type type;
    private Difficulty difficulty;
    private String question;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    @JsonProperty("incorrect_answers")
    private Set<String> incorrectAnswers = new HashSet<>();

    public Question() {
    }
    public Question(Category category, Type type, Difficulty difficulty, String question, String correctAnswer, Set<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public Category getCategory() {
        return category;
    }

    public Type isMultipleChoice() {
        return type;
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

        if (!Objects.equals(category, question1.category)) return false;
        if (!Objects.equals(type, question1.type))
            return false;
        if (difficulty != question1.difficulty) return false;
        if (!Objects.equals(question, question1.question)) return false;
        if (!Objects.equals(correctAnswer, question1.correctAnswer))
            return false;
        return Objects.equals(incorrectAnswers, question1.incorrectAnswers);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
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
                ", isMultipleChoice=" + type +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", inCorrectAnswers=" + incorrectAnswers +
                '}';
    }

}

package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solovev.quiz_game.model.enums.Difficulty;
import com.solovev.quiz_game.model.enums.QuestionType;
import org.apache.commons.text.StringEscapeUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class represents question object
 */
public class Question {
    @JsonProperty("category")
    private Category category;
    private QuestionType questionType;
    private Difficulty difficulty;
    private String question;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    @JsonProperty("incorrect_answers")
    private Set<String> incorrectAnswers = new HashSet<>();

    public Question() {
    }

    public Question(Category category, QuestionType questionType, Difficulty difficulty, String question, String correctAnswer, Set<String> incorrectAnswers) {
        this.category = category;
        this.questionType = questionType;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    /**
     * Decodes special symbols used in this question
     */
    public void decodeHTML() {
        question = StringEscapeUtils.unescapeHtml4(question);
        correctAnswer = StringEscapeUtils.unescapeHtml4(correctAnswer);
        incorrectAnswers =
                incorrectAnswers
                        .stream()
                        .map(StringEscapeUtils::unescapeHtml4)
                        .collect(Collectors.toSet());
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public QuestionType getType() {
        return questionType;
    }

    public void setType(QuestionType questionType) {
        this.questionType = questionType;
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

    public QuestionType isMultipleChoice() {
        return questionType;
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
        if (!Objects.equals(questionType, question1.questionType))
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
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
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
                ", isMultipleChoice=" + questionType +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", inCorrectAnswers=" + incorrectAnswers +
                '}';
    }

}

package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private static final byte[] KEY = "This is the key".getBytes();
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

    /**
     * Encrypts this question, so it cannot be directly read from file, if used second time - decrypts it to normal string again
     * note it is made simple and without any external libraries
     */
    public void encryptOrDecrypt(){
        correctAnswer = encryptOrDecrypt(correctAnswer);
        incorrectAnswers =
                incorrectAnswers
                        .stream()
                        .map(this::encryptOrDecrypt)
                        .collect(Collectors.toSet());
    }

    /**
     * encrypts string using xor
     * @param stringToEncrypt to be encrypted
     * @return encrypted String
     */
    private String encryptOrDecrypt(String stringToEncrypt){
        byte[] toEncrypt = stringToEncrypt.getBytes();
        byte[] encrypted = new byte[toEncrypt.length];
        for(int i = 0, keyCounter = 0; i < toEncrypt.length; i++, keyCounter++ ){
            if(keyCounter >= KEY.length){
                keyCounter = 0;
            }
            encrypted[i] = (byte) (toEncrypt[i]^KEY[keyCounter]);
        }
        return new String(encrypted);
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
        return Objects.equals(category, question1.category)
                && questionType == question1.questionType
                && difficulty == question1.difficulty
                && Objects.equals(question, question1.question)
                && Objects.equals(correctAnswer, question1.correctAnswer)
                && Objects.equals(incorrectAnswers, question1.incorrectAnswers);

    }

    @Override
    public int hashCode() {
        return Objects.hash(category, questionType, difficulty, question, correctAnswer, incorrectAnswers);
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

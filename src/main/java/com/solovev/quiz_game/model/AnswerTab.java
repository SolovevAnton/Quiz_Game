package com.solovev.quiz_game.model;

import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.enums.QuestionType;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.*;

/**
 * Class used to create Tabs based on the question and store all answer related info
 */
public class AnswerTab {
    private final AnchorPane mainPane = new AnchorPane();
    private final Label questionText = new Label();
    private final VBox answers = new VBox(10); // Spacing between rows
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Question question;
    private final int questionNumber;
    private boolean isCorrect;

    /**
     * Adds buttons to be decorated
     *
     * @param question this tab is based on
     */
    public AnswerTab(Question question, int questionNumber) {
        mainPane.setPrefWidth(600);
        mainPane.setPrefHeight(360);
        mainPane.getChildren().addAll(questionText, answers);

        this.question = question;
        this.questionNumber = questionNumber;

        labelInitialization();

    }

    /**
     * To initialize label, and position it in the stack pane
     */
    private void labelInitialization() {
        questionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        questionText.setTextAlignment(TextAlignment.CENTER);
        questionText.setWrapText(true);
        questionText.setMaxWidth(mainPane.getPrefWidth() * 5.0/6); //little less than main paine


    }

    /**
     * Places the question text
     */
    private void placeLabel(){
        //positioning
        questionText.widthProperty().addListener( w -> {
            AnchorPane.setLeftAnchor(questionText,
                    (mainPane.getPrefWidth() - questionText.getWidth()) / 2); //center question text
        });
    }

    /**
     * to initialize and place VBox with answers
     */
    private void placeAnswers() {
        answers.widthProperty().addListener(w -> {
                    AnchorPane.setLeftAnchor(answers, (mainPane.getPrefWidth() - answers.getWidth()) / 2); //center width
                }
        );
        questionText.heightProperty().addListener(h -> {
            AnchorPane.setTopAnchor(answers,questionText.getHeight() + 20 ); //little lower than question text
        });
    }

    /**
     * Creates tab without buttons
     *
     * @return created tab
     */
    public Tab createTab() {
        Tab result = new Tab("Q" + questionNumber);
        //adds listener, so if q is answered the title will turn green
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                result.setStyle("-fx-background-color: #93e9be");
                //checks if answer is correct
                checkAnswer(newValue);
            }
        });

        questionText.setText(question.getQuestion());

        answers.getChildren().setAll(radioButtonsAnswersFactory(question));

        placeAnswers();

        result.setContent(mainPane);
        return result;
    }

    /**
     * Creates radio button groups based on the answers
     *
     * @param question to create buttons from
     * @return group of exclusive buttons
     */
    private Collection<RadioButton> radioButtonsAnswersFactory(Question question) { //todo add different options for diff questions
        List<RadioButton> answersForVBox = new ArrayList<>();
        question.getIncorrectAnswers().forEach(q -> answersForVBox.add(new RadioButton(q)));
        answersForVBox.add(new RadioButton(question.getCorrectAnswer()));

        Font font;
        //sets different font and order in vbox for multiple and boolean questions
        if (question.isMultipleChoice()) {
            Collections.shuffle(answersForVBox);
            font = Font.font(15);
        } else {
            answersForVBox.sort(Comparator.comparing(RadioButton::getText).reversed()); //todo why lambda not working?
            font = Font.font(17);
        }

        //sets font and adds to toogle group
        answersForVBox.forEach(a -> {
            a.setFont(font);
            a.setToggleGroup(toggleGroup);
        });

        return answersForVBox;
    }

    /**
     * Checks answer and sets is Correct flag to correct or not
     *
     * @param toggle selected toggle. will bew typecast to radiobutton
     * @return what is set to isCorrect after this method
     */
    private boolean checkAnswer(Toggle toggle) {
        isCorrect = ((RadioButton) toggle).getText().equals(question.getCorrectAnswer());
        return isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}

package com.solovev.quiz_game.model;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    /**
     * Margin before first text line
     */
    private final double upperMargin = 20.0;
    private final Tab resultTab;
    private String selectedAnswer;

    /**
     * Adds buttons to be decorated
     *
     * @param question this tab is based on
     */
    public AnswerTab(Question question, int questionNumber) {
        mainPane.setPrefWidth(600);
        mainPane.setPrefHeight(350);
        mainPane.getChildren().addAll(questionText, answers);

        this.question = question;
        this.questionNumber = questionNumber;

        resultTab = new Tab("Q" + questionNumber);

        labelInitialization();

    }

    /**
     * Creates tab without buttons
     *
     * @return created tab
     */
    public Tab createTab() {

        //adds listener, so if q is answered the title will turn green
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                resultTab.setStyle("-fx-background-color: #93e9be");
                //checks if answer is correct
                selectedAnswer = ((RadioButton)newValue).getText();
            }
        });
        questionText.setText(question.getQuestion());
        answers.getChildren().setAll(radioButtonsAnswersFactory(question));

        placeAnswers();

        resultTab.setContent(mainPane);
        mainPane.setLayoutY(10);
        return resultTab;
    }

    /**
     * Creates tab with two buttons
     * @param leftButton to add to the left
     * @param rightButton to add to the right
     * @return created tab
     */
    public Tab createTab(Button leftButton, Button rightButton) {
        positionLeftButton(leftButton);
        positionRightButton(rightButton);
        mainPane.getChildren().addAll(leftButton, rightButton);
        return createTab();
    }

    /**
     * Creates tab with one button
     * @param button to ba added
     * @param isLeftButton if true will be added left, else right
     * @return created tab
     */
    public Tab createTab(Button button, boolean isLeftButton){
        if(isLeftButton){
            positionLeftButton(button);
        } else {
            positionRightButton(button);
        }
        mainPane.getChildren().add(button);
        return createTab();
    }

    /**
     * Assigns position to the left button based on the original text borders
     */
    private void positionLeftButton(Button leftButton) {
        AnchorPane.setLeftAnchor(leftButton, (mainPane.getPrefWidth() - questionText.getMaxWidth()) / 2); //sets at the beginning og the text
        positionButtonHeight(leftButton);
    }

    /**
     * Assigns position to the right button based on the original text borders
     */
    private void positionRightButton(Button rightButton) {
        rightButton.widthProperty().addListener(w -> {
            AnchorPane.setLeftAnchor(rightButton, ((mainPane.getPrefWidth() + questionText.getMaxWidth()) / 2) - rightButton.getWidth()); //sets at the end of the text
        });

        positionButtonHeight(rightButton);
    }

    /**
     * Positions button slightly above bottom of the main pane based on its size
     *
     * @param button to position
     */
    private void positionButtonHeight(Button button) {
        button.heightProperty().addListener(h -> {
            AnchorPane.setTopAnchor(button, mainPane.getPrefHeight() - button.getHeight() - 10); // slightly above bottom
        });
    }

    /**
     * To initialize label, and position it in the stack pane
     */
    private void labelInitialization() {
        questionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        questionText.setTextAlignment(TextAlignment.CENTER);
        questionText.setWrapText(true);
        questionText.setMaxWidth(mainPane.getPrefWidth() * 5.0 / 6); //little less than main paine

        placeLabel();
    }

    /**
     * Places the question text
     */
    private void placeLabel() {
        //positioning
        questionText.widthProperty().addListener(w -> {
            AnchorPane.setLeftAnchor(questionText,
                    (mainPane.getPrefWidth() - questionText.getWidth()) / 2); //center question text width
        });

        AnchorPane.setTopAnchor(questionText, upperMargin); // margin from top
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
            AnchorPane.setTopAnchor(answers, questionText.getHeight() + upperMargin + 20); //little lower than question text
        });
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


    public boolean isCorrect() {
        return question.getCorrectAnswer().equals(selectedAnswer);
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public Question getQuestion() {
        return question;
    }
}

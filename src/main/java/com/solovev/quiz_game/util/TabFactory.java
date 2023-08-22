package com.solovev.quiz_game.util;

import com.solovev.quiz_game.model.Question;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class used to create Tabs based on the question
 */
public class TabFactory {
    private AnchorPane mainPane = new AnchorPane();
    private Label questionText = new Label();
    private Button previous;
    private Button next;
    private VBox answers = new VBox(10); // Spacing between rows
    private ToggleGroup toggleGroup = new ToggleGroup();

    /**
     * Adds buttons to be decorated
     *
     * @param previous previous button
     * @param next     next button
     */
    public TabFactory(Button previous, Button next) {
        mainPane.setPrefWidth(600);
        mainPane.setPrefHeight(360);
        mainPane.getChildren().addAll(questionText, answers);
        this.previous = previous;
        this.next = next;

        labelInitialization();

        decoratePrevious();
        decorateNext();
    }

    /**
     * decorates previous button
     */
    private void decoratePrevious() {
        previous.setPrefHeight(35.0);
        previous.setPrefWidth(90.0);
        previous.getStyleClass().addAll("rounded", "success", "button-outlined");
    }

    private void decorateNext() {
        next.setPrefHeight(35.0);
        next.setPrefWidth(90.0);
        next.getStyleClass().addAll("rounded", "success", "button-outlined");
    }

    /**
     * To initialize label, and position it in the stack pane
     */
    private void labelInitialization() {
        questionText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        questionText.setTextAlignment(TextAlignment.CENTER);
        questionText.setWrapText(true);
        questionText.setPrefWidth(mainPane.getPrefWidth() * 5.0 / 6);
        questionText.setPrefHeight(100);
        //positioning
        AnchorPane.setLeftAnchor(questionText,
                (mainPane.getPrefWidth() - questionText.getPrefWidth()) / 2);
    }

    /**
     * to initialize and place VBox with answers
     */
    private void placeAnswers() {
        // Center the VBox within the AnchorPane
        AnchorPane.setLeftAnchor(answers, AnchorPane.getLeftAnchor(questionText)); // same as text
        AnchorPane.setTopAnchor(answers, questionText.getPrefHeight());
    }

    /**
     * Creates tab with two buttons, and decorates this buttons
     *
     * @param questionNumber number of the question
     * @param question       question to create tub from
     * @return created
     */
    public Tab createTab(int questionNumber, Question question) {
        Tab result = new Tab("Q" + questionNumber);
        //adds listener, so if q is answered the title will turn green
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                result.setStyle("-fx-background-color: #93e9be");
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
    private Collection<RadioButton> radioButtonsAnswersFactory(Question question) { //todo add diferent options for diff questions
        List<RadioButton> answersForVBox = new ArrayList<>();
        question.getIncorrectAnswers().forEach(q -> answersForVBox.add(new RadioButton(q)));
        answersForVBox.add(new RadioButton(question.getCorrectAnswer()));
        Collections.shuffle(answersForVBox);

        answersForVBox.forEach(a -> a.setToggleGroup(toggleGroup));

        return answersForVBox;
    }

}

package com.solovev.quiz_game.util;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

/**
 * Creates buttons for the tub pane
 */
public class ButtonFactory {
    private final TabPane tabPane;
    public ButtonFactory(TabPane tabPane){
        this.tabPane = tabPane;
    }

    /**
     * Creates button that on action goes to prev tabPane
     * @return Button with this functionality
     */
    public Button preaviousButton(){
        Button prevButton = new Button("< Previous");
        setStandardButtonSize(prevButton);
        prevButton.setOnAction(event -> {
            int currentIndex = tabPane.getSelectionModel().getSelectedIndex();
            if (currentIndex > 0) {
                tabPane.getSelectionModel().select(currentIndex - 1);
            }
        });
        prevButton.setStyle("-fx-background-color: #ede2c5");
        return prevButton;
    }
    /**
     * Creates button that on action goes to next tab in tabPane
     * @return Button with this functionality
     */
    public Button nextButton(){
        Button nextButton = new Button("Next >");
        setStandardButtonSize(nextButton);
        nextButton.setOnAction(event -> {
            int currentIndex = tabPane.getSelectionModel().getSelectedIndex();
            if (currentIndex < tabPane.getTabs().size() - 1) {
                tabPane.getSelectionModel().select(currentIndex + 1);
            }
        });
        nextButton.setStyle("-fx-background-color: #c5e4ed");
        return nextButton;
    }

    /**
     * Creates button that jumps to the last tab
     * @return Button with this functionality
     */
    public Button finishButton(){
        Button finishButton = new Button("Finish!");
        setStandardButtonSize(finishButton);
        finishButton.setOnAction(event ->tabPane.getSelectionModel().select(tabPane.getTabs().size() - 1));
        finishButton.getStyleClass().add("success");
        return finishButton;
    }

    /**
     * Sets pref size for buttons
     */
    private void setStandardButtonSize(Button button){
        button.setPrefWidth(90);
        button.setPrefHeight(35);
    }
}

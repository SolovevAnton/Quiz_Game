package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.AnswerTab;
import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.util.ButtonFactory;
import com.solovev.quiz_game.util.FormsManager;
import com.solovev.quiz_game.util.QuizFileChooserSaverAndLoader;
import com.solovev.quiz_game.util.WindowManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameForm implements ControllerData<Quiz> {
    @FXML
    public Label labelCorrectPercent;
    @FXML
    public Label labelCorrectAnswers;
    @FXML
    public TabPane tabPainMain;
    @FXML
    public CheckBox boxCorrectAnswers;
    @FXML
    public TableView<AnswerTab> answersTable;
    @FXML
    public TableColumn<AnswerTab, Boolean> correctnessColumn;
    @FXML
    public TableColumn<AnswerTab, String> questionColumn;
    @FXML
    public TableColumn<AnswerTab, Integer> numberColumn;
    private final TableColumn<AnswerTab, String> answersColumn = new TableColumn<>("Answers");

    private final List<AnswerTab> answerTabs = new ArrayList<>();
    private Quiz quiz;


    /**
     * to initialize everything related to the Tabs
     */
    private void initializeTabs() {
        int questionCounter = 1;
        ButtonFactory factory = new ButtonFactory(tabPainMain);
        List<Tab> tabs = tabPainMain.getTabs();

        for (Question q : quiz.getQuestions()) {
            int questionNumber = questionCounter++;
            AnswerTab answerTab = new AnswerTab(q, questionNumber);
            answerTabs.add(answerTab);
            //checks and defines behaviour if its first or last tab
            Tab tabToAdd = questionNumber == 1
                    ? answerTab.createTab(factory.nextButton(), false)
                    : questionNumber == quiz.size()
                    ? answerTab.createTab(factory.preaviousButton(), factory.finishButton())
                    : answerTab.createTab(factory.preaviousButton(), factory.nextButton());

            tabs.add(tabToAdd);
        }
        //removes and adds results tub
        tabs.add(tabs.remove(0));
    }

    /**
     * Initializing table columns NOT CONTENT
     */
    private void initializeAnswersTable() {
        //set number column
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        //set question column
        questionColumn.setCellValueFactory(callback -> new ReadOnlyObjectWrapper<String>(callback.getValue().getQuestion().getQuestion()));
        //set is correct column
        correctnessColumn.setCellFactory(tableColumn ->
                new TableCell<AnswerTab, Boolean>() {
                    final CheckBox btn = new CheckBox();

                    {
                        btn.setDisable(true);
                        btn.setStyle("-fx-font-size: 15px; -fx-opacity: 1;");
                        btn.setAlignment(Pos.CENTER_RIGHT);
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setSelected(answersTable.getItems().get(getIndex()).isCorrect());
                            setGraphic(btn);
                        }
                        setText(null);
                    }
                }
        );

        //answers column creation
        answersColumn.setVisible(false);
        answersColumn.setCellValueFactory(callback -> new ReadOnlyObjectWrapper<String>(callback.getValue().getQuestion().getCorrectAnswer()));
        answersTable.getColumns().add(answersColumn);
    }

    public void checkResults(ActionEvent actionEvent) {
        if (
                answerTabs.stream().filter(AnswerTab::isAnswered).count() == answerTabs.size() //checks if all are answered
        ) {

            answersTable.setItems(FXCollections.observableList(answerTabs));
            answersTable.refresh();
            //setAnswers
            answersColumn.setVisible(boxCorrectAnswers.isSelected());
            //labels
            long correctAnswers = answerTabs.stream().filter(AnswerTab::isCorrect).count();
            labelCorrectAnswers.setText(labelCorrectAnswers.getText().replaceFirst(".+/", correctAnswers + "/"));
            String correctPercent = String.format("%d", correctAnswers * 100 / answerTabs.size());
            labelCorrectPercent.setText(labelCorrectPercent.getText().replaceFirst(".+%", correctPercent + "%"));

            //checks all actions for the tabs
            answerTabs.forEach(a -> a.check(boxCorrectAnswers.isSelected()));
        } else {
            WindowManager.showAlertWithoutHeaderText("Cannot proceed", "Please select answer for every question", Alert.AlertType.WARNING);
        }
    }

    @Override
    public void initData(Quiz quiz) {
        this.quiz = quiz;
        initializeTabs();

        //initialize label
        labelCorrectAnswers.setText(labelCorrectAnswers.getText().replace("XX", String.valueOf(quiz.size())));
        //answer table
        initializeAnswersTable();
    }

    @FXML
    public void buttonToMainMenu(ActionEvent actionEvent) throws IOException {
        FormsManager.openMainForm();
        FormsManager.closeWindow(actionEvent);
    }

    @FXML
    public void buttonSaveQuiz(ActionEvent actionEvent) throws IOException {
        QuizFileChooserSaverAndLoader.getInstance().saveQuiz(quiz);
    }

    @FXML
    public void buttonRestartQuiz(ActionEvent actionEvent) {
        //clear table and answer tabs
        answerTabs.clear();
        answersTable.setItems(FXCollections.observableList(new ArrayList<>()) );

        //refresh tabs
        List<Tab> tabs = tabPainMain.getTabs();
        Tab resultTab = tabs.remove(tabs.size() - 1); //taking result tab
        tabs.clear();
        tabs.add(resultTab);

        initializeTabs();


        //labels
        labelCorrectAnswers.setText(labelCorrectAnswers.getText().replaceAll(".*/", "??/"));
        labelCorrectPercent.setText(labelCorrectPercent.getText().replaceAll(".+%", "???%"));

    }
}

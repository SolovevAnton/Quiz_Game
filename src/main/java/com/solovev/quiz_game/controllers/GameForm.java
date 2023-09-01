package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.AnswerTab;
import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.repositories.Repository;
import com.solovev.quiz_game.util.ButtonFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

    private final List<AnswerTab> answerTabs = new ArrayList<>();


    /**
     * to initialize everything related to the Tabs
     */
    private void initializeTabs(Quiz quiz) {
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
    }


    /**
     * todo REMOVE used only for tests
     */
    public void initialize() throws IOException {
        File file = new File("D:\\Git\\Practice_Projects\\JavaSE\\Quiz_Game\\src\\test\\resources\\questionsWithHTMLCodes.json");
        Repository<Quiz> fileRepo = new QuizRepository(file, false);
        initData(fileRepo.takeData());
    }

    public void checkResults(ActionEvent actionEvent) { // todo add alert
        answersTable.setItems(FXCollections.observableList(answerTabs));
        answersTable.refresh();

        //labels
        long correctAnswers = answerTabs.stream().filter(AnswerTab::isCorrect).count();
        labelCorrectAnswers.setText(labelCorrectAnswers.getText().replaceFirst(".+/", correctAnswers + "/"));
        String correctPercent = String.format("%d", correctAnswers * 100 / answerTabs.size());
        labelCorrectPercent.setText(labelCorrectPercent.getText().replaceFirst(".+%", correctPercent + "%"));
    }

    @Override
    public void initData(Quiz quiz) {
        initializeTabs(quiz);
        //initialize label
        labelCorrectAnswers.setText(labelCorrectAnswers.getText().replace("XX", String.valueOf(quiz.size())));
        //answer table
        initializeAnswersTable();
    }


}

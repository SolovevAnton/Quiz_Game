package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.repositories.Repository;
import com.solovev.quiz_game.model.AnswerTab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameForm implements ControllerData<Quiz> {
    @FXML
    public TextField textFieldCorrectAnswers;
    @FXML
    public TextField textFieldCorrectRate;
    @FXML
    public TabPane tabPainMain;
    private final Button prevButton = new Button("Previous");
    private Button nextButton = new Button("Next");
    private Collection<AnswerTab> answerTabs = new ArrayList<>();

    /**
     * todo REMOVE used only for tests
     */
    public void initialize() throws IOException {
        File file = new File("D:\\Git\\Practice_Projects\\JavaSE\\Quiz_Game\\src\\test\\resources\\questionsWithHTMLCodes.json");
        Repository<Quiz> fileRepo = new QuizRepository(file, false);
        initData(fileRepo.takeData());
    }

    public void checkResults(ActionEvent actionEvent) {
    }

    /**
     * Initializes button actions
     */
    private void buttonInitializer() {
        prevButton.setOnAction(event -> {
            int currentIndex = tabPainMain.getSelectionModel().getSelectedIndex();
            if (currentIndex > 0) {
                tabPainMain.getSelectionModel().select(currentIndex - 1);
            }
        });

        nextButton.setOnAction(event -> {
            int currentIndex = tabPainMain.getSelectionModel().getSelectedIndex();;
            if (currentIndex < tabPainMain.getTabs().size() - 1) {
                tabPainMain.getSelectionModel().select(currentIndex + 1);
            }
        });

    }

    @Override
    public void initData(Quiz quiz) {
        int questionCounter = 1;
        Collection<Tab> tabs = tabPainMain.getTabs();
        for(Question q : quiz.getQuestions()){
            AnswerTab answerTab = new AnswerTab(q);
            answerTabs.add(answerTab);
            tabs.add(answerTab.createTab(questionCounter++));
        }

        buttonInitializer();
    }


}

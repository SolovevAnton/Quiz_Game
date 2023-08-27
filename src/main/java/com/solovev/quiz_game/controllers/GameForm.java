package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.repositories.Repository;
import com.solovev.quiz_game.model.AnswerTab;
import com.solovev.quiz_game.util.ButtonFactory;
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
    private Collection<AnswerTab> answerTabs = new ArrayList<>();

    /**
     * todo REMOVE used only for tests
     */
    public void initialize() throws IOException {
        File file = new File("D:\\Git\\Practice_Projects\\JavaSE\\Quiz_Game\\src\\test\\resources\\savedNormalQuiz.json");
        Repository<Quiz> fileRepo = new QuizRepository(file, false);
        initData(fileRepo.takeData());
    }

    public void checkResults(ActionEvent actionEvent) {
    }

    @Override
    public void initData(Quiz quiz) {
        int questionCounter = 1;
        ButtonFactory factory = new ButtonFactory(tabPainMain);
        Collection<Tab> tabs = tabPainMain.getTabs();
        for(Question q : quiz.getQuestions()){
            AnswerTab answerTab = new AnswerTab(q,questionCounter++);
            answerTabs.add(answerTab);
            tabs.add(answerTab.createTab(factory.preaviousButton(),factory.nextButton()));
        }
    }


}

package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.AnswerTab;
import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.repositories.Repository;
import com.solovev.quiz_game.util.ButtonFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameForm implements ControllerData<Quiz> {
    @FXML
    public TextField textFieldCorrectAnswers;
    @FXML
    public TextField textFieldCorrectRate;
    @FXML
    public TabPane tabPainMain;
    private final Collection<AnswerTab> answerTabs = new ArrayList<>();

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


}

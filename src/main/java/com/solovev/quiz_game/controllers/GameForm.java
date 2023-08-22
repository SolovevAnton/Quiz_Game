package com.solovev.quiz_game.controllers;

import com.solovev.quiz_game.model.Question;
import com.solovev.quiz_game.model.Quiz;
import com.solovev.quiz_game.repositories.QuizRepository;
import com.solovev.quiz_game.repositories.Repository;
import com.solovev.quiz_game.util.TabFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class GameForm implements ControllerData<Quiz> {
    public TextField textFieldCorrectAnswers;
    public TextField textFieldCorrectRate;
    public TabPane tabPainMain;

    private int questionCounter;
    private final Map<Integer, Question> questionMap = new HashMap<>();//keys starts from one
    private final Button prevButton = new Button("Previous");
    private Button nextButton = new Button("Next");
    private TabFactory tabFactory= new TabFactory(prevButton,nextButton);


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
     * Creates tab based on the question, and adds it to question map
     * method made public ONLY FOR TESTING PURPOSES
     *
     * @param question to create tab from
     * @return created tab
     */
    public Tab tabFactory(Question question) {
        questionMap.put(++questionCounter, question);
        return new TabFactory(prevButton,nextButton).createTab(questionCounter,question); //todo refactor new
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
        for(Question q : quiz.getQuestions()){
            tabPainMain.getTabs().add(tabFactory(q));
        }

        buttonInitializer();
    }


}

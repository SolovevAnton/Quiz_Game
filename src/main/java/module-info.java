module com.solovev.quiz_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens com.solovev.quiz_game to javafx.fxml;
    opens com.solovev.quiz_game.model to com.fasterxml.jackson.databind;
    opens com.solovev.quiz_game.repositories to com.fasterxml.jackson.databind;
    opens com.solovev.quiz_game.util.enums to com.fasterxml.jackson.databind;


    exports com.solovev.quiz_game.util.enums;
    exports com.solovev.quiz_game;
    exports com.solovev.quiz_game.controllers;
    exports com.solovev.quiz_game.model;
    opens com.solovev.quiz_game.controllers to javafx.fxml;
}
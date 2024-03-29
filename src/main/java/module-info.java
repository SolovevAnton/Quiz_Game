module com.solovev.quiz_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.text;
    requires java.desktop;
    requires java.prefs;

    opens com.solovev.quiz_game to javafx.fxml;
    opens com.solovev.quiz_game.model to com.fasterxml.jackson.databind;
    opens com.solovev.quiz_game.repositories to com.fasterxml.jackson.databind;
    opens com.solovev.quiz_game.model.enums to com.fasterxml.jackson.databind;
    opens com.solovev.quiz_game.controllers to javafx.fxml;


    exports com.solovev.quiz_game.model.enums;
    exports com.solovev.quiz_game;
    exports com.solovev.quiz_game.controllers;
    exports com.solovev.quiz_game.model;
}
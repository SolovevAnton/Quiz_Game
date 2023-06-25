module com.solovev.quiz_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.annotation;

    opens com.solovev.quiz_game to javafx.fxml;
    exports com.solovev.quiz_game;
    exports com.solovev.quiz_game.controllers;
    opens com.solovev.quiz_game.controllers to javafx.fxml;
}
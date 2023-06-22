module com.solovev.quiz_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.solovev.quiz_game to javafx.fxml;
    exports com.solovev.quiz_game;
    exports com.solovev.quiz_game.controllers;
}
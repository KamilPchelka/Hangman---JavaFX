package pl.kamilpchelka.codecool.hangman.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainMenuController {

    private static final Logger log = LoggerFactory.getLogger(MainMenuController.class);

    private static final String STAGE_TITLE = "Hangman";
    private final Scene scene;
    private Stage stage;

    public MainMenuController(FXMLLoader loader, Stage stage) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        showStage();
    }

    public void showStage() {
        stage.setScene(scene);
        fadeTransition();
        stage.show();

    }

    public void handlePlayButtonEvents(ActionEvent event) throws IOException {
        //log.info("{}", ((Button) event.getSource()).getText());
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/game_scene.fxml"));
        new GameController(loader, stage, this);


    }

    public void handleQuitButtonEvents() {
        stage.close();
    }


    private void fadeTransition() {
        FadeTransition x = new FadeTransition(new Duration(500), scene.getRoot());
        x.setFromValue(0);
        x.setToValue(200);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }
}

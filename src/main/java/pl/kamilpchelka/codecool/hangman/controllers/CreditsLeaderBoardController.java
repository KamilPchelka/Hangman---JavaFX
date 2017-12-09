package pl.kamilpchelka.codecool.hangman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.LeaderBoardInjector;

import java.io.IOException;

public class CreditsLeaderBoardController {


    private final Stage stage;
    private final Scene scene;
    private final MainMenuController mainMenuController;

    @FXML
    Label labeHeaderMsg, labelBodyMsg;

    public CreditsLeaderBoardController(FXMLLoader loader, Stage stage, MainMenuController mainMenuController, boolean creditsStage) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        this.mainMenuController = mainMenuController;
        showStage();
        if (creditsStage) showCreditsStage();
        else if (!creditsStage) showLeaderBoardStage();

    }

    private void showStage() {
        stage.setScene(scene);
    }

    private void showLeaderBoardStage() {
        labeHeaderMsg.setText("Top Scores");
        labelBodyMsg.setText(LeaderBoardInjector.get().getTopResults());
    }

    private void showCreditsStage() {
        labeHeaderMsg.setText("Credits");
        labelBodyMsg.setText("Created by Kamil Pchełka" + "\n" + "Framework: JavaFX 2.0");
    }

    @FXML
    public void handleMainMenuButtonEvents() {
        mainMenuController.showStage();
    }
}

package pl.kamilpchelka.codecool.hangman.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pl.kamilpchelka.codecool.hangman.dao.LeaderBoard;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.LeaderBoardInjector;
import pl.kamilpchelka.codecool.hangman.models.Player;

import java.io.IOException;

public class EndGameController {

    private final Stage stage;
    private final Scene scene;
    private final MainMenuController mainMenuController;

    @FXML
    Label endGameHeaderMsg, endGameBodyMsg;

    public EndGameController(FXMLLoader loader, Stage stage, MainMenuController mainMenuController) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        this.mainMenuController = mainMenuController;
        showStage();
    }

    private void showStage() {
        stage.setScene(scene);
    }

    public void displayLostScene() {
        endGameHeaderMsg.setText("You have lost !");
        showLeaderBoard();
    }

    public void displayWinScene(Player player, String time) {
        endGameHeaderMsg.setText("You have won");
        endGameBodyMsg.setText(
                String.format("Congratulations %s !", player.getName())
                        + String.format("\nYou guessed after %d tries !", player.getGuessingTries())
                        + String.format("\nYour score: %f", player.getScore())
                        + String.format("\nIt took: %s", time));
        showLeaderBoard();
    }

    public void showLeaderBoard() {
        LeaderBoard leaderBoard = LeaderBoardInjector.get();
        endGameBodyMsg.setText(endGameBodyMsg.getText() + "\n\n" + leaderBoard.getTopResults());
    }

    @FXML
    public void handleMainMenuButtonEvents() {
        mainMenuController.showStage();
    }

    @FXML
    public void handleTryAgainButtonEvents(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/game_scene.fxml"));
        new GameController(loader, stage, mainMenuController);


    }
}

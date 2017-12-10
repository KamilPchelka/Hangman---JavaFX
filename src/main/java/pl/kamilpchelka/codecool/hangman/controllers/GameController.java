package pl.kamilpchelka.codecool.hangman.controllers;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.GameInjector;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.LeaderBoardInjector;
import pl.kamilpchelka.codecool.hangman.enumerations.GameState;
import pl.kamilpchelka.codecool.hangman.models.Game;
import pl.kamilpchelka.codecool.hangman.models.Player;
import pl.kamilpchelka.codecool.hangman.popups.WordInputPopup;
import pl.kamilpchelka.codecool.hangman.utils.ScoreCalculator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final Scene scene;
    @FXML
    public Pane hangmanPane;
    @FXML
    public Label labelWithCapitalName, labelWithCountryName, labelWithHealth, labelWithTime;
    public int timerSeconds;
    Task<Void> timerTask;
    private MainMenuController mainMenuController;
    private Stage stage;
    private Game game;

    public GameController(FXMLLoader loader, Stage stage, MainMenuController mainMenuController) throws IOException {
        loader.setController(this);
        this.stage = stage;
        this.scene = new Scene(loader.load());
        this.mainMenuController = mainMenuController;
        showStage();
        runGame();

    }

    private void runGame() {
        this.game = GameInjector.get();
        game.setLabelWithCapitalName(labelWithCapitalName);
        game.setLabelWithCountryName(labelWithCountryName);
        game.setLabelWithHealth(labelWithHealth);
        game.fillCapitalNameLabelWithDashes();
        game.setHangmanPartsObservableList(getHangmanParts());
        runTimer();
    }

    private void showStage() {
        stage.setScene(scene);
    }

    private ObservableList<Node> getHangmanParts() {
        return hangmanPane.getChildren();
    }

    public void runTimer() {
        timerTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int time = 0;
                Calendar calendar = Calendar.getInstance();
                while (true) {
                    time += 1;
                    timerSeconds = time;
                    Thread.sleep(1000);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.SECOND, time);
                    updateMessage(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime())));
                }
            }
        };
        labelWithTime.textProperty().bind(timerTask.messageProperty());
        Thread thread = new Thread(timerTask);
        thread.start();
    }

    public void processGameStateEvent(GameState gameState) {
        timerTask.cancel(true);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/game_end_scene.fxml"));
        EndGameController endGameController = null;
        try {
            endGameController = new EndGameController(loader, stage, mainMenuController);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gameState == GameState.USER_LOSE) {
            endGameController.displayLostScene();
        } else if (gameState == GameState.USER_WIN) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, timerSeconds);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            String time = String.valueOf(new SimpleDateFormat("HH:mm:ss").format(calendar.getTime()));
            Player player = game.getPlayer();
            player.setTime(timerSeconds);
            player.setName(new WordInputPopup("Type your nick", true, stage, 1).getUserInputText());
            ScoreCalculator calculator = player1 ->
                    ((double) player1.getCorrectTries() / (double) player1.getGuessingTries()) / player.getTime() * 10000;
            player.setScore(calculator.getScore(player));
            LeaderBoardInjector.get().addNewPlayer(player);
            endGameController.displayWinScene(player, time);
        }
    }

    @FXML
    public void handleMainMenuButtonEvents() {
        timerTask.cancel(true);
        mainMenuController.showStage();
    }


    @FXML
    public void handleEntireWordButton() {
        WordInputPopup userInputWord = new WordInputPopup("Type the word below", false, stage, 2);
        GameState gameState = game.processUserAnswerAndReturnGameState(userInputWord.getUserInputText());
        if (gameState != GameState.GAME_STILL_RUNNING) processGameStateEvent(gameState);

    }

    @FXML
    public void handleLetterButtons(ActionEvent event) {
        Button letterButton = (Button) event.getSource();
        GameState gameState = game.processUserAnswerAndReturnGameState(letterButton.getText());
        if (gameState != GameState.GAME_STILL_RUNNING) processGameStateEvent(gameState);
        letterButton.setDisable(true);
    }


}

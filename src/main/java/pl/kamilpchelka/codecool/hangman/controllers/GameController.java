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
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final Scene scene;
    @FXML
    public Pane hangmanPane;
    @FXML
    public Label labelWithCapitalName, labelWithCountryName, labelWithHealth, labelWithTime;
    Task<Void> timerTask;
    public int timerSeconds;
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

        ObservableList<Node> children = hangmanPane.getChildren();

        Line gallow1 = new Line(300, 300, 25, 300);
        gallow1.setStroke(Color.BROWN);
        gallow1.setStrokeWidth(3);
        gallow1.setVisible(false);
        children.add(gallow1);

        Line gallow2 = new Line(25, 25, 25, 300);
        gallow2.setStroke(Color.BROWN);
        gallow2.setStrokeWidth(3);
        gallow2.setVisible(false);
        children.add(gallow2);

        Line gallow3 = new Line(25, 25, 200, 25);
        gallow3.setStroke(Color.BROWN);
        gallow3.setStrokeWidth(3);
        children.add(gallow3);
        gallow3.setVisible(false);

        Line rope = new Line(200, 25, 200, 75);
        rope.setStroke(Color.BROWN);
        rope.setStrokeWidth(3);
        rope.setVisible(false);
        children.add(rope);

        List<Shape> body = new ArrayList<Shape>();

        Ellipse head = new Ellipse(200, 112, 35, 35);
        head.setStroke(Color.BLACK);
        head.setFill(Color.WHITE);
        head.setStrokeWidth(5);
        head.setVisible(false);
        children.add(head);


        Line tor = new Line(200, 200, 200, 150);
        tor.setStroke(Color.GOLD);
        tor.setStrokeWidth(5);
        tor.setVisible(false);
        children.add(tor);

        Line lftArm = new Line(150, 225, 200, 175);
        lftArm.setStroke(Color.BLACK);
        lftArm.setStrokeWidth(5);
        lftArm.setVisible(false);
        children.add(lftArm);

        Line rtArm = new Line(250, 225, 200, 175);
        rtArm.setStroke(Color.BLACK);
        rtArm.setStrokeWidth(5);
        rtArm.setVisible(false);
        children.add(rtArm);

        Line lftLeg = new Line(200, 200, 175, 275);
        lftLeg.setStroke(Color.BLACK);
        lftLeg.setStrokeWidth(5);
        lftLeg.setVisible(false);
        children.add(lftLeg);

        Line rtLeg = new Line(200, 200, 225, 275);
        rtLeg.setStroke(Color.BLACK);
        rtLeg.setStrokeWidth(5);
        rtLeg.setVisible(false);
        children.add(rtLeg);

        return children;
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

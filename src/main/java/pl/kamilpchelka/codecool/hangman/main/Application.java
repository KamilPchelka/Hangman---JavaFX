package pl.kamilpchelka.codecool.hangman.main;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pl.kamilpchelka.codecool.hangman.controllers.MainMenuController;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.MusicPlayerInjector;
import pl.kamilpchelka.codecool.hangman.utils.MusicPlayer;

import java.io.File;


public class Application extends javafx.application.Application {
    private static MusicPlayer musicPlayer;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        new File("scores.txt").createNewFile();
        musicPlayer = MusicPlayerInjector.get();
        musicPlayer.playMainThemeMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main_menu.fxml"));
        MainMenuController mainMenuController = new MainMenuController(loader, primaryStage);
        loader.setController(mainMenuController);
    }
}
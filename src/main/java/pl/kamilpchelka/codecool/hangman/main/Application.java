package pl.kamilpchelka.codecool.hangman.main;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pl.kamilpchelka.codecool.hangman.controllers.MainMenuController;


public class
Application extends javafx.application.Application {


    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main_menu.fxml"));
        MainMenuController mainMenuController = new MainMenuController(loader, primaryStage);
        loader.setController(mainMenuController);
    }
}
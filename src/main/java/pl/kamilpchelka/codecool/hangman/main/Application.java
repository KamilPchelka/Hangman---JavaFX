package pl.kamilpchelka.codecool.hangman.main;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pl.kamilpchelka.codecool.hangman.controllers.MainMenuController;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class Application extends javafx.application.Application {


    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
//        Path path = Paths.get(getClass().getClassLoader().getResource("countries_and_capitals.txt").getPath());
//        try (Stream<String> lines = Files.lines(path)) {
//            lines.forEach(System.out::println);
//        } catch (Exception e) {
//
//        }

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main_menu.fxml"));
        MainMenuController mainMenuController = new MainMenuController(loader, primaryStage);
        loader.setController(mainMenuController);
    }
}
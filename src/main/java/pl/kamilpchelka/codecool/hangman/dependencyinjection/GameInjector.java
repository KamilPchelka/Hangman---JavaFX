package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.models.Game;
import pl.kamilpchelka.codecool.hangman.models.GameImpl;

public class GameInjector {

    public static Game get() {
        return new GameImpl();
    }
}

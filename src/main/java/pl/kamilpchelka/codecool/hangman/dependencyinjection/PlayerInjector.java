package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.models.Player;
import pl.kamilpchelka.codecool.hangman.models.PlayerImpl;

public class PlayerInjector {

    public static Player get() {
        return new PlayerImpl();
    }
}

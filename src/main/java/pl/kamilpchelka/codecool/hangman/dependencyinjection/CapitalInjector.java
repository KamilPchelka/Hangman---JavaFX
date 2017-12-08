package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.models.Capital;
import pl.kamilpchelka.codecool.hangman.models.CapitalImpl;

public class CapitalInjector {

    public static Capital get() {
        return new CapitalImpl();
    }
}

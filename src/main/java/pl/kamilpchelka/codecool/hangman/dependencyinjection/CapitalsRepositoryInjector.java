package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.dao.CapitalsRepository;
import pl.kamilpchelka.codecool.hangman.dao.CapitalsRepositoryImpl;

public class CapitalsRepositoryInjector {

    public static CapitalsRepository get() {
        return new CapitalsRepositoryImpl();
    }
}

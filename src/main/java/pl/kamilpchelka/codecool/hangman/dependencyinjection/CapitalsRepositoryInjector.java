package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.repositories.CapitalsRepository;
import pl.kamilpchelka.codecool.hangman.repositories.CapitalsRepositoryImpl;

public class CapitalsRepositoryInjector {

    public static CapitalsRepository get() {
        return new CapitalsRepositoryImpl();
    }
}

package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.utils.MusicPlayer;
import pl.kamilpchelka.codecool.hangman.utils.MusicPlayerImpl;

public class MusicPlayerInjector {

    public static MusicPlayer get() {
        return new MusicPlayerImpl();
    }
}

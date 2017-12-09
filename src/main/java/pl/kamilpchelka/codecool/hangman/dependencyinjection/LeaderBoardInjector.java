package pl.kamilpchelka.codecool.hangman.dependencyinjection;

import pl.kamilpchelka.codecool.hangman.dao.LeaderBoard;
import pl.kamilpchelka.codecool.hangman.dao.LeaderBoardImpl;

public class LeaderBoardInjector {

    public static LeaderBoard get() {
        return new LeaderBoardImpl();
    }
}

package pl.kamilpchelka.codecool.hangman.dao;

import pl.kamilpchelka.codecool.hangman.models.Player;

public interface LeaderBoard {

    void addNewPlayer(Player player);

    String getTopResults();

}

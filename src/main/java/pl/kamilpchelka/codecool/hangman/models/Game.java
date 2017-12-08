package pl.kamilpchelka.codecool.hangman.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import pl.kamilpchelka.codecool.hangman.enumerations.GameState;

public interface Game {

    GameState processUserAnswerAndReturnGameState(String answer);

    Player getPlayer();

    void setPlayer(Player player);

    Capital getCapital();

    void setCapital(Capital capital);

    void fillCapitalNameLabelWithDashes();

    void setLabelWithCountryName(Label label);

    void setLabelWithCapitalName(Label label);

    void setLabelWithHealth(Label label);

    void setHangmanPartsObservableList(ObservableList<Node> children);

}
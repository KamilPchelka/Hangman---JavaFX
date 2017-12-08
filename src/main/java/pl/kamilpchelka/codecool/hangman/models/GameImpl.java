package pl.kamilpchelka.codecool.hangman.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.CapitalsRepositoryInjector;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.PlayerInjector;
import pl.kamilpchelka.codecool.hangman.enumerations.GameState;
import pl.kamilpchelka.codecool.hangman.repositories.CapitalsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameImpl implements Game {

    public static final int HEALTH_VALUE_FOR_HINT = 1;
    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);
    private static final int HEALTH = 10;
    private Player player;
    private Capital capital;
    private Label labelWithCountryName;
    private Label labelWithCapitalName;
    private Label labelWithHealth;
    private ObservableList<Node> children;

    public GameImpl() {
        setPlayer(null);
        setCapital(null);
    }

    @Override
    public GameState processUserAnswerAndReturnGameState(String answer) {
        if (!isAnswerCorrectAndReplaceLabelText(answer)) {
            player.setHealth(player.getHealth() - 1);
            labelWithHealth.setText(String.valueOf(player.getHealth()));
            showNextHangmanPart();

            if (player.getHealth() == HEALTH_VALUE_FOR_HINT) {
                labelWithCountryName.setText("Country: " + capital.getCountryName());
            }

        }

        if (isUserLose()) {
            return GameState.USER_LOSE;
        } else if (isUserWon()) {
            return GameState.USER_WON;
        }

        return GameState.GAME_STILL_RUNNING;

    }

    private void showNextHangmanPart() {
        for (Node child : children) {
            if (!child.isVisible()) {
                child.setVisible(true);
                return;
            }
        }
    }

    public boolean isUserWon() {

        return !labelWithCapitalName.getText().contains("-");

    }

    public boolean isUserLose() {
        return player.getHealth() <= 0;

    }

    public boolean isAnswerCorrectAndReplaceLabelText(String answer) {
        String capitalName = capital.getCapitalName();
        StringBuilder labelWithCapitalNameText = new StringBuilder(labelWithCapitalName.getText());
        if (capitalName.toUpperCase().contains(answer.toUpperCase())) {

            for (int i = 0; i < capitalName.length(); i++) {
                char charAtIndex = capitalName.charAt(i);
                if (String.valueOf(charAtIndex).equalsIgnoreCase(answer)) {
                    labelWithCapitalNameText.setCharAt(i, charAtIndex);
                }
                labelWithCapitalName.setText(labelWithCapitalNameText.toString());
            }

            return true;
        }

        return false;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(Player player) {
        Optional<Player> playerOptional = Optional.ofNullable(player);
        if (playerOptional.isPresent()) {
            this.player = playerOptional.get();
        } else {
            this.player = PlayerInjector.get();
            this.player.setHealth(HEALTH);
        }
    }

    @Override
    public Capital getCapital() {
        return this.capital;
    }

    @Override
    public void setCapital(Capital capital) {

        Optional<Capital> capitalOptional = Optional.ofNullable(capital);
        if (capitalOptional.isPresent()) {
            this.capital = capitalOptional.get();
        } else {
            CapitalsRepository capitalsRepository = CapitalsRepositoryInjector.get();
            List<Capital> capitals = capitalsRepository.getCapitals();
            this.capital = capitals.get(new Random().nextInt(capitals.size()));
        }
        log.info(this.capital.getCapitalName());
    }


    @Override
    public void fillCapitalNameLabelWithDashes() {
        String capitalName = capital.getCapitalName();
        capitalName = capitalName.replaceAll("[a-zA-Z]", "-");
        labelWithCapitalName.setText(capitalName);

    }

    @Override
    public void setLabelWithCountryName(Label label) {
        this.labelWithCountryName = label;
    }

    @Override
    public void setLabelWithCapitalName(Label label) {
        this.labelWithCapitalName = label;
    }

    @Override
    public void setLabelWithHealth(Label label) {
        this.labelWithHealth = label;
    }

    @Override
    public void setHangmanPartsObservableList(ObservableList<Node> children) {
        this.children = children;
    }
}

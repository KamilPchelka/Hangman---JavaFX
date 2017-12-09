package pl.kamilpchelka.codecool.hangman.models;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kamilpchelka.codecool.hangman.dao.CapitalsRepository;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.CapitalsRepositoryInjector;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.MusicPlayerInjector;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.PlayerInjector;
import pl.kamilpchelka.codecool.hangman.enumerations.GameState;
import pl.kamilpchelka.codecool.hangman.utils.MusicPlayer;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameImpl implements Game {

    private static final int AMOUNT_OF_HEALTH_TO_DECREASE_AFTER_WRONG_LETTER = 1;
    private static final int MINIMUM_ANSWER_LENGTH = 1;
    public static final int HEALTH_VALUE_FOR_HINT = MINIMUM_ANSWER_LENGTH;
    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);
    private static final int HEALTH = 10;
    private static final int USER_HEALTH_WHEN_REQUIRED_TO_LOSE_THE_GAME = 0;
    private static final int AMOUNT_OF_HEALTH_TO_DECREASE_AFTER_WRONG_WORD = 2;
    private Player player;
    private Capital capital;
    private Label labelWithCountryName;
    private Label labelWithCapitalName;
    private Label labelWithHealth;
    private ObservableList<Node> children;
    private MusicPlayer musicPlayer;

    public GameImpl() {
        setPlayer(null);
        setCapital(null);
        this.musicPlayer = MusicPlayerInjector.get();
    }

    @Override
    public GameState processUserAnswerAndReturnGameState(String answer) {
        player.incrementGuessingTries();
        if (answer.length() == MINIMUM_ANSWER_LENGTH) {
            if (!isAnswerCorrectAndReplaceLabelText(answer)) {
                player.setHealth(player.getHealth() - AMOUNT_OF_HEALTH_TO_DECREASE_AFTER_WRONG_LETTER);
                showNextHangmanPart();
                musicPlayer.playBadAnswerMusic();
            } else {
                player.incrementCorrectTries();
                musicPlayer.playCorrectAnswerMusic();
            }

        } else if (answer.length() > MINIMUM_ANSWER_LENGTH) {
            if (capital.getCapitalName().equalsIgnoreCase(answer)) {
                labelWithCapitalName.setText(capital.getCapitalName());
                player.incrementCorrectTries();
                musicPlayer.playCorrectAnswerMusic();
                return GameState.USER_WIN;
            } else {
                showNextHangmanPart();
                showNextHangmanPart();
                if (player.getHealth() == 2) player.setHealth(0);
                else player.setHealth(player.getHealth() - AMOUNT_OF_HEALTH_TO_DECREASE_AFTER_WRONG_WORD);
                musicPlayer.playCorrectAnswerMusic();
            }
        }

        labelWithHealth.setText(String.valueOf(player.getHealth()));

        if (player.getHealth() == HEALTH_VALUE_FOR_HINT) {
            labelWithCountryName.setText("Country: " + capital.getCountryName());
        }
        if (isUserLose()) {
            return GameState.USER_LOSE;
        } else if (isUserWon()) {
            return GameState.USER_WIN;
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
        return player.getHealth() <= USER_HEALTH_WHEN_REQUIRED_TO_LOSE_THE_GAME;

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

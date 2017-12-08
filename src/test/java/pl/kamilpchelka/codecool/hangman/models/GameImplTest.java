package pl.kamilpchelka.codecool.hangman.models;

import javafx.scene.control.Label;
import org.junit.Rule;
import org.junit.Test;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.CapitalInjector;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.GameInjector;

import static org.junit.Assert.assertEquals;

public class GameImplTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void fillCapitalNameLabelWithDashes() throws Exception {

        Capital capital = CapitalInjector.get();
        capital.setCountryName("Poland");
        capital.setCapitalName("Warszawa");


        Game game = GameInjector.get();

        game.setCapital(capital);
        Label label = new Label("");

        game.setLabelWithCapitalName(label);

        game.fillCapitalNameLabelWithDashes();

        assertEquals("--------", label.getText());
    }

}
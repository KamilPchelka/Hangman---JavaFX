package pl.kamilpchelka.codecool.hangman.repositories;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.CapitalsRepositoryInjector;

import static org.junit.Assert.assertEquals;


public class CapitalsRepositoryImplTest {

    static final Logger logger =
            LoggerFactory.getLogger(CapitalsRepositoryImplTest.class);

    @Test
    public void checkFirstIndexOfCapitalsList() {
        CapitalsRepository capitalsRepository = CapitalsRepositoryInjector.get();
        assertEquals(capitalsRepository.getCapitals().get(0).getCapitalName(), "Kabul");
    }

    @Test
    public void checkLastIndexOfCapitalsList() {
        CapitalsRepository capitalsRepository = CapitalsRepositoryInjector.get();
        assertEquals(capitalsRepository.getCapitals().get(182).getCapitalName(), "Harare");
    }

    @Test
    public void checkLengthOfCapitalsList() {
        CapitalsRepository capitalsRepository = CapitalsRepositoryInjector.get();
        assertEquals(capitalsRepository.getCapitals().size(), 183);
    }

}

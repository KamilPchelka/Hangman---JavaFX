package pl.kamilpchelka.codecool.hangman.dao;

import pl.kamilpchelka.codecool.hangman.dependencyinjection.CapitalInjector;
import pl.kamilpchelka.codecool.hangman.models.Capital;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CapitalsRepositoryImpl implements CapitalsRepository {

    private static final int CAPITAL_NAME_INDEX = 1;
    private static final int COUNTRY_NAME_INDEX = 0;
    private static final String REGEX = ";";
    private static final String COUNTRIES_AND_CAPITALS_TXT = "countries_and_capitals.txt";

    private Stream<String> loadRows() {
        Path path = Paths.get(getClass().getClassLoader().getResource(COUNTRIES_AND_CAPITALS_TXT).getPath());
        try {
            return Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

    @Override
    public List<Capital> getCapitals() {
        return loadRows()
                .map(string -> {
                    String[] strings = string.split(REGEX);
                    Capital newCapital = CapitalInjector.get();
                    newCapital.setCapitalName(strings[CAPITAL_NAME_INDEX]);
                    newCapital.setCountryName(strings[COUNTRY_NAME_INDEX]);
                    return newCapital;
                })
                .collect(Collectors.toList());
    }
}

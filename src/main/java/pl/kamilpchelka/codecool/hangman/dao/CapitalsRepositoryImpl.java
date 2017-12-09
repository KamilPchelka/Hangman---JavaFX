package pl.kamilpchelka.codecool.hangman.dao;

import pl.kamilpchelka.codecool.hangman.dependencyinjection.CapitalInjector;
import pl.kamilpchelka.codecool.hangman.models.Capital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CapitalsRepositoryImpl implements CapitalsRepository {

    private static final int CAPITAL_NAME_INDEX = 1;
    private static final int COUNTRY_NAME_INDEX = 0;
    private static final String REGEX = ";";

    private List<String> loadRows() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("countries_and_capitals.txt")))) {
            return new ArrayList<>(reader.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Capital> getCapitals() {
        return loadRows()
                .stream()
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

package pl.kamilpchelka.codecool.hangman.dao;

import pl.kamilpchelka.codecool.hangman.models.Capital;

import java.util.List;

public interface CapitalsRepository {

    List<Capital> getCapitals();

}

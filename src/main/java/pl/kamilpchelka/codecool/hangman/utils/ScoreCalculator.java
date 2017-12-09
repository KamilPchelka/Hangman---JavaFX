package pl.kamilpchelka.codecool.hangman.utils;

import pl.kamilpchelka.codecool.hangman.models.Player;

@FunctionalInterface
public interface ScoreCalculator {
    double getScore(Player player);
}

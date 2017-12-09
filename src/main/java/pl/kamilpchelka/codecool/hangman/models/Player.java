package pl.kamilpchelka.codecool.hangman.models;

public interface Player {

    int getHealth();

    void setHealth(int health);

    String getName();

    void setName(String name);

    double getScore();

    void setScore(double score);

    void incrementGuessingTries();

    void incrementCorrectTries();

    int getTime();

    void setTime(int time);

    int getGuessingTries();

    void setGuessingTries(int guessingTries);

    int getCorrectTries();


}

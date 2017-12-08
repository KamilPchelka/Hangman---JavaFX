package pl.kamilpchelka.codecool.hangman.models;

public interface Player {

    int getHealth();

    void setHealth(int health);

    String getName();

    void setName(String name);

    int getScore();

    void setScore(int score);


}

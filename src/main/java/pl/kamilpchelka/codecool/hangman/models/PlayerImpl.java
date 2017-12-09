package pl.kamilpchelka.codecool.hangman.models;

public class PlayerImpl implements Player {

    private int health;
    private int guessingTries = 0;
    private int correctTries;
    private double score = 0;
    private String name;
    private int time;

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public void incrementGuessingTries() {
        guessingTries++;
    }

    @Override
    public void incrementCorrectTries() {
        correctTries++;
    }

    @Override
    public int getTime() {
        return this.time;
    }

    @Override
    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int getGuessingTries() {
        return guessingTries;
    }

    @Override
    public void setGuessingTries(int guessingTries) {
        this.guessingTries = guessingTries;
    }

    @Override
    public int getCorrectTries() {
        return correctTries;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}

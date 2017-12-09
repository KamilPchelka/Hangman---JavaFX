package pl.kamilpchelka.codecool.hangman.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class MusicPlayerImpl implements MusicPlayer {

    private static final double MAIN_MUSIC_VOLUME = 0.01;
    private static final double ANSWER_SOUND_VOLUME = 0.1;
    private static final int MAIN_MUSIC_CYCLE_COUNT = -1;
    MediaPlayer mediaPlayer;

    @Override
    public void playMainThemeMusic() {
        URL resource = getClass().getClassLoader().getResource("sounds/theme_music.mp3");
        Media media = new Media(resource.toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setCycleCount(MAIN_MUSIC_CYCLE_COUNT);
        mediaPlayer.setVolume(MAIN_MUSIC_VOLUME);
        System.out.println(mediaPlayer.getVolume());
        mediaPlayer.play();
    }

    @Override
    public void stopMainThemeMusic() {

    }

    @Override
    public void playCorrectAnswerMusic() {
        URL resource = getClass().getClassLoader().getResource("sounds/correct_answer.mp3");
        Media media = new Media(resource.toString());
        this.mediaPlayer = new MediaPlayer(media);
        //this.mediaPlayer.setCycleCount(-1);
        mediaPlayer.setVolume(ANSWER_SOUND_VOLUME);
        mediaPlayer.play();

    }

    @Override
    public void playBadAnswerMusic() {
        URL resource = getClass().getClassLoader().getResource("sounds/wrong_answer.mp3");
        Media media = new Media(resource.toString());
        this.mediaPlayer = new MediaPlayer(media);
        //this.mediaPlayer.setCycleCount(-1);
        mediaPlayer.setVolume(ANSWER_SOUND_VOLUME);
        mediaPlayer.play();

    }

    @Override
    public void playButtonClickMusic() {

    }
}

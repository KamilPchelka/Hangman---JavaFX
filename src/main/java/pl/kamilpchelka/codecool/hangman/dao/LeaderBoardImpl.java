package pl.kamilpchelka.codecool.hangman.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kamilpchelka.codecool.hangman.dependencyinjection.PlayerInjector;
import pl.kamilpchelka.codecool.hangman.models.Player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeaderBoardImpl implements LeaderBoard {

    private static final Logger log = LoggerFactory.getLogger(LeaderBoardImpl.class);


    private List<Player> players = new ArrayList<>();

    private void saveData() {
        Path path = Paths.get(getClass().getClassLoader().getResource("scores.txt").getPath());
        log.info(path.toString());
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            for (Player player : players) {
                String line = String.format("%s,%s,%s,%s", player.getName(), player.getScore(), player.getGuessingTries(), player.getTime());
                bufferedWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadData() {
        Path path = Paths.get(getClass().getClassLoader().getResource("scores.txt").getPath());
        try (Stream<String> lines = Files.lines(path)) {
            players = lines.map(line -> {
                String[] attributes = line.split(",");
                Player player = PlayerInjector.get();
                player.setName(attributes[0]);
                player.setScore(Double.parseDouble(attributes[1]));
                player.setGuessingTries(Integer.parseInt(attributes[2]));
                player.setTime(Integer.parseInt(attributes[3]));
                return player;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewPlayer(Player player) {
        players.add(player);
        saveData();
    }

    @Override
    public String getTopResults() {
        loadData();
        Comparator<Integer> comp = (player1, player2) -> player1.compareTo(player2);
        players.sort(Comparator.comparing(Player::getScore).reversed());
        return players
                .stream()
                .limit(10)
                .map(player -> {
                    return String.format("%d. Name: %s | Score: %d", players.indexOf(player) + 1, player.getName(), (long) player.getScore());
                }).collect(Collectors.joining("\n"));
    }
}

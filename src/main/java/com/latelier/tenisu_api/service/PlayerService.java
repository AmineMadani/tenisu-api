package com.latelier.tenisu_api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latelier.tenisu_api.model.Player;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private List<Player> players;

    // To load on application lauching
    @PostConstruct
    public void init() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream("headtohead.json");

        // Le JSON a une clé "players" qui contient un tableau de joueurs
        Map<String, List<Player>> map = mapper.readValue(is, new TypeReference<>() {});
        this.players = map.get("players");
    }

    // Get all players sorted by the number of wins (descendant)
    public List<Player> getPlayersSortedByWins() {
        return players.stream()
                .sorted((p1, p2) -> {
                    long wins1 = p1.getPlayerData().getLast().stream().filter(i -> i == 1).count();
                    long wins2 = p2.getPlayerData().getLast().stream().filter(i -> i == 1).count();
                    return Long.compare(wins2, wins1);
                })
                .collect(Collectors.toList());
    }

    // Get a Player by ID
    public Optional<Player> getPlayerById(String id) {
        return players.stream()
                .filter(p -> String.valueOf(p.getId()).equals(id))
                .findFirst();
    }

    // Add a new player to the players list
    public void addPlayer(Player player) {
        players.add(player);
    }

    // Get access to all existing players
    public List<Player> getAllPlayers() {
        return players;
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // 1. Meilleur pays par ratio de victoires
        Map<String, Double> winRatiosByCountry = players.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCountry().getCode(),
                        Collectors.averagingDouble(p -> {
                            List<Integer> last = p.getPlayerData().getLast();
                            long wins = last.stream().filter(i -> i == 1).count();
                            long losses = last.stream().filter(i -> i == 0).count();
                            if (wins + losses == 0) return 0.0;
                            return (double) wins / (wins + losses);
                        })
                ));

        String bestCountry = winRatiosByCountry.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        stats.put("bestCountry", bestCountry);

        // 2. Average BMI of all players = weight / height²
        double averageBMI = players.stream()
                .mapToDouble(p -> {
                    int weight = p.getPlayerData().getWeight();
                    int height = p.getPlayerData().getHeight();
                    if (height == 0) return 0;
                    return weight / Math.pow(height / 100.0, 2);
                })
                .average()
                .orElse(0.0);

        stats.put("averageBMI", averageBMI);

        // 3. Medial height calculation
        List<Integer> heights = players.stream()
                .map(p -> p.getPlayerData().getHeight())
                .sorted()
                .collect(Collectors.toList());

        double medianHeight;
        int size = heights.size();
        if (size == 0) {
            medianHeight = 0;
        } else if (size % 2 == 0) {
            medianHeight = (heights.get(size / 2 - 1) + heights.get(size / 2)) / 2.0;
        } else {
            medianHeight = heights.get(size / 2);
        }

        stats.put("medianHeight", medianHeight);

        return stats;
    }



}

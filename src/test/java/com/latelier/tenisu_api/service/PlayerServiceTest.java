package com.latelier.tenisu_api.service;

import com.latelier.tenisu_api.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    private PlayerService playerService;

    @BeforeEach
    void setUp() throws Exception {
        playerService = new PlayerService();
        playerService.init(); // loading the Json file
    }

    @Test
    void testReturnPlayerWhenIdExists() {
        Optional<Player> player = playerService.getPlayerById("102"); // Serena Williams
        assertTrue(player.isPresent());
        assertEquals("S.WIL", player.get().getShortname());
    }

    @Test
    void testeturnEmptyWhenIdDoesNotExist() {
        Optional<Player> player = playerService.getPlayerById("29061999");
        assertTrue(player.isEmpty());
    }

    @Test
    void testReturnPlayersSortedByWins() {
        List<Player> players = playerService.getPlayersSortedByWins();
        assertFalse(players.isEmpty());

        int wins0 = countWins(players.get(0));
        int wins1 = countWins(players.get(1));
        assertTrue(wins0 >= wins1);
    }

    @Test
    void testReturnStatisticsWithExpectedKeysAndTypes() {
        Map<String, Object> stats = playerService.getStatistics();

        assertTrue(stats.containsKey("bestCountry"));
        assertTrue(stats.containsKey("averageBMI"));
        assertTrue(stats.containsKey("medianHeight"));

        assertInstanceOf(String.class, stats.get("bestCountry"));
        assertInstanceOf(Double.class, stats.get("averageBMI"));
        assertInstanceOf(Integer.class, stats.get("medianHeight"));
    }

    private int countWins(Player player) {
        return (int) player.getPlayerData().getLast().stream().filter(i -> i == 1).count();
    }
}

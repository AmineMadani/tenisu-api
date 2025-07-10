package com.latelier.tenisu_api.controller;

import com.latelier.tenisu_api.model.Player;
import com.latelier.tenisu_api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    /**
     * GET /players
     * Returns the list of all players, sorted by number of wins (descending order).
     * If the list is empty, returns HTTP 204 No Content.
     */
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayersSortedByWins() {
        List<Player> sortedPlayers = playerService.getPlayersSortedByWins();

        if (sortedPlayers.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }

        return ResponseEntity.ok(sortedPlayers); // HTTP 200 + JSON list
    }

    /**
     * GET /players/{id}
     * Returns the details of a player by ID.
     * If the player is not found, returns HTTP 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable String id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); // HTTP 404
    }

    /**
     * POST /players
     * Adds a new player to the list (in-memory).
     * Expects a full Player JSON object in the request body.
     */
    @PostMapping
    public ResponseEntity<String> addPlayer(@RequestBody Player newPlayer) {
        playerService.addPlayer(newPlayer);
        return ResponseEntity.ok("Player added successfully"); // HTTP 200
    }
}

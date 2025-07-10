package com.latelier.tenisu_api.controller;

import com.latelier.tenisu_api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatsController {

    @Autowired
    private PlayerService playerService;

    /**
     * GET /statistics
     * Returns overall statistics across all players:
     * - bestCountry: the country with the most players
     * - averageBMI: average Body Mass Index of all players
     * - medianHeight: median height in cm
     * If the list is empty, returns HTTP 204 No Content.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = playerService.getStatistics();

        if (stats.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204
        }

        return ResponseEntity.ok(stats); // HTTP 200 + JSON stats
    }
}

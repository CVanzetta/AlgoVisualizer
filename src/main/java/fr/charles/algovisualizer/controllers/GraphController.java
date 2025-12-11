package fr.charles.algovisualizer.controllers;

import fr.charles.algovisualizer.dto.GraphRequest;
import fr.charles.algovisualizer.services.GraphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/algorithms")
    public Map<String, String> getAlgorithms() {
        return graphService.getAvailableAlgorithms();
    }

    @PostMapping("/{algorithm}")
    public ResponseEntity<List<Integer>> shortestPath(@PathVariable String algorithm, @RequestBody GraphRequest request) {
        try {
            List<Integer> path = graphService.findShortestPath(algorithm, request.getGraph(), request.getStart(), request.getEnd());
            return ResponseEntity.ok(path);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

package fr.charles.algovisualizer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.charles.algovisualizer.services.SortingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sort")
public class SortingController {

    private final SortingService sortingService;

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @GetMapping("/algorithms")
    public Map<String, String> getAlgorithms() {
        return sortingService.getAvailableAlgorithms();
    }

    @PostMapping("/{algorithm}")
    public ResponseEntity<List<int[]>> sort(@PathVariable String algorithm, @RequestBody int[] array) {
        try {
            List<int[]> steps = sortingService.sort(algorithm, array);
            return ResponseEntity.ok(steps);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

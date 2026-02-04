package fr.charles.algovisualizer.controllers;

import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import fr.charles.algovisualizer.services.SortingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sort")
@Validated
public class SortingController {

    private static final Logger logger = LoggerFactory.getLogger(SortingController.class);
    private final SortingService sortingService;

    public SortingController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @GetMapping("/algorithms")
    public Map<String, String> getAlgorithms() {
        return sortingService.getAvailableAlgorithms();
    }

    @PostMapping("/{algorithm}")
    public ResponseEntity<List<int[]>> sort(
            @PathVariable String algorithm, 
            @RequestBody @Size(max = 100000, message = "Array size exceeds maximum limit") int[] array) {
        try {
            if (array == null || array.length == 0) {
                logger.warn("Sort request rejected: empty or null array for algorithm {}", algorithm);
                return ResponseEntity.badRequest().build();
            }
            logger.info("Sort request: algorithm={}, arraySize={}", algorithm, array.length);
            List<int[]> steps = sortingService.sort(algorithm, array);
            logger.info("Sort completed successfully: algorithm={}, steps={}", algorithm, steps.size());
            return ResponseEntity.ok(steps);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid sort request: algorithm={}, error={}", algorithm, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Sort request failed: algorithm={}, error={}", algorithm, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package fr.charles.algovisualizer.controllers;

import jakarta.validation.constraints.Size;
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
                return ResponseEntity.badRequest().build();
            }
            List<int[]> steps = sortingService.sort(algorithm, array);
            return ResponseEntity.ok(steps);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

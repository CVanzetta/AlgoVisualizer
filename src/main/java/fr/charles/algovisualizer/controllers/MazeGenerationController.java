package fr.charles.algovisualizer.controllers;

import fr.charles.algovisualizer.dto.MazeGenerationRequest;
import fr.charles.algovisualizer.dto.MazeGenerationResponse;
import fr.charles.algovisualizer.services.MazeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller REST pour la génération de labyrinthes
 */
@RestController
@RequestMapping("/api/maze/generate")
// CORS est intentionnellement ouvert car :
// 1. Application éducative sans données sensibles
// 2. Endpoint de calcul pur (génération de labyrinthes)
// 3. Pas d'authentification requise par design
// 4. Pas de modifications de données persistantes
// NOTE: Si déploiement en production publique, restreindre aux domaines autorisés
@CrossOrigin(origins = "*")  // NOSONAR java:S5122
public class MazeGenerationController {
    
    @Autowired
    private MazeGenerationService mazeGenerationService;
    
    /**
     * Génère un labyrinthe
     * POST /api/maze/generate
     */
    @PostMapping
    public ResponseEntity<MazeGenerationResponse> generateMaze(@RequestBody MazeGenerationRequest request) {
        try {
            MazeGenerationResponse response = mazeGenerationService.generateMaze(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Liste les générateurs disponibles
     * GET /api/maze/generate/algorithms
     */
    @GetMapping("/algorithms")
    public ResponseEntity<Map<String, String>> getAvailableAlgorithms() {
        return ResponseEntity.ok(mazeGenerationService.getAvailableGenerators());
    }
}

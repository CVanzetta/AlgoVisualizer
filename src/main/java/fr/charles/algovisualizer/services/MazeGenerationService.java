package fr.charles.algovisualizer.services;

import fr.charles.algovisualizer.algorithms.maze.generator.*;
import fr.charles.algovisualizer.dto.MazeGenerationRequest;
import fr.charles.algovisualizer.dto.MazeGenerationResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service pour la génération de labyrinthes
 */
@Service
public class MazeGenerationService {
    
    private final Map<String, MazeGenerator> generators;
    
    public MazeGenerationService() {
        generators = new HashMap<>();
        generators.put("recursive", new RecursiveBacktrackingGenerator());
        generators.put("prim", new PrimGenerator());
        generators.put("binary", new BinaryTreeGenerator());
        generators.put("random", new RandomGenerator());
        generators.put("kruskal", new KruskalGenerator());
        generators.put("aldous-broder", new AldousBroderGenerator());
        generators.put("wilson", new WilsonGenerator());
        generators.put("hunt-kill", new HuntAndKillGenerator());
        generators.put("growing-tree", new GrowingTreeGenerator());
        generators.put("sidewinder", new SidewinderGenerator());
        generators.put("eller", new EllerGenerator());
        generators.put("recursive-division", new RecursiveDivisionGenerator());
    }
    
    /**
     * Génère un labyrinthe selon l'algorithme spécifié
     */
    public MazeGenerationResponse generateMaze(MazeGenerationRequest request) {
        MazeGenerator generator = generators.get(request.getAlgorithm());
        
        if (generator == null) {
            throw new IllegalArgumentException("Algorithme inconnu : " + request.getAlgorithm());
        }
        
        long startTime = System.currentTimeMillis();
        int[][] maze = generator.generate(request.getWidth(), request.getHeight());
        long executionTime = System.currentTimeMillis() - startTime;
        
        return new MazeGenerationResponse(maze, generator.getName(), executionTime);
    }
    
    /**
     * Retourne la liste des générateurs disponibles
     */
    public Map<String, String> getAvailableGenerators() {
        Map<String, String> result = new HashMap<>();
        generators.forEach((key, gen) -> result.put(key, gen.getName()));
        return result;
    }
}

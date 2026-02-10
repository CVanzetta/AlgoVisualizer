package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.Random;

/**
 * Générateur de labyrinthe aléatoire simple
 * 
 * PRINCIPE :
 * Pour chaque cellule, il y a 30% de chance d'être un mur
 * 
 * CARACTÉRISTIQUES :
 * - Très simple et rapide
 * - Pas de garantie de solution (peut créer des zones isolées)
 * - Utile pour les tests rapides
 * - Ne crée pas de "vrai" labyrinthe structuré
 */
public class RandomGenerator extends AbstractMazeGenerator {
    
    private static final double WALL_PROBABILITY = 0.3;
    
    @Override
    public String getName() {
        return "Random (30% walls)";
    }
    
    @Override
    public String getDescription() {
        return "Génération aléatoire simple (peut ne pas avoir de solution)";
    }
    
    @Override
    public int[][] generate(int width, int height) {
        int[][] maze = new int[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = random.nextDouble() < WALL_PROBABILITY ? 1 : 0;
            }
        }
        
        return maze;
    }
}

package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.Random;

/**
 * Générateur de labyrinthe par Binary Tree
 * 
 * PRINCIPE :
 * Pour chaque cellule du labyrinthe :
 * - Choisir aléatoirement de creuser vers le nord OU vers l'est
 * - Exception : si au bord nord, creuser vers l'est uniquement
 * - Exception : si au bord est, creuser vers le nord uniquement
 * 
 * CARACTÉRISTIQUES :
 * - Algorithme le plus simple et rapide
 * - Crée un biais diagonal (nord-est toujours ouvert)
 * - Pas de labyrinthe "parfait" (plusieurs chemins possibles)
 * - Facile à résoudre (suivre le mur nord ou est)
 * - Utile pour tester les algorithmes de résolution sur un cas simple
 */
public class BinaryTreeGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Binary Tree";
    }
    
    @Override
    public String getDescription() {
        return "Algorithme rapide créant un biais diagonal (facile à résoudre)";
    }
    
    @Override
    @SuppressWarnings("java:S3776")
    public int[][] generate(int width, int height) {
        int[][] maze = initializeMazeWithWalls(width, height);
        
        // Creuser les cellules selon Binary Tree
        for (int y = 0; y < height; y += 2) {
            for (int x = 0; x < width; x += 2) {
                markCellAsEmpty(maze, x, y); // Cellule actuelle = passage
                
                boolean canGoNorth = y > 0;
                boolean canGoEast = x < width - 2;
                
                if (canGoNorth && canGoEast) {
                    // Choisir aléatoirement nord ou est
                    if (random.nextBoolean()) {
                        removeWall(maze, x, y - 1); // Mur vers le nord
                    } else {
                        removeWall(maze, x + 1, y); // Mur vers l'est
                    }
                } else if (canGoNorth) {
                    removeWall(maze, x, y - 1); // Mur vers le nord uniquement
                } else if (canGoEast) {
                    removeWall(maze, x + 1, y); // Mur vers l'est uniquement
                }
            }
        }
        
        return maze;
    }
}

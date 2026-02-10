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
<<<<<<< HEAD
public class BinaryTreeGenerator extends AbstractMazeGenerator {
=======
public class BinaryTreeGenerator implements MazeGenerator {
    
    private Random random = new Random();
>>>>>>> dev
    
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
<<<<<<< HEAD
        int[][] maze = initializeMazeWithWalls(width, height);
=======
        // Initialiser toutes les cellules comme des passages
        int[][] maze = new int[height][width];
        
        // Créer une grille de murs d'abord
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = 1; // Mur
            }
        }
>>>>>>> dev
        
        // Creuser les cellules selon Binary Tree
        for (int y = 0; y < height; y += 2) {
            for (int x = 0; x < width; x += 2) {
<<<<<<< HEAD
                markCellAsEmpty(maze, x, y); // Cellule actuelle = passage
=======
                maze[y][x] = 0; // Cellule actuelle = passage
>>>>>>> dev
                
                boolean canGoNorth = y > 0;
                boolean canGoEast = x < width - 2;
                
                if (canGoNorth && canGoEast) {
                    // Choisir aléatoirement nord ou est
                    if (random.nextBoolean()) {
<<<<<<< HEAD
                        removeWall(maze, x, y - 1); // Mur vers le nord
                    } else {
                        removeWall(maze, x + 1, y); // Mur vers l'est
                    }
                } else if (canGoNorth) {
                    removeWall(maze, x, y - 1); // Mur vers le nord uniquement
                } else if (canGoEast) {
                    removeWall(maze, x + 1, y); // Mur vers l'est uniquement
=======
                        maze[y - 1][x] = 0; // Mur vers le nord
                    } else {
                        maze[y][x + 1] = 0; // Mur vers l'est
                    }
                } else if (canGoNorth) {
                    maze[y - 1][x] = 0; // Mur vers le nord uniquement
                } else if (canGoEast) {
                    maze[y][x + 1] = 0; // Mur vers l'est uniquement
>>>>>>> dev
                }
            }
        }
        
        return maze;
    }
}

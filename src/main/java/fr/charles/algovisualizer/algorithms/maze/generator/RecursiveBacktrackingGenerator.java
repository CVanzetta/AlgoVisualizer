package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par Recursive Backtracking (DFS)
 * 
 * PRINCIPE :
 * 1. Commencer à une cellule aléatoire
 * 2. Marquer la cellule comme visitée
 * 3. Tant qu'il y a des voisins non visités :
 *    - Choisir un voisin aléatoire non visité
 *    - Supprimer le mur entre les deux cellules
 *    - Appeler récursivement sur le voisin
 * 4. Retourner en arrière (backtrack) si aucun voisin
 * 
 * CARACTÉRISTIQUES :
 * - Labyrinthe "parfait" (une seule solution entre deux points)
 * - Longs couloirs sinueux
 * - Peu d'embranchements
 * - Difficulté moyenne pour la résolution
 */
public class RecursiveBacktrackingGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Recursive Backtracking (DFS)";
    }
    
    @Override
    public String getDescription() {
        return "Crée des labyrinthes avec de longs couloirs sinueux et peu d'embranchements";
    }
    
    @Override
    public int[][] generate(int width, int height) {
        // Initialiser avec des murs
        int[][] maze = initializeMazeWithWalls(width, height);
        
        // Commencer au centre
        int startX = width / 2;
        int startY = height / 2;
        
        // Lancer l'algorithme récursif
        carve(maze, startX, startY, width, height);
        
        return maze;
    }
    
    private void carve(int[][] maze, int x, int y, int width, int height) {
        // Marquer la cellule actuelle comme un passage
        maze[y][x] = 0;
        
        // Mélanger les directions aléatoirement
        List<int[]> dirList = new ArrayList<>(Arrays.asList(DIRECTIONS));
        Collections.shuffle(dirList, random);
        
        // Pour chaque direction
        for (int[] dir : dirList) {
            int nx = x + dir[0] * 2; // Sauter une cellule (pour le mur)
            int ny = y + dir[1] * 2;
            
            // Vérifier si la nouvelle position est valide et non visitée
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && maze[ny][nx] == 1) {
                // Supprimer le mur entre les deux cellules
                maze[y + dir[1]][x + dir[0]] = 0;
                
                // Récursion sur la nouvelle cellule
                carve(maze, nx, ny, width, height);
            }
        }
    }
}

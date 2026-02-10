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
<<<<<<< HEAD
public class RecursiveBacktrackingGenerator extends AbstractMazeGenerator {
=======
public class RecursiveBacktrackingGenerator implements MazeGenerator {
    
    private Random random = new Random();
>>>>>>> dev
    
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
<<<<<<< HEAD
        int[][] maze = initializeMazeWithWalls(width, height);
=======
        // Initialiser toutes les cellules comme des murs
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], 1);
        }
>>>>>>> dev
        
        // Commencer au centre
        int startX = width / 2;
        int startY = height / 2;
        
        // Lancer l'algorithme récursif
        carve(maze, startX, startY, width, height);
        
        return maze;
    }
    
    private void carve(int[][] maze, int x, int y, int width, int height) {
<<<<<<< HEAD
        markCellAsEmpty(maze, x, y);
        
        // Mélanger les directions aléatoirement
        List<int[]> dirList = new ArrayList<>(Arrays.asList(DIRECTIONS));
=======
        // Marquer la cellule actuelle comme un passage
        maze[y][x] = 0;
        
        // Directions : haut, droite, bas, gauche
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        
        // Mélanger les directions aléatoirement
        List<int[]> dirList = new ArrayList<>(Arrays.asList(directions));
>>>>>>> dev
        Collections.shuffle(dirList, random);
        
        // Pour chaque direction
        for (int[] dir : dirList) {
            int nx = x + dir[0] * 2; // Sauter une cellule (pour le mur)
            int ny = y + dir[1] * 2;
            
            // Vérifier si la nouvelle position est valide et non visitée
<<<<<<< HEAD
            if (isInBounds(nx, ny, width, height) && maze[ny][nx] == WALL) {
                // Supprimer le mur entre les deux cellules
                removeWall(maze, x + dir[0], y + dir[1]);
=======
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && maze[ny][nx] == 1) {
                // Supprimer le mur entre les deux cellules
                maze[y + dir[1]][x + dir[0]] = 0;
>>>>>>> dev
                
                // Récursion sur la nouvelle cellule
                carve(maze, nx, ny, width, height);
            }
        }
    }
}

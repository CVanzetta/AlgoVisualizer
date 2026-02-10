package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme de Prim
 * 
 * PRINCIPE :
 * 1. Commencer avec une cellule aléatoire dans le labyrinthe
 * 2. Ajouter tous ses murs voisins à une liste
 * 3. Tant que la liste n'est pas vide :
 *    - Choisir un mur aléatoire de la liste
 *    - Si la cellule de l'autre côté n'est pas visitée :
 *      * Supprimer le mur
 *      * Marquer la cellule comme visitée
 *      * Ajouter ses murs voisins à la liste
 *    - Retirer le mur de la liste
 * 
 * CARACTÉRISTIQUES :
 * - Labyrinthes avec de nombreux embranchements courts
 * - Structure moins "linéaire" que Recursive Backtracking
 * - Chemins plus variés
 * - Difficulté élevée pour la résolution
 */
public class PrimGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Prim's Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Crée des labyrinthes avec de nombreux embranchements courts et chemins variés";
    }
    
    @Override
    public int[][] generate(int width, int height) {
        // Initialiser toutes les cellules comme des murs
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], 1);
        }
        
        // Cellule de départ
        int startX = width / 2;
        int startY = height / 2;
        maze[startY][startX] = 0;
        
        // Liste des murs frontières
        List<Wall> walls = new ArrayList<>();
        addWalls(walls, startX, startY, width, height);
        
        // Tant qu'il y a des murs dans la liste
        while (!walls.isEmpty()) {
            // Choisir un mur aléatoire
            Wall wall = walls.remove(random.nextInt(walls.size()));
            
            int x = wall.x;
            int y = wall.y;
            int dx = wall.dx;
            int dy = wall.dy;
            
            int nx = x + dx;
            int ny = y + dy;
            
            // Vérifier si on peut creuser
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && maze[ny][nx] == 1) {
                // Compter les voisins déjà creusés
                int visitedNeighbors = countVisitedNeighbors(maze, nx, ny, width, height);
                
                // Ne creuser que si un seul voisin est visité (évite les boucles)
                if (visitedNeighbors == 1) {
                    maze[y][x] = 0; // Supprimer le mur
                    maze[ny][nx] = 0; // Creuser la cellule
                    addWalls(walls, nx, ny, width, height);
                }
            }
        }
        
        return maze;
    }
    
    private void addWalls(List<Wall> walls, int x, int y, int width, int height) {
        for (int[] dir : DIRECTIONS) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                walls.add(new Wall(nx, ny, dir[0], dir[1]));
            }
        }
    }
    
    private int countVisitedNeighbors(int[][] maze, int x, int y, int width, int height) {
        int count = 0;
        
        for (int[] dir : DIRECTIONS) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && maze[ny][nx] == 0) {
                count++;
            }
        }
        
        return count;
    }
    
    private static class Wall {
        int x;
        int y;
        int dx;
        int dy;
        
        Wall(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }
    }
}

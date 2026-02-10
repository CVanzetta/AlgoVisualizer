package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme Hunt-and-Kill
 * 
 * PRINCIPE (hybride entre DFS et scan) :
 * 1. KILL PHASE : Commencer à une cellule et faire une marche aléatoire (comme DFS)
 *    - Choisir un voisin non visité aléatoire
 *    - Supprimer le mur et se déplacer
 *    - Continuer jusqu'à être bloqué (plus de voisins non visités)
 * 
 * 2. HUNT PHASE : Scanner la grille pour trouver une cellule non visitée
 *    - Parcourir la grille ligne par ligne
 *    - Trouver une cellule non visitée adjacent à une cellule visitée
 *    - Supprimer le mur entre elles
 *    - Recommencer la KILL phase depuis cette cellule
 * 
 * 3. Répéter KILL → HUNT jusqu'à avoir visité toutes les cellules
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait" (une seule solution entre deux points)
 * - Labyrinthes avec de longs passages sinueux
 * - Légèrement plus long à générer que Recursive Backtracker
 * - Ressemble visuellement aux labyrinthes de Recursive Backtracker
 * 
 * AVANTAGES :
 * - Pas de récursion (pas de stack overflow)
 * - Pas besoin de stack explicite (contrairement à Recursive Backtracker)
 * - Labyrinthes esthétiques avec de longs couloirs
 * - Facile à animer (on voit la phase de "chasse")
 * 
 * DÉSAVANTAGES :
 * - La phase HUNT peut être lente (scan complet de la grille)
 * - Plus lent que Recursive Backtracker en pratique
 * - Nécessite de garder trace de toutes les cellules visitées
 */
public class HuntAndKillGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Hunt-and-Kill Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Alterne entre marche aléatoire (Kill) et scan de grille (Hunt)";
    }
    
    @Override
    @SuppressWarnings("java:S3776")
    public int[][] generate(int width, int height) {
        // Initialiser le labyrinthe avec espacement de 2
        int[][] maze = initializeMazeWithSpacedCells(width, height);
        
        // Grille de cellules
        int cellWidth = getCellWidth(width);
        int cellHeight = getCellHeight(height);
        
        // Tableau des cellules visitées
        boolean[][] visited = new boolean[cellHeight][cellWidth];
        int visitedCount = 0;
        int totalCells = cellWidth * cellHeight;
        
        // Commencer à une position aléatoire
        int currentX = random.nextInt(cellWidth);
        int currentY = random.nextInt(cellHeight);
        visited[currentY][currentX] = true;
        visitedCount++;
        
        while (visitedCount < totalCells) {
            // PHASE KILL : Marche aléatoire
            List<int[]> unvisitedNeighbors = getUnvisitedNeighbors(currentX, currentY, 
                                                                     visited, cellWidth, cellHeight, DIRECTIONS);
            
            while (!unvisitedNeighbors.isEmpty()) {
                // Choisir un voisin non visité aléatoire
                int[] neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
                int nextX = neighbor[0];
                int nextY = neighbor[1];
                
                // Supprimer le mur entre les deux cellules
                int[] wall = getWallPosition(currentX, currentY, nextX - currentX, nextY - currentY);
                maze[wall[1]][wall[0]] = 0;
                
                // Marquer le voisin comme visité et se déplacer
                visited[nextY][nextX] = true;
                visitedCount++;
                currentX = nextX;
                currentY = nextY;
                
                // Chercher les prochains voisins non visités
                unvisitedNeighbors = getUnvisitedNeighbors(currentX, currentY, 
                                                           visited, cellWidth, cellHeight, DIRECTIONS);
            }
            
            // PHASE HUNT : Scanner la grille pour trouver une cellule non visitée
            // adjacente à une cellule visitée
            boolean found = false;
            
            for (int y = 0; y < cellHeight && !found; y++) {
                for (int x = 0; x < cellWidth && !found; x++) {
                    if (!visited[y][x]) {
                        // Vérifier si cette cellule a un voisin visité
                        for (int[] dir : DIRECTIONS) {
                            int nx = x + dir[0];
                            int ny = y + dir[1];
                            
                            if (nx >= 0 && nx < cellWidth && ny >= 0 && ny < cellHeight && visited[ny][nx]) {
                                // Connecter cette cellule au labyrinthe
                                int[] wall = getWallPosition(x, y, dir[0], dir[1]);
                                maze[wall[1]][wall[0]] = 0;
                                
                                visited[y][x] = true;
                                visitedCount++;
                                currentX = x;
                                currentY = y;
                                found = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        return maze;
    }
    
    /**
     * Retourne la liste des voisins non visités d'une cellule
     */
    private List<int[]> getUnvisitedNeighbors(int x, int y, boolean[][] visited, 
                                               int cellWidth, int cellHeight, int[][] directions) {
        List<int[]> neighbors = new ArrayList<>();
        
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            
            if (nx >= 0 && nx < cellWidth && ny >= 0 && ny < cellHeight && !visited[ny][nx]) {
                neighbors.add(new int[]{nx, ny});
            }
        }
        
        return neighbors;
    }
}

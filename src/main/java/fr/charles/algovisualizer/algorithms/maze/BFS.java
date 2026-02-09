package fr.charles.algovisualizer.algorithms.maze;

import java.util.*;

/**
 * Algorithme BFS (Breadth-First Search) pour la résolution de labyrinthe
 * 
 * ========================================
 * PRINCIPE DU BFS
 * ========================================
 * Le BFS explore le labyrinthe niveau par niveau, garantissant de trouver
 * le chemin le plus court en nombre de cases.
 * 
 * Utilise une file (Queue) FIFO : First In, First Out
 * 
 * AVANTAGES :
 * - Garantit le chemin le plus court
 * - Simple à implémenter
 * 
 * INCONVÉNIENTS :
 * - Peut consommer beaucoup de mémoire sur de grands labyrinthes
 * 
 * ========================================
 * À IMPLÉMENTER
 * ========================================
 */
public class BFS implements MazeAlgorithm {
    
    @Override
    public String getName() {
        return "BFS (Breadth-First Search)";
    }
    
    @Override
    public MazeResult solve(int[][] maze, int startX, int startY, int endX, int endY) {
        long startTime = System.currentTimeMillis();
        
        // TODO: Implémenter l'algorithme BFS ici
        
        // 1. Créer une file (Queue) pour stocker les positions à explorer
        Queue<Position> queue = new LinkedList<>();
        
        // 2. Créer un Set pour marquer les positions visitées
        Set<Position> visited = new HashSet<>();
        
        // 3. Créer une Map pour reconstruire le chemin (parent de chaque position)
        Map<Position, Position> cameFrom = new HashMap<>();
        
        // 4. Ajouter la position de départ à la file
        Position start = new Position(startX, startY);
        queue.offer(start);
        visited.add(start);
        
        int visitedCount = 0;
        
        // 5. Tant que la file n'est pas vide
        while (!queue.isEmpty()) {
            // TODO: Défiler la première position
            Position current = null; // À COMPLÉTER
            
            visitedCount++;
            
            // TODO: Vérifier si on a atteint l'arrivée
            // Si oui, reconstruire le chemin et le retourner
            
            // TODO: Explorer les 4 voisins (haut, bas, gauche, droite)
            // Pour chaque voisin valide et non visité:
            //   - Le marquer comme visité
            //   - L'ajouter à la file
            //   - Enregistrer son parent dans cameFrom
            
            // Utiliser getNeighbors(maze, current.x, current.y) pour obtenir les voisins
        }
        
        // 6. Si on arrive ici, aucun chemin n'a été trouvé
        long endTime = System.currentTimeMillis();
        return new MazeResult(null, visitedCount, endTime - startTime);
    }
    
    /**
     * Reconstruit le chemin depuis l'arrivée jusqu'au départ
     */
    private List<Position> reconstructPath(Map<Position, Position> cameFrom, Position end) {
        List<Position> path = new ArrayList<>();
        Position current = end;
        
        // TODO: Remonter le chemin depuis end jusqu'au départ
        // en suivant cameFrom
        
        // VOTRE CODE ICI
        
        // Inverser le chemin pour avoir départ -> arrivée
        Collections.reverse(path);
        return path;
    }
    
    /**
     * Retourne les voisins valides d'une position
     */
    private List<Position> getNeighbors(int[][] maze, int x, int y) {
        List<Position> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}}; // Haut, Droite, Bas, Gauche
        
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            
            // Vérifier que la position est dans les limites et n'est pas un mur
            if (isValid(maze, newX, newY)) {
                neighbors.add(new Position(newX, newY));
            }
        }
        
        return neighbors;
    }
    
    /**
     * Vérifie si une position est valide (dans les limites et pas un mur)
     */
    private boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && x < maze[0].length && 
               y >= 0 && y < maze.length && 
               maze[y][x] != 1; // 1 = mur
    }
}

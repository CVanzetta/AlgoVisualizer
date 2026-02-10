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
public class BFS extends AbstractMazeAlgorithm {
    
    @Override
    public String getName() {
        return "BFS (Breadth-First Search)";
    }
    
    @Override
    public MazeResult solve(int[][] maze, int startX, int startY, int endX, int endY) {
        long startTime = System.currentTimeMillis();
        
        // ========================================
        // À IMPLÉMENTER : Algorithme BFS
        // ========================================
        
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
            // ÉTAPE 1: Défiler la première position
            Position current = queue.poll();
            
            visitedCount++;
            
            // ÉTAPE 2: Vérifier si on a atteint l'arrivée
            if (current.x == endX && current.y == endY) {
                return new MazeResult(reconstructPath(cameFrom, current), visitedCount, System.currentTimeMillis() - startTime);
            }
            
            // ÉTAPE 3: Explorer les 4 voisins (haut, bas, gauche, droite)
            List<Position> neighbors = getNeighbors(maze, current.x, current.y);
            for (Position neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        
        // 6. Si on arrive ici, aucun chemin n'a été trouvé
        long endTime = System.currentTimeMillis();
        return new MazeResult(null, visitedCount, endTime - startTime);
    }
}

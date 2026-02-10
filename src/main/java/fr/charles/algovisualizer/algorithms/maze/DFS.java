package fr.charles.algovisualizer.algorithms.maze;

import java.util.*;

/**
 * Algorithme DFS (Depth-First Search) pour la résolution de labyrinthe
 * 
 * ========================================
 * PRINCIPE DU DFS
 * ========================================
 * Le DFS explore en profondeur avant d'explorer en largeur.
 * Il suit un chemin jusqu'au bout avant de revenir en arrière (backtrack).
 * 
 * Utilise une pile (Stack) LIFO : Last In, First Out
 * 
 * AVANTAGES :
 * - Utilise moins de mémoire que BFS
 * - Simple à implémenter (version récursive ou itérative)
 * 
 * INCONVÉNIENTS :
 * - Ne garantit PAS le chemin le plus court
 * - Peut être très lent sur certains labyrinthes
 * 
 * ========================================
 * À IMPLÉMENTER
 * ========================================
 */
public class DFS extends AbstractMazeAlgorithm {
    
    @Override
    public String getName() {
        return "DFS (Depth-First Search)";
    }
    
    @Override
    public MazeResult solve(int[][] maze, int startX, int startY, int endX, int endY) {
        long startTime = System.currentTimeMillis();
        
        // ========================================
        // À IMPLÉMENTER : Algorithme DFS
        // ========================================
        
        // 1. Créer une pile (Deque) pour stocker les positions à explorer
        Deque<Position> stack = new ArrayDeque<>();
        
        // 2. Créer un Set pour marquer les positions visitées
        Set<Position> visited = new HashSet<>();
        
        // 3. Créer une Map pour reconstruire le chemin
        Map<Position, Position> cameFrom = new HashMap<>();
        
        // 4. Ajouter la position de départ à la pile
        Position start = new Position(startX, startY);
        stack.push(start);
        visited.add(start);
        
        int visitedCount = 0;
        
        // 5. Tant que la pile n'est pas vide
        while (!stack.isEmpty()) {
            // ÉTAPE 1: Dépiler la dernière position (LIFO au lieu de FIFO comme BFS)
            Position current = stack.pop();
            
            visitedCount++;
            
            // ÉTAPE 2: Vérifier si on a atteint l'arrivée
            if (current.x == endX && current.y == endY) {
                return new MazeResult(reconstructPath(cameFrom, current), visitedCount, System.currentTimeMillis() - startTime);
            }
            
            // ÉTAPE 3: Explorer les 4 voisins
            List<Position> neighbors = getNeighbors(maze, current.x, current.y);
            for (Position neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    stack.push(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        
        // Aucun chemin trouvé
        long endTime = System.currentTimeMillis();
        return new MazeResult(null, visitedCount, endTime - startTime);
    }
}

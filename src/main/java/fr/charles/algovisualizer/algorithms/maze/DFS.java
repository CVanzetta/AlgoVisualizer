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
public class DFS implements MazeAlgorithm {
    
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
    
    /**
     * Reconstruit le chemin depuis l'arrivée jusqu'au départ
     */
    @SuppressWarnings("unused")
    private List<Position> reconstructPath(Map<Position, Position> cameFrom, Position end) {
        List<Position> path = new ArrayList<>();
        Position current = end;
        
        while (current != null && cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        
        Collections.reverse(path);
        return path;
    }
    
    /**
     * Retourne les voisins valides
     */
    @SuppressWarnings("unused")
    private List<Position> getNeighbors(int[][] maze, int x, int y) {
        List<Position> neighbors = new ArrayList<>();
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            
            if (isValid(maze, newX, newY)) {
                neighbors.add(new Position(newX, newY));
            }
        }
        
        return neighbors;
    }
    
    private boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && x < maze[0].length && 
               y >= 0 && y < maze.length && 
               maze[y][x] != 1;
    }
}

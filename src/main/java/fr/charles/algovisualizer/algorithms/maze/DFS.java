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
        
        // TODO: Implémenter l'algorithme DFS ici
        
        // 1. Créer une pile (Stack) pour stocker les positions à explorer
        Stack<Position> stack = new Stack<>();
        
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
            // TODO: Dépiler la dernière position (au lieu de défiler comme BFS)
            Position current = null; // À COMPLÉTER
            
            visitedCount++;
            
            // TODO: Vérifier si on a atteint l'arrivée
            
            // TODO: Explorer les 4 voisins
            // Pour chaque voisin valide et non visité:
            //   - Le marquer comme visité
            //   - L'ajouter à la pile (au lieu de la file comme BFS)
            //   - Enregistrer son parent
            
            // VOTRE CODE ICI
        }
        
        // Aucun chemin trouvé
        long endTime = System.currentTimeMillis();
        return new MazeResult(null, visitedCount, endTime - startTime);
    }
    
    /**
     * Reconstruit le chemin
     */
    private List<Position> reconstructPath(Map<Position, Position> cameFrom, Position end) {
        List<Position> path = new ArrayList<>();
        
        // TODO: Implémenter
        // VOTRE CODE ICI
        
        Collections.reverse(path);
        return path;
    }
    
    /**
     * Retourne les voisins valides
     */
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

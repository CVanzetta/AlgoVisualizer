package fr.charles.algovisualizer.algorithms.maze;

import java.util.*;

/**
 * Algorithme A* (A-Star) pour la résolution de labyrinthe
 * 
 * ========================================
 * PRINCIPE DE A*
 * ========================================
 * A* utilise une fonction f(n) = g(n) + h(n) où :
 * - g(n) = coût réel du chemin depuis le départ jusqu'à n
 * - h(n) = estimation heuristique du coût de n jusqu'à l'arrivée
 * 
 * L'heuristique utilisée est généralement la distance de Manhattan :
 * h(n) = |x_current - x_goal| + |y_current - y_goal|
 * 
 * AVANTAGES :
 * - Garantit le chemin optimal si l'heuristique est admissible
 * - Plus rapide que Dijkstra grâce à l'heuristique
 * - Très utilisé dans les jeux vidéos et la robotique
 * 
 * INCONVÉNIENTS :
 * - Plus complexe à implémenter que BFS
 * - Nécessite une bonne heuristique
 * 
 * ========================================
 * À IMPLÉMENTER
 * ========================================
 */
public class AStar implements MazeAlgorithm {
    
    @Override
    public String getName() {
        return "A* (A-Star)";
    }
    
    @Override
    public MazeResult solve(int[][] maze, int startX, int startY, int endX, int endY) {
        long startTime = System.currentTimeMillis();
        
        // ========================================
        // À IMPLÉMENTER : Algorithme A*
        // ========================================
        
        // 1. Créer une PriorityQueue triée par f_score
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        
        // 2. Créer un Set pour les nœuds fermés (déjà traités)
        Set<Position> closedSet = new HashSet<>();
        
        // 3. Créer des Maps pour g_score, f_score et parent
        Map<Position, Double> gScore = new HashMap<>();
        Map<Position, Double> fScore = new HashMap<>();
        Map<Position, Position> cameFrom = new HashMap<>();
        
        // 4. Initialiser le départ
        Position start = new Position(startX, startY);
        
        gScore.put(start, 0.0);
        fScore.put(start, heuristic(startX, startY, endX, endY));
        openSet.offer(new Node(start, fScore.get(start)));
        
        int visitedCount = 0;
        
        // 5. Tant que openSet n'est pas vide
        while (!openSet.isEmpty()) {
            // ÉTAPE 1: Extraire le nœud avec le plus petit f_score
            Node currentNode = openSet.poll();
            Position current = currentNode.position;
            
            visitedCount++;
            
            // ÉTAPE 2: Vérifier si on a atteint l'arrivée
            if (current.x == endX && current.y == endY) {
                return new MazeResult(reconstructPath(cameFrom, current), visitedCount, System.currentTimeMillis() - startTime);
            }
            
            // ÉTAPE 3: Marquer comme traité
            closedSet.add(current);
            
            // ÉTAPE 4: Pour chaque voisin
            List<Position> neighbors = getNeighbors(maze, current.x, current.y);
            for (Position neighbor : neighbors) {
                if (closedSet.contains(neighbor)) continue;
                
                double tentativeGScore = gScore.get(current) + 1;
                
                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    double fScoreValue = tentativeGScore + heuristic(neighbor.x, neighbor.y, endX, endY);
                    fScore.put(neighbor, fScoreValue);
                    openSet.offer(new Node(neighbor, fScoreValue));
                }
            }
        }
        
        // Aucun chemin trouvé
        long endTime = System.currentTimeMillis();
        return new MazeResult(null, visitedCount, endTime - startTime);
    }
    
    /**
     * Fonction heuristique : distance de Manhattan
     * h(n) = |x1 - x2| + |y1 - y2|
     */
    private double heuristic(int x1, int y1, int x2, int y2) {
        return (double) Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
    /**
     * Classe interne pour représenter un nœud avec son f_score
     */
    private static class Node {
        Position position;
        double fScore;
        
        Node(Position position, double fScore) {
            this.position = position;
            this.fScore = fScore;
        }
    }
    
    /**
     * Reconstruit le chemin
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

package fr.charles.algovisualizer.algorithms.maze;

import java.util.*;

/**
 * Algorithme de Dijkstra pour la résolution de labyrinthe
 * 
 * ========================================
 * PRINCIPE DE DIJKSTRA
 * ========================================
 * Dijkstra est similaire à A* mais sans fonction heuristique.
 * Il explore les nœuds par ordre croissant de distance depuis le départ.
 * 
 * C'est essentiellement A* avec h(n) = 0
 * 
 * AVANTAGES :
 * - Garantit le chemin le plus court
 * - Fonctionne avec des coûts d'arêtes différents
 * - Algorithme de référence pour les plus courts chemins
 * 
 * INCONVÉNIENTS :
 * - Plus lent que A* (explore plus de nœuds)
 * - Utilise beaucoup de mémoire
 * 
 * ========================================
 * À IMPLÉMENTER
 * ========================================
 */
public class Dijkstra implements MazeAlgorithm {
    
    @Override
    public String getName() {
        return "Dijkstra";
    }
    
    @Override
    public MazeResult solve(int[][] maze, int startX, int startY, int endX, int endY) {
        long startTime = System.currentTimeMillis();
        
        // ========================================
        // À IMPLÉMENTER : Algorithme de Dijkstra
        // ========================================
        
        // 1. Créer une PriorityQueue triée par distance
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));
        
        // 2. Créer une Map pour les distances (initialisée à l'infini sauf départ = 0)
        Map<Position, Double> distance = new HashMap<>();
        
        // 3. Créer un Set pour les nœuds visités
        Set<Position> visited = new HashSet<>();
        
        // 4. Créer une Map pour reconstruire le chemin
        Map<Position, Position> cameFrom = new HashMap<>();
        
        // 5. Initialiser
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);
        
        distance.put(start, 0.0);
        pq.offer(new Node(start, 0.0));
        
        int visitedCount = 0;
        
        // 6. Tant que la priority queue n'est pas vide
        while (!pq.isEmpty()) {
            // ÉTAPE 1: Extraire le nœud avec la plus petite distance
            Node currentNode = pq.poll();
            Position current = currentNode.position;
            
            // ÉTAPE 2: Si déjà visité, skip
            if (visited.contains(current)) continue;
            
            visitedCount++;
            visited.add(current);
            
            // ÉTAPE 3: Vérifier si on a atteint l'arrivée
            if (current.x == endX && current.y == endY) {
                return new MazeResult(reconstructPath(cameFrom, current), visitedCount, System.currentTimeMillis() - startTime);
            }
            
            // ÉTAPE 4: Pour chaque voisin
            List<Position> neighbors = getNeighbors(maze, current.x, current.y);
            for (Position neighbor : neighbors) {
                if (visited.contains(neighbor)) continue;
                
                double newDistance = distance.get(current) + 1;
                
                if (!distance.containsKey(neighbor) || newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    cameFrom.put(neighbor, current);
                    pq.offer(new Node(neighbor, newDistance));
                }
            }
        }
        
        // Aucun chemin trouvé
        long endTime = System.currentTimeMillis();
        return new MazeResult(null, visitedCount, endTime - startTime);
    }
    
    /**
     * Classe interne pour représenter un nœud avec sa distance
     */
    private static class Node {
        Position position;
        double distance;
        
        Node(Position position, double distance) {
            this.position = position;
            this.distance = distance;
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

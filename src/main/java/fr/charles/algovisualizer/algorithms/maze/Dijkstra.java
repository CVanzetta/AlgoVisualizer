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
public class Dijkstra extends AbstractMazeAlgorithm {
    
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
}

package fr.charles.algovisualizer.algorithms.maze;

import java.util.List;

/**
 * Interface de base pour tous les algorithmes de résolution de labyrinthe
 * 
 * Chaque algorithme doit implémenter cette interface pour résoudre un labyrinthe
 * et retourner le chemin trouvé ainsi que les statistiques d'exécution.
 */
public interface MazeAlgorithm {
    
    /**
     * Résout le labyrinthe et retourne le résultat
     * 
     * @param maze La grille du labyrinthe (0 = vide, 1 = mur, 2 = départ, 3 = arrivée)
     * @param startX Position X du départ
     * @param startY Position Y du départ
     * @param endX Position X de l'arrivée
     * @param endY Position Y de l'arrivée
     * @return Le résultat contenant le chemin et les statistiques
     */
    MazeResult solve(int[][] maze, int startX, int startY, int endX, int endY);
    
    /**
     * Retourne le nom de l'algorithme
     */
    String getName();
    
    /**
     * Classe représentant une position dans le labyrinthe
     */
    class Position {
        public final int x;
        public final int y;
        
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }
        
        @Override
        public int hashCode() {
            return 31 * x + y;
        }
        
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    
    /**
     * Classe représentant le résultat d'un algorithme de labyrinthe
     */
    class MazeResult {
        private final List<Position> path;
        private final int visitedCount;
        private final long executionTime;
        private final boolean pathFound;
        
        public MazeResult(List<Position> path, int visitedCount, long executionTime) {
            this.path = path;
            this.visitedCount = visitedCount;
            this.executionTime = executionTime;
            this.pathFound = path != null && !path.isEmpty();
        }
        
        public List<Position> getPath() {
            return path;
        }
        
        public int getVisitedCount() {
            return visitedCount;
        }
        
        public long getExecutionTime() {
            return executionTime;
        }
        
        public boolean isPathFound() {
            return pathFound;
        }
        
        public int getPathLength() {
            return path != null ? path.size() : 0;
        }
    }
}

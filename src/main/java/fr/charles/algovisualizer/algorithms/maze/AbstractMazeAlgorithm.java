package fr.charles.algovisualizer.algorithms.maze;

import java.util.*;

/**
 * Classe abstraite de base pour les algorithmes de résolution de labyrinthe.
 * Factorise le code commun à tous les algorithmes (BFS, DFS, Dijkstra, A*).
 * 
 * ÉLÉMENTS FACTORISÉS :
 * - Reconstruction du chemin (reconstructPath)
 * - Recherche des voisins valides (getNeighbors)
 * - Validation des positions (isValid)
 * - Constante pour les directions cardinales
 * 
 * PRINCIPE DRY (Don't Repeat Yourself) :
 * Au lieu de dupliquer ces méthodes dans chaque algorithme,
 * on les définit une seule fois ici et tous les algorithmes en héritent.
 */
public abstract class AbstractMazeAlgorithm implements MazeAlgorithm {
    
    /** Directions cardinales : haut, droite, bas, gauche */
    protected static final int[][] DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    
    /** Valeur pour un mur dans le labyrinthe */
    protected static final int WALL = 1;
    
    /**
     * Reconstruit le chemin depuis l'arrivée jusqu'au départ.
     * 
     * Cette méthode remonte le chemin en suivant la Map cameFrom qui associe
     * chaque position à son parent (la position depuis laquelle on est arrivé).
     * 
     * @param cameFrom Map associant chaque position à son parent
     * @param end Position d'arrivée
     * @return Liste des positions formant le chemin (départ → arrivée)
     */
    protected List<Position> reconstructPath(Map<Position, Position> cameFrom, Position end) {
        List<Position> path = new ArrayList<>();
        Position current = end;
        
        // Remonter le chemin depuis end jusqu'au départ en suivant cameFrom
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        
        // Inverser le chemin pour avoir départ → arrivée
        Collections.reverse(path);
        return path;
    }
    
    /**
     * Retourne les voisins valides d'une position (haut, droite, bas, gauche).
     * 
     * Un voisin est valide s'il est dans les limites du labyrinthe et n'est pas un mur.
     * 
     * @param maze Grille du labyrinthe
     * @param x Position x courante
     * @param y Position y courante
     * @return Liste des positions voisines valides
     */
    protected List<Position> getNeighbors(int[][] maze, int x, int y) {
        List<Position> neighbors = new ArrayList<>();
        
        for (int[] dir : DIRECTIONS) {
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
     * Vérifie si une position est valide (dans les limites et pas un mur).
     * 
     * @param maze Grille du labyrinthe
     * @param x Position x à vérifier
     * @param y Position y à vérifier
     * @return true si la position est valide, false sinon
     */
    protected boolean isValid(int[][] maze, int x, int y) {
        return x >= 0 && x < maze[0].length && 
               y >= 0 && y < maze.length && 
               maze[y][x] != WALL;
    }
}

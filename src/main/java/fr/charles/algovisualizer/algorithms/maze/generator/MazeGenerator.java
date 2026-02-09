package fr.charles.algovisualizer.algorithms.maze.generator;

/**
 * Interface pour les algorithmes de génération de labyrinthe
 * 
 * Chaque algorithme de génération crée un labyrinthe unique avec différentes caractéristiques :
 * - Recursive Backtracking : labyrinthes parfaits avec de longs couloirs sinueux
 * - Prim's Algorithm : labyrinthes avec de nombreux embranchements courts
 * - Binary Tree : labyrinthes avec un biais (haut et droite)
 * - Kruskal's : labyrinthes uniformes avec distribution équilibrée
 */
public interface MazeGenerator {
    
    /**
     * Génère un labyrinthe
     * 
     * @param width Largeur du labyrinthe (nombre de colonnes)
     * @param height Hauteur du labyrinthe (nombre de lignes)
     * @return Grille 2D représentant le labyrinthe (0 = passage, 1 = mur)
     */
    int[][] generate(int width, int height);
    
    /**
     * Retourne le nom de l'algorithme de génération
     */
    String getName();
    
    /**
     * Retourne une description de l'algorithme
     */
    String getDescription();
}

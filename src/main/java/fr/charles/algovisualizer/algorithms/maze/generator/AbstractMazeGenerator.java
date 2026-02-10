package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.Arrays;
import java.util.Random;

/**
 * Classe abstraite de base pour les générateurs de labyrinthe.
 * Factorise le code commun à tous les générateurs pour réduire la duplication.
 * 
 * ÉLÉMENTS FACTORISÉS :
 * - Random generator (partagé entre tous les générateurs)
 * - Initialisation du maze avec des murs
 * - Directions cardinales (haut, droite, bas, gauche)
 * - Vérification des limites
 * - Calcul des cellules
 */
public abstract class AbstractMazeGenerator implements MazeGenerator {
    
    /** Générateur de nombres aléatoires partagé */
    protected final Random random = new Random();
    
    /** Directions cardinales : haut, droite, bas, gauche */
    protected static final int[][] DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    
    /** Valeur pour un passage */
    protected static final int EMPTY = 0;
    
    /** Valeur pour un mur */
    protected static final int WALL = 1;
    
    /**
     * Initialise un labyrinthe rempli de murs
     * 
     * @param width Largeur du labyrinthe
     * @param height Hauteur du labyrinthe
     * @return Grille 2D remplie de murs (1)
     */
    protected int[][] initializeMazeWithWalls(int width, int height) {
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], WALL);
        }
        return maze;
    }
    
    /**
     * Initialise un labyrinthe rempli de passages
     * 
     * @param width Largeur du labyrinthe
     * @param height Hauteur du labyrinthe
     * @return Grille 2D remplie de passages (0)
     */
    protected int[][] initializeMazeWithEmpty(int width, int height) {
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], EMPTY);
        }
        return maze;
    }
    
    /**
     * Vérifie si une position est dans les limites du labyrinthe
     * 
     * @param x Position x
     * @param y Position y
     * @param width Largeur du labyrinthe
     * @param height Hauteur du labyrinthe
     * @return true si la position est valide
     */
    protected boolean isInBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    /**
     * Calcule la largeur en cellules (pour les grilles à pas de 2)
     * 
     * @param width Largeur totale
     * @return Nombre de cellules en largeur
     */
    protected int getCellWidth(int width) {
        return (width - 1) / 2;
    }
    
    /**
     * Calcule la hauteur en cellules (pour les grilles à pas de 2)
     * 
     * @param height Hauteur totale
     * @return Nombre de cellules en hauteur
     */
    protected int getCellHeight(int height) {
        return (height - 1) / 2;
    }
    
    /**
     * Convertit une coordonnée de cellule en coordonnée de grille
     * 
     * @param cellCoord Coordonnée de cellule (0, 1, 2, ...)
     * @return Coordonnée de grille (1, 3, 5, ...)
     */
    protected int cellToGridCoord(int cellCoord) {
        return cellCoord * 2 + 1;
    }
    
    /**
     * Marque une cellule comme passage dans le maze
     * 
     * @param maze Grille du labyrinthe
     * @param x Position x de la cellule (coordonnées de grille)
     * @param y Position y de la cellule (coordonnées de grille)
     */
    protected void markCellAsEmpty(int[][] maze, int x, int y) {
        maze[y][x] = EMPTY;
    }
    
    /**
     * Marque un mur comme passage dans le maze
     * 
     * @param maze Grille du labyrinthe
     * @param x Position x du mur
     * @param y Position y du mur
     */
    protected void removeWall(int[][] maze, int x, int y) {
        maze[y][x] = EMPTY;
    }
}

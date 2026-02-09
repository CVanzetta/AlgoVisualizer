package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par Division Récursive
 * 
 * PRINCIPE (diviser pour régner - approche top-down) :
 * Contrairement aux autres algorithmes qui CREUSENT des passages dans des murs,
 * Recursive Division CONSTRUIT des murs dans un espace vide !
 * 
 * ALGORITHME :
 * 1. Commencer avec une pièce vide (pas de murs internes)
 * 2. Diviser la pièce en 4 quadrants en traçant 2 murs (horizontal et vertical)
 * 3. Choisir 3 des 4 intersections mur-mur et y faire un passage
 *    (laisser 1 intersection fermée crée des chemins alternatifs)
 * 4. Appliquer récursivement sur chaque quadrant
 * 5. S'arrêter quand les pièces sont trop petites
 * 
 * EXEMPLE ASCII :
 * 
 * Étape 1: [Grande pièce vide]
 * 
 * Étape 2: Division
 * ┌─────┬─────┐
 * │  A  │  B  │
 * ├─────┼─────┤
 * │  C  │  D  │
 * └─────┴─────┘
 * 
 * Étape 3: Passages (exemple: portes en A-B, A-C, B-D)
 * ┌─────○─────┐
 * │  A  │  B  │
 * ○─────█─────○
 * │  C  │  D  │
 * └─────┴─────┘
 * 
 * Étape 4: Récursion sur A, B, C, D
 * 
 * ORIENTATION :
 * - Pièce large : diviser verticalement
 * - Pièce haute : diviser horizontalement
 * - Pièce carrée : choisir aléatoirement
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait" (une seule solution)
 * - Structure très géométrique et régulière
 * - Longs couloirs droits
 * - Motifs visibles (quadrants emboîtés)
 * - Prévisible mais esthétique
 * 
 * AVANTAGES :
 * - Visuel très distinct et reconnaissable
 * - Excellent pour les jeux (salles avec couloirs)
 * - Facile à implémenter
 * - Génération rapide
 * - Structure hiérarchique claire
 * 
 * DÉSAVANTAGES :
 * - Trop prévisible (motif de division visible)
 * - Manque d'aspect "organique"
 * - Facile à résoudre (structure géométrique)
 * - Pas réaliste pour un vrai labyrinthe
 * 
 * USAGE : Cartes de jeux, donjons RPG, niveaux procéduraux avec structure
 */
public class RecursiveDivisionGenerator implements MazeGenerator {
    
    private Random random = new Random();
    
    @Override
    public String getName() {
        return "Recursive Division";
    }
    
    @Override
    public String getDescription() {
        return "Divise l'espace avec des murs (approche top-down, structure géométrique)";
    }
    
    @Override
    public int[][] generate(int width, int height) {
        // Commencer avec toutes les cellules comme des PASSAGES
        int[][] maze = new int[height][width];
        // Tout est vide au départ
        
        // Créer les murs extérieurs
        for (int x = 0; x < width; x++) {
            maze[0][x] = 1;
            maze[height - 1][x] = 1;
        }
        for (int y = 0; y < height; y++) {
            maze[y][0] = 1;
            maze[y][width - 1] = 1;
        }
        
        // Lancer la division récursive
        divide(maze, 1, 1, width - 2, height - 2);
        
        return maze;
    }
    
    /**
     * Divise récursivement une chambre en 4 quadrants
     * 
     * @param maze La grille du labyrinthe
     * @param x Position X du coin supérieur gauche de la chambre
     * @param y Position Y du coin supérieur gauche de la chambre
     * @param w Largeur de la chambre
     * @param h Hauteur de la chambre
     */
    private void divide(int[][] maze, int x, int y, int w, int h) {
        // Condition d'arrêt : chambre trop petite
        if (w < 2 || h < 2) {
            return;
        }
        
        // Déterminer l'orientation de la division
        boolean horizontal;
        
        if (w > h) {
            // Chambre large : division verticale
            horizontal = false;
        } else if (h > w) {
            // Chambre haute : division horizontale
            horizontal = true;
        } else {
            // Chambre carrée : choisir aléatoirement
            horizontal = random.nextBoolean();
        }
        
        if (horizontal) {
            // Division horizontale
            divideHorizontally(maze, x, y, w, h);
        } else {
            // Division verticale
            divideVertically(maze, x, y, w, h);
        }
    }
    
    /**
     * Divise horizontalement une chambre
     */
    private void divideHorizontally(int[][] maze, int x, int y, int w, int h) {
        // Choisir où placer le mur horizontal (position paire pour la grille)
        int wallY = y + 2 * random.nextInt((h - 1) / 2 + 1);
        if (wallY >= y + h) wallY = y + h - 1;
        
        // Tracer le mur horizontal
        for (int i = x; i < x + w; i++) {
            maze[wallY][i] = 1;
        }
        
        // Choisir une position pour le passage (position impaire)
        int passageX = x + 2 * random.nextInt((w + 1) / 2);
        if (passageX >= x + w) passageX = x + w - 1;
        maze[wallY][passageX] = 0;
        
        // Diviser récursivement les deux chambres créées
        divide(maze, x, y, w, wallY - y);           // Chambre du haut
        divide(maze, x, wallY + 1, w, h - (wallY - y) - 1); // Chambre du bas
    }
    
    /**
     * Divise verticalement une chambre
     */
    private void divideVertically(int[][] maze, int x, int y, int w, int h) {
        // Choisir où placer le mur vertical (position paire pour la grille)
        int wallX = x + 2 * random.nextInt((w - 1) / 2 + 1);
        if (wallX >= x + w) wallX = x + w - 1;
        
        // Tracer le mur vertical
        for (int i = y; i < y + h; i++) {
            maze[i][wallX] = 1;
        }
        
        // Choisir une position pour le passage (position impaire)
        int passageY = y + 2 * random.nextInt((h + 1) / 2);
        if (passageY >= y + h) passageY = y + h - 1;
        maze[passageY][wallX] = 0;
        
        // Diviser récursivement les deux chambres créées
        divide(maze, x, y, wallX - x, h);           // Chambre de gauche
        divide(maze, wallX + 1, y, w - (wallX - x) - 1, h); // Chambre de droite
    }
}

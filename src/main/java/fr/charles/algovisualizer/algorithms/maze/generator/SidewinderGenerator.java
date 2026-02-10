package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme de Sidewinder
 * 
 * PRINCIPE (traitement ligne par ligne) :
 * Sidewinder est une variante de Binary Tree qui réduit le biais :
 * 
 * 1. Traiter chaque ligne de haut en bas
 * 2. Pour chaque cellule de la ligne (de gauche à droite) :
 *    - Créer un "run" (séquence de cellules connectées horizontalement)
 *    - Lancer une pièce : pile ou face ?
 *      * PILE : Continuer le run vers l'est (supprimer le mur à droite)
 *      * FACE : Fermer le run et creuser vers le nord depuis une cellule aléatoire du run
 *    - Exception ligne du haut : toujours aller vers l'est
 *    - Exception colonne de droite : toujours aller vers le nord
 * 
 * EXEMPLE :
 * Ligne 1: A-B-C-D  (tout connecté horizontalement)
 *          |
 * Ligne 2: E F G-H  (E↑, F solo↑, G-H↑)
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait" (une seule solution)
 * - Biais vers le nord et l'est (mais moins fort que Binary Tree)
 * - Couloir complet en haut (toute la ligne supérieure est ouverte)
 * - Structure prévisible
 * 
 * AVANTAGES :
 * - Extrêmement rapide (O(n), une seule passe)
 * - Très simple à implémenter
 * - Pas de récursion, pas de structures de données complexes
 * - Génération en "streaming" (ligne par ligne)
 * - Meilleur que Binary Tree (moins de biais)
 * 
 * DÉSAVANTAGES :
 * - Biais directionnel visible (nord-est favorisé)
 * - Facile à résoudre (suivre le mur nord ou est)
 * - Pas de véritable "labyrinthe" complexe
 * - Ligne du haut toujours complètement ouverte
 * 
 * USAGE : Tests rapides, génération en temps réel, algorithmes pédagogiques
 */
<<<<<<< HEAD
public class SidewinderGenerator extends AbstractMazeGenerator {
=======
public class SidewinderGenerator implements MazeGenerator {
    
    private Random random = new Random();
>>>>>>> dev
    
    @Override
    public String getName() {
        return "Sidewinder Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Traitement ligne par ligne avec runs horizontaux (rapide, biais nord-est)";
    }
    
    @Override
    @SuppressWarnings("java:S3776")
    public int[][] generate(int width, int height) {
<<<<<<< HEAD
        int[][] maze = initializeMazeWithWalls(width, height);
        
        // Grille de cellules
        int cellWidth = getCellWidth(width);
        int cellHeight = getCellHeight(height);
=======
        // Initialiser toutes les cellules comme des murs
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], 1);
        }
        
        // Grille de cellules
        int cellWidth = (width - 1) / 2;
        int cellHeight = (height - 1) / 2;
>>>>>>> dev
        
        // Marquer les positions de cellules comme des passages
        for (int y = 0; y < cellHeight; y++) {
            for (int x = 0; x < cellWidth; x++) {
                maze[y * 2 + 1][x * 2 + 1] = 0;
            }
        }
        
        // Traiter chaque ligne de haut en bas
        for (int y = 0; y < cellHeight; y++) {
            // Liste des cellules du "run" actuel
            List<Integer> runCells = new ArrayList<>();
            
            // Traiter chaque cellule de la ligne
            for (int x = 0; x < cellWidth; x++) {
                runCells.add(x);
                
                // Décider : continuer à l'est ou fermer le run et aller au nord ?
                boolean shouldCloseRun;
                
                // Cas spéciaux :
                if (y == 0) {
                    // Ligne du haut : toujours continuer à l'est
                    shouldCloseRun = false;
                } else if (x == cellWidth - 1) {
                    // Dernière colonne : toujours fermer le run (aller au nord)
                    shouldCloseRun = true;
                } else {
                    // Cas normal : 50% de chance de fermer le run
                    shouldCloseRun = random.nextBoolean();
                }
                
                if (shouldCloseRun) {
                    // Fermer le run : choisir une cellule aléatoire du run et creuser au nord
                    int runCell = runCells.get(random.nextInt(runCells.size()));
                    
                    // Supprimer le mur au nord de cette cellule
                    int wallX = runCell * 2 + 1;
                    int wallY = y * 2 + 1 - 1; // Nord
                    maze[wallY][wallX] = 0;
                    
                    // Vider le run
                    runCells.clear();
                } else if (x < cellWidth - 1) {
                    // Continuer le run vers l'est : supprimer le mur à droite
                    int wallX = x * 2 + 1 + 1; // Est
                    int wallY = y * 2 + 1;
                    maze[wallY][wallX] = 0;
                }
            }
        }
        
        return maze;
    }
}

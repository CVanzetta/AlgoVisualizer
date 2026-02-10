package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme d'Eller
 * 
 * PRINCIPE (génération ligne par ligne avec ensembles) :
 * Eller's est le SEUL algorithme qui peut générer un labyrinthe infini en mémoire O(largeur) !
 * 
 * ÉTAPES POUR CHAQUE LIGNE :
 * 1. INITIALISATION : Assigner chaque cellule sans ensemble à un nouvel ensemble
 * 
 * 2. JONCTIONS HORIZONTALES (Est) :
 *    - Pour chaque cellule et sa voisine de droite :
 *      * Si dans des ensembles différents :
 *        - Décider aléatoirement de les fusionner ou non
 *        - Si oui : supprimer le mur et fusionner les ensembles
 * 
 * 3. JONCTIONS VERTICALES (Sud) :
 *    - Pour chaque ensemble de la ligne :
 *      * Au moins UN passage vers le sud (garantir la connexion)
 *      * Pour chaque cellule, décider aléatoirement de creuser vers le sud
 *      * Les cellules qui descendent conservent leur ensemble
 * 
 * 4. LIGNE SUIVANTE :
 *    - Les cellules ayant un passage au-dessus gardent leur ensemble
 *    - Les autres reçoivent de nouveaux ensembles
 * 
 * 5. DERNIÈRE LIGNE : Fusionner tous les ensembles restants
 * 
 * EXEMPLE :
 * Ligne 1: [A] [A] [B]    (A-A même ensemble, B séparé)
 * Descente: ↓        ↓    (A descend 1 fois, B descend)
 * Ligne 2: [A] [C] [B]    (nouveau ensemble C)
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait"
 * - Génération ligne par ligne (streaming)
 * - Labyrinthes uniformes et équilibrés
 * - Le plus efficace en mémoire
 * 
 * AVANTAGES :
 * - Mémoire O(largeur) au lieu de O(largeur × hauteur)
 * - Peut générer des labyrinthes INFINIS
 * - Rapide : O(largeur × hauteur)
 * - Pas de récursion
 * - Génération en streaming (pour des jeux procéduraux)
 * 
 * DÉSAVANTAGES :
 * - Le plus complexe à implémenter correctement
 * - Nécessite une structure Union-Find par ligne
 * - Difficile à visualiser pendant la génération
 * - Bugs subtils si mal implémenté
 * 
 * USAGE : Génération de mondes infinis, roguelikes, jeux procéduraux
 */
public class EllerGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Eller's Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Génération ligne par ligne en O(width) mémoire (peut créer des labyrinthes infinis)";
    }
    
    @Override
    @SuppressWarnings({"java:S3776", "java:S1541", "java:S6541"})
    public int[][] generate(int width, int height) {
        // Initialiser le labyrinthe avec espacement de 2
        int[][] maze = initializeMazeWithSpacedCells(width, height);
        
        // Grille de cellules
        int cellWidth = getCellWidth(width);
        int cellHeight = getCellHeight(height);
        
        // Tableau des ensembles pour la ligne actuelle
        int[] sets = new int[cellWidth];
        int nextSet = 1;
        
        // Générer chaque ligne
        for (int row = 0; row < cellHeight; row++) {
            // ÉTAPE 1 : Initialiser les cellules sans ensemble
            for (int x = 0; x < cellWidth; x++) {
                if (sets[x] == 0) {
                    sets[x] = nextSet++;
                }
            }
            
            // ÉTAPE 2 : Jonctions horizontales (Est)
            for (int x = 0; x < cellWidth - 1; x++) {
                // Décider de fusionner avec le voisin de droite
                boolean shouldMerge = random.nextBoolean();
                
                // Ne fusionner que si dans des ensembles différents
                if (shouldMerge && sets[x] != sets[x + 1]) {
                    // Supprimer le mur à l'est
                    int wallX = cellToGridCoord(x) + 1;
                    int wallY = cellToGridCoord(row);
                    maze[wallY][wallX] = 0;
                    
                    // Fusionner les ensembles
                    int oldSet = sets[x + 1];
                    int newSet = sets[x];
                    for (int i = 0; i < cellWidth; i++) {
                        if (sets[i] == oldSet) {
                            sets[i] = newSet;
                        }
                    }
                }
            }
            
            // ÉTAPE 3 : Jonctions verticales (Sud)
            if (row < cellHeight - 1) {
                // Pour chaque ensemble, garantir au moins une sortie vers le sud
                Map<Integer, List<Integer>> setMembers = new HashMap<>();
                
                for (int x = 0; x < cellWidth; x++) {
                    setMembers.computeIfAbsent(sets[x], k -> new ArrayList<>()).add(x);
                }
                
                // Nouvelle ligne : réinitialiser les ensembles
                int[] newSets = new int[cellWidth];
                
                for (List<Integer> members : setMembers.values()) {
                    // Au moins une connexion vers le sud par ensemble
                    boolean hasConnection = false;
                    
                    for (int x : members) {
                        // Décider de créer une connexion vers le sud
                        boolean shouldConnect = random.nextBoolean() || !hasConnection;
                        
                        if (shouldConnect) {
                            // Supprimer le mur au sud
                            int wallX = x * 2 + 1;
                            int wallY = row * 2 + 1 + 1;
                            maze[wallY][wallX] = 0;
                            
                            // Cette cellule conserve son ensemble en descendant
                            newSets[x] = sets[x];
                            hasConnection = true;
                        }
                    }
                }
                
                // Appliquer les nouveaux ensembles
                sets = newSets;
            } else {
                // DERNIÈRE LIGNE : Fusionner tous les ensembles restants
                for (int x = 0; x < cellWidth - 1; x++) {
                    if (sets[x] != sets[x + 1]) {
                        // Supprimer le mur
                        int wallX = cellToGridCoord(x) + 1;
                        int wallY = cellToGridCoord(row);
                        maze[wallY][wallX] = 0;
                        
                        // Fusionner les ensembles
                        int oldSet = sets[x + 1];
                        int newSet = sets[x];
                        for (int i = 0; i < cellWidth; i++) {
                            if (sets[i] == oldSet) {
                                sets[i] = newSet;
                            }
                        }
                    }
                }
            }
        }
        
        return maze;
    }
}

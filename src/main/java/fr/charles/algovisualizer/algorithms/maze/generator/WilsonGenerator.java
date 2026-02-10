package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme de Wilson
 * 
 * PRINCIPE (marche aléatoire avec effacement de boucles) :
 * 1. Choisir une cellule de départ et la marquer comme faisant partie du labyrinthe
 * 2. Répéter jusqu'à ce que toutes les cellules soient dans le labyrinthe :
 *    - Choisir une cellule aléatoire non encore dans le labyrinthe
 *    - Effectuer une marche aléatoire jusqu'à atteindre le labyrinthe
 *    - Si on revient sur ses pas, EFFACER la boucle
 *    - Une fois le labyrinthe atteint, ajouter tout le chemin au labyrinthe
 * 
 * EXEMPLE DE MARCHE AVEC EFFACEMENT :
 * Chemin : A → B → C → D → B → E
 * Quand on atteint B la 2ème fois, on efface C → D → B
 * Résultat : A → B → E
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait" (un seul chemin entre deux points)
 * - Distribution uniforme GARANTIE (comme Aldous-Broder)
 * - Pas de biais directionnel
 * - Plus rapide qu'Aldous-Broder en pratique
 * 
 * AVANTAGES :
 * - Distribution mathématiquement uniforme
 * - Plus rapide qu'Aldous-Broder (O(n log n) moyen)
 * - Pas de récursion
 * - Visuellement intéressant (on voit les chemins se former)
 * 
 * DÉSAVANTAGES :
 * - Plus complexe à implémenter (gestion des boucles)
 * - Les premières marches peuvent être longues
 * - Nécessite de mémoriser les chemins
 */
public class WilsonGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Wilson's Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Marche aléatoire avec effacement de boucles (uniforme et plus rapide qu'Aldous-Broder)";
    }
    
    @Override
    @SuppressWarnings({"java:S3776", "java:S1541", "java:S6541"})
    public int[][] generate(int width, int height) {
        int[][] maze = initializeMazeWithWalls(width, height);
        
        // Grille de cellules
        int cellWidth = getCellWidth(width);
        int cellHeight = getCellHeight(height);
        
        // Marquer toutes les positions de cellules comme des passages
        for (int y = 0; y < cellHeight; y++) {
            for (int x = 0; x < cellWidth; x++) {
                maze[y * 2 + 1][x * 2 + 1] = 0;
            }
        }
        
        // Tableau pour suivre les cellules dans le labyrinthe
        boolean[][] inMaze = new boolean[cellHeight][cellWidth];
        int cellsInMaze = 0;
        int totalCells = cellWidth * cellHeight;
        
        // Commencer avec une cellule aléatoire dans le labyrinthe
        int startX = random.nextInt(cellWidth);
        int startY = random.nextInt(cellHeight);
        inMaze[startY][startX] = true;
        cellsInMaze++;
        
        // Directions : haut, droite, bas, gauche
        int[][] directions = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        
        // Continuer jusqu'à avoir toutes les cellules dans le labyrinthe
        while (cellsInMaze < totalCells) {
            // Choisir une cellule aléatoire pas encore dans le labyrinthe
            int currentX;
            int currentY;
            do {
                currentX = random.nextInt(cellWidth);
                currentY = random.nextInt(cellHeight);
            } while (inMaze[currentY][currentX]);
            
            // Effectuer une marche aléatoire jusqu'à atteindre le labyrinthe
            // path[y][x] stocke la direction prise depuis cette cellule
            int[][] path = new int[cellHeight][cellWidth];
            for (int y = 0; y < cellHeight; y++) {
                Arrays.fill(path[y], -1);
            }
            
            // Marcher jusqu'à atteindre une cellule du labyrinthe
            while (!inMaze[currentY][currentX]) {
                // Choisir une direction aléatoire
                int dir = random.nextInt(4);
                int nextX = currentX + directions[dir][0];
                int nextY = currentY + directions[dir][1];
                
                // Vérifier les limites
                if (nextX >= 0 && nextX < cellWidth && nextY >= 0 && nextY < cellHeight) {
                    // Enregistrer la direction (efface les boucles automatiquement)
                    path[currentY][currentX] = dir;
                    currentX = nextX;
                    currentY = nextY;
                }
            }
            
            // Rejouer le chemin et l'ajouter au labyrinthe
            currentX = -1;
            currentY = -1;
            
            // Trouver le point de départ du chemin
            for (int y = 0; y < cellHeight; y++) {
                for (int x = 0; x < cellWidth; x++) {
                    if (path[y][x] != -1 && !inMaze[y][x]) {
                        // Vérifier si c'est bien un point de départ (pas référencé par d'autres)
                        boolean isStart = true;
                        for (int ny = 0; ny < cellHeight; ny++) {
                            for (int nx = 0; nx < cellWidth; nx++) {
                                if (path[ny][nx] != -1) {
                                    int testX = nx + directions[path[ny][nx]][0];
                                    int testY = ny + directions[path[ny][nx]][1];
                                    if (testX == x && testY == y && !inMaze[ny][nx]) {
                                        isStart = false;
                                        break;
                                    }
                                }
                            }
                            if (!isStart) break;
                        }
                        if (isStart) {
                            currentX = x;
                            currentY = y;
                            break;
                        }
                    }
                }
                if (currentX != -1) break;
            }
            
            // Suivre le chemin et supprimer les murs
            while (currentX != -1 && currentY != -1 && path[currentY][currentX] != -1) {
                int dir = path[currentY][currentX];
                int nextX = currentX + directions[dir][0];
                int nextY = currentY + directions[dir][1];
                
                // Supprimer le mur
                int wallX = currentX * 2 + 1 + directions[dir][0];
                int wallY = currentY * 2 + 1 + directions[dir][1];
                maze[wallY][wallX] = 0;
                
                // Marquer la cellule comme dans le labyrinthe
                inMaze[currentY][currentX] = true;
                cellsInMaze++;
                
                currentX = nextX;
                currentY = nextY;
            }
        }
        
        return maze;
    }
}

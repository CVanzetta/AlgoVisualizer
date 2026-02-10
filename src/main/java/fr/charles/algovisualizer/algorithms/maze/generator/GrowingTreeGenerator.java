package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme Growing Tree
 * 
 * PRINCIPE (généralisation de plusieurs algorithmes) :
 * Growing Tree est un FRAMEWORK qui unifie plusieurs algorithmes selon la façon
 * dont on choisit la prochaine cellule dans une liste :
 * 
 * 1. Commencer avec une cellule dans une liste
 * 2. Répéter jusqu'à ce que la liste soit vide :
 *    - CHOIX CRUCIAL : Sélectionner une cellule de la liste selon une STRATÉGIE
 *    - Si elle a des voisins non visités :
 *      * Choisir un voisin aléatoire
 *      * Supprimer le mur
 *      * Ajouter le voisin à la liste
 *    - Sinon : Retirer la cellule de la liste
 * 
 * STRATÉGIES DE SÉLECTION :
 * - Newest (dernière ajoutée) → Recursive Backtracker (DFS)
 * - Oldest (première ajoutée) → Simplified Prim (BFS)
 * - Random → Mix unique entre BFS et DFS
 * - Mix (50% newest, 50% random) → Labyrinthes équilibrés
 * 
 * IMPLÉMENTATION ICI : Stratégie MIXED (recommandée)
 * - 50% de chance de prendre la plus récente (DFS-like)
 * - 50% de chance de prendre une cellule aléatoire (exploration)
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait"
 * - Très flexible (change selon la stratégie)
 * - Balance entre longs couloirs et embranchements
 * 
 * AVANTAGES :
 * - Extrêmement flexible et paramétrable
 * - Permet de créer des variations infinies
 * - Pas de récursion
 * - Performance similaire à Recursive Backtracker
 * 
 * DÉSAVANTAGES :
 * - Plus complexe conceptuellement
 * - Nécessite de maintenir une liste active
 */
public class GrowingTreeGenerator extends AbstractMazeGenerator {
    
    // Stratégie : probabilité de choisir la cellule la plus récente (0.0 à 1.0)
    // 1.0 = toujours la plus récente (Recursive Backtracker)
    // 0.0 = toujours aléatoire
    // 0.5 = mix équilibré (recommandé)
    private static final double NEWEST_PROBABILITY = 0.5;
    
    @Override
    public String getName() {
        return "Growing Tree Algorithm (Mixed)";
    }
    
    @Override
    public String getDescription() {
        return "Framework flexible (50% newest, 50% random) - balance DFS et exploration";
    }
    
    @Override
    @SuppressWarnings("java:S3776")
    public int[][] generate(int width, int height) {
        // Initialiser le labyrinthe avec espacement de 2
        int[][] maze = initializeMazeWithSpacedCells(width, height);
        
        // Grille de cellules
        int cellWidth = getCellWidth(width);
        int cellHeight = getCellHeight(height);
        
        // Tableau des cellules visitées
        boolean[][] visited = new boolean[cellHeight][cellWidth];
        
        // Liste des cellules actives (Growing Tree)
        List<Cell> activeCells = new ArrayList<>();
        
        // Commencer avec une cellule aléatoire
        int startX = random.nextInt(cellWidth);
        int startY = random.nextInt(cellHeight);
        visited[startY][startX] = true;
        activeCells.add(new Cell(startX, startY));
        
        // Continuer tant qu'il y a des cellules actives
        while (!activeCells.isEmpty()) {
            // STRATÉGIE DE SÉLECTION : Mixed (50% newest, 50% random)
            int index;
            if (random.nextDouble() < NEWEST_PROBABILITY) {
                // Choisir la plus récente (dernière de la liste)
                index = activeCells.size() - 1;
            } else {
                // Choisir une cellule aléatoire
                index = random.nextInt(activeCells.size());
            }
            
            Cell current = activeCells.get(index);
            
            // Trouver les voisins non visités
            List<int[]> unvisitedNeighbors = new ArrayList<>();
            
            for (int[] dir : DIRECTIONS) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];
                
                if (nx >= 0 && nx < cellWidth && ny >= 0 && ny < cellHeight && !visited[ny][nx]) {
                    unvisitedNeighbors.add(new int[]{nx, ny, dir[0], dir[1]});
                }
            }
            
            if (!unvisitedNeighbors.isEmpty()) {
                // Choisir un voisin aléatoire
                int[] neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
                int nx = neighbor[0];
                int ny = neighbor[1];
                int dx = neighbor[2];
                int dy = neighbor[3];
                
                // Supprimer le mur entre les deux cellules
                int[] wall = getWallPosition(current.x, current.y, dx, dy);
                maze[wall[1]][wall[0]] = 0;
                
                // Marquer le voisin comme visité et l'ajouter à la liste
                visited[ny][nx] = true;
                activeCells.add(new Cell(nx, ny));
            } else {
                // Aucun voisin non visité : retirer cette cellule de la liste
                activeCells.remove(index);
            }
        }
        
        return maze;
    }
    
    /**
     * Classe représentant une cellule (position x, y)
     */
    private static class Cell {
        int x;
        int y;
        
        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

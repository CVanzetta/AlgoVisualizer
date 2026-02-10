package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme de Kruskal
 * 
 * PRINCIPE (algorithme de l'arbre couvrant minimal) :
 * 1. Commencer avec toutes les cellules comme des ensembles séparés
 * 2. Créer une liste de tous les murs possibles
 * 3. Mélanger aléatoirement cette liste
 * 4. Pour chaque mur :
 *    - Si les cellules de chaque côté appartiennent à des ensembles différents :
 *      * Supprimer le mur
 *      * Fusionner les deux ensembles
 * 5. Continuer jusqu'à ce que toutes les cellules soient dans le même ensemble
 * 
 * STRUCTURE UNION-FIND (Disjoint Set Union) :
 * - parent[] : pour chaque cellule, indique son parent
 * - rank[] : pour optimiser la fusion (hauteur de l'arbre)
 * - find(x) : trouve la racine de l'ensemble contenant x (avec compression de chemin)
 * - union(x, y) : fusionne les ensembles contenant x et y
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait" (une seule solution entre deux points)
 * - Distribution très uniforme des passages
 * - Pas de biais directionnel
 * - Excellente balance entre complexité et résolvabilité
 * 
 * AVANTAGES :
 * - Génération rapide et efficace
 * - Labyrinthes équilibrés et esthétiques
 * - Pas de récursion (pas de risque de stack overflow)
 * 
 * DÉSAVANTAGES :
 * - Plus complexe à implémenter (Union-Find)
 * - Nécessite plus de mémoire (liste de tous les murs)
 */
public class KruskalGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Kruskal's Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Utilise Union-Find pour créer des labyrinthes uniformes sans biais";
    }
    
    @Override
    public int[][] generate(int width, int height) {
        int[][] maze = initializeMazeWithWalls(width, height);
        
        // Créer la grille de cellules (espacement de 2 pour les murs)
        int cellWidth = (width - 1) / 2;
        int cellHeight = (height - 1) / 2;
        
        // Union-Find : chaque cellule commence dans son propre ensemble
        UnionFind uf = new UnionFind(cellWidth * cellHeight);
        
        // Marquer toutes les cellules comme des passages
        for (int y = 0; y < cellHeight; y++) {
            for (int x = 0; x < cellWidth; x++) {
                maze[y * 2 + 1][x * 2 + 1] = 0;
            }
        }
        
        // Créer la liste de tous les murs possibles
        List<Wall> walls = new ArrayList<>();
        
        // Murs horizontaux (entre cellules de même ligne)
        for (int y = 0; y < cellHeight; y++) {
            for (int x = 0; x < cellWidth - 1; x++) {
                walls.add(new Wall(x, y, x + 1, y, x * 2 + 2, y * 2 + 1));
            }
        }
        
        // Murs verticaux (entre cellules de même colonne)
        for (int y = 0; y < cellHeight - 1; y++) {
            for (int x = 0; x < cellWidth; x++) {
                walls.add(new Wall(x, y, x, y + 1, x * 2 + 1, y * 2 + 2));
            }
        }
        
        // Mélanger aléatoirement les murs
        Collections.shuffle(walls, random);
        
        // Traiter chaque mur
        for (Wall wall : walls) {
            // Indices des cellules dans le tableau Union-Find
            int cell1 = wall.cell1Y * cellWidth + wall.cell1X;
            int cell2 = wall.cell2Y * cellWidth + wall.cell2X;
            
            // Si les deux cellules ne sont pas dans le même ensemble
            if (uf.find(cell1) != uf.find(cell2)) {
                // Supprimer le mur
                maze[wall.wallY][wall.wallX] = 0;
                
                // Fusionner les ensembles
                uf.union(cell1, cell2);
            }
        }
        
        return maze;
    }
    
    /**
     * Classe représentant un mur entre deux cellules
     */
    private static class Wall {
        int cell1X;
        int cell1Y;
        int cell2X;
        int cell2Y;
        int wallX;
        int wallY;
        
        Wall(int cell1X, int cell1Y, int cell2X, int cell2Y, int wallX, int wallY) {
            this.cell1X = cell1X;
            this.cell1Y = cell1Y;
            this.cell2X = cell2X;
            this.cell2Y = cell2Y;
            this.wallX = wallX;
            this.wallY = wallY;
        }
    }
    
    /**
     * Structure de données Union-Find (Disjoint Set Union)
     * Permet de gérer efficacement des ensembles disjoints
     */
    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        
        UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            
            // Chaque élément est son propre parent au départ
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        /**
         * Trouve la racine de l'ensemble contenant x
         * Utilise la compression de chemin pour optimiser
         */
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Compression de chemin
            }
            return parent[x];
        }
        
        /**
         * Fusionne les ensembles contenant x et y
         * Utilise l'union par rang pour garder l'arbre équilibré
         */
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX == rootY) return;
            
            // Union par rang : attacher l'arbre le plus petit sous le plus grand
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}

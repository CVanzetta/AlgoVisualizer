package fr.charles.algovisualizer.algorithms.maze.generator;

import java.util.*;

/**
 * Générateur de labyrinthe par l'algorithme d'Aldous-Broder
 * 
 * PRINCIPE (marche aléatoire) :
 * 1. Commencer à une cellule aléatoire et la marquer comme visitée
 * 2. Répéter jusqu'à ce que toutes les cellules soient visitées :
 *    - Choisir un voisin aléatoire
 *    - Si le voisin n'a pas été visité :
 *      * Supprimer le mur entre les deux cellules
 *      * Marquer le voisin comme visité
 *    - Se déplacer vers le voisin (visité ou non)
 * 3. Le processus peut sembler long car on revisite sans cesse les mêmes zones
 * 
 * CARACTÉRISTIQUES :
 * - Crée un labyrinthe "parfait" (un seul chemin entre deux points)
 * - Distribution uniforme GARANTIE (tous les labyrinthes possibles ont la même probabilité)
 * - Pas de biais directionnel
 * - Le seul algorithme qui génère une distribution vraiment uniforme
 * 
 * AVANTAGES :
 * - Génération mathématiquement uniforme (idéal pour les statistiques)
 * - Très simple à implémenter (pas de structures de données complexes)
 * - Pas de récursion
 * 
 * DÉSAVANTAGES :
 * - TRÈS LENT pour les grands labyrinthes (O(n²) en moyenne)
 * - Les dernières cellules peuvent prendre un temps exponentiel à atteindre
 * - Beaucoup de mouvements "inutiles"
 * 
 * NOTE : Pour améliorer la vitesse, on peut basculer vers Wilson's après avoir visité 
 * une certaine proportion de cellules (stratégie hybride).
 */
public class AldousBroderGenerator extends AbstractMazeGenerator {
    
    @Override
    public String getName() {
        return "Aldous-Broder Algorithm";
    }
    
    @Override
    public String getDescription() {
        return "Marche aléatoire garantissant une distribution uniforme (lent mais équitable)";
    }
    
    @Override
    public int[][] generate(int width, int height) {
        // Initialiser toutes les cellules comme des murs
        int[][] maze = new int[height][width];
        for (int y = 0; y < height; y++) {
            Arrays.fill(maze[y], 1);
        }
        
        // Grille de cellules (espacement de 2 pour les murs)
        int cellWidth = (width - 1) / 2;
        int cellHeight = (height - 1) / 2;
        
        // Marquer toutes les positions de cellules comme des passages
        for (int y = 0; y < cellHeight; y++) {
            for (int x = 0; x < cellWidth; x++) {
                maze[y * 2 + 1][x * 2 + 1] = 0;
            }
        }
        
        // Tableau pour suivre les cellules visitées
        boolean[][] visited = new boolean[cellHeight][cellWidth];
        int visitedCount = 0;
        int totalCells = cellWidth * cellHeight;
        
        // Commencer à une cellule aléatoire
        int currentX = random.nextInt(cellWidth);
        int currentY = random.nextInt(cellHeight);
        visited[currentY][currentX] = true;
        visitedCount++;
        
        // Continuer jusqu'à avoir visité toutes les cellules
        while (visitedCount < totalCells) {
            // Trouver les voisins valides
            List<int[]> validNeighbors = new ArrayList<>();
            
            for (int[] dir : DIRECTIONS) {
                int nx = currentX + dir[0];
                int ny = currentY + dir[1];
                
                if (nx >= 0 && nx < cellWidth && ny >= 0 && ny < cellHeight) {
                    validNeighbors.add(new int[]{nx, ny});
                }
            }
            
            // Choisir un voisin aléatoire
            int[] neighbor = validNeighbors.get(random.nextInt(validNeighbors.size()));
            int nextX = neighbor[0];
            int nextY = neighbor[1];
            
            // Si le voisin n'a pas encore été visité
            if (!visited[nextY][nextX]) {
                // Supprimer le mur entre les deux cellules
                int wallX = currentX * 2 + 1 + (nextX - currentX);
                int wallY = currentY * 2 + 1 + (nextY - currentY);
                maze[wallY][wallX] = 0;
                
                // Marquer le voisin comme visité
                visited[nextY][nextX] = true;
                visitedCount++;
            }
            
            // Se déplacer vers le voisin (qu'il soit visité ou non)
            currentX = nextX;
            currentY = nextY;
        }
        
        return maze;
    }
}

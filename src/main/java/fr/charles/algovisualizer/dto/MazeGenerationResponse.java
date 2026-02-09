package fr.charles.algovisualizer.dto;

/**
 * DTO pour les réponses de génération de labyrinthe
 */
public class MazeGenerationResponse {
    
    private int[][] maze;
    private String algorithm;
    private long executionTime;
    
    public MazeGenerationResponse() {
    }
    
    public MazeGenerationResponse(int[][] maze, String algorithm, long executionTime) {
        this.maze = maze;
        this.algorithm = algorithm;
        this.executionTime = executionTime;
    }
    
    public int[][] getMaze() {
        return maze;
    }
    
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public long getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}

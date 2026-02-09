package fr.charles.algovisualizer.dto;

/**
 * DTO pour les requêtes de génération de labyrinthe
 */
public class MazeGenerationRequest {
    
    private String algorithm; // "recursive", "prim", "binary", "random"
    private int width;
    private int height;
    
    public MazeGenerationRequest() {
    }
    
    public MazeGenerationRequest(String algorithm, int width, int height) {
        this.algorithm = algorithm;
        this.width = width;
        this.height = height;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
}

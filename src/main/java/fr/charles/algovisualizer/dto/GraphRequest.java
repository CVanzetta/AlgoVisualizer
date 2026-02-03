package fr.charles.algovisualizer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.util.Map;

public class GraphRequest {
    @NotNull(message = "Graph cannot be null")
    @Size(max = 1000, message = "Graph cannot have more than 1000 nodes")
    private Map<Integer, Map<Integer, Integer>> graph;
    
    @Min(value = 0, message = "Start node must be non-negative")
    private int start;
    
    @Min(value = 0, message = "End node must be non-negative")
    private int end;

    public GraphRequest() {
    }

    public GraphRequest(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        this.graph = graph;
        this.start = start;
        this.end = end;
    }

    public Map<Integer, Map<Integer, Integer>> getGraph() {
        return graph;
    }

    public void setGraph(Map<Integer, Map<Integer, Integer>> graph) {
        this.graph = graph;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

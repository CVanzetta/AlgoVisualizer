package fr.charles.algovisualizer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Map;

public class GraphRequest {
    @NotNull(message = "Graph cannot be null")
    @Size(max = 1000, message = "Graph cannot have more than 1000 nodes")
    private Map<Integer, Map<Integer, Integer>> graph;
    
    @NotNull(message = "Start node cannot be null")
    @PositiveOrZero(message = "Start node must be non-negative")
    private Integer start;
    
    @NotNull(message = "End node cannot be null")
    @PositiveOrZero(message = "End node must be non-negative")
    private Integer end;

    public GraphRequest() {
    }

    public GraphRequest(Map<Integer, Map<Integer, Integer>> graph, Integer start, Integer end) {
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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}

package fr.charles.algovisualizer.dto;

import java.util.Map;

public class GraphRequest {
    private Map<Integer, Map<Integer, Integer>> graph;
    private int start;
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

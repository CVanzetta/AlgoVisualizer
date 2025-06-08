package fr.charles.algovisualizer.algorithms.graph;

import java.util.List;
import java.util.Map;

public interface GraphAlgorithm {
    List<Integer> findShortestPath(Map<Integer, Map<Integer, Integer>> graph, int start, int end);
    String getName();
}

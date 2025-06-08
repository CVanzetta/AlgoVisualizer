package fr.charles.algovisualizer.services;

import fr.charles.algovisualizer.algorithms.graph.Dijkstra;
import fr.charles.algovisualizer.algorithms.graph.GraphAlgorithm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;

@Service
public class GraphService {

    private final Map<String, GraphAlgorithm> algorithms = new HashMap<>();

    public GraphService() {
        registerAlgorithm(new Dijkstra());
    }

    private void registerAlgorithm(GraphAlgorithm algorithm) {
        algorithms.put(slugify(algorithm.getName()), algorithm);
    }

    private String slugify(String name) {
        return name.toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
    }

    public List<Integer> findShortestPath(String algorithmName, Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        GraphAlgorithm algorithm = algorithms.get(slugify(algorithmName));
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithme inconnu : " + algorithmName);
        }
        return algorithm.findShortestPath(graph, start, end);
    }

    public Map<String, String> getAvailableAlgorithms() {
        Map<String, String> available = new HashMap<>();
        algorithms.forEach((key, value) -> available.put(key, value.getName()));
        return available;
    }
}

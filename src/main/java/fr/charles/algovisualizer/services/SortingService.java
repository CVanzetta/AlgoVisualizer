package fr.charles.algovisualizer.services;

import fr.charles.algovisualizer.algorithms.sorting.SortingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class SortingService {

    private final Map<String, SortingAlgorithm> algorithms = new HashMap<>();

    @Autowired
    public SortingService(List<SortingAlgorithm> algorithmList) {
        // Auto-register all algorithms found by Spring
        algorithmList.forEach(this::registerAlgorithm);
    }

    private void registerAlgorithm(SortingAlgorithm algorithm) {
        algorithms.put(slugify(algorithm.getName()), algorithm);
    }

    private String slugify(String name) {
        return name.toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
    }

    public List<int[]> sort(String algorithmName, int[] array) {
        SortingAlgorithm algorithm = algorithms.get(slugify(algorithmName));
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithme inconnu : " + algorithmName);
        }
        return algorithm.sort(array);
    }

    public Map<String, String> getAvailableAlgorithms() {
        Map<String, String> available = new HashMap<>();
        algorithms.forEach((key, value) -> available.put(key, value.getName()));
        return available;
    }
}

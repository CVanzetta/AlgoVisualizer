package fr.charles.algovisualizer.services;

import fr.charles.algovisualizer.algorithms.sorting.SortingAlgorithm;
import fr.charles.algovisualizer.algorithms.sorting.BubbleSort;
import fr.charles.algovisualizer.algorithms.sorting.QuickSort;
import fr.charles.algovisualizer.algorithms.sorting.InsertionSort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;

@Service
public class SortingService {

    private final Map<String, SortingAlgorithm> algorithms = new HashMap<>();

    public SortingService() {
        // Enregistrement des algorithmes disponibles
        registerAlgorithm(new BubbleSort());
        registerAlgorithm(new QuickSort());
        registerAlgorithm(new InsertionSort());
    }

    private void registerAlgorithm(SortingAlgorithm algorithm) {
        algorithms.put(algorithm.getName().toLowerCase(Locale.ROOT), algorithm);
    }

    public List<int[]> sort(String algorithmName, int[] array) {
        SortingAlgorithm algorithm = algorithms.get(algorithmName.toLowerCase(Locale.ROOT));
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
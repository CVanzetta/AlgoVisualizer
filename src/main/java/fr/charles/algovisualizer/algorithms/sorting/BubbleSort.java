package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BubbleSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int n = array.length;
        
        // Limit step recording for large arrays to prevent OOM
        int recordInterval = n > 1000 ? Math.max(1, n / 500) : 1;
        int stepCount = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap elements
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                // Record step with interval for large arrays
                if (++stepCount % recordInterval == 0) {
                    steps.add(array.clone());
                }
            }
        }
        
        // Always record final state
        if (steps.isEmpty() || !java.util.Arrays.equals(steps.get(steps.size() - 1), array)) {
            steps.add(array.clone());
        }

        return steps;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}

package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertionSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int n = array.length;
        
        // Limit step recording for large arrays
        int recordInterval = n > 1000 ? Math.max(1, n / 500) : 1;
        int stepCount = 0;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
                
                // Record step with interval
                if (++stepCount % recordInterval == 0) {
                    steps.add(array.clone());
                }
            }
            array[j + 1] = key;
            
            // Record after each insertion
            if (stepCount % recordInterval == 0) {
                steps.add(array.clone());
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
        return "Insertion Sort";
    }
}

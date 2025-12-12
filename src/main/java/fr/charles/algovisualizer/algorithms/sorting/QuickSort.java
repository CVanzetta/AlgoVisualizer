package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuickSort implements SortingAlgorithm {

    private int stepCount = 0;
    private int recordInterval = 1;

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int[] arr = array.clone();
        
        // Limit step recording for large arrays
        recordInterval = arr.length > 1000 ? Math.max(1, arr.length / 1000) : 1;
        stepCount = 0;
        
        steps.add(arr.clone());
        quickSort(arr, 0, arr.length - 1, steps);
        
        // Always record final state
        if (steps.isEmpty() || !java.util.Arrays.equals(steps.get(steps.size() - 1), arr)) {
            steps.add(arr.clone());
        }
        
        return steps;
    }

    private void quickSort(int[] array, int low, int high, List<int[]> steps) {
        if (low < high) {
            int pi = partition(array, low, high, steps);
            quickSort(array, low, pi - 1, steps);
            quickSort(array, pi + 1, high, steps);
        }
    }

    private int partition(int[] array, int low, int high, List<int[]> steps) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                
                // Record step with interval
                if (++stepCount % recordInterval == 0) {
                    steps.add(array.clone());
                }
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        
        if (stepCount % recordInterval == 0) {
            steps.add(array.clone());
        }
        
        return i + 1;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}



package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShellSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;
        
        // Record initial state
        steps.add(arr.clone());
        
        // Start with a big gap, then reduce the gap
        for (int interval = n / 2; interval > 0; interval /= 2) {
            // Do a gapped insertion sort for this interval size
            for (int i = interval; i < n; i += 1) {
                int temp = arr[i];
                int j;
                
                // Shift earlier gap-sorted elements up until the correct location for arr[i] is found
                for (j = i; j >= interval && arr[j - interval] > temp; j -= interval) {
                    arr[j] = arr[j - interval];
                    
                    // Record step after each shift
                    steps.add(arr.clone());
                }
                
                // Put temp in its correct location
                arr[j] = temp;
                
                // Record step after insertion
                steps.add(arr.clone());
            }
        }
        
        return steps;
    }

    @Override
    public String getName() {
        return "Shell Sort";
    }
}

package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RadixSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int[] arr = array.clone();
        
        if (arr == null || arr.length == 0) {
            return steps;
        }
        
        // Record initial state
        steps.add(arr.clone());
        
        // Find the maximum value to know the number of digits
        int max = findMax(arr);
        
        // Apply Counting Sort for each digit (units, tens, hundreds, etc.)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp, steps);
        }
        
        return steps;
    }

    // Find the maximum value in the array
    private int findMax(int[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    // Counting sort based on a particular digit (exp = 1, 10, 100, ...)
    private void countingSortByDigit(int[] arr, int exp, List<int[]> steps) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // digits from 0 to 9
        
        // Count occurrences for the target digit
        for (int value : arr) {
            int digit = (value / exp) % 10;
            count[digit]++;
        }
        
        // Transform into cumulative positions (prefix sum)
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the new order (start from the end for stability)
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        // Copy back to original array
        System.arraycopy(output, 0, arr, 0, n);
        
        // Record step after sorting by this digit
        steps.add(arr.clone());
    }

    @Override
    public String getName() {
        return "Radix Sort";
    }
}

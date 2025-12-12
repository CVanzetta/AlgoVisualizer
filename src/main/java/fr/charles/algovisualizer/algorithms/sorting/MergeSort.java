package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MergeSort implements SortingAlgorithm {

    private List<int[]> steps;
    private int stepCount = 0;
    private int recordInterval = 1;

    @Override
    public List<int[]> sort(int[] array) {
        steps = new ArrayList<>();
        int[] arr = array.clone();
        
        // Limit step recording for large arrays
        recordInterval = arr.length > 1000 ? Math.max(1, arr.length / 1000) : 1;
        stepCount = 0;
        
        // Record initial state
        steps.add(arr.clone());
        
        // Start merge sort
        mergeSort(arr, 0, arr.length - 1);
        
        // Always record final state
        if (steps.isEmpty() || !java.util.Arrays.equals(steps.get(steps.size() - 1), arr)) {
            steps.add(arr.clone());
        }
        
        return steps;
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temp arrays
        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        // Merge the temp arrays
        int i = 0, j = 0;
        int k = left;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
            
            // Record step with interval
            if (++stepCount % recordInterval == 0) {
                steps.add(arr.clone());
            }
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            
            if (stepCount % recordInterval == 0) {
                steps.add(arr.clone());
            }
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            
            if (stepCount % recordInterval == 0) {
                steps.add(arr.clone());
            }
        }
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }
}

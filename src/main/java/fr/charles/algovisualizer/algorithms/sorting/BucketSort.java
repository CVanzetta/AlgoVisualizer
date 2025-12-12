package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("bucket")
public class BucketSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int[] arr = array.clone();
        int n = arr.length;
        
        if (n <= 0) {
            return steps;
        }
        
        // Record initial state
        steps.add(arr.clone());
        
        // Find maximum value to normalize bucket indices
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > maxValue) maxValue = arr[i];
            if (arr[i] < minValue) minValue = arr[i];
        }
        
        int range = maxValue - minValue + 1;
        int bucketCount = Math.max(1, n / 2); // Use n/2 buckets for better distribution
        
        // Create empty buckets
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Distribute elements into buckets
        for (int i = 0; i < n; i++) {
            int bucketIndex = (int) ((long)(arr[i] - minValue) * bucketCount / range);
            if (bucketIndex >= bucketCount) bucketIndex = bucketCount - 1; // Handle edge case
            buckets[bucketIndex].add(arr[i]);
        }
        
        // Sort each bucket and collect results
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            // Sort the bucket
            Collections.sort(buckets[i]);
            
            // Copy sorted elements back to array
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[index++] = buckets[i].get(j);
                
                // Record step after each element placement
                steps.add(arr.clone());
            }
        }
        
        return steps;
    }

    @Override
    public String getName() {
        return "Bucket Sort";
    }
}

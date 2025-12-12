package fr.charles.algovisualizer.algorithms.sorting;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void testMergeSort() {
        MergeSort mergeSort = new MergeSort();
        int[] input = {5, 2, 8, 1, 9, 3};
        
        List<int[]> steps = mergeSort.sort(input);
        
        assertNotNull(steps);
        assertTrue(steps.size() > 0);
        
        // Verify that the final result is sorted
        int[] result = steps.get(steps.size() - 1);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1], 
                "Array should be sorted at index " + i);
        }
    }
    
    @Test
    void testMergeSortAlreadySorted() {
        MergeSort mergeSort = new MergeSort();
        int[] input = {1, 2, 3, 4, 5};
        
        List<int[]> steps = mergeSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }
    
    @Test
    void testMergeSortReverseSorted() {
        MergeSort mergeSort = new MergeSort();
        int[] input = {5, 4, 3, 2, 1};
        
        List<int[]> steps = mergeSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }
    
    @Test
    void testGetName() {
        MergeSort mergeSort = new MergeSort();
        assertEquals("Merge Sort", mergeSort.getName());
    }
}

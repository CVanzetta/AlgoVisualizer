package fr.charles.algovisualizer.algorithms.sorting;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RadixSortTest {

    @Test
    void testRadixSort() {
        RadixSort radixSort = new RadixSort();
        int[] input = {170, 45, 75, 90, 802, 24, 2, 66};
        
        List<int[]> steps = radixSort.sort(input);
        
        assertNotNull(steps);
        assertTrue(steps.size() > 0);
        
        // Verify that the final result is sorted
        int[] result = steps.get(steps.size() - 1);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1], 
                "Array should be sorted at index " + i);
        }
        
        // Verify the expected sorted order
        assertArrayEquals(new int[]{2, 24, 45, 66, 75, 90, 170, 802}, result);
    }
    
    @Test
    void testRadixSortSmallNumbers() {
        RadixSort radixSort = new RadixSort();
        int[] input = {5, 2, 8, 1, 9, 3};
        
        List<int[]> steps = radixSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 5, 8, 9}, result);
    }
    
    @Test
    void testRadixSortAlreadySorted() {
        RadixSort radixSort = new RadixSort();
        int[] input = {1, 2, 3, 4, 5};
        
        List<int[]> steps = radixSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }
    
    @Test
    void testRadixSortWithDuplicates() {
        RadixSort radixSort = new RadixSort();
        int[] input = {50, 20, 80, 20, 90, 10, 50, 50};
        
        List<int[]> steps = radixSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{10, 20, 20, 50, 50, 50, 80, 90}, result);
    }
    
    @Test
    void testRadixSortSingleDigit() {
        RadixSort radixSort = new RadixSort();
        int[] input = {9, 3, 7, 1, 5};
        
        List<int[]> steps = radixSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, result);
    }
    
    @Test
    void testRadixSortLargeNumbers() {
        RadixSort radixSort = new RadixSort();
        int[] input = {9999, 1234, 5678, 100, 9876, 500};
        
        List<int[]> steps = radixSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{100, 500, 1234, 5678, 9876, 9999}, result);
    }
    
    @Test
    void testGetName() {
        RadixSort radixSort = new RadixSort();
        assertEquals("Radix Sort", radixSort.getName());
    }
}

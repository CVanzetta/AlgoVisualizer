package fr.charles.algovisualizer.algorithms.sorting;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest {

    @Test
    void testShellSort() {
        ShellSort shellSort = new ShellSort();
        int[] input = {9, 8, 3, 7, 5, 6, 4, 1};
        
        List<int[]> steps = shellSort.sort(input);
        
        assertNotNull(steps);
        assertTrue(steps.size() > 0);
        
        // Verify that the final result is sorted
        int[] result = steps.get(steps.size() - 1);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1], 
                "Array should be sorted at index " + i);
        }
        
        // Verify the expected sorted order
        assertArrayEquals(new int[]{1, 3, 4, 5, 6, 7, 8, 9}, result);
    }
    
    @Test
    void testShellSortAlreadySorted() {
        ShellSort shellSort = new ShellSort();
        int[] input = {1, 2, 3, 4, 5};
        
        List<int[]> steps = shellSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }
    
    @Test
    void testShellSortReverseSorted() {
        ShellSort shellSort = new ShellSort();
        int[] input = {5, 4, 3, 2, 1};
        
        List<int[]> steps = shellSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }
    
    @Test
    void testShellSortWithDuplicates() {
        ShellSort shellSort = new ShellSort();
        int[] input = {5, 2, 8, 2, 9, 1, 5, 5};
        
        List<int[]> steps = shellSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 2, 5, 5, 5, 8, 9}, result);
    }
    
    @Test
    void testGetName() {
        ShellSort shellSort = new ShellSort();
        assertEquals("Shell Sort", shellSort.getName());
    }
}

package fr.charles.algovisualizer.algorithms.sorting;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BucketSortTest {

    @Test
    void testBucketSort() {
        BucketSort bucketSort = new BucketSort();
        int[] input = {42, 32, 33, 52, 37, 47, 51};
        
        List<int[]> steps = bucketSort.sort(input);
        
        assertNotNull(steps);
        assertTrue(steps.size() > 0);
        
        // Verify that the final result is sorted
        int[] result = steps.get(steps.size() - 1);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1], 
                "Array should be sorted at index " + i);
        }
        
        // Verify the expected sorted order
        assertArrayEquals(new int[]{32, 33, 37, 42, 47, 51, 52}, result);
    }
    
    @Test
    void testBucketSortWithLargeRange() {
        BucketSort bucketSort = new BucketSort();
        int[] input = {170, 45, 75, 90, 802, 24, 2, 66};
        
        List<int[]> steps = bucketSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        
        // Verify sorted
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1]);
        }
        
        assertArrayEquals(new int[]{2, 24, 45, 66, 75, 90, 170, 802}, result);
    }
    
    @Test
    void testBucketSortAlreadySorted() {
        BucketSort bucketSort = new BucketSort();
        int[] input = {1, 2, 3, 4, 5};
        
        List<int[]> steps = bucketSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }
    
    @Test
    void testBucketSortWithDuplicates() {
        BucketSort bucketSort = new BucketSort();
        int[] input = {5, 2, 8, 2, 9, 1, 5, 5};
        
        List<int[]> steps = bucketSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{1, 2, 2, 5, 5, 5, 8, 9}, result);
    }
    
    @Test
    void testBucketSortSingleElement() {
        BucketSort bucketSort = new BucketSort();
        int[] input = {42};
        
        List<int[]> steps = bucketSort.sort(input);
        
        assertNotNull(steps);
        int[] result = steps.get(steps.size() - 1);
        assertArrayEquals(new int[]{42}, result);
    }
    
    @Test
    void testGetName() {
        BucketSort bucketSort = new BucketSort();
        assertEquals("Bucket Sort", bucketSort.getName());
    }
}

package fr.charles.algovisualizer.algorithms.sorting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InsertionSortTest {
    @Test
    void insertionSortSortsAndCountsSteps() {
        InsertionSort sorter = new InsertionSort();
        int[] array = {3, 2, 1};
        List<int[]> steps = sorter.sort(array);
        assertArrayEquals(new int[]{1, 2, 3}, array);
        assertArrayEquals(new int[]{1, 2, 3}, steps.get(steps.size() - 1));
        assertEquals(5, steps.size());
    }
}

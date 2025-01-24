package fr.charles.algovisualizer.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
                steps.add(array.clone());
            }
            array[j + 1] = key;
            steps.add(array.clone());
        }

        return steps;
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}

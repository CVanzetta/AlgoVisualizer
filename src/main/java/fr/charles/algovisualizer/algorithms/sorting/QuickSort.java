package fr.charles.algovisualizer.algorithms.sorting;

import java.util.ArrayList;
import java.util.List;

public class QuickSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        quickSort(array, 0, array.length - 1, steps);
        return steps;
    }

    private void quickSort(int[] array, int low, int high, List<int[]> steps) {
        if (low < high) {
            int pi = partition(array, low, high, steps);
            quickSort(array, low, pi - 1, steps);
            quickSort(array, pi + 1, high, steps);
        }
    }

    private int partition(int[] array, int low, int high, List<int[]> steps) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                steps.add(array.clone());
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        steps.add(array.clone());
        return i + 1;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}



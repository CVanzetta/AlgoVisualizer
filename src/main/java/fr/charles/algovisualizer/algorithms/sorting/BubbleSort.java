package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BubbleSort implements SortingAlgorithm {

    @Override
    public List<int[]> sort(int[] array) {
        // Déclarez et initialisez la variable steps pour stocker les étapes
        List<int[]> steps = new ArrayList<>();
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Échange des éléments
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                // Ajoutez une copie du tableau actuel dans les étapes
                steps.add(array.clone());
            }
        }

        // Retournez les étapes pour la visualisation
        return steps;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}

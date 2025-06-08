package fr.charles.algovisualizer.algorithms.graph;

import java.util.*;

public class Dijkstra implements GraphAlgorithm {

    @Override
    public List<Integer> findShortestPath(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        for (Integer node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(new int[]{start, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int node = current[0];
            if (!visited.add(node)) {
                continue;
            }
            if (node == end) {
                break;
            }
            for (Map.Entry<Integer, Integer> entry : graph.getOrDefault(node, Collections.emptyMap()).entrySet()) {
                int neighbor = entry.getKey();
                int weight = entry.getValue();
                int newDist = distances.get(node) + weight;
                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, node);
                    queue.add(new int[]{neighbor, newDist});
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        Integer current = end;
        if (!previous.containsKey(current) && start != end) {
            return path; // no path
        }
        path.add(current);
        while (previous.containsKey(current)) {
            current = previous.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public String getName() {
        return "Dijkstra";
    }
}

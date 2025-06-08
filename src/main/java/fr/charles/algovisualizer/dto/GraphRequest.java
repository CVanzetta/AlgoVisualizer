package fr.charles.algovisualizer.dto;

import java.util.Map;

public record GraphRequest(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
}

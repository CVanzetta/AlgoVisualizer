package fr.charles.algovisualizer.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class ValidGraphValidator implements ConstraintValidator<ValidGraph, Map<Integer, Map<Integer, Integer>>> {

    private static final int MAX_NODES = 1000;
    private static final int MAX_EDGES_PER_NODE = 100;
    private static final int MAX_WEIGHT = 1000000;
    private static final int MIN_WEIGHT = -1000;

    @Override
    public boolean isValid(Map<Integer, Map<Integer, Integer>> graph, ConstraintValidatorContext context) {
        if (graph == null || graph.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Graph cannot be null or empty")
                    .addConstraintViolation();
            return false;
        }

        // Check number of nodes
        if (graph.size() > MAX_NODES) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Graph cannot have more than " + MAX_NODES + " nodes")
                    .addConstraintViolation();
            return false;
        }

        // Validate each node and its edges
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : graph.entrySet()) {
            Integer nodeId = entry.getKey();
            Map<Integer, Integer> edges = entry.getValue();

            // Validate node ID
            if (nodeId == null || nodeId < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Node IDs must be non-negative")
                        .addConstraintViolation();
                return false;
            }

            // Validate edges
            if (edges == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Edges map cannot be null")
                        .addConstraintViolation();
                return false;
            }

            if (edges.size() > MAX_EDGES_PER_NODE) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Node cannot have more than " + MAX_EDGES_PER_NODE + " edges")
                        .addConstraintViolation();
                return false;
            }

            // Validate edge weights
            for (Map.Entry<Integer, Integer> edge : edges.entrySet()) {
                Integer targetNode = edge.getKey();
                Integer weight = edge.getValue();

                if (targetNode == null || targetNode < 0) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Target node IDs must be non-negative")
                            .addConstraintViolation();
                    return false;
                }

                if (weight == null) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Edge weights cannot be null")
                            .addConstraintViolation();
                    return false;
                }

                // Check weight bounds to prevent overflow and unrealistic values
                if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Edge weight must be between " + MIN_WEIGHT + " and " + MAX_WEIGHT)
                            .addConstraintViolation();
                    return false;
                }
            }
        }

        return true;
    }
}

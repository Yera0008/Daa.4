package graph.dagsp;

import java.util.*;
import graph.util.Metrics;

public class DAGShortestPath {

    public static Map<Integer, Double> shortestPath(
            Map<Integer, List<int[]>> graph, int source, List<Integer> topo, Metrics metrics) {

        metrics.start();
        Map<Integer, Double> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Double.POSITIVE_INFINITY);
        dist.put(source, 0.0);

        for (int u : topo) {
            if (dist.get(u) != Double.POSITIVE_INFINITY) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    metrics.countRelax();
                    int v = edge[0];
                    double w = edge[1];
                    if (dist.get(v) > dist.get(u) + w)
                        dist.put(v, dist.get(u) + w);
                }
            }
        }
        metrics.stop();
        metrics.printSummary("DAG Shortest Path");
        return dist;
    }

    public static Map<Integer, Double> longestPath(
            Map<Integer, List<int[]>> graph, int source, List<Integer> topo, Metrics metrics) {

        metrics.start();
        Map<Integer, Double> dist = new HashMap<>();
        for (int v : graph.keySet()) dist.put(v, Double.NEGATIVE_INFINITY);
        dist.put(source, 0.0);

        for (int u : topo) {
            if (dist.get(u) != Double.NEGATIVE_INFINITY) {
                for (int[] edge : graph.getOrDefault(u, Collections.emptyList())) {
                    metrics.countRelax();
                    int v = edge[0];
                    double w = edge[1];
                    if (dist.get(v) < dist.get(u) + w)
                        dist.put(v, dist.get(u) + w);
                }
            }
        }
        metrics.stop();
        metrics.printSummary("DAG Longest Path");
        return dist;
    }
}

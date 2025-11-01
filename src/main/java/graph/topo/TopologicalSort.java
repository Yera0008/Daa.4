package graph.topo;

import java.util.*;
import graph.util.Metrics;

public class TopologicalSort {

    public static List<Integer> sort(Map<Integer, List<Integer>> graph, int n, Metrics metrics) {
        metrics.start();
        int[] indegree = new int[n];
        for (List<Integer> adj : graph.values()) {
            for (int v : adj)
                indegree[v]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (indegree[i] == 0) {
                q.add(i);
                metrics.countPush();
            }

        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            topo.add(u);
            for (int v : graph.getOrDefault(u, Collections.emptyList())) {
                metrics.countEdge();
                if (--indegree[v] == 0) {
                    q.add(v);
                    metrics.countPush();
                }
            }
        }
        metrics.stop();
        metrics.printSummary("Topological Sort");
        return topo;
    }
}

package graph.scc;

import java.util.*;
import graph.util.Metrics;

public class SCCFinder {
    private int time;
    private final Map<Integer, List<Integer>> graph;
    private final int n;
    private final int[] disc, low;
    private final boolean[] stackMember;
    private final Deque<Integer> stack;
    private final List<List<Integer>> sccList;
    private final Metrics metrics;

    public SCCFinder(Map<Integer, List<Integer>> graph, int n, Metrics metrics) {
        this.graph = graph;
        this.n = n;
        this.metrics = metrics;
        disc = new int[n];
        low = new int[n];
        stackMember = new boolean[n];
        stack = new ArrayDeque<>();
        sccList = new ArrayList<>();
        Arrays.fill(disc, -1);
        Arrays.fill(low, -1);
    }

    public List<List<Integer>> findSCCs() {
        metrics.start();
        for (int i = 0; i < n; i++) {
            if (disc[i] == -1)
                dfs(i);
        }
        metrics.stop();
        metrics.printSummary("SCC Finder");
        return sccList;
    }

    private void dfs(int u) {
        metrics.countVisit();
        disc[u] = low[u] = ++time;
        stack.push(u);
        stackMember[u] = true;

        for (int v : graph.getOrDefault(u, Collections.emptyList())) {
            metrics.countEdge();
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (stackMember[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> scc = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                stackMember[w] = false;
                scc.add(w);
            } while (w != u);
            sccList.add(scc);
        }
    }

    public Map<Integer, List<Integer>> buildCondensationGraph() {
        Map<Integer, List<Integer>> dag = new HashMap<>();
        Map<Integer, Integer> nodeToComp = new HashMap<>();
        for (int i = 0; i < sccList.size(); i++) {
            for (int v : sccList.get(i))
                nodeToComp.put(v, i);
        }

        for (int i = 0; i < sccList.size(); i++)
            dag.put(i, new ArrayList<>());

        for (int u = 0; u < n; u++) {
            for (int v : graph.getOrDefault(u, Collections.emptyList())) {
                int cu = nodeToComp.get(u), cv = nodeToComp.get(v);
                if (cu != cv && !dag.get(cu).contains(cv))
                    dag.get(cu).add(cv);
            }
        }
        return dag;
    }
}

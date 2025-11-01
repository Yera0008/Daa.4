package graph;

import graph.scc.SCCFinder;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPath;
import graph.util.Metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class GraphTests {

    @Test
    void testSCCDetectionAndCondensation() {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, List.of(1));
        graph.put(1, List.of(2));
        graph.put(2, List.of(0));
        graph.put(3, List.of(4));
        graph.put(4, List.of());

        Metrics m = new Metrics();
        SCCFinder sccFinder = new SCCFinder(graph, 5, m);
        var sccs = sccFinder.findSCCs();
        assertTrue(sccs.size() >= 1, "Should find at least one SCC");
    }

    @Test
    void testTopologicalOrder() {
        Map<Integer, List<Integer>> dag = new HashMap<>();
        dag.put(0, List.of(1));
        dag.put(1, List.of(2));
        dag.put(2, List.of(3));
        dag.put(3, List.of());

        Metrics m = new Metrics();
        List<Integer> order = TopologicalSort.sort(dag, 4, m);
        assertEquals(4, order.size());
    }

    @Test
    void testShortestAndLongestPath() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        graph.put(0, List.of(new int[]{1, 2}, new int[]{2, 4}));
        graph.put(1, List.of(new int[]{2, 1}, new int[]{3, 7}));
        graph.put(2, List.of(new int[]{3, 3}));
        graph.put(3, new ArrayList<>());

        List<Integer> topo = List.of(0, 1, 2, 3);
        Metrics m1 = new Metrics();
        var shortest = DAGShortestPath.shortestPath(graph, 0, topo, m1);
        Metrics m2 = new Metrics();
        var longest = DAGShortestPath.longestPath(graph, 0, topo, m2);

        assertEquals(0.0, shortest.get(0));
        assertTrue(longest.get(3) >= shortest.get(3));
    }
}

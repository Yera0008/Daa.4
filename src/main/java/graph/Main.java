package graph;

import com.google.gson.*;
import graph.scc.SCCFinder;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGShortestPath;
import graph.util.Metrics;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        File dataDir = new File("data");
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            System.out.println("No data folder found! Run DatasetGenerator first.");
            return;
        }

        File[] files = dataDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            System.out.println("No dataset files found. Please generate them first.");
            return;
        }

        Arrays.sort(files);

        for (File file : files) {
            String path = file.getPath();
            System.out.println("\n=== Running algorithms for dataset: " + path + " ===\n");

            try {
                runAlgorithmsOnDataset(path);
            } catch (Exception e) {
                System.err.println("⚠️ Error processing " + path + ": " + e.getMessage());
            }
        }
    }

    private static void runAlgorithmsOnDataset(String path) throws Exception {
        Gson gson = new Gson();
        Map<String, List<List<Double>>> data = gson.fromJson(new FileReader(path), Map.class);

        Map<Integer, List<int[]>> weightedGraph = new HashMap<>();
        Map<Integer, List<Integer>> unweighted = new HashMap<>();

        for (var entry : data.entrySet()) {
            int u = Integer.parseInt(entry.getKey());
            List<int[]> edges = new ArrayList<>();
            List<Integer> plain = new ArrayList<>();
            for (List<Double> pair : entry.getValue()) {
                int to = pair.get(0).intValue();
                int w = pair.get(1).intValue();
                edges.add(new int[]{to, w});
                plain.add(to);
            }
            weightedGraph.put(u, edges);
            unweighted.put(u, plain);
        }

        int n = unweighted.size();
        int edgeCount = weightedGraph.values().stream().mapToInt(List::size).sum();

        System.out.printf("Vertices: %d | Edges: %d%n%n", n, edgeCount);

        Metrics m1 = new Metrics();
        SCCFinder scc = new SCCFinder(unweighted, n, m1);
        var sccs = scc.findSCCs();
        System.out.println("SCCs found: " + sccs.size());

        Map<Integer, List<Integer>> dag = scc.buildCondensationGraph();

        Metrics m2 = new Metrics();
        List<Integer> topo = TopologicalSort.sort(dag, dag.size(), m2);

        Metrics m3 = new Metrics();
        DAGShortestPath.shortestPath(weightedGraph, 0, topo, m3);

        Metrics m4 = new Metrics();
        DAGShortestPath.longestPath(weightedGraph, 0, topo, m4);

        System.out.println("----------------------------------------------------\n");
    }
}

package graph.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DatasetGenerator {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        generateAll();
    }

    public static void generateAll() throws IOException {
        File dataDir = new File("data");
        if (!dataDir.exists()) dataDir.mkdirs();

        generateCategory("small", 6, 10, 3);
        generateCategory("medium", 10, 20, 3);
        generateCategory("large", 20, 50, 3);
        System.out.println("All datasets generated in /data/");
    }

    private static void generateCategory(String name, int minN, int maxN, int count) throws IOException {
        for (int i = 1; i <= count; i++) {
            int n = random.nextInt(maxN - minN + 1) + minN;
            boolean cyclic = random.nextBoolean();
            double density = cyclic ? 0.4 : 0.2;
            Map<Integer, List<int[]>> graph = createGraph(n, density, cyclic);
            saveGraph(graph, "data/" + name + "_" + i + ".json");
        }
    }

    private static Map<Integer, List<int[]>> createGraph(int n, double density, boolean cyclic) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());
        int maxEdges = (int) (n * (n - 1) * density);
        for (int e = 0; e < maxEdges; e++) {
            int u = random.nextInt(n);
            int v = random.nextInt(n);
            if (u == v) continue;
            int w = random.nextInt(10) + 1;
            if (!cyclic && v < u) continue;
            graph.get(u).add(new int[]{v, w});
        }
        return graph;
    }

    private static void saveGraph(Map<Integer, List<int[]>> graph, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(graph, writer);
        }
        System.out.println("Saved: " + fileName);
    }
}

package graph.util;

public class Metrics {
    private long startTime, endTime;
    private int dfsVisits, edgesProcessed, relaxations, topoPushes;

    public void start() { startTime = System.nanoTime(); }
    public void stop() { endTime = System.nanoTime(); }

    public void countVisit() { dfsVisits++; }
    public void countEdge() { edgesProcessed++; }
    public void countRelax() { relaxations++; }
    public void countPush() { topoPushes++; }

    public void printSummary(String name) {
        System.out.println("=== " + name + " Metrics ===");
        System.out.printf("DFS visits: %d | Edges processed: %d | Relaxations: %d | Kahn pushes: %d%n",
                dfsVisits, edgesProcessed, relaxations, topoPushes);
        System.out.printf("Runtime: %.3f ms%n", (endTime - startTime) / 1e6);
        System.out.println("==============================\n");
    }
}

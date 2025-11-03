Assignment 4

This project implements and evaluates graph algorithms for analyzing network structures such as those found in smart city applications.
It focuses on Strongly Connected Components (SCCs), Topological Sorting, and Shortest/Longest Path calculations in Directed Acyclic Graphs (DAGs) — all with detailed performance metrics and automated dataset generation.

Features

Algorithms Implemented

Tarjan’s Algorithm for Strongly Connected Components

Condensation Graph creation from SCCs

Kahn’s Algorithm for Topological Sorting

DAG Shortest Path (based on topological order)

DAG Longest Path (modified relaxation)

Performance Metrics
Each algorithm records:

DFS Visits

Edges Processed

Relaxations

Kahn Queue Pushes

Total Runtime (ms)

Dataset Generation:

Automatically generates 9 datasets:

small, medium, and large

Each includes cyclic and acyclic examples

Files saved in /data/ folder

Automated Metrics Runner:

Runs all algorithms on every dataset

Records vertex/edge counts, SCCs, and runtimes

Prints full summary to the console

🧩 Project Structure
Assignment4/
 ├── pom.xml
 ├── data/                
 └── src/
      ├── main/java/graph/
      │    ├── Main.java                  
      │    ├── scc/SCCFinder.java             
      │    ├── topo/TopologicalSort.java      
      │    ├── dagsp/DAGShortestPath.java     
      │    └── util/
      │         ├── DatasetGenerator.java     
      │         └── Metrics.java             
      └── test/java/graph/
           └── GraphTests.java                

Experiment Results
| Dataset  | Vertices | Edges | SCCs | SCC Time (ms) | Topo Time (ms) | Shortest Time (ms) | Longest Time (ms) |
| -------- | -------- | ----- | ---- | ------------- | -------------- | ------------------ | ----------------- |
| small_1  | 8        | 5     | 8    | 0.029         | 0.038          | 0.017              | 0.015             |
| small_2  | 7        | 2     | 7    | 0.021         | 0.024          | 0.015              | 0.009             |
| small_3  | 8        | 6     | 8    | 0.043         | 0.027          | 0.012              | 0.011             |
| medium_1 | 11       | 40    | 3    | 0.021         | 0.015          | 0.010              | 0.009             |
| medium_2 | 15       | 75    | 1    | 0.026         | 0.014          | 0.012              | 0.010             |
| medium_3 | 12       | 46    | 2    | 0.030         | 0.024          | 0.015              | 0.011             |
| large_1  | 49       | 239   | 49   | 0.338         | 0.369          | 0.175              | 0.072             |
| large_2  | 43       | 182   | 43   | 0.061         | 0.107          | 0.033              | 0.049             |
| large_3  | 24       | 62    | 24   | 0.038         | 0.059          | 0.031              | 0.055             |

🧠 Analysis

Runtime increases with dataset size, as expected from O(V + E) complexity.

Graphs with SCCs < vertices indicate cyclic graphs (e.g., medium_1 with 3 SCCs).

Acyclic graphs (SCCs = vertices) show pure DAG behavior.

Shortest/Longest Path relaxations vary depending on reachability and density.

Topological Sort always processes roughly one edge per vertex in DAGs.

Overall, the algorithms perform efficiently across all dataset sizes with runtimes below 1 ms.

containing:

dataset,vertices,edges,sccs,scc_time_ms,topo_time_ms,shortest_time_ms,longest_time_ms
small_1,8,5,8,0.029,0.038,0.017,0.015

Complexity Summary:
- Algorithm	Time Complexity	Description
- Tarjan SCC	O(V + E)	DFS-based component discovery
- Kahn Topo Sort	O(V + E)	In-degree queue processing
- DAG Shortest Path	O(V + E)	Relax edges in topo order
- DAG Longest Path	O(V + E)	Reverse relaxation order

Conclusion:
- All algorithms were successfully implemented, tested, and benchmarked on randomly generated datasets of varying size and density.
- The results demonstrate expected theoretical performance and correctness of Tarjan’s SCCFinder, Kahn’s Topological Sort, and DAG Shortest/Longest Path implementations.

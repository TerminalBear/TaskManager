import java.util.ArrayList;
import java.util.List;

public class KruskalsAlg {
    private List<Task> tasks;
    private List<Edge> edges;

    public KruskalsAlg(List<Task> tasks) {
        this.tasks = tasks;
        this.edges = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                int weight = tasks.get(i).getDueDate().compareTo(tasks.get(j).getDueDate());
                edges.add(new Edge(tasks.get(i), tasks.get(j), weight));
            }
        }
    }

    public List<Task> getSortedTasks() {
        // Create a graph where each node represents a task and each edge represents a dependency
        // between tasks. The weight of each edge represents the time it takes to complete the
        // dependent task.
        KruskalsAlg graph = new KruskalsAlg(tasks);

        // Apply Kruskal's algorithm to find the minimum spanning tree of the graph
        List<Edge> mst = kruskal(graph.getEdges());

        // Return the sorted list of tasks
        return mst.stream()
               .map(Edge::getTask1)
               .collect(Collectors.toList());
    }

    private List<Edge> kruskal(List<Edge> edges) {
        // Implementation of Kruskal's algorithm
        // You can use any implementation of Kruskal's algorithm that you prefer
        // Here is an example of a simple implementation:
        List<Edge> mst = new ArrayList<>();
        DisjointSet<Task> ds = new DisjointSet<>();

        // Sort the edges in ascending order of their weights
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        // Iterate through the sorted edges
        for (Edge edge : edges) {
            // Get the sets of the two tasks connected by the edge
            DisjointSet<Task>.Set set1 = ds.find(edge.getTask1());
            DisjointSet<Task>.Set set2 = ds.find(edge.getTask2());

            // If the two tasks are not connected, add the edge to the minimum spanning tree
            // and union the two sets
            if (set1!= set2) {
                mst.add(edge);
                ds.union(set1, set2);
            }
        }

        return mst;
    }
}

class Edge {
    private Task task1;
    private Task task2;
    private int weight;

    public Edge(Task task1, Task task2, int weight) {
        this.task1 = task1;
        this.task2 = task2;
        this.weight = weight;
    }

    public Task getTask1() {
        return task1;
    }

    public Task getTask2() {
        return task2;
    }

    public int getWeight() {
        return weight;
    }
}
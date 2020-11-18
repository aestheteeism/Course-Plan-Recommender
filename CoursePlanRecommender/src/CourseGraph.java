import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class CourseGraph {
    private Map<Course, List<Course>> graph;
    private PriorityQueue<Course> allElectives;
    private ArrayList<Course> allCourses;
    private CourseSet courseSet;
    int electiveHours = 0;

    // Constructor
    public CourseGraph(String path) {
        this.courseSet = new CourseSet(path);
        this.graph = courseSet.toAdjacencyList();
        this.allElectives = courseSet.getElectives();
        this.allCourses = courseSet.getAllCourses();
    }

    // Copy Constructor
    public CourseGraph(CourseGraph copiedGraph) {
        courseSet = copiedGraph.getAllCourses();
        this.graph = courseSet.toAdjacencyList();
        this.allElectives = courseSet.getElectives();
    }

    public CourseSet getAllCourses() {
        return courseSet;
    }

    // Function to add an edge to graph
    public void addEdge(Course u, Course v) {
        graph.get(u).add(v);
    }

    /**--- ALGORITHM 1 ---**/

    public void addElectives() {
        while (electiveHours < 21) {
            if (!allElectives.isEmpty()) {
                Course bestCourse = selectBest(allElectives);
                courseSet.setAsMandatory(bestCourse);
                electiveHours += bestCourse.getCreditHour();
            }
        }
        graph = courseSet.toAdjacencyList();
    }

    private Course selectBest(PriorityQueue<Course> allElectives) {
        Course bestCourse = allElectives.peek();
        if (bestCourse.getNrt() == 0) {
            int minDif = Integer.MAX_VALUE;
            for (Course course : allElectives) {
                if (course.getNrt() == 0 && course.getDifficulty() < minDif) {
                    minDif = course.getNrt();
                    bestCourse = course;
                }
            }
            // Put the bestCourse on top of the queue
            bestCourse.setNrt(6);
        }

        bestCourse = allElectives.poll();
        return bestCourse;
    }

    /**--- END OF ALGORITHM 1 ---**/


//    public void topologicalSort() {
//        // Create a array to store
//        // indegrees of all
//        // vertices. Initialize all
//        // indegrees as 0.
//        int indegree[] = new int[V];
//
//        // Traverse adjacency lists
//        // to fill indegrees of
//        // vertices. This step takes
//        // O(V+E) time
//        for (int i = 0; i < V; i++) {
//            ArrayList<Integer> temp
//                    = (ArrayList<Integer>)adj[i];
//            for (int node : temp) {
//                indegree[node]++;
//            }
//        }
//
//        // Create a queue and enqueue
//        // all vertices with indegree 0
//        Queue<Integer> q
//                = new LinkedList<Integer>();
//        for (int i = 0; i < V; i++) {
//            if (indegree[i] == 0)
//                q.add(i);
//        }
//
//        // Initialize count of visited vertices
//        int cnt = 0;
//
//        // Create a vector to store result
//        // (A topological ordering of the vertices)
//        Vector<Integer> topOrder = new Vector<Integer>();
//        while (!q.isEmpty()) {
//            // Extract front of queue
//            // (or perform dequeue)
//            // and add it to topological order
//            int u = q.poll();
//            topOrder.add(u);
//
//            // Iterate through all its
//            // neighbouring nodes
//            // of dequeued node u and
//            // decrease their in-degree
//            // by 1
//            for (int node : adj[u]) {
//                // If in-degree becomes zero,
//                // add it to queue
//                if (--indegree[node] == 0)
//                    q.add(node);
//            }
//            cnt++;
//        }
//
//        // Check if there was a cycle
//        if (cnt != V) {
//            System.out.println(
//                    "There exists a cycle in the graph");
//            return;
//        }
//
//        // Print topological order
//        for (int i : topOrder) {
//            System.out.print(i + " ");
//        }
//    }



    // Print Graph
    public void printGraph() {
        int count = 0;
        for (Map.Entry<Course, List<Course>> entry : graph.entrySet()) {
            Course key = entry.getKey();
            if (key.isMandatory()) {
                count++;
            }
            List<Course> value = entry.getValue();
            System.out.println("\nAdjacency list of vertex " + key.getName());
            System.out.print("head");
            for(Course c : value) {
                System.out.print(" -> " + c.getName());
            }
            System.out.println();
        }
        System.out.println(count);
    }
}

import java.util.*;

public class CourseGraph {
    private Map<Course, List<Course>> graph;
    private PriorityQueue<Course> allElectives;
    private ArrayList<Course> allCourses;
    private CourseSet courseSet;
    int electiveHours = 0;

    // Constructor
    public CourseGraph(String path) {
        this.courseSet = new CourseSet(path);
        this.graph = courseSet.toAdjacencyList(false);
        this.allElectives = courseSet.getElectives();
        this.allCourses = courseSet.getAllCourses();
    }

    // Copy Constructor
    public CourseGraph(CourseGraph copiedGraph) {
        courseSet = copiedGraph.getAllCourses();
        this.graph = courseSet.toAdjacencyList(false);
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
                //System.out.println("PriQ is" + allElectives);
                Course bestCourse = allElectives.poll();
                //System.out.println("Best Course is " + bestCourse);
                courseSet.setAsMandatory(bestCourse);
                electiveHours += bestCourse.getCreditHour();
            }
        }
        // Update the graph with selected electives
        graph = courseSet.toAdjacencyList(true);
    }

    /**--- END OF ALGORITHM 1 ---**/


    /**--- ALGORITHM 2 ---**/

    public void selectCourses() {
        Map<Course, Integer> indegree = getIndegree();
        int termCount = 0;
        Queue<Course> headQueue = new LinkedList<>();
        PriorityQueue sameLevelCourses =  new PriorityQueue<Course>(Comparator.comparingInt(o -> -getImportance(o)));
    }

    private int getImportance(Course course) {
        // Number of post-requisites - number of pre-requisites
        return fullLength(graph.get(course), false) - fullLength(course.getPreReqs(), true);
    }

    private int fullLength(List<Course> requisites, boolean isPreReq) {
        int length = requisites.size();
        for (Course course : requisites) {
            if (isPreReq) length += fullLength(course.getPreReqs(), true);
            else length += fullLength(graph.get(course), false);
        }
        return length;
    }


//    public void topologicalSort() {

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



    public Map<Course, Integer> getIndegree() {
        Map<Course, Integer> indegree = new HashMap<>();
        for (Map.Entry<Course, List<Course>> entry : graph.entrySet()) {
            for(Course course : entry.getValue()) {
                if (!indegree.keySet().contains(course)) {
                    indegree.put(course, 0);
                } else {
                    indegree.replace(course, indegree.get(course) + 1);
                }
            }
        }
        return indegree;
    }

    // Print Graph
    public void printGraph() {
        int count = 0;
        for (Map.Entry<Course, List<Course>> entry : graph.entrySet()) {
            Course key = entry.getKey();
            count++;

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

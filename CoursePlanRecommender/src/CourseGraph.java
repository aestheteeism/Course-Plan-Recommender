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
        this.allCourses = courseSet.getAllCourses();
    }

    public CourseSet getAllCourses() {
        return courseSet;
    }

    public Map<Course, List<Course>> getAdjList() {
        return graph;
    }


    // Function to add an edge to graph
    public void addEdge(Course u, Course v) {
        graph.get(u).add(v);
    }

    /**--- ALGORITHM 1 ---**/

    public void addElectives() {
        while (electiveHours < 21) {
            if (!allElectives.isEmpty()) {
                Course bestCourse = allElectives.poll();
                courseSet.setAsMandatory(bestCourse);
                electiveHours += bestCourse.getCreditHour();
            }
        }
        // Update the graph with selected electives
        graph = courseSet.toAdjacencyList(true);
    }

    private Course selectBest(PriorityQueue<Course> allElectives) {
        Course bestCourse = allElectives.peek();
        if (bestCourse.getNrt() == 0) {
            int minDif = Integer.MAX_VALUE;
            for (Course course : allElectives) {
                if (course.getNrt() == 0 && course.getDifficulty() < minDif) {
                    minDif = course.getDifficulty();
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


    /**--- ALGORITHM 2 ---**/

    public void selectCourses() {
        int termCount = 0;
        Queue<Course> headQueue = new LinkedList<>();
        PriorityQueue sameLevelCourses =  new PriorityQueue<Course>(Comparator.comparingInt(o -> -getImportance(o)));
        CourseGraph sortingGraph = new CourseGraph(this);
        Map<Course, Integer> indegreeList = getIndegree(sortingGraph);
        ArrayList<Course> startingNodes = getStartingNodes(sortingGraph);
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

    public ArrayList<Course> getStartingNodes(CourseGraph courseGraph) {
        ArrayList<Course> startingNodes = new ArrayList<>();
        Map<Course, Integer> indegreeList = getIndegree(courseGraph);

        for (Course course : indegreeList.keySet()) {
            if (indegreeList.get(course) == 0) {
                startingNodes.add(course);
            }
        }

        return startingNodes;
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



    public Map<Course, Integer> getIndegree(CourseGraph courseGraph) {
        Map<Course, Integer> indegree = new HashMap<>();
        Map<Course, List<Course>> adjList = courseGraph.getAdjList();

        for (Map.Entry<Course, List<Course>> entry : adjList.entrySet()) {
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

    /**--- END OF ALGORITHM 2 ---**/

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

    // Print Selected Courses
    public void printMandatory() {
        int count = 0;
        for (Map.Entry<Course, List<Course>> entry : graph.entrySet()) {
            Course key = entry.getKey();
            count++;
            System.out.println(key.getName());
        }
        System.out.println(count);
    }
}

import java.util.*;

public class CourseGraph {
    private Map<Course, List<Course>> majorGraph;
    private PriorityQueue<Course> allElectives;
    private ArrayList<Course> allCourses;
    private CourseSet courseSet;
    int electiveHours = 0;

    // Constructor
    public CourseGraph(String path) {
        this.courseSet = new CourseSet(path);
        this.majorGraph = courseSet.toAdjacencyList(false);
        this.allElectives = courseSet.getElectives();
        this.allCourses = courseSet.getAllCourses();
    }

    // Copy Constructor
    public CourseGraph(CourseGraph copiedGraph) {
        courseSet = copiedGraph.getCourseSet();
        this.majorGraph = courseSet.toAdjacencyList(false);
        this.allElectives = courseSet.getElectives();
        this.allCourses = courseSet.getAllCourses();
    }

    public CourseSet getCourseSet() {
        return courseSet;
    }

    public ArrayList<Course> getAllCourses() {
        return allCourses;
    }

    public Map<Course, List<Course>> getAdjList() {
        return majorGraph;
    }

    // Function to add an edge to graph
    public void addEdge(Course u, Course v) {
        majorGraph.get(u).add(v);
    }

    // Function to delete an edge to graph
    public void deleteEdge(Course u, Course v) {
        majorGraph.get(u).remove(v);
    }

    // Function to clear all edges of a node
    public void clearEdges(Course u) {
        majorGraph.put(u, new ArrayList<Course>());
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
        majorGraph = courseSet.toAdjacencyList(true);
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

    public void selectCourses(CoursePlan coursePlan) {
        int termCount = 0;
        Queue<Course> headQueue = new LinkedList<>();
        PriorityQueue<Course> sameLevelCourses =  new PriorityQueue<Course>(Comparator.comparingInt(o -> -getImportance(o)));
        CourseGraph sortingGraph = new CourseGraph(this);
        Map<Course, Integer> indegreeList = getIndegree(sortingGraph);
        ArrayList<Course> startingNodes = getStartingNodes(sortingGraph);

        for (Course course : startingNodes) {
            sameLevelCourses.add(course);
        }

        while (!sameLevelCourses.isEmpty()) {
            Course course = sameLevelCourses.poll();
            headQueue.add(course);
        }

        headQueue.add(null);
        while (!headQueue.isEmpty()) {
            Course course = headQueue.poll();

            if (course == null) {
                termCount += 2;
                headQueue.add(null);
            } else {
                coursePlan.addMajorCourse(course, termCount);
            }

            List<Course> adjacentCourses = sortingGraph.getAdjList().get(course);
            sortingGraph.clearEdges(course);
            for (Course nextCourse : adjacentCourses) {
                indegreeList.put(nextCourse, indegreeList.get(nextCourse) - 1);
                if (indegreeList.get(nextCourse) == 0) {
                    sameLevelCourses.add(nextCourse);
                }
            }

            while (!sameLevelCourses.isEmpty()) {
                course = sameLevelCourses.poll();
                headQueue.add(course);
            }
        }

    }

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

    private int getImportance(Course course) {
        // Number of post-requisites - number of pre-requisites
        return fullLength(majorGraph.get(course), false) - fullLength(course.getPreReqs(), true);
    }

    private int fullLength(List<Course> requisites, boolean isPreReq) {
        if (requisites == null) {
            return 0;
        } else {
            int length = requisites.size();
            for (Course course : requisites) {
                if (isPreReq) length += fullLength(course.getPreReqs(), true);
                else length += fullLength(majorGraph.get(course), false);
            }
            return length;
        }
    }

    /**--- END OF ALGORITHM 2 ---**/

    // Print Graph
    public void printGraph() {
        int count = 0;
        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
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
        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
            Course key = entry.getKey();
            if (key.isMandatory()) {
                count++;
                System.out.println(key.getName());
            }
        }
        System.out.printf("In total: %d courses\n", count);
        System.out.println("=============================\n");
    }
}

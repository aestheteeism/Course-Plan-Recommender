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
        CourseSet courseSet = new CourseSet(path);
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

}

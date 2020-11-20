import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class CoursePlan {
    private Map<Course, List<Course>> graph;
    private PriorityQueue<Course> allElectives;
    private ArrayList<Course> allCourses;
    private CourseSet courseSet;
    int electiveHours = 0;

    // Constructor
    public CoursePlan(String path) {
        this.courseSet = new CourseSet(path);
        this.graph = courseSet.toAdjacencyList(false);
        this.allElectives = courseSet.getElectives();
        this.allCourses = courseSet.getAllCourses();
    }

    // Copy Constructor
    public CoursePlan(CourseGraph copiedGraph) {
        courseSet = copiedGraph.getAllCourses();
        this.graph = courseSet.toAdjacencyList(false);
        this.allElectives = courseSet.getElectives();
    }
}

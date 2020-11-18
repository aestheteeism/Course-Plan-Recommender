import java.util.*; 

public class Main {

	public static void main(String[] args) {
//		final int minGH = 96;
//		CourseSet allCourses = new CourseSet("/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/allCourses.txt");
//		Map<Course, List<Course>> electiveGraph = allCourses.toAdjacencyList();
//		printGraph(electiveGraph);

		String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/allCourses.txt";
		CourseGraph graph = new CourseGraph(path);

		graph.printGraph();

		graph.addElectives();

		graph.printGraph();
	}


}

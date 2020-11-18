import java.util.*; 

public class Main {

	public static void main(String[] args) {
//		final int minGH = 96;
//		CourseSet allCourses = new CourseSet("/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/allCourses.txt");
//		Map<Course, List<Course>> electiveGraph = allCourses.toAdjacencyList();
//		printGraph(electiveGraph);

		//String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/allCourses.txt";
		String path = "mandatoryCourses.txt";
		CourseGraph majorGraph = new CourseGraph(path);

		majorGraph.printGraph();

		majorGraph.addElectives();

		majorGraph.printGraph();
		
		getMiamiPlan();
		
	}


	public static void getMiamiPlan() {
		CourseSet foundationI = new CourseSet("foundationI.txt"); 
		System.out.println(foundationI.getAllCourses());
		
		CourseSet foundationIIA = new CourseSet("foundationIIA.txt"); 
		System.out.println(foundationIIA.getAllCourses());
		
		CourseSet foundationIIB = new CourseSet("foundationIIB.txt"); 
		System.out.println(foundationIIB.getAllCourses());
		
		CourseSet foundationIIC = new CourseSet("foundationIIC.txt"); 
		System.out.println(foundationIIC.getAllCourses());
	}
}

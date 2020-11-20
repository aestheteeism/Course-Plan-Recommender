import java.util.*; 

public class Main {

	public static void main(String[] args) {
//		final int minGH = 96;
//		CourseSet allCourses = new CourseSet("/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/allCourses.txt");
//		Map<Course, List<Course>> electiveGraph = allCourses.toAdjacencyList();
//		printGraph(electiveGraph);

		String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/majorCourses.txt";
//		String path = "majorCourses.txt";

		CourseGraph majorGraph = new CourseGraph(path);
		majorGraph.printGraph();

		majorGraph.addElectives();
		majorGraph.printGraph();

		majorGraph.printMandatory();
		
//		getMiamiPlan();
		
	}


	public static void getMiamiPlan() {
		String head = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/";
//		String head = "";

		CourseSet foundationI = new CourseSet(head + "foundationI.txt");
		System.out.println("Foundation I: " + foundationI.getAllCourses());
		
		CourseSet foundationIIA = new CourseSet(head + "foundationIIA.txt");
		System.out.println("Foundation IIA: " + foundationIIA.getAllCourses());
		
		CourseSet foundationIIB = new CourseSet(head + "foundationIIB.txt");
		System.out.println("Foundation IIB: " + foundationIIB.getAllCourses());
		
		CourseSet foundationIIC = new CourseSet(head + "foundationIIC.txt");
		System.out.println("Foundation IIC: " + foundationIIC.getAllCourses());
		
		CourseSet foundationIII = new CourseSet(head + "foundationIII.txt");
		System.out.println("Foundation III: " + foundationIII.getAllCourses());
		
		CourseSet foundationIVA = new CourseSet(head + "foundationIVA.txt");
		System.out.println("Foundation IVA: " + foundationIVA.getAllCourses());
		
		CourseSet foundationIVB = new CourseSet(head + "foundationIVB.txt");
		System.out.println("Foundation IVB: " + foundationIVB.getAllCourses());
		
		CourseSet foundationV = new CourseSet(head + "foundationV.txt");
		System.out.println("Foundation V: " + foundationV.getAllCourses());
	}
}

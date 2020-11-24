import java.util.*; 

public class Main {

	public static void main(String[] args) {
//		String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/majorCourses.txt"; //for local usage
		String path = "majorCourses.txt";
		CourseGraph majorGraph = new CourseGraph(path);
		CoursePlan coursePlan = new CoursePlan();

		System.out.println("======== FULL SET OF COURSES: ========");
		majorGraph.printAllCourses();

		System.out.println("======== AFTER SELECTING MAJOR ELECTIVES (ALGO 1): ========");
		majorGraph.addElectives();
		majorGraph.printMandatory();

		System.out.println("======== AFTER ARRANGING MAJOR COURSES (ALGO 2): ========");
		majorGraph.selectCourses(coursePlan);

		coursePlan.printCoursePlan();
		System.out.println("=====================================================\n\n");

		System.out.println("======== AFTER ADDING MIAMI PLAN COURSES (ALGO 3): ========");
		majorGraph.selectMiamiPlan(coursePlan);
		coursePlan.printCoursePlan();
		System.out.println("=====================================================\n\n");
	}
}

import java.util.*; 

public class Main {

	public static void main(String[] args) {
		String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/majorCourses.txt";
//		String path = "majorCourses.txt";
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

//	COURSE PLAN:
//	Term 1 : CSE 174 || CEC 101 || MTH 151 || ENG 111 || CSE 262 || STC 135 ||
//	Credit Hours: 18 || Difficilty: 10
//
//	Term 2 : ECO 201 || CSE 271 || CSE 102 || MTH 231 || STA 301 ||
//	Credit Hours: 15 || Difficilty: 10
//
//	Term 3 : ENG 313 || CSE 274 || CSE 278 ||
//	Credit Hours: 9  || Difficilty: 8
//
//	Term 4 : CSE 201 || CSE 385 || CSE 474 ||
//	Credit Hours: 9  || Difficilty: 10
//
//	Term 5 : CSE 432 || CSE 465 || CSE 374 ||
//	Credit Hours: 9  || Difficilty: 12
//
//	Term 6 : CSE 486 || CSE 383 || CSE 381 ||
//	Credit Hours: 9  || Difficilty: 10
//
//	Term 7 : CSE 448 || CSE 484 || CSE 451 ||
//	Credit Hours: 8  || Difficilty: 12
//
//	Term 8 : CSE 467 || CSE 449 ||
//	Credit Hours: 5  || Difficilty: 8


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

import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {
        testAlgo1();
        testAlgo2();
        testAlgo3();
    }

    public static void testAlgo2() {
        String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/majorCourses.txt";
//		String path = "majorCourses.txt";
        CourseGraph majorGraph = new CourseGraph(path);
        CoursePlan coursePlan = new CoursePlan();

        System.out.println("\n\n=========== TESTING ALGO 2: ===========\n");


        majorGraph.addElectives();
        System.out.println("======== RUNNING ALGO 2: ========");
        majorGraph.selectCourses(coursePlan);
        coursePlan.printCoursePlan();
        System.out.println("=====================================================\n\n");


        CourseGraph majorGraph1 = new CourseGraph(path);
        CoursePlan coursePlan1 = new CoursePlan();
        majorGraph1.addElectives();
        System.out.println("======== RUNNING TOPOLOGICAL SORT: ========");
        majorGraph1.topologicalSortCourses(coursePlan1);
        coursePlan1.printCoursePlan();
        System.out.println("=====================================================\n\n");

        System.out.printf("%-25s %10s %10s\n", "", "Algo 2", "Topo Sort");
        System.out.printf("%-25s %10f %10f\n", "Mean Credit Hours:", coursePlan.meanCreHours(), coursePlan1.meanCreHours());
        System.out.printf("%-25s %10f %10f\n", "Variance of Credit Hours:", coursePlan.varianceCreHours(), coursePlan1.varianceCreHours());
        System.out.printf("%-25s %10f %10f\n", "Average Term Score:", coursePlan.calculateScore(), coursePlan1.calculateScore());
        System.out.printf("%-25s %10f %10f\n", "Variance of Term Score:", coursePlan.varianceScore(), coursePlan1.varianceScore());
    }

    public static void testAlgo3() {
        String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/majorCourses.txt";
//		String path = "majorCourses.txt";
        CourseGraph majorGraph = new CourseGraph(path);
        CoursePlan coursePlan = new CoursePlan();

        System.out.println("\n\n=========== TESTING ALGO 3: ===========\n");

        majorGraph.addElectives();
        majorGraph.selectCourses(coursePlan);

        majorGraph.selectMiamiPlan(coursePlan);
        ArrayList<Course> fullMP = majorGraph.getMiamiPlan();
        ArrayList<Course> selectedMP = majorGraph.getAllSelectedMP();

        System.out.printf("%-28s %10s %10s\n", "", "Selected", "Full");
        System.out.printf("%-28s %10f %10f\n", "Mean Difficulty:", meanDifficulty(selectedMP), meanDifficulty(fullMP));
        System.out.printf("%-28s %10f %10f\n", "Variance of Difficulty:", varianceDifficulty(selectedMP), varianceDifficulty(fullMP));
        System.out.printf("%-28s %10f %10f\n", "Mean Interest Level:", meanNRT(selectedMP), meanNRT(fullMP));
        System.out.printf("%-28s %10f %10f\n", "Variance of Interest Level:", varianceNRT(selectedMP), varianceNRT(fullMP));

    }

    public static void testAlgo1() {
        String path = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/majorCourses.txt";
//		String path = "majorCourses.txt";
        CourseGraph majorGraph = new CourseGraph(path);
        CoursePlan coursePlan = new CoursePlan();

        System.out.println("\n\n=========== TESTING ALGO 1: ===========\n");

        ArrayList<Course> fullElectives = majorGraph.getAllMajorElectives();
        majorGraph.addElectives();
        ArrayList<Course> selectedElectives = majorGraph.getAllSelectedElectives();

        System.out.printf("%-28s %10s %10s\n", "", "Selected", "Full");
        System.out.printf("%-28s %10f %10f\n", "Mean Difficulty:", meanDifficulty(selectedElectives), meanDifficulty(fullElectives));
        System.out.printf("%-28s %10f %10f\n", "Variance of Difficulty:", varianceDifficulty(selectedElectives), varianceDifficulty(fullElectives));
        System.out.printf("%-28s %10f %10f\n", "Mean Interest Level:", meanNRT(selectedElectives), meanNRT(fullElectives));
        System.out.printf("%-28s %10f %10f\n", "Variance of Interest Level:", varianceNRT(selectedElectives), varianceNRT(fullElectives));

    }

        public static double varianceDifficulty(ArrayList<Course> list) {
        double mean = meanDifficulty(list);

        // Variance
        double variance = 0;
        for (int i = 0; i < list.size(); i++) {
            variance += Math.pow(list.get(i).getDifficulty() - mean, 2);
        }
        variance /= list.size();

        return variance;
    }

    public static double meanDifficulty(ArrayList<Course> list) {
        // Mean
        double mean = 0.0;
        for (int i = 0; i < list.size(); i++) {
            mean += list.get(i).getDifficulty();
        }
        mean /= list.size();

        return mean;
    }

    public static double varianceNRT(ArrayList<Course> list) {
        double mean = meanNRT(list);

        // Variance
        double variance = 0;
        for (int i = 0; i < list.size(); i++) {
            variance += Math.pow(list.get(i).getNrt() - mean, 2);
        }
        variance /= list.size();

        return variance;
    }

    public static double meanNRT(ArrayList<Course> list) {
        // Mean
        double mean = 0.0;
        for (int i = 0; i < list.size(); i++) {
            mean += list.get(i).getNrt();
        }
        mean /= list.size();

        return mean;
    }
}

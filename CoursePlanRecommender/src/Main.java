import java.util.*; 

public class Main {

	public static void main(String[] args) {
		final int minGH = 96;
		AllCourses allCourses = new AllCourses(); 
		allCourses.readFile("allCourses.txt");
		Map<Course, List<Course>> electiveGraph = allCourses.toAdjacencyList();
		printGraph(electiveGraph);
	}
	
    static void printGraph(Map<Course, List<Course>> graph) { 
    	for (Map.Entry<Course, List<Course>> entry : graph.entrySet()) {
    	    Course key = entry.getKey();
    	    List<Course> value = entry.getValue();
    	    System.out.println("\nAdjacency list of vertex " + key.getName());
    	    System.out.print("head");
    	    for(Course c : value) {
    	    	System.out.print(" -> " + c.getName());
    	    }
    	    System.out.println();
    	}
    } 

}

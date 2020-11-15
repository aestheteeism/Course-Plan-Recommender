import java.util.List;

public class Main {

	public static void main(String[] args) {
		final int minGH = 96; 
		AllMandatoryCourses allMandatoryCourses = new AllMandatoryCourses(); 
		allMandatoryCourses.readFile("mandatoryCourses.txt"); 
		List<Course> mandatoryCourses = allMandatoryCourses.getMandatoryCourses(); 
		
		for(Course c : mandatoryCourses) {
			System.out.println(c.toString());
		}
	}


}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AllMandatoryCourses {

	private ArrayList<Course> mandatoryCourses;

	public AllMandatoryCourses() {
		mandatoryCourses = new ArrayList<Course>();
	}

	public void readFile(String path) {
		File inputMandatory = new File(path); 
		Scanner sc;
		try {
			sc = new Scanner(inputMandatory);
			while(sc.hasNextLine()) {
				Course course = new Course(); 
				String line = sc.nextLine();
				String[] data = line.split("\t");
				
				course.setId(Integer.parseInt(data[0]));
				course.setName(data[1]); 
				course.setCreditHour(Integer.parseInt(data[2])); 
				course.setDifficulty(Integer.parseInt(data[3])); 
				course.setNrt(Integer.parseInt(data[4]));
				ArrayList<Course> prereqs = new ArrayList<Course>(); 
				if(data.length >= 5) {
					for(int i = 5; i < data.length; i++) {
						prereqs.add(getCourseByName(data[i])); 
					}
				}
				course.setPrereqs(prereqs); 
				mandatoryCourses.add(course); 

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public Course getCourseByName(String name) {
		for(Course course : mandatoryCourses) {
			if(course.getName().contains(name)) {
				return course;
			}
		}
		return null; 
	}

	public ArrayList<Course> getMandatoryCourses() {
		return mandatoryCourses;
	}

	public void setMandatoryCourses(ArrayList<Course> mandatoryCourses) {
		this.mandatoryCourses = mandatoryCourses;
	}


}

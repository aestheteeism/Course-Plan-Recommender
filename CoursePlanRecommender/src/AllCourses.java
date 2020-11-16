import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AllCourses {

	private ArrayList<Course> allCourses;

	public AllCourses() {
		allCourses = new ArrayList<Course>();
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
				course.setMandatory(Boolean.parseBoolean(data[5])); 
				ArrayList<Course> prereqs = new ArrayList<Course>(); 
				if(data.length >= 6) {
					for(int i = 6; i < data.length; i++) {
						prereqs.add(getCourseByName(data[i])); 
					}
				}
				course.setPrereqs(prereqs); 
				allCourses.add(course); 

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public Course getCourseByName(String name) {
		for(Course course : allCourses) {
			if(course.getName().contains(name)) {
				return course;
			}
		}
		return null; 
	}

	public ArrayList<Course> getMandatoryCourses() {
		return allCourses;
	}

	public void setMandatoryCourses(ArrayList<Course> mandatoryCourses) {
		this.allCourses = mandatoryCourses;
	}

	public Map<Course, List<Course>> toAdjacencyList() {
		Map<Course, List<Course>> adjList = new TreeMap<Course, List<Course>>();  
		for (Course course : allCourses) {
			Course src = course;
			List<Course> lst = new ArrayList<Course>(); 
			ArrayList<Course> prereqs = course.getPrereqs(); 
			for(Course prereq : prereqs) {
				if(prereq != null) {
					adjList.get(prereq).add(course);
				}		
			}
			adjList.put(src, lst);
		}
		return adjList; 
	}


}

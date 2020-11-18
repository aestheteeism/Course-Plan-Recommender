import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CourseSet {

	private ArrayList<Course> allCourses;
	private PriorityQueue<Course> allElectives;


	public CourseSet(String path) {
		allCourses = new ArrayList<Course>();
		allElectives = new PriorityQueue<Course>(Comparator.comparingInt(o -> -o.getNrt()));
		readFile(path);
	}

	public void readFile(String path) {
		File inputMandatory = new File(path); 
		Scanner sc;
		try {
			sc = new Scanner(inputMandatory);
			while (sc.hasNextLine()) {
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
				if (data.length >= 6) {
					for(int i = 6; i < data.length; i++) {
						prereqs.add(getCourseByName(data[i])); 
					}
				}
				course.setPrereqs(prereqs); 
				allCourses.add(course);
				if (!course.isMandatory()) {
					allElectives.add(course);
				}
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

	public PriorityQueue<Course> getElectives() {
		return allElectives;
	}

	public ArrayList<Course> getAllCourses() {
		return allCourses;
	}

	public void setAsMandatory(Course elective) {
		for (Course course : allCourses) {
			if (course.getName().equals(elective.getName())) {
				course.setMandatory(true);
				break;
			}
		}
	}

	public Map<Course, List<Course>> toAdjacencyList() {
		Map<Course, List<Course>> adjList = new TreeMap<Course, List<Course>>();  
		for (Course course : allCourses) {
			Course src = course;
			List<Course> lst = new ArrayList<Course>();
			ArrayList<Course> preReqs = course.getPrereqs();
			for (Course preReq : preReqs) {
				if(preReq != null) {
					adjList.get(preReq).add(course);
				}		
			}
			adjList.put(src, lst);
		}
		return adjList; 
	}
}

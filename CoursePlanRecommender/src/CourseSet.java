import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CourseSet {

	private ArrayList<Course> allCourses;
	private PriorityQueue<Course> allElectives;


	public CourseSet(String path) {
		allCourses = new ArrayList<Course>();
		allElectives = new PriorityQueue<Course>(new Comparator<Course>() {
			@Override
			public int compare(Course c1, Course c2) {
				int compare = Integer.compare(c2.getNrt(), c1.getNrt());
				if (compare == 0) {
					compare = Integer.compare(c1.getDifficulty(), c2.getDifficulty());
				}
				return compare;				
			}
		});
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
				course.setFoundation(data[6]);
				ArrayList<Course> preReqs = new ArrayList<Course>();
				if (data.length >= 7) {
					for(int i = 7; i < data.length; i++) {
						preReqs.add(getCourseByName(data[i]));
					}
				}
				course.setPreReqs(preReqs);
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

	public Map<Course, List<Course>> toAdjacencyList(boolean mandatoryOnly) {
		Map<Course, List<Course>> adjList = new TreeMap<Course, List<Course>>();
		for (Course course : allCourses) {
			if (mandatoryOnly && !course.isMandatory()) {
				continue;
			}

			Course src = course;
			List<Course> lst = new ArrayList<Course>();
			ArrayList<Course> preReqs = course.getPreReqs();
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

import java.util.ArrayList;

public class Course {
    private int id; 
	private String name; 
	private int creditHour; 
	private int difficulty; 
	private int nrt; 
	private ArrayList<Course> prereqs;


	public Course() {}


	public Course(int id, String name, int creditHour, int difficulty, int nrt, ArrayList<Course> prereqs) {
		super();
		this.id = id;
		this.name = name;
		this.creditHour = creditHour;
		this.difficulty = difficulty;
		this.nrt = nrt;
		this.prereqs = prereqs;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCreditHour() {
		return creditHour;
	}

	public void setCreditHour(int creditHour) {
		this.creditHour = creditHour;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getNrt() {
		return nrt;
	}

	public void setNrt(int nrt) {
		this.nrt = nrt;
	}

	public ArrayList<Course> getPrereqs() {
		return prereqs;
	}

	public void setPrereqs(ArrayList<Course> prereqs) {
		this.prereqs = prereqs;
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", creditHour=" + creditHour + ", difficulty=" + difficulty
				+ ", nrt=" + nrt + ", prereqs=" + prereqs + "]";
	}


}

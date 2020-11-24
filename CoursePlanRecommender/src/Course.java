import java.util.ArrayList;

public class Course implements Comparable<Course> {
    private int id; 
	private String name; 
	private int creditHour; 
	private int difficulty; 
	private int nrt;
	private String foundation;
	private boolean isMandatory;
	private boolean isPicked;
	private ArrayList<Course> preReqs;


	public Course() {}

	public Course(int id, String name, int creditHour, int difficulty, int nrt, String foundation, boolean isMandatory,
			ArrayList<Course> preReqs) {
		super();
		this.id = id;
		this.name = name;
		this.creditHour = creditHour;
		this.difficulty = difficulty;
		this.nrt = nrt;
		this.foundation = foundation;
		this.isMandatory = isMandatory;
		this.preReqs = preReqs;
		this.isPicked = false;
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

	public String getFoundation() {
		return foundation;
	}

	public void setFoundation(String foundation) {
		this.foundation = foundation;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public ArrayList<Course> getPreReqs() {
		return preReqs;
	}

	public void setPreReqs(ArrayList<Course> preReqs) {
		this.preReqs = preReqs;
	}

	public boolean isPicked() {
		return isPicked;
	}

	public void setIsPicked(boolean isPicked) {
		this.isPicked = isPicked;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", creditHour=" + creditHour + ", difficulty=" + difficulty
				+ ", nrt=" + nrt + ", isMandatory=" + isMandatory + "]";
	}

	@Override
	public int compareTo(Course o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.getId(), o.getId());
	}
}

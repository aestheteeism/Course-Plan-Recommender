import java.util.ArrayList;

public class Term {
    private ArrayList<Course> term;
    private int creditHours;
    private int minCreHours;
    private int maxCreHours;
    private int difficulty;
    private int maxDiff;

    public Term() {
        this(0, 12, 20, 0, 15);
    }

    public Term(int creditHours, int minCreHours, int maxCreHours, int difficulty, int maxDiff) {
        this.term = new ArrayList<>();
        this.creditHours = creditHours;
        this.minCreHours = minCreHours;
        this.maxCreHours = maxCreHours;
        this.difficulty = difficulty;
        this.maxDiff = maxDiff;
    }

    public Term(Term term) {
        this(term.getCreditHours(), term.getMinCreHours(), term.getMaxCreHours(), term.getDifficulty(), term.getMaxDiff());
    }

    public int getCreditHours() {
        return creditHours;
    }

    public int getMinCreHours() {
        return minCreHours;
    }

    public int getMaxCreHours() {
        return maxCreHours;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMaxDiff() {
        return maxDiff;
    }

    public ArrayList<Course> getTerm() {
        return term;
    }

    public void updateCreditHours(int creditHours) {
        this.creditHours += creditHours;
    }

    public void setMinCreHours(int minCreHours) {
        this.minCreHours = minCreHours;
    }

    public void setMaxCreHours(int maxCreHours) {
        this.maxCreHours = maxCreHours;
    }

    public void updateDifficulty(int difficulty) {
        this.difficulty += difficulty;
    }

    public void setMaxDiff(int maxDiff) {
        this.maxDiff = maxDiff;
    }

    public void addCourse(Course course) {
        this.term.add(course);
    }

    public void printCourses() {
        for (Course course : term) {
            System.out.printf(course.getName().substring(0, 7) + " || ");
        }
        System.out.printf("%nCredit Hours: %-2d || Difficilty: %-2d%n%n", creditHours, difficulty);
    }
}

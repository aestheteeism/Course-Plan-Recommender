import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class CoursePlan {
    private Term[] coursePlan;

    // Constructor
    public CoursePlan() {
        this.coursePlan = new Term[8];
        for (int i = 0; i < coursePlan.length; i++) {
            coursePlan[i] = new Term();
        }
    }

    public void addMajorCourse(Course course, int termCount) {
        if (!course.isPicked()) {
            for (int i = termCount; i < coursePlan.length; i++) {
                Term term = coursePlan[i];
                if (term.getCreditHours() + course.getCreditHour() <= term.getMaxCreHours()
                        && term.getDifficulty() + course.getDifficulty() <= term.getMaxDiff() - 3) {
                    term.addCourse(course);
                    term.updateCreditHours(course.getCreditHour());
                    term.updateDifficulty(course.getDifficulty());
                    course.setIsPicked(true);
                    break;
                }
            }
        }
    }

    public void printCoursePlan() {
        System.out.println("COURSE PLAN:");

        for (int i = 0; i < coursePlan.length; i++) {
            Term term = coursePlan[i];
            System.out.printf("Term %-2d: ", i + 1);
            term.printCourses();
        }
    }

}

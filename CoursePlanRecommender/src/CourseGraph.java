import java.util.*;

public class CourseGraph {
    private Map<Course, List<Course>> majorGraph;
    private PriorityQueue<Course> allMajorElectives;
    private ArrayList<Course> allMajorCourses;
    private CourseSet majorCourseSet;
    private PriorityQueue<Course> miamiPlan;
    private Map<String, Integer> foundationCreHours;
    private ArrayList<Course> allSelectedElectives;
    private ArrayList<Course> allSelectedMP;
    private int maxElectiveHours;

    // Constructor
    public CourseGraph(String majorDataPath) {
        this.majorCourseSet = new CourseSet(majorDataPath);
        this.majorGraph = majorCourseSet.toAdjacencyList(false);
        this.allMajorElectives = majorCourseSet.getElectives();
        this.allMajorCourses = majorCourseSet.getAllCourses();
        this.miamiPlan = null;
        this.foundationCreHours = getFoundationsCre();
        this.maxElectiveHours = 21;
        this.allSelectedElectives = new ArrayList<>();
        this.allSelectedMP = new ArrayList<>();
    }

    // Copy Constructor
    public CourseGraph(CourseGraph copiedGraph) {
        majorCourseSet = copiedGraph.getMajorCourseSet();
        this.majorGraph = majorCourseSet.toAdjacencyList(false);
        this.allMajorElectives = majorCourseSet.getElectives();
        this.allMajorCourses = majorCourseSet.getAllCourses();
        this.miamiPlan = null;
        this.foundationCreHours = getFoundationsCre();
        this.maxElectiveHours = 21;
        this.allSelectedElectives = new ArrayList<>();
        this.allSelectedMP = new ArrayList<>();
    }

    public CourseSet getMajorCourseSet() {
        return majorCourseSet;
    }

    public ArrayList<Course> getMiamiPlan() {
        return new ArrayList<>(miamiPlan);
    }

    public ArrayList<Course> getAllMajorCourses() {
        return allMajorCourses;
    }

    public ArrayList<Course> getAllMajorElectives() {
        return new ArrayList<>(allMajorElectives);
    }

    public ArrayList<Course> getAllSelectedElectives() {
        return allSelectedElectives;
    }

    public ArrayList<Course> getAllSelectedMP() {
        return allSelectedMP;
    }

    public Map<Course, List<Course>> getAdjList() {
        return majorGraph;
    }

    public int getMaxElectiveHours() {
        return maxElectiveHours;
    }

    public void setMaxElectiveHours(int maxElectiveHours) {
        this.maxElectiveHours = maxElectiveHours;
    }

    // Function to add an edge to graph
    public void addEdge(Course u, Course v) {
        majorGraph.get(u).add(v);
    }

    // Function to delete an edge to graph
    public void deleteEdge(Course u, Course v) {
        majorGraph.get(u).remove(v);
    }

    // Function to clear all edges of a node
    public void clearEdges(Course u) {
        majorGraph.put(u, new ArrayList<Course>());
    }


    /**--- ALGORITHM 1 ---**/

    public void addElectives() {
        int electiveHours = 0;
        while (electiveHours < maxElectiveHours) {
            if (!allMajorElectives.isEmpty()) {
                Course bestCourse = allMajorElectives.poll();
                bestCourse.setMandatory(true);
                allSelectedElectives.add(bestCourse);
                electiveHours += bestCourse.getCreditHour();
            }
        }
        // Update the graph with selected electives
        majorGraph = majorCourseSet.toAdjacencyList(true);
    }

    /**--- END OF ALGORITHM 1 ---**/


    /**--- ALGORITHM 2 ---**/

    public void selectCourses(CoursePlan coursePlan) {
        int termCount = 0;
        Queue<Course> headQueue = new LinkedList<>();
        PriorityQueue<Course> sameLevelCourses = new PriorityQueue<Course>(new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                int compare = Integer.compare(getImportance(c2), getImportance(c1));
                if (compare == 0) {
                    compare = Double.compare(Integer.parseInt(c1.getName().substring(4,5))/100.0, Integer.parseInt(c2.getName().substring(4,5))/100.0);
                }
                return compare;
            }
        });

        Map<Course, Integer> indegreeList = getIndegree(this);
        ArrayList<Course> startingNodes = getStartingNodes(this);

        for (Course course : startingNodes) {
            sameLevelCourses.add(course);
        }

        while (!sameLevelCourses.isEmpty()) {
            Course course = sameLevelCourses.poll();
            headQueue.add(course);
        }

        headQueue.add(null);
        while (!headQueue.isEmpty()) {
            Course course = headQueue.poll();

            if (course == null) {
                termCount += 1;
                if (termCount >= 8) {
                    break;
                }
                headQueue.add(null);
            } else {
                coursePlan.addMajorCourse(course, termCount);
                if (!course.getFoundation().equals("M")) {
                    updateFoundationsCre(course.getFoundation(), course.getCreditHour());
                }

                List<Course> adjacentCourses = majorGraph.get(course);
                for (Course nextCourse : adjacentCourses) {
                    indegreeList.put(nextCourse, indegreeList.get(nextCourse) - 1);
                    if (indegreeList.get(nextCourse) == 0) {
                        sameLevelCourses.add(nextCourse);
                    }
                }

                while (!sameLevelCourses.isEmpty()) {
                    course = sameLevelCourses.poll();
                    headQueue.add(course);
                }
            }
        }
    }

    public Map<Course, Integer> getIndegree(CourseGraph courseGraph) {
        Map<Course, Integer> indegree = new HashMap<>();

        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
            Course course = entry.getKey();
            indegree.put(course, course.getPreReqs().size());
        }

        return indegree;
    }

    public ArrayList<Course> getStartingNodes(CourseGraph courseGraph) {
        ArrayList<Course> startingNodes = new ArrayList<>();
        Map<Course, Integer> indegreeList = getIndegree(courseGraph);

        for (Course course : indegreeList.keySet()) {
            if (indegreeList.get(course) == 0) {
                startingNodes.add(course);
            }
        }

        return startingNodes;
    }

    private int getImportance(Course course) {
        // Number of post-requisites - number of pre-requisites
        return fullLength(majorGraph.get(course), false) - fullLength(course.getPreReqs(), true);
    }

    private int fullLength(List<Course> requisites, boolean isPreReq) {
        int length = 0;
        if (requisites == null) {
        } else {
            for (Course course : requisites) {
                if (isPreReq) length = 1 + fullLength(course.getPreReqs(), true);
                else length = 1 + fullLength(majorGraph.get(course), false);
            }
        }
        return length;
    }

    /**--- END OF ALGORITHM 2 ---**/


    /**--- ALGORITHM 3 ---**/

    public void selectMiamiPlan(CoursePlan coursePlan) {
        miamiPlan = loadMiamiPlanSet();

        while (!foundationCreHours.isEmpty()) {
            if (!miamiPlan.isEmpty()) {
                Course bestMiamiPlan = miamiPlan.poll();
                if (updateFoundationsCre(bestMiamiPlan.getFoundation(), bestMiamiPlan.getCreditHour())) {
                    coursePlan.addMiamiPlan(bestMiamiPlan);
                    allSelectedMP.add(bestMiamiPlan);
                }
            }
        }
    }

    private Map<String, Integer> getFoundationsCre() {
        Map<String, Integer> foundations = new TreeMap<>();
        foundations.put("I", 3);
        foundations.put("IIA", 3);
        foundations.put("IIB", 3);
        foundations.put("IIC", 3);
        foundations.put("III", 6);
        foundations.put("IVA", 5);
        foundations.put("IVB", 5);
        foundations.put("V", 3);

        return foundations;
    }

    private boolean updateFoundationsCre(String courseFoundation, int creHours) {
        // Update course's foundation's credit hours left, remove any fulfilled foundation

        if (foundationCreHours.get(courseFoundation) != null) {
            int remainingCre = foundationCreHours.get(courseFoundation) - creHours;

            if (remainingCre > 0) {
                foundationCreHours.put(courseFoundation, Math.max(remainingCre, 0));
            } else {
                foundationCreHours.remove(courseFoundation);
            }

            return true;

        } else if (courseFoundation.indexOf(",") != -1) {
            String[] foundationOpt = courseFoundation.split(",");

            for (String subFoundation : foundationOpt) {
                if (updateFoundationsCre(subFoundation, creHours)) {
                    return true;
                }
            }
        }

        return false;
    }

    public PriorityQueue<Course> loadMiamiPlanSet() {
        // Path to data files
        String head = "/Users/buihq/Desktop/Huy Bui/IntelliJ/Course-Plan-Recommender/CoursePlanRecommender/";
        String path = head + "foundations.txt";
        CourseSet miamiPlanSet =  new CourseSet(path);

        return miamiPlanSet.getElectives();
    }

    /**--- END OF ALGORITHM 3 ---**/


    // Print Methods
    public void printGraph() {
        int count = 0;
        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
            Course key = entry.getKey();
            count++;

            List<Course> value = entry.getValue();
            System.out.print(key.getName());
            System.out.print(" ==>");
            for(Course c : value) {
                System.out.print(" + " + c.getName());
            }
            System.out.println();
        }
        System.out.println(count);
    }

    public void printMandatory() {
        int count = 0;
        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
            Course key = entry.getKey();
            if (key.isMandatory()) {
                count++;
                System.out.println(key.getName());
            }
        }
        System.out.printf("In total: %d courses\n", count);
        System.out.println("===================================================\n\n");
    }

    public void printAllCourses() {
        int count = 0;
        System.out.println("Core Courses:");
        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
            Course key = entry.getKey();
            if (key.isMandatory()) {
                count++;
                System.out.println(key.getName());
            }
        }
        System.out.printf("In total: %d courses\n", count);

        count = 0;
        System.out.println("\nElective Courses:");
        for (Map.Entry<Course, List<Course>> entry : majorGraph.entrySet()) {
            Course key = entry.getKey();
            if (!key.isMandatory()) {
                count++;
                System.out.println(key.getName());
            }
        }
        System.out.printf("In total: %d courses\n", count);
        System.out.println("=====================================================\n\n");
    }



    /**--- FOR TESTING ALGORITHM 2 ---**/

    public void topologicalSortCourses(CoursePlan coursePlan) {
        Queue<Course> headQueue = new LinkedList<>();
        Map<Course, Integer> indegreeList = getIndegree(this);
        ArrayList<Course> startingNodes = getStartingNodes(this);

        for (Course course : startingNodes) {
            headQueue.add(course);
        }

        int count = 0;

        while (!headQueue.isEmpty()) {
            Course course = headQueue.poll();

            coursePlan.addMajorCourse(course, 0);
            if (!course.getFoundation().equals("M")) {
                updateFoundationsCre(course.getFoundation(), course.getCreditHour());
            }

            List<Course> adjacentCourses = majorGraph.get(course);
            for (Course nextCourse : adjacentCourses) {
                indegreeList.put(nextCourse, indegreeList.get(nextCourse) - 1);
                if (indegreeList.get(nextCourse) == 0) {
                    headQueue.add(nextCourse);
                }
            }
        }
    }
}

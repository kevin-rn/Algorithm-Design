// Test:
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Scanner;
import org.junit.*;
import org.junit.rules.*;

public class UTest {

  private long time = 0;

  @Rule
  public TestName name = new TestName();

  @Before
  public void setUp() {
    time = System.currentTimeMillis();
  }

  @After
  public void tearDown() {
    System.out.println("Test '" + name.getMethodName() + "' took " + (System.currentTimeMillis() - time) + "ms");
  }

  @Test(timeout = 100)
  public void exampleOneStudentImpossible_MissingLowerBounds() {
    int n = 1;
    int m = 1;
    boolean[] student_mon = { false, true };
    boolean[] student_tues = { false, false };
    boolean[] supervisor_mon = { false, true };
    boolean[] supervisor_tues = { false, true };
    boolean[][] selected = { {}, { false, true } };
    /*
		 * This test models the situation where:
		 * Student 1 is available on Mondays
		 * Supervisor 1 is available on both days.
		 * Student 1 can be paired to supervisor 1.
		 * This is not doable since every supervisor requires at least 3 students.
		 */
    assertFalse(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
  }

  @Test(timeout = 100)
  public void exampleThreeStudentsPossible() {
    int n = 3;
    int m = 1;
    boolean[] student_mon = { false, true, true, true };
    boolean[] student_tues = { false, false, false, false };
    boolean[] supervisor_mon = { false, true };
    boolean[] supervisor_tues = { false, true };
    boolean[][] selected = { {}, { false, true }, { false, true }, { false, true } };
    /*
	   * This test models the situation where:
	   * Students 1,2,3 are available on Mondays
	   * Supervisor 1 is available on both days.
	   * Every student can be paired to supervisor 1.
	   * This is doable as it matches all criteria.
	   */
    assertTrue(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
  }

  @Test(timeout = 100)
  public void exampleSixStudentsPossible() {
    int n = 6;
    int m = 2;
    boolean[] student_mon = { false, true, true, true, false, false, false };
    boolean[] student_tues = { false, false, false, false, true, true, true };
    boolean[] supervisor_mon = { false, true, true };
    boolean[] supervisor_tues = { false, true, false };
    boolean[][] selected = { {}, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true } };
    /*
		 * This test models the situation where:
		 * Students 1,2,3 are available on Mondays and Students 4,5,6 are available on Tuesday.
		 * Supervisor 1 is available on both days and supervisor 2 only on Mondays.
		 * Every student can be paired to supervisor 1 and supervisor 2.
		 * This is doable as we can match students 1,2,3 to supervisor 2 and 4,5,6 to supervisor 1.
		 */
    assertTrue(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
  }

  @Test(timeout = 100)
  public void exampleSixStudentsImpossible() {
    int n = 6;
    int m = 2;
    boolean[] student_mon = { false, true, true, true, true, false, false };
    boolean[] student_tues = { false, false, false, false, false, true, true };
    boolean[] supervisor_mon = { false, false, true };
    boolean[] supervisor_tues = { false, true, false };
    boolean[][] selected = { {}, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true }, { false, true, true } };
    /*
		 * This test models the situation where:
		 * Students 1,2,3,4 are available on Mondays and Students 5,6 are available on Tuesday.
		 * Supervisor 1 is only available on Tuesdays and supervisor 2 only on Mondays.
		 * Every student can be paired to supervisor 1 and supervisor 2.
		 * This is impossible as we cannot give the minimum of 3 students to supervisor 2.
		 */
    assertFalse(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
  }

  @Test(timeout = 100)
  public void exampleTooManyToFitIntoOneDay_MissingSupplyDemandOnSourceSink() {
    int n = 12;
    int m = 1;
    boolean[] student_mon = new boolean[n + 1];
    boolean[] student_tues = new boolean[n + 1];
    for (int i = 1; i <= n; i++) {
      student_mon[i] = true;
      student_tues[i] = false;
    }
    boolean[] supervisor_mon = { false, true };
    boolean[] supervisor_tues = { false, false };
    boolean[][] selected = new boolean[n + 1][m + 1];
    for (int i = 1; i <= n; i++) {
      selected[i][1] = true;
    }
    /*
		 * This test models the situation where:
		 * Students 1 through 12 are available on Monday
		 * Supervisor 1 is available on Monday.
		 * Every student can be paired to supervisor 1.
		 * This is impossible as the supervisor can see at most 2 groups of at most 5 students on Monday.
		 */
    assertFalse(ResearchProject.areThereGroups(n, m, student_mon, student_tues, supervisor_mon, supervisor_tues, selected));
  }

  private static boolean parseOutputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    boolean ans = sc.nextBoolean();
    sc.close();
    return ans;
  }

  private static class ProblemInstance {

    int n;

    int m;

    boolean[] stud_mon;

    boolean[] stud_tues;

    boolean[] staff_mon;

    boolean[] staff_tues;

    boolean[][] matches;

    public ProblemInstance(int n, int m, boolean[] stud_mon, boolean[] stud_tues, boolean[] staff_mon, boolean[] staff_tues, boolean[][] matches) {
      this.n = n;
      this.m = m;
      this.stud_mon = stud_mon;
      this.stud_tues = stud_tues;
      this.staff_mon = staff_mon;
      this.staff_tues = staff_tues;
      this.matches = matches;
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    int m = sc.nextInt();
    int num_stud_monday = sc.nextInt();
    int num_stud_tuesday = sc.nextInt();
    int num_staff_monday = sc.nextInt();
    int num_staff_tuesday = sc.nextInt();
    int num_matches = sc.nextInt();
    boolean[] studentMonday = new boolean[n + 1];
    boolean[] studentTuesday = new boolean[n + 1];
    boolean[] staffMonday = new boolean[n + 1];
    boolean[] staffTuesday = new boolean[n + 1];
    for (int i = 0; i < num_stud_monday; i++) {
      studentMonday[sc.nextInt()] = true;
    }
    for (int i = 0; i < num_stud_tuesday; i++) {
      studentTuesday[sc.nextInt()] = true;
    }
    for (int i = 0; i < num_staff_monday; i++) {
      staffMonday[sc.nextInt()] = true;
    }
    for (int i = 0; i < num_staff_tuesday; i++) {
      staffTuesday[sc.nextInt()] = true;
    }
    boolean[][] matches = new boolean[n + 1][m + 1];
    for (int i = 0; i < num_matches; i++) {
      matches[sc.nextInt()][sc.nextInt()] = true;
    }
    sc.close();
    return new ProblemInstance(n, m, studentMonday, studentTuesday, staffMonday, staffTuesday, matches);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    boolean theirSolution = ResearchProject.areThereGroups(S.n, S.m, S.stud_mon, S.stud_tues, S.staff_mon, S.staff_tues, S.matches);
    boolean ourSolutions = parseOutputFile(fileName + ".out");
    assertEquals(ourSolutions, theirSolution);
  }
}

// _______________________________________________________________________________________________________________________
// Solution:
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined solely by a manual inspection of your implementation.
 */
class ResearchProject {

  /**
   *   You should implement the method below. Note that you can use the graph structure below.
   *   @param n The number of students.
   *   @param m The number of supervisors.
   *   @param student_availability_mon is an array of size (n+1) that is true iff student i is available on Monday. You should ignore student_availability_mon[0].
   *   @param student_availability_tues is an array of size (n+1) that is true iff student i is available on Tuesday. You should ignore student_availability_tues[0].
   *   @param supervisor_availability_mon is an array of size (m+1) that is true iff supervisor j is available on Monday. You should ignore supervisor_availability_mon[0].
   *   @param supervisor_availability_tues is an array of size (m+1) that is true iff supervisor j is available on Tuesday. You should ignore supervisor_availability_tues[0].
   *   @param selected is an array of size (n+1)x(m+1) that is true iff student i selected supervisor j. You should use entries selected[1][1] through selected[n][m].
   *   @return true iff there is a valid allocation of students over supervisors.
   */
  public static boolean areThereGroups(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {
    return solveWithSeparateArrays(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
  // return solveProper(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
  // return solveNoLowerBounds(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
  // return solveWithoutSupplyDemandOnSourceSink(n, m, student_availability_mon, student_availability_tues, supervisor_availability_mon, supervisor_availability_tues, selected);
  }

  private static boolean solveWithSeparateArrays(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {
    Node source = new Node(0, -n);
    Node sink = new Node(-1, n);
    Node[] students = new Node[n + 1];
    Node[] supervisors = new Node[m + 1];
    Node[] students_monday = new Node[n + 1];
    Node[] students_tuesday = new Node[n + 1];
    Node[] supervisors_monday_1 = new Node[m + 1];
    Node[] supervisors_monday_2 = new Node[m + 1];
    Node[] supervisors_tuesday_1 = new Node[m + 1];
    Node[] supervisors_tuesday_2 = new Node[m + 1];
    for (int i = 1; i <= n; i++) {
      students[i] = new Node(i, 0);
      students_monday[i] = new Node(i, 0);
      students_tuesday[i] = new Node(i, 0);
    }
    for (int j = 1; j <= m; j++) {
      supervisors[j] = new Node(j, 0);
      supervisors_monday_1[j] = new Node(j, 0);
      supervisors_monday_2[j] = new Node(j, 0);
      supervisors_tuesday_1[j] = new Node(j, 0);
      supervisors_tuesday_2[j] = new Node(j, 0);
    }
    for (int i = 1; i <= n; i++) {
      source.addEdge(students[i], 0, 1);
      if (student_availability_mon[i]) {
        students[i].addEdge(students_monday[i], 0, 1);
      }
      if (student_availability_tues[i]) {
        students[i].addEdge(students_tuesday[i], 0, 1);
      }
    }
    for (int j = 1; j <= m; j++) {
      supervisors[j].addEdge(sink, 3, 12);
      if (supervisor_availability_mon[j]) {
        supervisors_monday_1[j].addEdge(supervisors[j], 0, 5);
        supervisors_monday_2[j].addEdge(supervisors[j], 0, 5);
      }
      if (supervisor_availability_tues[j]) {
        supervisors_tuesday_1[j].addEdge(supervisors[j], 0, 5);
        supervisors_tuesday_2[j].addEdge(supervisors[j], 0, 5);
      }
    }
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (selected[i][j]) {
          students_monday[i].addEdge(supervisors_monday_1[j], 0, 1);
          students_monday[i].addEdge(supervisors_monday_2[j], 0, 1);
          students_tuesday[i].addEdge(supervisors_tuesday_1[j], 0, 1);
          students_tuesday[i].addEdge(supervisors_tuesday_2[j], 0, 1);
        }
      }
    }
    ArrayList<Node> allNodes = new ArrayList<>(2 + 3 * n + 5 * m);
    allNodes.add(source);
    allNodes.add(sink);
    for (int i = 1; i <= n; i++) {
      allNodes.add(students[i]);
      allNodes.add(students_monday[i]);
      allNodes.add(students_tuesday[i]);
    }
    for (int j = 1; j <= m; j++) {
      allNodes.add(supervisors[j]);
      allNodes.add(supervisors_monday_1[j]);
      allNodes.add(supervisors_monday_2[j]);
      allNodes.add(supervisors_tuesday_1[j]);
      allNodes.add(supervisors_tuesday_2[j]);
    }
    Graph g = new Graph(allNodes);
    return g.hasCirculation();
  }

  private static boolean solveNoLowerBounds(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {
    ArrayList<Node> allNodes = new ArrayList<>(2 + 3 * n + 5 * m);
    Node source = new Node(0, -n);
    allNodes.add(source);
    // Student nodes are 1 through n.
    for (int i = 1; i <= n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff nodes are n+1 through n+m
    for (int j = n + 1; j <= n + m; j++) {
      allNodes.add(new Node(j, 0));
    }
    // Student Monday are n+m+1 through n+m+n
    for (int i = n + m + 1; i <= n + m + n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Student Tuesday are n+m+n+1 through n+m+2n
    for (int i = n + m + n + 1; i <= n + m + n + n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff Monday are n+m+2n+1 through n+m+2n+2m
    for (int i = n + m + 2 * n + 1; i <= n + m + 2 * n + 2 * m; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff Tuesday are n+m+2n+2m+1 through n+m+2n+4m
    for (int i = n + m + 2 * n + 2 * m + 1; i <= n + m + 2 * n + 4 * m; i++) {
      allNodes.add(new Node(i, 0));
    }
    Node sink = new Node(n + m + 2 * n + 4 * m + 1, n);
    allNodes.add(sink);
    // Connect source to students
    for (int i = 1; i <= n; i++) {
      source.addEdge(allNodes.get(i), 0, 1);
    }
    // Connect staff to sink
    for (int j = n + 1; j <= n + m; j++) {
      allNodes.get(j).addEdge(sink, 0, 12);
    }
    // Connect student to days
    for (int i = 1; i <= n; i++) {
      if (student_availability_mon[i]) {
        allNodes.get(i).addEdge(allNodes.get(n + m + i), 0, 1);
      }
      if (student_availability_tues[i]) {
        allNodes.get(i).addEdge(allNodes.get(n + m + n + i), 0, 1);
      }
    }
    // Connect Staff days to staff.
    for (int j = 1; j <= m; j++) {
      if (supervisor_availability_mon[j]) {
        allNodes.get(n + m + 2 * n + j).addEdge(allNodes.get(n + j), 0, 5);
        allNodes.get(n + m + 2 * n + m + j).addEdge(allNodes.get(n + j), 0, 5);
      }
      if (supervisor_availability_tues[j]) {
        allNodes.get(n + m + 2 * n + 2 * m + j).addEdge(allNodes.get(n + j), 0, 5);
        allNodes.get(n + m + 2 * n + 3 * m + j).addEdge(allNodes.get(n + j), 0, 5);
      }
    }
    // Connect students to staff
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (selected[i][j]) {
          // Connect monday from student to Tuesday of staff
          allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + j), 0, 1);
          allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + m + j), 0, 1);
          // Connect Tuesday from student to Tuesday of staff
          allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 2 * m + j), 0, 1);
          allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 3 * m + j), 0, 1);
        }
      }
    }
    sink.addEdge(source, 0, Integer.MAX_VALUE / 2);
    Graph g = new Graph(allNodes);
    return g.hasCirculation();
  }

  private static boolean solveProper(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {
    ArrayList<Node> allNodes = new ArrayList<>(2 + 3 * n + 5 * m);
    Node source = new Node(0, -n);
    allNodes.add(source);
    // Student nodes are 1 through n.
    for (int i = 1; i <= n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff nodes are n+1 through n+m
    for (int j = n + 1; j <= n + m; j++) {
      allNodes.add(new Node(j, 0));
    }
    // Student Monday are n+m+1 through n+m+n
    for (int i = n + m + 1; i <= n + m + n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Student Tuesday are n+m+n+1 through n+m+2n
    for (int i = n + m + n + 1; i <= n + m + n + n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff Monday are n+m+2n+1 through n+m+2n+2m
    for (int i = n + m + 2 * n + 1; i <= n + m + 2 * n + 2 * m; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff Tuesday are n+m+2n+2m+1 through n+m+2n+4m
    for (int i = n + m + 2 * n + 2 * m + 1; i <= n + m + 2 * n + 4 * m; i++) {
      allNodes.add(new Node(i, 0));
    }
    Node sink = new Node(n + m + 2 * n + 4 * m + 1, n);
    allNodes.add(sink);
    // Connect source to students
    for (int i = 1; i <= n; i++) {
      source.addEdge(allNodes.get(i), 0, 1);
    }
    // Connect staff to sink
    for (int j = n + 1; j <= n + m; j++) {
      allNodes.get(j).addEdge(sink, 3, 12);
    }
    // Connect student to days
    for (int i = 1; i <= n; i++) {
      if (student_availability_mon[i]) {
        allNodes.get(i).addEdge(allNodes.get(n + m + i), 0, 1);
      }
      if (student_availability_tues[i]) {
        allNodes.get(i).addEdge(allNodes.get(n + m + n + i), 0, 1);
      }
    }
    // Connect Staff days to staff.
    for (int j = 1; j <= m; j++) {
      if (supervisor_availability_mon[j]) {
        allNodes.get(n + m + 2 * n + j).addEdge(allNodes.get(n + j), 0, 5);
        allNodes.get(n + m + 2 * n + m + j).addEdge(allNodes.get(n + j), 0, 5);
      }
      if (supervisor_availability_tues[j]) {
        allNodes.get(n + m + 2 * n + 2 * m + j).addEdge(allNodes.get(n + j), 0, 5);
        allNodes.get(n + m + 2 * n + 3 * m + j).addEdge(allNodes.get(n + j), 0, 5);
      }
    }
    // Connect students to staff
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (selected[i][j]) {
          // Connect monday from student to Tuesday of staff
          allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + j), 0, 1);
          allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + m + j), 0, 1);
          // Connect Tuesday from student to Tuesday of staff
          allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 2 * m + j), 0, 1);
          allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 3 * m + j), 0, 1);
        }
      }
    }
    sink.addEdge(source, 0, Integer.MAX_VALUE / 2);
    Graph g = new Graph(allNodes);
    return g.hasCirculation();
  }

  private static boolean solveWithoutSupplyDemandOnSourceSink(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {
    ArrayList<Node> allNodes = new ArrayList<>(n + m + 2);
    Node source = new Node(0, 0);
    allNodes.add(source);
    // Student nodes are 1 through n.
    for (int i = 1; i <= n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff nodes are n+1 through n+m
    for (int j = n + 1; j <= n + m; j++) {
      allNodes.add(new Node(j, 0));
    }
    // Student Monday are n+m+1 through n+m+n
    for (int i = n + m + 1; i <= n + m + n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Student Tuesday are n+m+n+1 through n+m+2n
    for (int i = n + m + n + 1; i <= n + m + n + n; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff Monday are n+m+2n+1 through n+m+2n+2m
    for (int i = n + m + 2 * n + 1; i <= n + m + 2 * n + 2 * m; i++) {
      allNodes.add(new Node(i, 0));
    }
    // Staff Tuesday are n+m+2n+2m+1 through n+m+2n+4m
    for (int i = n + m + 2 * n + 2 * m + 1; i <= n + m + 2 * n + 4 * m; i++) {
      allNodes.add(new Node(i, 0));
    }
    Node sink = new Node(n + m + 2 * n + 4 * m + 1, 0);
    allNodes.add(sink);
    // Connect source to students
    for (int i = 1; i <= n; i++) {
      source.addEdge(allNodes.get(i), 0, 1);
    }
    // Connect staff to sink
    for (int j = n + 1; j <= n + m; j++) {
      allNodes.get(j).addEdge(sink, 3, 12);
    }
    // Connect student to days
    for (int i = 1; i <= n; i++) {
      if (student_availability_mon[i]) {
        allNodes.get(i).addEdge(allNodes.get(n + m + i), 0, 1);
      }
      if (student_availability_tues[i]) {
        allNodes.get(i).addEdge(allNodes.get(n + m + n + i), 0, 1);
      }
    }
    // Connect Staff days to staff.
    for (int j = 1; j <= m; j++) {
      if (supervisor_availability_mon[j]) {
        allNodes.get(n + m + 2 * n + j).addEdge(allNodes.get(n + j), 0, 5);
        allNodes.get(n + m + 2 * n + m + j).addEdge(allNodes.get(n + j), 0, 5);
      }
      if (supervisor_availability_tues[j]) {
        allNodes.get(n + m + 2 * n + 2 * m + j).addEdge(allNodes.get(n + j), 0, 5);
        allNodes.get(n + m + 2 * n + 3 * m + j).addEdge(allNodes.get(n + j), 0, 5);
      }
    }
    // Connect students to staff
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (selected[i][j]) {
          // Connect monday from student to Tuesday of staff
          allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + j), 0, 1);
          allNodes.get(n + m + i).addEdge(allNodes.get(n + m + 2 * n + m + j), 0, 1);
          // Connect Tuesday from student to Tuesday of staff
          allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 2 * m + j), 0, 1);
          allNodes.get(n + m + n + i).addEdge(allNodes.get(n + m + 2 * n + 3 * m + j), 0, 1);
        }
      }
    }
    sink.addEdge(source, 0, Integer.MAX_VALUE / 2);
    Graph g = new Graph(allNodes);
    return g.hasCirculation();
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_2_2019_2020/research_project/data/secret";
    File dir = new File(dirName);
    System.out.println(dir.exists());
    for (File f : dir.listFiles()) {
      if (f.getName().endsWith("in")) {
        FileInputStream in = new FileInputStream(f);
        System.out.println(f.getAbsolutePath());
        String ans = run(in);
        System.out.println(ans);
        PrintWriter out = new PrintWriter(f.getAbsolutePath().replace(".in", ".out"));
        out.println(ans);
        out.close();
        System.out.println();
      }
    }
  }

  public static String run(InputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    int m = sc.nextInt();
    int num_stud_monday = sc.nextInt();
    int num_stud_tuesday = sc.nextInt();
    int num_staff_monday = sc.nextInt();
    int num_staff_tuesday = sc.nextInt();
    int num_matches = sc.nextInt();
    boolean[] studentMonday = new boolean[n + 1];
    boolean[] studentTuesday = new boolean[n + 1];
    boolean[] staffMonday = new boolean[n + 1];
    boolean[] staffTuesday = new boolean[n + 1];
    for (int i = 0; i < num_stud_monday; i++) {
      studentMonday[sc.nextInt()] = true;
    }
    for (int i = 0; i < num_stud_tuesday; i++) {
      studentTuesday[sc.nextInt()] = true;
    }
    for (int i = 0; i < num_staff_monday; i++) {
      staffMonday[sc.nextInt()] = true;
    }
    for (int i = 0; i < num_staff_tuesday; i++) {
      staffTuesday[sc.nextInt()] = true;
    }
    boolean[][] matches = new boolean[n + 1][m + 1];
    for (int i = 0; i < num_matches; i++) {
      matches[sc.nextInt()][sc.nextInt()] = true;
    }
    sc.close();
    boolean ans = ResearchProject.areThereGroups(n, m, studentMonday, studentTuesday, staffMonday, staffTuesday, matches);
    return Boolean.toString(ans);
  }
}

class Graph {

  private List<Node> nodes;

  private Node source;

  private Node sink;

  public Graph(List<Node> nodes) {
    this.nodes = nodes;
    this.source = null;
    this.sink = null;
  }

  public Node getSink() {
    return sink;
  }

  public Node getSource() {
    return source;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public boolean equals(Object other) {
    if (other instanceof Graph) {
      Graph that = (Graph) other;
      return this.nodes.equals(that.nodes);
    }
    return false;
  }

  public boolean hasCirculation() {
    this.removeLowerBounds();
    int D = this.removeSupplyDemand();
    int x = MaxFlow.maximizeFlow(this);
    return x == D;
  }

  private void removeLowerBounds() {
    for (Node n : this.getNodes()) {
      for (Edge e : n.edges) {
        if (e.lower > 0) {
          e.capacity -= e.lower;
          e.backwards.capacity -= e.lower;
          e.backwards.flow -= e.lower;
          e.from.d += e.lower;
          e.to.d -= e.lower;
          e.lower = 0;
        }
      }
    }
  }

  private int removeSupplyDemand() {
    int Dplus = 0, Dmin = 0;
    int maxId = 0;
    for (Node n : this.getNodes()) {
      maxId = Math.max(n.id, maxId);
    }
    Node newSource = new Node(maxId + 1, 0);
    Node newSink = new Node(maxId + 2, 0);
    for (Node n : this.getNodes()) {
      if (n.d < 0) {
        newSource.addEdge(n, 0, -n.d);
        Dmin -= n.d;
      } else if (n.d > 0) {
        n.addEdge(newSink, 0, n.d);
        Dplus += n.d;
      }
      n.d = 0;
    }
    if (Dmin != Dplus) {
      throw new IllegalArgumentException("Demand and supply are not equal!");
    }
    this.nodes.add(newSource);
    this.nodes.add(newSink);
    this.source = newSource;
    this.sink = newSink;
    return Dplus;
  }
}

class Node {

  protected int id;

  protected int d;

  protected Collection<Edge> edges;

  /**
   *  Create a new node
   *  @param id: Id for the node.
   *  @param d: demand for the node. Remember that supply is represented as a negative demand.
   */
  public Node(int id, int d) {
    this.id = id;
    this.d = d;
    this.edges = new ArrayList<Edge>();
  }

  public void addEdge(Node to, int lower, int upper) {
    Edge e = new Edge(lower, upper, this, to);
    edges.add(e);
    to.getEdges().add(e.getBackwards());
  }

  public Collection<Edge> getEdges() {
    return edges;
  }

  public int getId() {
    return id;
  }

  public boolean equals(Object other) {
    if (other instanceof Node) {
      Node that = (Node) other;
      if (id == that.getId())
        return edges.equals(that.getEdges());
    }
    return false;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getId());
    sb.append(" ");
    sb.append(this.getEdges().size());
    sb.append(":");
    for (Edge e : this.getEdges()) {
      sb.append("(");
      sb.append(e.from.getId());
      sb.append(" --[");
      sb.append(e.lower);
      sb.append(',');
      sb.append(e.capacity);
      sb.append("]-> ");
      sb.append(e.to.getId());
      sb.append(")");
    }
    return sb.toString();
  }
}

class Edge {

  protected int lower;

  protected int capacity;

  protected int flow;

  protected Node from;

  protected Node to;

  protected Edge backwards;

  private Edge(Edge e) {
    this.lower = 0;
    this.flow = e.getCapacity();
    this.capacity = e.getCapacity();
    this.from = e.getTo();
    this.to = e.getFrom();
    this.backwards = e;
  }

  protected Edge(int lower, int capacity, Node from, Node to) {
    this.lower = lower;
    this.capacity = capacity;
    this.from = from;
    this.to = to;
    this.flow = 0;
    this.backwards = new Edge(this);
  }

  public void augmentFlow(int add) {
    assert (flow + add <= capacity);
    flow += add;
    backwards.setFlow(getResidual());
  }

  public Edge getBackwards() {
    return backwards;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getFlow() {
    return flow;
  }

  public Node getFrom() {
    return from;
  }

  public int getResidual() {
    return capacity - flow;
  }

  public Node getTo() {
    return to;
  }

  private void setFlow(int f) {
    assert (f <= capacity);
    this.flow = f;
  }

  public boolean equals(Object other) {
    if (other instanceof Edge) {
      Edge that = (Edge) other;
      return this.capacity == that.capacity && this.flow == that.flow && this.from.getId() == that.getFrom().getId() && this.to.getId() == that.getTo().getId();
    }
    return false;
  }
}

class MaxFlow {

  private static List<Edge> findPath(Graph g, Node start, Node end) {
    Map<Node, Edge> mapPath = new HashMap<Node, Edge>();
    Queue<Node> sQueue = new LinkedList<Node>();
    Node currentNode = start;
    sQueue.add(currentNode);
    while (!sQueue.isEmpty() && currentNode != end) {
      currentNode = sQueue.remove();
      for (Edge e : currentNode.getEdges()) {
        Node to = e.getTo();
        if (to != start && mapPath.get(to) == null && e.getResidual() > 0) {
          sQueue.add(e.getTo());
          mapPath.put(to, e);
        }
      }
    }
    if (sQueue.isEmpty() && currentNode != end)
      return null;
    LinkedList<Edge> path = new LinkedList<Edge>();
    Node current = end;
    while (mapPath.get(current) != null) {
      Edge e = mapPath.get(current);
      path.addFirst(e);
      current = e.getFrom();
    }
    return path;
  }

  public static int maximizeFlow(Graph g) {
    int f = 0;
    Node sink = g.getSink();
    Node source = g.getSource();
    List<Edge> path;
    while ((path = findPath(g, source, sink)) != null) {
      int r = Integer.MAX_VALUE;
      for (Edge e : path) {
        r = Math.min(r, e.getResidual());
      }
      for (Edge e : path) {
        e.augmentFlow(r);
      }
      f += r;
    }
    return f;
  }
}

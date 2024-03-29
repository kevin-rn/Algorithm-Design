// My Solution:
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class PresentationTime {

  /**
   *  You should implement this method.
   *  @param n the number of students
   *  @param presenterNames the names of the presenters p_1 through p_n. Note you should only use entries presenterNames[1] up to and including presenterNames[n].
   *  @param startTimes the start times of the presentations s_1 through s_n. Note you should only use entries startTimes[1] up to and including startTimes[n].
   *  @param endTimes the end times of the presentations e_1 through e_n. Note you should only use entries endTimes[1] up to and including endTimes[n].
   *  @return a largest possible set of presenters whose presentation we can attend.
   */
  public static Set<String> whatPresentations(int n, String[] presenterNames, int[] startTimes, int[] endTimes) {
    Set<String> result = new HashSet<String>();
    Presentation[] p = new Presentation[n];
    for(int i = 1; i<=n;i++) {
      p[i-1] = new Presentation(presenterNames[i], startTimes[i], endTimes[i]);
    }
    Arrays.sort(p);
    result.add(p[0].presenter);
    Presentation last = p[0];
    for(int i = 1; i < n; i++) {
      if(p[i].start >= last.end) {
        result.add(p[i].presenter);
        last = p[i];
     }
    }
    return result;
  
  }
  
  
  public static class Presentation implements Comparable<Presentation> {
    String presenter;
    int start;
    int end;
    
    public Presentation(String presenter, int start, int end) {
      this.presenter = presenter;
      this.start = start;
      this.end = end;
    }
    
    @Override
    public int compareTo(Presentation other) {
      return Integer.compare(this.end, other.end);
    }
  }
}

// __________________________________________________________________________________________________________________________________________________
// Official Solution
import java.io.*;
import java.util.*;

/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class PresentationTime {

  public static /**
   *  You should implement this method.
   *  @param n the number of speakers
   *  @param presenterNames the names of the presenters p_1 through p_n. Note you should only use entries presenterNames[1] up to and including presenterNames[n].
   *  @param startTimes the start times of the presentations s_1 through s_n. Note you should only use entries startTimes[1] up to and including startTimes[n].
   *  @param endTimes the end times of the presentations e_1 through e_n. Note you should only use entries endTimes[1] up to and including endTimes[n].
   *  @return a largest possible set of presenters whose presentation we can attend.
   */
  Set<String> whatPresentations(int n, String[] presenterNames, int[] startTimes, int[] endTimes) {
    return solveProper(n, presenterNames, startTimes, endTimes);
  // return solveQuadraticCheck(n, presenterNames, startTimes, endTimes);
  }

  public static Set<String> solveProper(int n, String[] studentNames, int[] startTimes, int[] endTimes) {
    Student[] students = new Student[n];
    for (int i = 0; i < n; i++) {
      students[i] = new Student(studentNames[i + 1], startTimes[i + 1], endTimes[i + 1]);
    }
    Arrays.sort(students);
    Set<String> result = new HashSet<>();
    int lastEnd = 0;
    for (int i = 0; i < n; i++) {
      if (students[i].start >= lastEnd) {
        lastEnd = students[i].end;
        result.add(students[i].name);
      }
    }
    return result;
  }

  public static Set<String> solveQuadraticCheck(int n, String[] studentNames, int[] startTimes, int[] endTimes) {
    Student[] students = new Student[n];
    for (int i = 0; i < n; i++) {
      students[i] = new Student(studentNames[i + 1], startTimes[i + 1], endTimes[i + 1]);
    }
    Arrays.sort(students);
    Set<String> result = new HashSet<>();
    Set<Student> chosenStudents = new HashSet<>();
    for (int i = 0; i < n; i++) {
      if (!conflict(students[i], chosenStudents)) {
        result.add(students[i].name);
        chosenStudents.add(students[i]);
      }
    }
    return result;
  }

  private static boolean conflict(Student student, Set<Student> chosenStudents) {
    for (Student s : chosenStudents) {
      if (s.end > student.start && s.start <= student.start) {
        return true;
      } else if (s.start >= student.start && s.end <= student.end) {
        return true;
      } else if (s.start < student.end && student.end <= s.end) {
        return true;
      }
    }
    return false;
  }

  public static class Student implements Comparable<Student> {

    String name;

    int start;

    int end;

    public Student(String name, int start, int end) {
      this.name = name;
      this.start = start;
      this.end = end;
    }

    @Override
    public int compareTo(Student student) {
      return Integer.compare(this.end, student.end);
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    String dirName = "src/main/java/adweblab/exams/exam_1_2019_2020/basic_greedy/data/secret";
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

  private static String run(FileInputStream in) {
    Scanner sc = new Scanner(in);
    int n = sc.nextInt();
    String[] names = new String[n + 1];
    int[] start = new int[n + 1];
    int[] end = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      names[i] = sc.next();
      start[i] = sc.nextInt();
      end[i] = sc.nextInt();
    }
    sc.close();
    Set<String> res = PresentationTime.whatPresentations(n, names, start, end);
    StringBuilder sb = new StringBuilder();
    sb.append(res.size());
    sb.append('\n');
    for (String name : res) {
      sb.append(name);
      sb.append('\n');
    }
    return sb.toString();
  }
}

// __________________________________________________________________________________________________________________________________________________
// Test:
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.*;
import org.junit.*;
import org.junit.rules.TestName;

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

  public static void noConflicts(Set<String> theirSolution, ProblemInstance S) {
    Student[] stud = new Student[theirSolution.size()];
    int i = 0;
    for (String name : theirSolution) {
      stud[i] = new Student(name, S.nameToStart.get(name), S.nameToEnd.get(name));
      i++;
    }
    Arrays.sort(stud);
    int lastEnd = 0;
    for (i = 0; i < theirSolution.size(); i++) {
      assertTrue(stud[i].start >= lastEnd);
      lastEnd = stud[i].end;
    }
  }

  private static class ProblemInstance {

    int n;

    String[] c;

    int[] s;

    int[] e;

    Map<String, Integer> nameToStart;

    Map<String, Integer> nameToEnd;

    public ProblemInstance(int n, String[] c, int[] s, int[] e) {
      this.n = n;
      this.c = c;
      this.s = s;
      this.e = e;
      this.nameToStart = new HashMap<>();
      this.nameToEnd = new HashMap<>();
      for (int i = 1; i <= n; i++) {
        nameToStart.put(c[i], s[i]);
        nameToEnd.put(c[i], e[i]);
      }
    }
  }

  private static ProblemInstance parseInputFile(String fileName) {
    Scanner sc = new Scanner(WebLab.getData(fileName));
    int n = sc.nextInt();
    String[] names = new String[n + 1];
    int[] start = new int[n + 1];
    int[] end = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      names[i] = sc.next();
      start[i] = sc.nextInt();
      end[i] = sc.nextInt();
    }
    sc.close();
    return new ProblemInstance(n, names, start, end);
  }

  private static void runTestWithFile(String fileName) {
    ProblemInstance S = parseInputFile(fileName + ".in");
    Set<String> ourResult = parseOutputFile(fileName + ".out");
    Set<String> theirSolution = PresentationTime.whatPresentations(S.n, S.c, S.s, S.e);
    assertEquals(ourResult.size(), theirSolution.size());
    noConflicts(theirSolution, S);
  }

  public static class Student implements Comparable<Student> {

    String name;

    int start;

    int end;

    public Student(String name, int start, int end) {
      this.name = name;
      this.start = start;
      this.end = end;
    }

    @Override
    public int compareTo(Student student) {
      return Integer.compare(this.end, student.end);
    }
  }

  private static Set<String> parseOutputFile(String s) {
    Scanner sc = new Scanner(WebLab.getData(s));
    Set<String> result = new HashSet<>();
    int n = sc.nextInt();
    while (n > 0) {
      result.add(sc.next());
      n--;
    }
    sc.close();
    return result;
  }

  @Test(timeout = 100)
  public void example() {
    int n = 4;
    String[] presenters = { "", "Mia", "Phoenix", "Maya", "Miles" };
    int[] startTimes = { 0, 12, 1, 17, 13 };
    int[] endTimes = { 0, 18, 11, 20, 15 };
    Set<String> result = new HashSet<>();
    result.add("Phoenix");
    result.add("Maya");
    result.add("Miles");
    assertEquals(result, PresentationTime.whatPresentations(n, presenters, startTimes, endTimes));
  }

  @Test(timeout = 100)
  public void testTwoOptions() {
    int n = 3;
    String[] presenters = { "", "Mia", "Phoenix", "Pearl" };
    int[] startTimes = { 0, 1, 1, 15 };
    int[] endTimes = { 0, 11, 11, 17 };
    Set<String> theirSolution = PresentationTime.whatPresentations(n, presenters, startTimes, endTimes);
    assertEquals(2, theirSolution.size());
    noConflicts(theirSolution, new ProblemInstance(n, presenters, startTimes, endTimes));
  }
}

// Test:
import static org.junit.Assert.*;
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

  @Test(timeout = 100)
  public void example() {
    int solution = 2;
    int n = 3;
    Skill[] skills = new Skill[4];
    skills[1] = new Skill("Binary counting", false);
    skills[2] = new Skill("Huffman encoding", true);
    skills[3] = new Skill("Exchange arguments", true);
    assertEquals(solution, HowManyOnes.numberOfCompletedSkills(n, skills));
  }

  public void testWithNumbers(int sol, int n) {
    Skill[] arr = new Skill[n + 1];
    for (int i = 1; i <= n; i++) {
      arr[i] = new Skill("", i > n - sol);
    }
    assertEquals(sol, HowManyOnes.numberOfCompletedSkills(n, arr));
  }

  @Test(timeout = 100)
  public void test01() {
    testWithNumbers(8, 100);
  }

  @Test(timeout = 100)
  public void test02() {
    testWithNumbers(80, 100);
  }

  @Test(timeout = 500)
  public void test03() {
    testWithNumbers(452, 1_000);
  }

  @Test(timeout = 100)
  public void test04() {
    testWithNumbers(8, 200);
  }

  @Test(timeout = 100)
  public void test05() {
    testWithNumbers(42, 500);
  }

  @Test(timeout = 100)
  public void test06() {
    testWithNumbers(0, 100);
  }

  @Test(timeout = 100)
  public void test07() {
    testWithNumbers(100, 100);
  }

  @Test(timeout = 100)
  public void test08() {
    testWithNumbers(18, 150);
  }

  @Test(timeout = 100)
  public void test09() {
    testWithNumbers(800, 1000);
  }

  @Test(timeout = 100)
  public void test10() {
    testWithNumbers(512, 10_000);
  }

  @Test(timeout = 100)
  public void test11() {
    testWithNumbers(789, 1000);
  }

 @Test(timeout = 3000)
  public void testDelay() {
    int sol = 7859;
    int n = 10000;
    Skill[] arr = new Skill[n + 1];
    for (int i = 1; i <= n; i++) {
      arr[i] = new SlowSkill("", i > n - sol);
    }
    assertEquals(sol, HowManyOnes.numberOfCompletedSkills(n, arr));
    System.out.println("For 10000 skill, it needed a total of this many isCompleted calls: " + SlowSkill.cnt);
    assertTrue(SlowSkill.cnt < 500);
  }
}

class SlowSkill extends Skill {

  static int cnt = 0;

  public SlowSkill(String name, boolean completed) {
    super(name, completed);
  }

  @Override
  public boolean isCompleted() {
    cnt++;
    return super.isCompleted();
  }
}

// __________________________________________________________________________________________________________________________________
// O(n^2) Solution:
/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HowManyOnes {

  /**
   *  You should implement this method.
   *  @param n the number of elements in skills.
   *  @param skills the sorted array of `Skill`s (see Library for their implementation) to look through. Note that you should use entries skills[1] to skills[n]!
   *  @return the number of completed skills in the sorted array.
   */
  public static int numberOfCompletedSkills(int n, Skill[] skills) {
    if(n == 0) return 0;
    return count(1, n, skills);
  }
  
  public static int count(int low, int high, Skill[] skills) {
    if(low > high) return 0;
    int c = (skills[low].isCompleted()) ? 1 : 0;
    int mid = (low + high)/2;
    return c + count(low +1, mid, skills) + count(mid+1, high, skills);
  }
}

// __________________________________________________________________________________________________________________________________
// Official Solution:
/**
 * WARNING: The spec tests are not necessarily equal to your grade!
 * You can use them help you test for the correctness of your algorithm,
 * but the final grade is determined by a manual inspection of your implementation.
 */
class HowManyOnes {

  /**
   *  You should implement this method.
   *  @param n the number of elements in skills.
   *  @param skills the sorted array of `Skill`s (see Library for their implementation) to look through. Note that you should use entries skills[0] to skills[n]!
   *  @return the number of completed skills in the sorted array.
   */
  public static int numberOfCompletedSkills(int n, Skill[] skills) {
    // return solveSlow(skills);
    return solveProper(skills, 1, n);
  }

  public static int solveProper(Skill[] numbers, int left, int right) {
    if (!numbers[right].isCompleted()) {
      return 0;
    }
    if (numbers[left].isCompleted()) {
      return right - left + 1;
    }
    int mid = (left + right) / 2;
    return solveProper(numbers, left, mid) + solveProper(numbers, mid + 1, right);
  }

  public static int solveSlow(Skill[] numbers) {
    int sum = 0;
    for (int i = 1; i < numbers.length; i++) {
      sum += numbers[i].isCompleted() ? 1 : 0;
    }
    return sum;
  }
}

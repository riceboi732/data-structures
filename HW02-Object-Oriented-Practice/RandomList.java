import java.util.*;

public class RandomList {

    public static final int MINIMUM_SIZE = -10;
    public static final int MAXIMUM_SIZE = 0;
    public static ArrayList<Integer> numList;
    public int len;
    public static int count = 0;


    public RandomList (int n) {
        Random rand = new Random();
        numList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            numList.add(rand.nextInt(MAXIMUM_SIZE - MINIMUM_SIZE)+MINIMUM_SIZE);
        }

    }

    public int getSum () {
        int sum = 0;
        for (int i = 0; i < numList.size(); i++) {
            sum += numList.get(i);
        }
        return sum;
    }
    public int getInstanceCount() {
      int count = 0;
      count++;
      return count;
    }
    public static void main(String[] args) {
        int len = Integer.parseInt(args[0]);
        RandomList randomList = new RandomList(len);
        System.out.println("Numbers in list: " + numList);
        System.out.println("Sum of the numbers: " + randomList.getSum());
        System.out.println("How many RandomList instances did this program create? It created " + randomList.getInstanceCount());
    }
}

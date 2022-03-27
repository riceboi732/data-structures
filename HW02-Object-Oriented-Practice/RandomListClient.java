public class RandomListClient {
    public static void main(String[] args) {
        int sum1 = 0;
        int count = 0;
        RandomList randomList = null;
        while (sum1 >= -10) {
            count++;
            randomList = new RandomList(5);
            sum1 = randomList.getSum();
        }
        System.out.println("The first list with a sum of less than -10 had the following numbers: " + randomList.numList);
        System.out.println("This list has a sum of " + sum1 + ".");
        System.out.println("A total of " + count + " lists were created.");
    }
}


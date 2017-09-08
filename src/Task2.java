

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import static com.alibaba.fastjson.JSON.parseArray;

/**
 * Created by sveta on 07.09.2017.
 *
 */
public class Task2 {
    private static final String DONE = "DONE!";

    static String readJson(String fileName) throws FileNotFoundException {
        return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.print("Reading JSON ... \t\t\t\t\t\t");
        String json = readJson("files/10 000 000 Numbers.txt");
        System.out.println(DONE);

        System.out.print("Parsing JSON ... \t\t\t\t\t\t");
        List<Integer> list = parseArray(json, Integer.class);
        System.out.println(DONE);

        System.out.println("List original size =\t" + list.size());
        System.out.print("Increasing collection size ... \t\t\t");
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        System.out.println(DONE);
        System.out.println("List increased size =\t" + list.size());
        System.out.println();

        System.out.print("Converting to better collection ... \t");
        ArrayList<Integer> betterCollection = new ArrayList<>(list.size());
        betterCollection.addAll(list);
        list = betterCollection;
        System.out.println(DONE);

        long startTime;

        System.out.print("Calculating by single thread ... \t\t");
        startTime = System.currentTimeMillis();
        long oneThreadSum = getSumOneTread(list);
        long oSumTime = System.currentTimeMillis()-startTime;
        System.out.println(DONE);

        System.out.print("Calculating by multiple threads ... \t");
        startTime = System.currentTimeMillis();
        long multiThreadSum = getSumMultyTread(list);
        long mSumTime = System.currentTimeMillis()-startTime;
        System.out.println(DONE);
        System.out.println();

        System.out.println("Single thread:");
        System.out.println("\tSUM  = " + oneThreadSum);
        System.out.println("\tTime = " + oSumTime);
        System.out.println();

        System.out.println("Multi threads:");
        System.out.println("\tSUM  = " + multiThreadSum);
        System.out.println("\tTime = " + mSumTime);
        System.out.println();

        //multiply(list);
        //getSumofElemWithDiv(list);
        //getMinus(list);
    }

    private static long getSumMultyTread(List<Integer> list) throws InterruptedException {
        final int numProcessors = Runtime.getRuntime().availableProcessors();
        final List<Long> listSum = new ArrayList<>(numProcessors);

        List<Thread> threads = new ArrayList<>(numProcessors);
        for (int i = 0; i < numProcessors; i++) {
            final int left = list.size() / numProcessors * i;
            final int right = list.size() / numProcessors * (i + 1);
            // System.out.println("Thread[" + i + "]:\n\tleft = " + left + "\n\tright = " + (right-1) + "\n");

            Thread thread = new Thread(()->{
                long sum = getSumOneTread(list, left, right);
                listSum.add(sum);
            });
            thread.setPriority(9);
            thread.start();
            threads.add(thread);
        }

        long totalSum = 0;
        for (Thread thread : threads) thread.join();
        for (long sum : listSum) totalSum += sum;
        return totalSum;
    }

    private static long getSumOneTread(List<Integer> list) {
        return getSumOneTread(list, 0, list.size());
    }

    private static long getSumOneTread(List<Integer> list, int left, int right){
        long sum = 0;
        for (int i = left; i < right; i++)
            sum += list.get(i);
        return sum;
    }

    private static void getMinus(List<Integer> list) {
        double result =0;
        for (Integer num: list) {
            result = result - num;
        }
        System.out.println("result ="+ result);
    }

    private static void getSumofElemWithDiv(List<Integer> list) {
        double sum =0;
        for (int i = 1; i < list.size(); i++) {
            sum = sum + (list.get(i-1)/i);
        }
        System.out.println("Sum with div ="+sum);
    }

    private static void multiply(List<Integer> list) {
        long result = 1;
        for (Integer num: list) {
            result = result*num;
        }
        System.out.println("multiply ="+result);
    }
}


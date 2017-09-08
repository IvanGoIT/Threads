package sample;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ArrayX {
    static void generate(String fileName, int amount) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write("[ ");
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            int x = r.nextInt(1000000);
            if (i == 0) writer.write("" + x);
            else writer.write("," + x);
        }
        writer.write(" ]");
        writer.flush();
        writer.close();
    }

    static String readJson(String fileName) throws FileNotFoundException {
        return new Scanner(new File(fileName)).useDelimiter("\\Z").next();
    }

    static List<Integer> list;

    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName = "files/10 000 000 Numbers.txt";
        generate(fileName, 10000000);

        Thread thread = new Thread(() -> {
            try {
                list = JSON.parseArray(readJson(fileName), Integer.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("Join");
        thread.join();
        System.out.println("size = " + list.size());
    }
}

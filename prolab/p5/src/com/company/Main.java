package com.company;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {


    public static void main(String[] args) {
        Graph g = new Graph();


        String s = "./src/lat long.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(s));

            String st;
            s = "";
            while ((st = br.readLine()) != null)
                s += st + "\n";
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String[] ar = s.split("\n");

//        System.out.println(g.getEdgeAt(3).getOpposite(g.getVertexAt(4)));
        ArrayList<ArrayList<String>> ss = new ArrayList<>();

        for (String x : ar)
            ss.add(new ArrayList<>(Arrays.asList(x.split(","))));

        s = "./src/komsuluklar.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(s));

            String st;
            s = "";
            while ((st = br.readLine()) != null)
                s += st + "\n";
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ar = s.split("\n");
        ss.remove(0);

        ArrayList<ArrayList<String>> s2 = new ArrayList<>();

        for (String x : ar)
            s2.add(new ArrayList<>(Arrays.asList(x.split(","))));
        s2.remove(0);
        s2.remove(s2.size() - 1);
        s2.remove(s2.size() - 1);


        for (int i = 0; i < s2.size(); i++) {
            for (int j = 1; j < s2.get(i).size(); j++) {
                ss.get(i).add(s2.get(i).get(j));
            }
            ss.get(i).add(s2.get(i).get(0));
        }
//        for (ArrayList x : ss) {
//            for (Object xx : x) System.out.print(xx + " ");
//            System.out.println();
//        }
        long st = System.currentTimeMillis();
        g.init(ss);

//        System.out.println(g);
//        System.out.println();
//        System.out.println(g.edgeList.size());


        JFrame f = new JFrame("Harita");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(g.draw());
        g.joinAllThreads();//   *** join diyip bekletmemize gerek kalmaz o zaman

        long end = System.currentTimeMillis();
        System.out.println("total time  = " + (end - st) / 1000.0 + " seconds !");
        g.prntSabit100();
        g.prntYuzde50Kar();


        f.setSize(1920, 1080);
        f.setVisible(true);


    }

}

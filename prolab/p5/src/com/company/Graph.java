package com.company;

import javax.swing.*;
import java.awt.*;

import java.util.*;



public class Graph {
    ArrayList<Vertex> vertexList;
    ArrayList<Edge> edgeList;
    private Threading t[] = new Threading[6];
    private ArrayList<ArrayList<Double>> sabit100;// Max kara gore sirali          baslangic  -  yolcu sayisi  -  {Tum hedef sehirlere 100 tl ile elde edilen karlar}
    private ArrayList<ArrayList<Double>> yuzde50kar;

    public Graph() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
        sabit100 = new ArrayList<>(46);
        yuzde50kar = new ArrayList<>(10);
        for (int i = 0; i < 46; i++) {
            sabit100.add(new ArrayList<>());
        }
        for (int i = 0; i < 10; i++) {
            yuzde50kar.add(new ArrayList<>());
        }


    }

    public void prntSabit100() {
        System.out.println("\n\n SABIT 100 TL ICIN ELDE EDILEN KARLAR\n");
        for (ArrayList<Double> dd : sabit100) {

            System.out.print((int) dd.get(0).doubleValue() + "\tkisi" + "\t" +
                    vertexList.get((int) dd.get(1).doubleValue()).name + "\t\t-->>\t\t" +
                    vertexList.get((int) dd.get(2).doubleValue()).name + "\t\t=\t\t" +
                    dd.get(3));

            System.out.println();
        }

    }

    public void prntYuzde50Kar() {
        System.out.println("\n\n YUZDE 50 KAR ELDE ETMEK ICIN KISI BASI ALINMASI GEREKEN UCRET\n");
        for (ArrayList<Double> dd : yuzde50kar) {

            System.out.print((int) dd.get(0).doubleValue() + "\tkisi" + "\t" +
                    vertexList.get((int) dd.get(1).doubleValue()).name + "\t\t-->>\t\t" +
                    vertexList.get((int) dd.get(2).doubleValue()).name + "\t\t=\t\t" +
                    dd.get(3) + "\t\t" + dd.get(4));

            System.out.println();
        }

    }
    public void init(ArrayList<ArrayList<String>> all) {


        for (int i = 0; i < all.size(); i++) {
            ArrayList<String> st = all.get(i);
            Vertex v = new Vertex(i + 1, (int) Double.parseDouble(st.get(3)), Double.parseDouble(st.get(0)), Double.parseDouble(st.get(1)), st.get(st.size() - 1));
            vertexList.add(v);

        }
        for (int i = 0; i < vertexList.size(); i++) {
            ArrayList<String> st = all.get(i);
            for (Integer ii : getNeigs(st)) {
                Vertex vv = vertexList.get(i);
                if (!vv.hasNeig(vertexList.get(ii - 1)))
                    addEdge(vv.plaka, ii);


            }
        }

        //9 thread 81 ilden 9 ar tanesi icin her yolcu sayisini deneyip max karin elde edildigi parametreleri costs a yazar

        for (int i = 0; i < 5; i++) {
            t[i] = new Threading(i + 1, true);
            t[i].start();
        }
        t[5] = new Threading(6, false);
        t[5].start();
    }


    private ArrayList<Integer> getNeigs(ArrayList<String> st) {
        ArrayList<Integer> i = new ArrayList<>();
        for (String x : st.subList(4, st.size() - 1)) {
            i.add(Integer.parseInt(x));
        }

        return i;
    }



    public void joinAllThreads() {
        //threadler bulma isini bitirmeden siralamaya baslamasin diye bekletiyoruz
        try {
            for (int i = 0; i < 6; i++) {

                t[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addEdge(int i, int j) {

        Vertex vi = vertexList.get(i - 1);
        Vertex vj = vertexList.get(j - 1);
        Edge e = new Edge(vi, vj);
        vi.addNeighbor(vj, e);
        vj.addNeighbor(vi, e);
        edgeList.add(e);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices:\n");
        for (int i = 0; i < vertexList.size(); i++) {
            sb.append("    ");
            sb.append(i + 1);
            sb.append("\n");
        }
        sb.append("Edges:\n");
        for (Edge e : edgeList) {
            ArrayList<Vertex> endpoints = e.getEndpoints();
            sb.append("    ");
            sb.append(endpoints.get(0).getElement());
            sb.append("-");
            sb.append(endpoints.get(1).getElement());
            sb.append("\tlength ");
            sb.append(e.len);
            sb.append("\tdegree ");
            sb.append(e.degree);
            sb.append("\n");
        }
        return sb.toString();
    }

    public Draw draw() {

        return new Draw(this);
    }

    static class Draw extends JPanel {
        Graph gg;

        private Draw(Graph gg) {
            this.gg = gg;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(Color.WHITE);
            g.setColor(Color.BLACK);
            int ltmn = 36;
            int ltmx = 42;
            int lnmn = 26;
            int lnmx = 44;
            for (Edge e : gg.edgeList) {
                Vertex f = e.endpoints.get(0);
                Vertex s = e.endpoints.get(1);
                g.drawLine((int) (1900 * (f.lon - lnmn) / (lnmx - lnmn)), (int) (1080 - 1000 * (f.lat - ltmn) / (ltmx - ltmn)),
                        (int) (1900 * (s.lon - lnmn) / (lnmx - lnmn)), (int) (1080 - 1000 * (s.lat - ltmn) / (ltmx - ltmn)));//Ters cizdirmek icin 1080 alindi
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 15));

            for (Vertex v : gg.vertexList) {
                //sehir adlarinin yazildigi kisim
                g.drawString(v.name, (int) (1900 * (v.lon - lnmn) / (lnmx - lnmn)), (int) (1080 - 1000 * (v.lat - ltmn) / (ltmx - ltmn)));
            }

        }

    }

    static class Vertex {
        private int plaka;
        private int alti;
        private double lat, lon;

        public String name;
        private HashMap<Vertex, Edge> neighborList;

        private Vertex(int plaka, int alti, double lat, double lon, String name) {

            this.plaka = plaka;
            this.alti = alti;
            this.lat = lat;
            this.lon = lon;
            this.name = name;
            neighborList = new HashMap<>();
        }


        private void addNeighbor(Vertex v, Edge e) {
            neighborList.put(v, e);

        }

        public int getElement() {
            return this.plaka;
        }

        public String toString() {
            return Integer.toString(plaka);
        }

        private boolean hasNeig(Vertex v) {

            return neighborList.containsKey(v);
        }

    }

    static class Edge {
        ArrayList<Vertex> endpoints;
        double len;
        int h1, h2;
        double degree;

        private Edge(Vertex i, Vertex j) {
            endpoints = new ArrayList<>();
            endpoints.add(i);
            this.h1 = i.alti;
            endpoints.add(j);
            this.h2 = j.alti;
            this.len = calcDistance(i, j);
            this.degree = calcDegree(i, j, len);

        }

        private boolean hasEndpoint(Vertex v) {
            return endpoints.contains(v);
        }
        private double calcDistance(Vertex i, Vertex j) {
            int R = 6371;
            double lt1 = Math.toRadians(i.lat);
            double lt2 = Math.toRadians(j.lat);
            double dlt = Math.toRadians(j.lat - i.lat);
            double dln = Math.toRadians(j.lon - i.lon);

            double a = Math.sin(dlt / 2) * Math.sin(dlt / 2) +
                    Math.cos(lt1) * Math.cos(lt2) * Math.sin(dln / 2) * Math.sin(dln / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


            return R * c;

        }

        private double calcDegree(Vertex i, Vertex j, double d) {
            double h1 = i.alti;
            double h2 = j.alti;
            if (h2 > h1)
                return 180 * Math.atan((h2 - h1) / d) / Math.PI;
            else
                return 180 * Math.atan((h1 - h2) / d) / Math.PI;


        }

        public double calcDegree(double hf, double hs) {

            if (h2 + hs > h1 + hf)
                return 180 * Math.atan((h2 + hs - h1 - hf) / len) / Math.PI;
            else
                return 180 * Math.atan((h1 + hf - h2 - hs) / len) / Math.PI;


        }

        public ArrayList<Vertex> getEndpoints() {
            return endpoints;
        }



        public Vertex getOpposite(Vertex v) {
            if (v.getElement() == endpoints.get(0).getElement()) {
                return endpoints.get(1);
            } else if (v.getElement() == endpoints.get(1).getElement()) {
                return endpoints.get(0);
            } else {
                System.out.println("Endpoint 0: " + endpoints.get(0));
                System.out.println("Endpoint 1: " + endpoints.get(1));
                System.out.println("Checking for vertex: " + v.getElement());
                throw new IllegalArgumentException("Vertex not an endpoint of this edge.");
            }
        }

        public String toString() {
            return "(Edge " +
                    endpoints.get(0).getElement() +
                    "-" +
                    endpoints.get(1).getElement() +
                    ")";
        }
    }

    class Threading extends Thread {

        private int threadID;
        private boolean sabitOr50;

        private int numOfPass;

        private ArrayList<Double> fillInfinity() {
            ArrayList<Double> ret = new ArrayList<>(81);

            for (int i = 0; i < 81; i++)
                ret.add(Double.POSITIVE_INFINITY);
            return ret;
        }

        private Threading(int id, boolean sabitOr50) {

            this.numOfPass = 5;
            this.sabitOr50 = sabitOr50;
            this.threadID = id;

            System.out.println("created");
        }

        public void run() {
            //System.out.println("Running " + threadID);
            if (sabitOr50) {
                for (int j = (this.threadID - 1) * 10; j < this.threadID * 10; j++) {
                    if (j > 45)
                        break;

                    double mn = Double.POSITIVE_INFINITY;
                    int from = -1;
                    int to = -1;

                    for (int l = 0; l < 81; l++) {


                        ArrayList<Double> cost = fillInfinity();

                        ArrayList<Double> yol = fillInfinity();
                        cost.set(l, 0.);
                        yol.set(l, 0.);

                        for (int i = 0; i < 80; i++) {
                            for (Edge e : edgeList) {
                                Vertex v[] = {e.getEndpoints().get(0), e.getEndpoints().get(1)};
                                if (v[0] == vertexList.get(l)) {
                                    if (e.degree < (80 - numOfPass - j)) {

                                        cost.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);
                                    }
                                    if (e.calcDegree(0, 50) < (80 - numOfPass - j)) {

                                        yol.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);
                                    }

                                } else if (v[1] == vertexList.get(l)) {
                                    if (e.degree < (80 - numOfPass - j)) {
                                        cost.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);

                                    }
                                    if (e.calcDegree(50, 0) < (80 - numOfPass - j)) {
                                        yol.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);

                                    }

                                } else {
                                    int min = 0;
                                    if (yol.get(v[0].plaka - 1) >= yol.get(v[1].plaka - 1))
                                        min = 1;
                                    if (e.degree < (80 - numOfPass - j)) {


                                        if (yol.get(v[min].plaka - 1) + e.len < yol.get(e.getOpposite(v[min]).plaka - 1))
                                            yol.set(e.getOpposite(v[min]).plaka - 1, yol.get(v[min].plaka - 1) + e.len);
                                    }
                                    if (min == 0) {
                                        if (e.calcDegree(50, 0) < (80 - numOfPass - j)) {


                                            if (yol.get(v[min].plaka - 1) + e.len < cost.get(e.getOpposite(v[min]).plaka - 1))
                                                cost.set(e.getOpposite(v[min]).plaka - 1, yol.get(v[min].plaka - 1) + e.len);
                                        }

                                    } else {
                                        if (e.calcDegree(0, 50) < (80 - numOfPass - j)) {


                                            if (yol.get(v[min].plaka - 1) + e.len < cost.get(e.getOpposite(v[min]).plaka - 1))
                                                cost.set(e.getOpposite(v[min]).plaka - 1, yol.get(v[min].plaka - 1) + e.len);
                                        }

                                    }


                                }

                            }

                        }

                        cost.set(l, Double.POSITIVE_INFINITY);
                        double currentMn = Collections.min(cost, new Comparator<Double>() {
                            @Override
                            public int compare(Double o1, Double o2) {

                                if (o1 < o2) {
                                    return -1;
                                } else if (o1.equals(o2)) {

                                    return 0;
                                } else

                                    return 1;
                            }
                        });
                        if (currentMn < mn) {
                            mn = currentMn;
                            from = l;
                            to = cost.indexOf(currentMn);
                        }


                    }

                    sabit100.set(j, new ArrayList<>(Arrays.asList((double) j + 5, (double) from, (double) to, 100 * (j + 5) - 10 * mn)));
                }
            } else {
                for (int j = 0; j < 46; j += 5) {
                    if (j > 45)
                        break;

                    double mx = Double.NEGATIVE_INFINITY;
                    int from = -1;
                    int to = -1;

                    for (int l = 0; l < 81; l++) {//5+j yolcu sayisi icin


                        ArrayList<Double> cost = fillInfinity();

                        ArrayList<Double> yol = fillInfinity();
                        cost.set(l, 0.);
                        yol.set(l, 0.);

                        for (int i = 0; i < 80; i++) {
                            for (Edge e : edgeList) {
                                //if(l==18 &&e.hasEndpoint(vertexList.get(l)) &&e.getOpposite(vertexList.get(l)).name.equals("Cankırı") )System.out.println(e.calcDegree(50,0));

                                Vertex v[] = {e.getEndpoints().get(0), e.getEndpoints().get(1)};
                                if (v[0] == vertexList.get(l)) {
                                    if (e.degree < (80 - numOfPass - j)) {
                                        cost.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);

                                    }

                                    if (e.calcDegree(0, 50) < (80 - numOfPass - j)) {
                                        yol.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);

                                    }

                                } else if (v[1] == vertexList.get(l)) {

                                    if (e.degree < (80 - numOfPass - j)) {
                                        cost.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);
                                    }
                                    if (e.calcDegree(50, 0) < (80 - numOfPass - j)) {
                                        yol.set(e.getOpposite(vertexList.get(l)).plaka - 1, e.len);
                                    }

                                } else {

                                    int min = 0;
                                    if (yol.get(v[0].plaka - 1) >= yol.get(v[1].plaka - 1))
                                        min = 1;
                                    if (e.degree < (80 - numOfPass - j)) {


                                        if (yol.get(v[min].plaka - 1) + e.len < yol.get(e.getOpposite(v[min]).plaka - 1))
                                            yol.set(e.getOpposite(v[min]).plaka - 1, yol.get(v[min].plaka - 1) + e.len);
                                    }
                                    if (min == 0) {
                                        if (e.calcDegree(50, 0) < (80 - numOfPass - j)) {


                                            if (yol.get(v[min].plaka - 1) + e.len < cost.get(e.getOpposite(v[min]).plaka - 1))
                                                cost.set(e.getOpposite(v[min]).plaka - 1, yol.get(v[min].plaka - 1) + e.len);
                                        }

                                    } else {
                                        if (e.calcDegree(0, 50) < (80 - numOfPass - j)) {


                                            if (yol.get(v[min].plaka - 1) + e.len < cost.get(e.getOpposite(v[min]).plaka - 1))
                                                cost.set(e.getOpposite(v[min]).plaka - 1, yol.get(v[min].plaka - 1) + e.len);
                                        }

                                    }


                                }

                            }

                        }
                        for (int i = 0; i < cost.size(); i++) {
                            if (cost.get(i).isInfinite())
                                cost.set(i, -1.);
                        }
                        Double currentMx = Collections.max(cost, (o1, o2) -> {

                            if (o1 > o2) {
                                return 1;
                            } else if (o1.equals(o2)) {

                                return 0;
                            } else

                                return -1;
                        });
                        for (int i = 0; i < cost.size(); i++) {
                            if (cost.get(i) == -1)
                                cost.set(i, Double.POSITIVE_INFINITY);
                        }

                        if (l == 18) {
                            for (double d : cost) {
                                System.out.print(d + " ");
                            }
                            System.out.println();
                            System.out.println(currentMx);
                        }

                        if (!currentMx.isInfinite() && currentMx > mx) {

                            mx = currentMx;
                            from = l;
                            to = cost.indexOf(currentMx);
                        }


                    }

                    yuzde50kar.set(((j) / 5), new ArrayList<>(Arrays.asList((double) j + 5, (double) from, (double) to, 2 * mx / (j + 5), mx)));
                }

            } 
        }
    }
}

#include "mainwindow.h"
#include <QApplication>
#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include<graph.h>
#include<QDesktopWidget>
using namespace std;
int main(int argc, char *argv[])
{




    ifstream in("/home/brk/prolab-2-1/lat long.txt");

    vector <vector<string>> latlongtxt;
    string tmp;
    vector<string> tmpvec;

    while (getline(in, tmp))
        tmpvec.push_back(tmp);

    for (const auto &x : tmpvec) {
        stringstream ss(x);
        vector<string> tmpvec2;
        while (getline(ss,tmp,',')) {
            tmpvec2.push_back(tmp);
        }
        latlongtxt.push_back(tmpvec2);
    }

 //   cout << latlongtxt.size()<<endl;

    ifstream in2("/home/brk/prolab-2-1/komsuluklar.txt");

    tmpvec.clear();
    while (getline(in2, tmp))
        tmpvec.push_back(tmp);

    int iter = 0;
    for (const auto &x : tmpvec) {
        stringstream ss(x);

        while (getline(ss,tmp,',')) {
            latlongtxt[iter].push_back(tmp);
        }
        iter++;
    }


    Graph g(latlongtxt);
    g.findCosts();

   g.printVertices();
    g.printEdges();

    QApplication a(argc, argv);
    MainWindow w;
    w.g=&g;
    w.getNames();
    w.move(QApplication::desktop()->screen()->rect().center()-w.rect().center());
    w.show();

    return a.exec();
}

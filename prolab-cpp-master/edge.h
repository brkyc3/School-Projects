#ifndef EDGE_H
#define EDGE_H
#include "vertex.h"

#include<utility>
#include<cmath>
#include<iostream>

class Edge
{
public:
    Edge( Vertex* first, Vertex* second);

    Vertex* getOther(Vertex* v);
    int pgetOther(int firstPlaka);
    void print();
    double calcDegree(); 
    double calcDegree(Vertex* v,bool startOrEnd);
    double calcDistance();

    double distance;
    double degree;

    std::vector<Vertex*> vertices;
    std::vector<double> heights;




};
std::ostream &operator<<(std::ostream &os,const Edge& e);
#endif // EDGE_H

#ifndef VERTEX_H
#define VERTEX_H
#include<iostream>
#include<string>
#include<vector>
class Edge;
class Vertex
{

public:
    Vertex(int plaka,std::string name,double lat,double lon ,double alt ,std::vector<int> neighbours );
    void addEdge( Edge * e);
    bool hasEdgeWith( Vertex *v);
    void print();
    int plaka;
    Edge * before;
    std::string name;
    double lat,lon,alt;
    std::vector<int> neigs;
    std::vector<Edge*> edges;


private:


};

#endif // VERTEX_H

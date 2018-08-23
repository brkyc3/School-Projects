#ifndef GRAPH_H
#define GRAPH_H
#include<vector>
#include "edge.h"
#include "vertex.h"
#include<string>
#include<iostream>
#include<algorithm>
#include<array>
#include<thread>
class Graph
{

public:
    Graph(const std::vector<std::vector<std::string>>& v);
    ~Graph();

    void printVertices();
    void printEdges();
    std::vector<double> dijkstra(int startPlaka,int destPlaka,int numOfTraveller,bool createPath=false);
    bool printPath(Vertex *,Vertex *);
    bool hasPath(Vertex *,Vertex *);
    bool hasPath(int strt,int dest);
    bool printPath(int,int);
    void findCosts();
    void createEdge( Vertex * v1, Vertex *v2);
    static void threadControlFunc(int id,Graph *g);

    std::vector<Edge*> edges;
    std::vector<Vertex*> vertices;
    std::vector<std::array<double,3>>* minn;
    std::vector<std::array<double,4>>* maxx;
    std::thread threads[5];
    static bool threadsFinished[5];


};

#endif // GRAPH_H

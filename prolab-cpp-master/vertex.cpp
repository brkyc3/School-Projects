#include "vertex.h"
#include"edge.h"
Vertex::Vertex(int plaka,std::string name,double lat,double lon ,double alt ,std::vector<int> neighbours )
{
    this->plaka=plaka;
    this->name=name;
    this->lat=lat;
    this->lon=lon;
    this->alt=alt;

    for(auto x :neighbours){
        neigs.push_back(x);
    }
this->before= NULL;
}
void Vertex:: addEdge( Edge * e){

    edges.push_back(e);
}
bool Vertex::hasEdgeWith( Vertex *v){
    for(auto e :this->edges){
        if(e->getOther(this)->name==v->name)return 1;
    }
    return 0;
}


void Vertex::print(){
    std::cout<<name<<"\t"<<lat<<"\t"<<lon<<"\t"<<alt<<"\t";
    for(auto e :edges){
       std::cout<< e->getOther(this)->name<<"\t";
    }
    std::cout<<std::endl;

}

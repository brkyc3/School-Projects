#include "edge.h"

Edge::Edge( Vertex* first , Vertex* second)
{

vertices.push_back(first);
vertices.push_back(second);
this->distance=calcDistance();
heights.push_back(first->alt);
heights.push_back(second->alt);
this->degree=calcDegree();

}

Vertex* Edge::getOther( Vertex *v){

   return (this->vertices[0]->name == v->name)? this->vertices[1]:this->vertices[0];
}
int Edge::pgetOther(int v){
   return (this->vertices[0]->plaka == v)? this->vertices[1]->plaka:this->vertices[0]->plaka;
}
double Edge::calcDistance(){
                int R = 6371;
                double lt1 = M_PI*(this->vertices[0]->lat)/180;
               double lt2 = M_PI*(this->vertices[1]->lat)/180;
                double dlt = lt2-lt1;
                double dln = M_PI*(this->vertices[1]->lon -this->vertices[0]->lon)/180;

               double a = sin(dlt / 2) * sin(dlt / 2) +
                       cos(lt1) * cos(lt2) * sin(dln / 2) *sin(dln / 2);
               double c = 2 *atan2(sqrt(a), sqrt(1 - a));


               return R*c;

}





double Edge::calcDegree(){


        return 180*atan((std::abs(heights[1]-heights[0]))/distance)/M_PI;
}
double Edge::calcDegree(Vertex * v,bool startOrEnd){
    if(startOrEnd)


        return 180*atan(std::abs(this->getOther(v)->alt+50-(v->alt))/distance)/M_PI;


    else

            return 180*atan(std::abs(this->getOther(v)->alt-(v->alt+50))/distance)/M_PI;



}



void Edge::print(){
    std::cout<<vertices[0]->name<<"\t<--->\t"<<vertices[1]->name<<"\t\t"<<this->distance<<" km\t\t"<<this->degree<<" degree"<<std::endl;

}


std::ostream &operator<<(std::ostream &os, const Edge &e)
{

    os<<e.vertices[0]->name<<"\t<--->\t"<<e.vertices[1]->name<<"\t\t"<<e.distance<<" km\t\t"<<e.degree<<" degree"<<std::endl;
return os;
}

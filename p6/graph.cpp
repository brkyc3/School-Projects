#include "graph.h"

Graph::Graph(const std::vector<std::vector<std::string>>& v)
{
    for(auto vec:v){
        std::vector<int> neigs;
        for(auto iter = vec.begin()+5;iter<vec.end();iter++){
            neigs.push_back(std::stoi(*iter));
        }
        Vertex* tmp=new Vertex(std::stoi(vec[2]),vec[4],std::stod(vec[0]),std::stod(vec[1]),std::stod(vec[3]),neigs);
        vertices.push_back(tmp);

    }
    for(auto &vs: vertices){
        for(auto x:vs->neigs){
            if(!vs->hasEdgeWith(vertices[x-1])) {
                createEdge(vs,vertices[x-1]);
            }

        }
    }
    minn=new std::vector<std::array<double,3>>(46);
    maxx=new std::vector<std::array<double,4>>(5);
}
Graph::~Graph(){
    for(auto& v:vertices)
        delete (v);
    for(auto& e:edges)
        delete(e);
}

void Graph::createEdge( Vertex * v1, Vertex * v2){


    Edge *e=new Edge(v1,v2);
    v1->addEdge(e);
    v2->addEdge(e);
    this->edges.push_back(e);

}

bool Graph::threadsFinished[5]={false,false,false,false,false};
void Graph::findCosts()
{
    for(int i = 1 ;i<6;i++)
    {
        threads[i-1]=std::thread(threadControlFunc,i,this);

    }


}
void Graph::threadControlFunc(int id,Graph *g){
    std::cout<<"started  "<<id<<std::endl;
    for(int i = (id-1)*10;i<id*10 && i!=46;i++){
        double minCost=INFINITY;
        double sMinPlaka=-1;
        double eMinPlaka=-1;

        double sMaxPlaka=-1;
        double eMaxPlaka=-1;
        double maxCost=0;

        if((i+5)%10 != 0){
        for(int j = 0 ;j<81;j++){
            for(int k = j+1;k<81;k++){
                double cost=g->dijkstra(j+1,k+1,i+5)[k];
                if(cost<minCost){
                    minCost=cost;
                    sMinPlaka=j+1;
                    eMinPlaka=k+1;
                }
            }
        }
        }
        else{

            for(int j = 0 ;j<81;j++){
                for(int k = j+1;k<81;k++){
                    double cost=g->dijkstra(j+1,k+1,i+5)[k];
                    if(cost==INFINITY)continue;
                    if(cost>maxCost){
                        maxCost=cost;
                        sMaxPlaka=j+1;
                        eMaxPlaka=k+1;
                    }
                    if(cost<minCost){
                        minCost=cost;
                        sMinPlaka=j+1;
                        eMinPlaka=k+1;
                    }

                }
            }
            g->maxx->at(-1 +(i+5)/10)[0]=sMaxPlaka;
           g-> maxx->at(-1 +(i+5)/10)[1]=eMaxPlaka;
           g-> maxx->at(-1 +(i+5)/10)[2]=maxCost;
           g-> maxx->at(-1 +(i+5)/10)[3]=i+5;
        }
       g-> minn->at(i)[0]=sMinPlaka;
        g->minn->at(i)[1]=eMinPlaka;
        g->minn->at(i)[2]=minCost;


    }
    threadsFinished[id-1]=true;
    std::cout<<"finish"<<id<<std::endl;
}

std::vector<double> Graph::dijkstra(int startPlaka,int destPlaka,int numOfTraveller,bool calculatePath){
    if(calculatePath){
        for(auto & v :vertices){
            v->before=NULL;
        }
    }
    std::vector<std::vector<double>> costs(81,std::vector<double>(81));
    std::vector<bool> visitedVertices(81);
    double deg=80-numOfTraveller;
    for(auto & vec :costs){
        std::fill(vec.begin(),vec.end(),INFINITY);
    }
    int currentVertex = startPlaka;
    costs[0][currentVertex-1]=0;
    Vertex *crVr =NULL;
    for(int i =0 ; i< 80 ;i++){

        visitedVertices[currentVertex-1]=true;



        crVr= vertices[currentVertex-1];
        for(const auto& e:vertices[currentVertex-1]->edges){
            if(currentVertex==startPlaka){
                if(e->pgetOther(crVr->plaka)==destPlaka){
                    if(e->degree<deg && costs[i][currentVertex-1]+e->distance < costs[i][e->pgetOther(crVr->plaka)-1]){
                         costs[i][e->pgetOther(crVr->plaka)-1]=costs[i][currentVertex-1]+e->distance;
                         if(calculatePath)
                        vertices[e->pgetOther(crVr->plaka)-1]->before =e;
                    }


                }else{
                    if(e->calcDegree(crVr,1)<deg && costs[i][currentVertex-1]+e->distance < costs[i][e->pgetOther(crVr->plaka)-1]){
                         costs[i][e->pgetOther(crVr->plaka)-1]=costs[i][currentVertex-1]+e->distance;
                         if(calculatePath)
                         vertices[e->pgetOther(crVr->plaka)-1]->before =e;

                    }

                }
            }else{
                if(e->pgetOther(crVr->plaka)==destPlaka){
                    if(e->calcDegree(crVr,0)<deg && costs[i][currentVertex-1]+e->distance < costs[i][e->pgetOther(crVr->plaka)-1]){
                         costs[i][e->pgetOther(crVr->plaka)-1]=costs[i][currentVertex-1]+e->distance;
                         if(calculatePath)
                         vertices[e->pgetOther(crVr->plaka)-1]->before =e;

                    }
                }else{
                    if(e->degree<deg && costs[i][currentVertex-1]+e->distance < costs[i][e->pgetOther(crVr->plaka)-1]){
                         costs[i][e->pgetOther(crVr->plaka)-1]=costs[i][currentVertex-1]+e->distance;
                         if(calculatePath)
                         vertices[e->pgetOther(crVr->plaka)-1]->before =e;

                    }

                }


            }
        }

        bool nothingCopied = true;
        for(int x = 0 ; x< 81;x++){
            if(!visitedVertices[x]){
                costs[i+1][x]=costs[i][x];
                nothingCopied=false;
            }
        }



        currentVertex=std::min_element(costs[i+1].begin(),costs[i+1].end())-costs[i+1].begin()+1;
        if( nothingCopied == true || visitedVertices[currentVertex-1])return costs[i];
        for(int x = 0 ; x< 81;x++){
            if(visitedVertices[x]){
                costs[i+1][x]=costs[i][x];
            }
        }

    }

return costs[80];
}

void Graph::printVertices(){
    for(auto v :vertices){
      v->print();
    }
}
void Graph::printEdges(){
    for(auto e :this->edges){
        std::cout<<*e;
    }
    std::cout<<"Number of edges "<<edges.size()<<std::endl;
}
bool Graph::printPath(Vertex * strt ,Vertex * dest)
{
    if(dest->before ==NULL){
        std::cout<<"Yol bulunamadi !"<<std::endl;
        return false;
    }
    for(Vertex *iter=dest;;){
        std::cout<<iter->name<< " - ";
        if(iter->name==strt->name)break;
        else
            iter=iter->before->getOther(iter);
    }
    std::cout<<std::endl;
    return true;
}

bool Graph::hasPath(Vertex * v1, Vertex *v2)
{
    return (v2->before ==NULL)?false:true;



}

bool Graph::hasPath(int strt, int dest)
{
    return hasPath(vertices[strt-1],vertices[dest-1]);
}

bool Graph::printPath(int strt, int dest)
{
 return printPath(vertices[strt-1],vertices[dest-1]);

}


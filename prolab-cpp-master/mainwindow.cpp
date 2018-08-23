#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{

    ui->setupUi(this);

    QTimer * myTimer= new QTimer(this);
     myTimer->setInterval(1000); // ms

     connect(myTimer, SIGNAL(timeout()), this, SLOT(doNextFrame()));
     myTimer->start();
     startedTime=  std::chrono::system_clock::now();
      of=new std::ofstream ("out.txt");
}

MainWindow::~MainWindow()
{
    delete ui;
    of->close();
    delete of;
}

void MainWindow::getNames()
{
    QStringList sl;
    for(const auto x : g->vertices){
        sl<<QString::fromStdString( x->name);
    }
    QCompleter *completer = new QCompleter(sl, this);
    completer->setCaseSensitivity(Qt::CaseInsensitive);
    ui->comboBox->addItems(sl);
    ui->comboBox->setCompleter(completer);
    ui->comboBox_2->addItems(sl);
    ui->comboBox_2->setCompleter(completer);
    ui->comboBox_3->addItem(QString::fromStdString("Sabit ücret"));
    ui->comboBox_3->addItem(QString::fromStdString("% Kara göre ücret"));
    ui->label_4->setText(QString::fromStdString("Seçim yapın"));
}

void MainWindow::paintEvent(QPaintEvent *e)
{

    QPainter p(this);
    int minLat = 36;
    int maxLat = 42;
    int minLon = 26;
    int maxLon = 44;

    for(const auto & e:g->edges){
        p.drawLine((int)(-10+1500*(e->vertices[0]->lon-minLon)/(maxLon-minLon)),(int)(800-700*(e->vertices[0]->lat-minLat)/(maxLat-minLat)),
                (int)(-10+1500*(e->vertices[1]->lon-minLon)/(maxLon-minLon)),(int)(800-700*(e->vertices[1]->lat-minLat)/(maxLat-minLat)));

    }
    for(const auto & v :g->vertices){
        QPen pen;
        pen.setColor(QColor(160,0,0));
        pen.setWidth(5);
        p.setPen(pen);
        p.drawPoint((int)(-10+1500*(v->lon-minLon)/(maxLon-minLon)),(int)(800-700*(v->lat-minLat)/(maxLat-minLat)));
        pen.setColor(QColor(160,0,0));
        pen.setWidth(10);
        p.setPen(pen);
        p.drawText((int)(-30+1500*(v->lon-minLon)/(maxLon-minLon)),(int)(800-700*(v->lat-minLat)/(maxLat-minLat)),QString::fromStdString(v->name));
    }


    this->g->dijkstra(strt,dest,travellers,true);
    if(this->g->hasPath(strt,dest)){
        QPen pen;
        pen.setColor(QColor(200,0,0));
        pen.setWidth(5);
        p.setPen(pen);



        for(Vertex *v= g->vertices[dest-1];;){


           Edge * e= v->before;


           p.drawLine((int)(-10+1500*(e->vertices[0]->lon-minLon)/(maxLon-minLon)),(int)(800-700*(e->vertices[0]->lat-minLat)/(maxLat-minLat)),
                   (int)(-10+1500*(e->vertices[1]->lon-minLon)/(maxLon-minLon)),(int)(800-700*(e->vertices[1]->lat-minLat)/(maxLat-minLat)));

           v=e->getOther(v);
           if(v->name == g->vertices[strt-1]->name)break;
        }




    }


}

void MainWindow::doNextFrame(){


    int progress = 0 ;
    bool allThreadsFinished=true;

    for(const auto &b :g->threadsFinished){
        if(!b)allThreadsFinished=false;
        else progress++;
    }
    if(allThreadsFinished==false){

        ui->progressBar->setValue(progress*20);

    }
    else{
        if(ui->progressBar->value()<100){
        ui->progressBar->setValue(progress*20);
        ui->progressBar->setVisible(false);
        ui->label_6->setVisible(false);
        const auto dur =(std::chrono::high_resolution_clock::now()-startedTime);
        std::cout<<"total calculating time "<<dur.count()*pow(10,-9)<< " seconds"<<std::endl<<std::endl;
        totalTime=dur.count();
        }
    }
    this->update();
}


void MainWindow::on_pushButton_clicked()
{






    std::string f=ui->comboBox->currentText().toStdString();
    std::string s=ui->comboBox_2->currentText().toStdString();
    for(const auto & v:g->vertices){
        if(v->name==f)strt=v->plaka;
        else if(v->name == s)dest=v->plaka;
    }


    travellers=ui->horizontalSlider->value();

    auto vect=this->g->dijkstra(strt,dest,travellers,true);
    this->buttonClicked=true;
    if(vect[dest-1]!=INFINITY){
        ui->label_5->setText(QString::number(vect[dest-1],5,3)+" km");
        if(ui->comboBox_3->currentIndex()==0)
            ui->textBrowser->append(QString::number(ui->horizontalSlider->value())+"\t"+
                                    QString::fromStdString( g->vertices[strt-1]->name)+"\t\t"+
                                    QString::fromStdString( g->vertices[dest-1]->name)+"\t\t"+
                                    QString::number( ui->horizontalSlider->value()*ui->lineEdit->text().toDouble()-10*vect[dest-1]));
        else
            ui->textBrowser->append(QString::number(ui->horizontalSlider->value())+"\t"+
                                    QString::fromStdString( g->vertices[strt-1]->name)+"\t"+
                                    QString::fromStdString( g->vertices[dest-1]->name)+"\t"+
                                    QString::number( (10*vect[dest-1])/((100-ui->lineEdit->text().toDouble())/100)/ui->horizontalSlider->value())+"\t"+
                                    QString::number( (10*vect[dest-1])/((100-ui->lineEdit->text().toDouble())/100)*(ui->lineEdit->text().toDouble())/100));
    }
    else{
        ui->label_5->setText("YOL YOK!");
    }
    for(Vertex *v= g->vertices[dest-1];;){
       Edge * e= v->before;
       if(e==NULL)break;
       *of<<*e;
       v=e->getOther(v);
       if(v->name == g->vertices[strt-1]->name)break;
    }
    *of<<"\n";
    this->update();
}

void MainWindow::on_comboBox_3_currentIndexChanged(int index)
{
ui->label_4->setText(" ");
if(index==0)
    ui->lineEdit->setText("Tl ücreti girin");
else
    ui->lineEdit->setText("Kar %sini girin");

}

void MainWindow::on_pushButton_2_clicked()
{
    bool allThreadsFinished=true;
    int progress=0;
    for(const auto &b :g->threadsFinished){
        if(!b)allThreadsFinished=false;
        else progress++;
    }
    if(allThreadsFinished==false){

        ui->progressBar->setValue(progress*20);
        return ;
    }
    else{
        ui->progressBar->setVisible(false);
        ui->label_6->setVisible(false);
    }
    if(ui->comboBox_3->currentIndex()==0){
        int numOfPass = 5;
        ui->textBrowser->clear();
        ui->textBrowser->append("Y.Sayısı\tBaslangıç\t\tBitiş\t\tKar");
        for(auto iter = g->minn->begin();iter<g->minn->end();iter++){
        ui->textBrowser->append(QString::number(numOfPass)+"\t"+
                                QString::fromStdString( g->vertices[iter->at(0)-1]->name)+"\t\t"+
                                QString::fromStdString( g->vertices[iter->at(1)-1]->name)+"\t\t"+
                                QString::number( numOfPass*ui->lineEdit->text().toDouble()-10*iter->at(2)));
        numOfPass++;
        }
    }else if(ui->comboBox_3->currentIndex()==1){
        std::sort(g->maxx->begin(),g->maxx->end(),[](const auto& ar1,const auto& ar2){
            return ar1.at(2)>ar2.at(2);
        });

        ui->textBrowser->clear();
        ui->textBrowser->append("Y.Sayısı\tBaslangıç\tBitiş\tBirÜcret\tKar");
        for(auto iter = g->maxx->begin();iter<g->maxx->end();iter++){
        ui->textBrowser->append(QString::number(iter->at(3))+"\t"+
                                QString::fromStdString( g->vertices[iter->at(0)-1]->name)+"\t"+
                                QString::fromStdString( g->vertices[iter->at(1)-1]->name)+"\t"+
                                QString::number( (10*iter->at(2))/((100-ui->lineEdit->text().toDouble())/100)/iter->at(3))+"\t"+
                                QString::number( (10*iter->at(2))/((100-ui->lineEdit->text().toDouble())/100)*(ui->lineEdit->text().toDouble())/100)); //maliyet + 0.3*toplamodedigi =toplamodedigi  -> toplamodedigi =maliyet*(10/7)

        }

    }
}



void MainWindow::on_lineEdit_selectionChanged()
{
    ui->lineEdit->setText("");
}

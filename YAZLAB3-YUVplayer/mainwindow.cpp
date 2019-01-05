#include "mainwindow.h"
#include "ui_mainwindow.h"
#include<QLabel>
#include<QImage>
#include <QGraphicsScene>
#include<fstream>
#include<vector>
#include<QKeyEvent>
#include<iostream>
#include<QFileInfo>
#include<QFile>
#include<QDir>
#include <QFileDialog>
#include<QDebug>
#include<exception>
#include<QComboBox>
#include<memory>
#include <QMessageBox>
#include<typeinfo>
#include<QApplication>
#include<QDesktopWidget>
#include<thread>
#include<QTimer>
#include<chrono>
MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
this->setStyleSheet("background-color: black;");

    scene = new QGraphicsScene(this);
ui->comboBox->setStyleSheet("background-color: #770000;");
ui->lineEdit->setStyleSheet("background-color: #770000;");
ui->lineEdit_2->setStyleSheet("background-color: #770000;");
ui->pushButton->setStyleSheet("background-color: #770000;");
ui->pushButton_2->setStyleSheet("background-color: #770000;");


        ui->mainImage->setBackgroundBrush(QBrush(Qt::black, Qt::SolidPattern));
        ui->mainImage->setScene(scene);

        ui->comboBox->addItem("YUV444");
        ui->comboBox->addItem("YUV422");
        ui->comboBox->addItem("YUV420");

        startTimer(50);


       ui->verticalWidget->setStyleSheet("background-color: #770000;");

       yenidenKonumlandir(1455,803);
       gizle(false);
        scene->clear();

}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::keyPressEvent(QKeyEvent *event)
{
    if(event->key() == Qt::Key::Key_D)
        {

         qDebug()<<"girdi";
            if(currentFrame<maxFrame){

            showIthFrame(currentFrame++);

            }
        }
        else if (event->key() == Qt::Key::Key_A)
        {
         qDebug()<<"girdi";
        if(currentFrame>0){
        currentFrame--;
        showIthFrame(currentFrame);
    }


    } else if (event->key() ==Qt::Key::Key_Space) {
        qDebug()<<"girdi";
        oynat =!oynat;
    }
    else if (event->key() == Qt::Key::Key_Escape) {
        gizle(false);
        qDebug()<<"esc";
    }
}

void MainWindow::setData(std::vector<QPixmap> &p)
{
    currentFrame=0;
this->frames=p;
maxFrame=p.size();
}

std::vector<QPixmap> MainWindow::yuvToPixmap( YuvType y, int m, int n)
{
    std::ifstream is(filepath.toStdString(),std::ios::binary);


    int frames=frameSayisiBul(y,m,n);


    std::vector<QPixmap> allFrames;

    while(frames--){


    QImage im(n,m ,QImage::Format_RGB16);
    switch (y) {
    case YuvType::y444 :{
        char buff[m*n*3];

        is.read(buff,sizeof(buff));





        for (int i=0;i<m*n;i++) {
            int satir=i/n;
            int sutun = i -satir*n;
            im.setPixel(sutun,satir  ,qRgb(buff[i],buff[i],buff[i]));

            //  std::cout<<satir<<", "<< sutun<<std::endl;

        }

        break;
    }
    case YuvType::y422:{
        char buff[m*n*2];

        is.read(buff,sizeof(buff));
        for (int i=0;i<m*n;i++) {
            int satir=i/n;
            int sutun = i -satir*n;
            im.setPixel(sutun,satir,qRgb(buff[i],buff[i],buff[i]));

        }

        break;
    }
    case YuvType::y420:{
        char buff[m*n + (m*n)/2];

        is.read(buff,sizeof(buff));
        for (int i=0;i<m*n;i++) {
            int satir=i/n;
            int sutun = i -satir*n;
        im.setPixel(sutun,satir,qRgb(buff[i],buff[i],buff[i]));
        }

    }
    }

    allFrames.push_back(QPixmap::fromImage(im));

    }


    return allFrames;

}

void MainWindow::showIthFrame(int i)
{




    scene->addPixmap(frames[i]);
    scene->setSceneRect(frames[i].rect());
    if(i == frames.size()-1)oynat=false;
}

int MainWindow::frameSayisiBul( YuvType y, int m, int n)
{
    QFileInfo f(filepath);
    if(y==YuvType::y444)
    {
        int integer=f.size()/(m*n*3);
        if((double)f.size()/(m*n*3) != (double)integer)
        {
            QMessageBox *messageBox =  new QMessageBox(QMessageBox::Icon::Information,"Dikkat","Frame sayisi tutarli degil, girdiginiz genislik ve uzunluk degerlerinin videoya uygun oldugundan emin olun!",0);
messageBox->setStyleSheet("background-color: #770000;");
            messageBox->setFixedSize(500,500);
            messageBox->show();

        }

        return f.size()/(m*n*3);
    }
    else if (y==YuvType::y422){
        int integer=f.size()/(m*n*2);
        if((double)f.size()/(m*n*2) != (double)integer)
        {
            QMessageBox *messageBox =  new QMessageBox(QMessageBox::Icon::Information,"Dikkat","Frame sayisi tutarli degil, girdiginiz genislik ve uzunluk degerlerinin videoya uygun oldugundan emin olun!",0);
messageBox->setStyleSheet("background-color: #770000;");
            messageBox->setFixedSize(500,500);
            messageBox->show();
        }
        return f.size()/(m*n*2);
    }
    else{

        int integer=f.size()/(m*n + m*n/2);
        if((double)f.size()/(m*n + m*n/2) != (double)integer)
        {
            QMessageBox *messageBox =  new QMessageBox(QMessageBox::Icon::Information,"Dikkat","Frame sayisi tutarli degil, girdiginiz genislik ve uzunluk degerlerinin videoya uygun oldugundan emin olun!",0);
messageBox->setStyleSheet("background-color: #770000;");
            messageBox->setFixedSize(500,500);
            messageBox->show();
        }
        return f.size()/(m*n + m*n/2);


    }


}

void MainWindow::saveFrames(QString path)
{


    int i=0;

    QDir dir;

    if (!dir.exists(path))
        dir.mkpath(path);

    QMessageBox *mbox = new QMessageBox(QMessageBox::Icon::Information,"basarili","dosyaya yazma basarili",0);
mbox->setStyleSheet("background-color: #770000;");

    try{
        
       

    for(auto &frame :frames){
        QString filename = path+"/frame"+QString::number(i)+".bmp";
        //qDebug()<<filename;
        frame.save(filename, "BMP");

        i++;
    }
  mbox->setWindowTitle(tr("basarili!"));
    mbox->setText("dosyaya yazma basarili!");
    mbox->show();
    std::this_thread::sleep_for(std::chrono::seconds(1));
    mbox->close();
    delete  mbox;
    qDebug()<<"dosyaya yazma islemi tamamlandi";
    }catch(std::exception &e){
        qDebug()<<e.what();
        mbox->setWindowTitle(tr("hata!"));
        mbox->setStyleSheet("background-color: #770000;");
          mbox->setText("dosayaya yazarken hata meydana geldi");
          mbox->setIcon(QMessageBox::Icon::Critical);
          mbox->show();
          std::this_thread::sleep_for(std::chrono::milliseconds(1500));
          mbox->close();
          delete  mbox;
           qDebug()<<"dosyaya yazma islemi sorunla karsilasti";
    }
}

void MainWindow::gizle(bool b)
{
    ui->verticalWidget->setVisible(!b);
ui->verticalWidget1->setVisible(b);

if(!b){
    oynat=false;
}
}

void MainWindow::yenidenKonumlandir(int m, int n)
{
    this->setFixedSize(m+10*m/100,n+10*n/100);

   ui->verticalWidget1->setGeometry(0,0,m+10*m/100,n+10*n/100);
    ui->verticalWidget->setGeometry((m+10*m/100)/2-100,(n+10*n/100)/2-100, 200,200);

    QRect screenGeometry = QApplication::desktop()->screenGeometry();
    int x = (screenGeometry.width()-this->width()) / 2;
    int y = (screenGeometry.height()-this->height()) / 2;
    this->move(x, y);
    this->show();
}

void MainWindow::timerEvent(QTimerEvent *event)
{

    if(oynat){
        if(maxFrame>0 && currentFrame<maxFrame)
        showIthFrame(currentFrame++);
    }
}

void MainWindow::on_pushButton_clicked()
{

        scene->clear();
        QString filename =  QFileDialog::getOpenFileName(
              this,
              "Open Document",
              QDir::currentPath(),
              "YUV files (*.yuv)");

        if( !filename.isNull() )
        {
          qDebug() << "selected file path : " << filename.toUtf8();

        }else {
            return;
}

        filepath=filename;

            int h = ui->lineEdit->text().toInt();
            int w = ui->lineEdit_2->text().toInt();
            if(h<=0 || w <=0){
                QMessageBox *messageBox =  new QMessageBox(QMessageBox::Icon::Critical,"Hata","Genislik ve uzunluk pozitif sayi degerleri girilmeli!",0);
                messageBox->setStyleSheet("background-color: #770000;");
                messageBox->setFixedSize(500,500);
                messageBox->show();
                return;
            }
            YuvType y = ui->comboBox->currentIndex() ==0? YuvType::y444 : ui->comboBox->currentIndex() ==1?YuvType::y422:YuvType::y420 ;
            auto a = yuvToPixmap(y,h,w);
            setData(a);
yenidenKonumlandir(w,h);
                showIthFrame(0);

gizle(true);



}

void MainWindow::on_pushButton_2_clicked()
{
    QString filename =  QFileDialog::getExistingDirectory(
          this,
          "Open Document",
          QDir::currentPath(),
          QFileDialog::ShowDirsOnly | QFileDialog::DontResolveSymlinks);

    if( !filename.isNull() )
    {
      qDebug() << "selected file path : " << filename.toUtf8();

    }else {
return;
}

    std::thread t(&MainWindow::saveFrames,this,filename.toUtf8());
    t.detach();

   gizle(true);

}

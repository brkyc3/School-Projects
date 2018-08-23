#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include<QtGui>
#include<QtCore>
#include<QCompleter>
#include"graph.h"
#include<chrono>
#include<fstream>
namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    Graph *g;
    void getNames();
    int strt=54;
    int dest=30;
    std::chrono::time_point<std::chrono::system_clock>  startedTime;
    long totalTime;
    std::ofstream * of;
    int travellers=5;
    bool buttonClicked=false;


private:
    Ui::MainWindow *ui;
protected:
    void paintEvent(QPaintEvent *);
private slots:


    // where
    public slots:
    virtual void doNextFrame();;
    void on_pushButton_clicked();
    void on_comboBox_3_currentIndexChanged(int index);
    void on_pushButton_2_clicked();



    void on_lineEdit_selectionChanged();
};

#endif // MAINWINDOW_H

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QGraphicsScene>
#include <QMainWindow>
#include<QString>
#include<mutex>
enum class YuvType{
    y444,
    y422,
    y420
};

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
protected:
    void keyPressEvent(QKeyEvent *event);
    void setData(std::vector<QPixmap> &p);
private slots:
    void on_pushButton_clicked();

    void on_pushButton_2_clicked();

private:
    QString filepath;
    Ui::MainWindow *ui;
    std::vector<QPixmap> yuvToPixmap(YuvType y, int m, int n);
    std::vector<QPixmap> frames;
    int currentFrame=0;
    int maxFrame=-1;
    void showIthFrame(int i);
    QGraphicsScene *scene;
    int frameSayisiBul(YuvType y, int m, int n);
    void saveFrames(QString);
    bool oynat=false;
    void gizle(bool b);
    void yenidenKonumlandir(int m,int n);
    std::mutex frameBufferMutex;
    // QObject interface
protected:
    void timerEvent(QTimerEvent *event);
};

#endif // MAINWINDOW_H

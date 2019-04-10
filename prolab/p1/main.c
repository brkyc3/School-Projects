/*		prolab1 matematik carki

170201106 burak yazici			170201112 furkan şahin

Ozdeger bulma , Schur teoremi , Nilpotentlik kontrolu,
Katsayilari bilinen polinomun tum karmasik koklerini bulma(durand-kerner methodu ile) 

işlemleri gerceklestirildi.

*/

#include <stdio.h>
#include <stdlib.h>
#include<math.h>
#include<time.h>
typedef struct{
    double reel;
    double i;
}karmasik;

void cark(int n);

karmasik kcarp(karmasik a,karmasik b);
karmasik kpower(karmasik a ,int pow);
karmasik ktopla(karmasik a,karmasik b);         //karmasik sayi temel islemleri
karmasik kfark(karmasik a, karmasik b);
karmasik kbol(karmasik a,karmasik b);
karmasik yerinekoy(int * denklem,karmasik a,int N);  //karakteristik denklemde yerine yazip cikan karmasik sayiyi geri dondurur

karmasik * durandKerner(int * karakDenklem,int N); //https://en.wikipedia.org/wiki/Durand%E2%80%93Kerner_method  kokleri bulan fonksiyon
karmasik * kokler=0;


int tr(int * m,int N);    //matrisin izi
void mtcarp(int * f,int * s,int * r,int N);
karmasik * ozdegerbul(int *matris,int n);
int schur(karmasik * oz,int * matris,int n);
int nilpo(int * matris,int n);

int *matris=0;//kullanicinin girdigi matris
int *katsayilar =0;//karakteristik denklem katsayilari
int *matrisUst =0;//katsayilarin hesaplanmasında kullanmak icin gerekli olan A^1 A^2 ...A^n matrislerini tutar
FILE *dosya;

int main()
{   int donus_sayisi =0;


    printf(" Carkin kac defa donecegini girin = ");
    scanf("%d",&donus_sayisi);



    cark(donus_sayisi);
    return 0;
}
void cark(int k){
    srand(time(NULL));
    int n=0;
    while(k--){
        switch((rand()%241)%4){
    case 0 :

        dosya = fopen("ozdeger.txt","w");
        fprintf(dosya, "OZDEGER\nGirilen matris:\n");

        free(matris);
        printf("\n  OZDEGER\n");
        printf("Matrisin boyutunu girin : ");

        scanf("%d",&n);
        matris =(int *)malloc(n*n*sizeof(int));
         for(int i=0;i<n;i++){
            for(int j =0;j<n;j++){
                printf(" %d. satir ,%d. sutun = ",i+1,j+1);
                scanf("%d",matris+i*n+j);
                fprintf(dosya,"%d\t",*(matris+i*n+j));
    }
        fprintf(dosya,"\n");
    }
        ozdegerbul(matris,n);
        break;
    case 1:
        dosya = fopen("schur.txt","w");
        fprintf(dosya, "SCHUR\nGirilen matris:\n");

        free(matris);
         printf("\n  SCHUR TEOREMI\n");
         printf("Matrisin boyutunu girin : ");

        scanf("%d",&n);
        matris =(int *)malloc(n*n*sizeof(int *));
         for(int i=0;i<n;i++){
            for(int j =0;j<n;j++){
                printf(" %d. satir ,%d. sutun = ",i+1,j+1);
                scanf("%d",matris+i*n+j);

                fprintf(dosya,"%15d\t",*(matris+i*n+j));
    }
         fprintf(dosya,"\n");
    }
        schur(ozdegerbul(matris,n),matris,n);
        break;
    case 2:
        dosya = fopen("ozvektor.txt","w");

        //asdasd
        break;
    case 3:
        dosya = fopen("nilpotent.txt","w");
        fprintf(dosya, "NILPOTENT\nGirilen matris:\n");

        free(matris);
        printf("\n  NILPOTENT\n");
        printf("Matrisin boyutunu girin : ");

        scanf("%d",&n);
        matris =(int *)malloc(n*n*sizeof(int *));
         for(int i=0;i<n;i++){
            for(int j =0;j<n;j++){

                *(matris+i*n+j)=(rand()%16)*pow(-1,rand()%2);
                printf("%15d\t",*(matris+i*n+j));

                fprintf(dosya,"%15d\t",*(matris+i*n+j));
    }
            printf("\n");
            fprintf(dosya,"\n");
    }
        nilpo(matris,n);
        break;
        }
    }
}

int schur(karmasik * oz,int * matris,int n){
    karmasik kucuk,buyuk;
    kucuk.i=0;
    kucuk.reel=0;
    buyuk.i=0;
    buyuk.reel=0;

    for(int i=0;i<n;i++){
        kucuk=ktopla(kucuk,kpower(*(oz+i),2));               //OZDEGERLERIN KARELERI TOPLAMI
        //printf("\n%f %fi\n",kucuk.reel,kucuk.i);
    }

    for(int i=0;i<n;i++)
        for(int j=0;j<n;j++)
            buyuk.reel+=*(matris+i*n+j) * *(matris+i*n+j);      //MATRISIN TEK TEK TUM ELEMENLARININ KARELERININ TOPLAMI

     printf("\n%0.1f   <=   %0.1f\n",kucuk.reel,buyuk.reel);


     fprintf(dosya,"OZDEGER KARELERI TOPLAMI %40.1f\nMATRISIN TEK TEK TUM ELEMENLARININ KARELERININ TOPLAMI %15.1f\n\n%0.1f   <=  %0.1f\n",kucuk.reel,buyuk.reel,kucuk.reel,buyuk.reel);
     //Sinir degeri ya buyuk.reel yada kucuk.reel??????????

    return buyuk.reel;
}

int nilpo(int * matris,int n){
    free(matrisUst);
    int kontrolSayisi=0;
    printf("\nKacinci usse kadar kontrol edilsin? =");
    scanf("%d",&kontrolSayisi);

    matrisUst = (int *)malloc(n*n*kontrolSayisi*sizeof(int *));
    int nipoTrue=1;





    for(int i =0;i<n*n;i++)
    *(matrisUst+i)=*(matris+i);

    //KONTROL SAYISI KADAR TUM USLERI BULUP matrisUste YAZAR
    for(int i=1;i<kontrolSayisi;i++){


        mtcarp((matrisUst+(i-1)*n*n),(matrisUst),(matrisUst+i*n*n),n);
        printf("\n%d.us\n",i+1);
        fprintf(dosya, "\n%d.us\n",i+1);
        //EN SON YAZILAN USSUN TUM ELEMANLARINI KONTROL EDIP NILPOTENTLIK ARAR
        for(int k =0;k<n*n;k++){
            char c=0;
                if(k%n==n-1)
                    c='\n';
                else
                    c=0;
                 printf("%15d%c",*(matrisUst+i*n*n+k),c);
                 fprintf(dosya, "%15d%c",*(matrisUst+i*n*n+k),c);
            if(*(matrisUst+i*n*n+k)!=0)
                    nipoTrue=0;


        }
         printf("\n\n");
         fprintf(dosya, "\n\n");
    }
        if(nipoTrue==0){

            printf("Nilpotent degil!!\n");
             fprintf(dosya, "Nilpotent degil!!\n");
        }
        else{
            fprintf(dosya, "Nilpotent degil!!\n");
             printf("Nilpotent!!!!!\n");
        }

    return nipoTrue;
}
//N*N lik matrisi carpip "ret" e yazar
void mtcarp(int * f,int * s,int * ret,int N){




    /*for(int k=0;k<N*N;k++){
            printf("%d      ",*(f+k));
            printf("%d      ",*(s+k));


        }
*/
    for(int i=0;i<N;i++){
    for(int j=0;j<N;j++){



        *(ret +i*N+j)=0;

        for(int k=0;k<N;k++){

            *(ret +i*N+j)+=*(f +i*N+k)*(*(s +k*N+j));

        }

        //printf("%d ",*(ret +i*N+j));
    }
   // printf("\n");
}


}
karmasik * ozdegerbul(int *matris,int n){





    free(katsayilar);
    free(matrisUst);

    katsayilar =(int *)malloc((n+1)*sizeof(int ));
    matrisUst = (int *)malloc(n*n*n*sizeof(int ));




    for(int i =0;i<n*n;i++)
    *(matrisUst+i)=*(matris+i);
    for(int i=1;i<n;i++){


        mtcarp((matrisUst+(i-1)*n*n),(matrisUst),(matrisUst+i*n*n),n); //mastrisin usleini bulup matrisUst e atar

    }
    *(katsayilar)=pow(-1,n);
    for(int i =1;i<=n;i++){
        int toplam=0;
            for(int k=i-1,j=0;k>=0;k--,j++){
                toplam+=*(katsayilar+k)*tr((matrisUst+(j)*n*n),n);     //
            }

        *(katsayilar+i)=(-1.0/i)*toplam;

    }
    /*
    for(int i=0;i<n;i++){
    for(int j =0;j<n;j++){
        printf(" %d ",*(matris+i*n+j));

    }

    printf("\n");
    }*/

    //****************************************************************************************************************
    for(int k =0;k<n;k++){
//???
            fprintf(dosya,"\n%d. us\n",k+1);
            printf("%d. us\n",k+1);
    for(int i=0;i<n;i++){
    for(int j =0;j<n;j++){
        printf("%15d",*(matrisUst+k*n*n+i*n+j));
        fprintf(dosya,"%15d",*(matrisUst+k*n*n+i*n+j));
    }
    fprintf(dosya,"\n");
    printf("\n");
    }
    fprintf(dosya,"\n");
    printf("\n");
    }
    //*****************************************************************************************************************
    fprintf(dosya,"\n\nKarakteristik denklem katsayilari \n\n");
    printf("\n\nKarakteristik denklem katsayilari \n\n");
    for(int i =0;i<=n;i++){

        fprintf(dosya, "%15d  ",*(katsayilar+i));
        printf("%15d  ",*(katsayilar+i));
    }
    fprintf(dosya,"\n\n\n\n");
    printf("\n");

return durandKerner(katsayilar,n);;
}
//GIRILEN MATRISIN IZINI BULUP DONDURUR
int tr(int * m,int n){


    /*   for(int i=0;i<n;i++){
    for(int j =0;j<n;j++){
        printf(" %d ",*(m+i*n+j));

    }
    printf("\n");
    }*/



    int trace=0;
    for(int i=0,j=0;i<n;i++,j++){
        trace+=*(m+i*n+j);
    }
    fprintf(dosya,"\n trace %d\n",trace);
//printf("\n trace %d\n",trace);
return trace;

}

karmasik * durandKerner(int * karakDenklem,int N){
    if(*(karakDenklem)<0)
        for(int i=0;i<=N;i++){
            *(karakDenklem+i)*=-1;
        }

    karmasik first;
    first.i=0.9;
    first.reel=0.4;     //BASLANGIC DEGERİ KARESİ 1 OLMAYAN YADA REEL OLMAYAN HERHANGI BIR SAYI
    kokler=(karmasik *)malloc(N*sizeof(karmasik));

    //SECILEN DEGERIN ILK KOKTEN N. KOKE KADAR BASLANGIC DEGERLERINI ATAR
    for(int i=0;i<N;i++)
        *(kokler +i)=kpower(first,i);



for(int f=0;f<100;f++){


    for(int i=0;i<N;i++){
        karmasik payda;
        payda.i=0;
        payda.reel=1;
        for(int j =i+1;j<N;j++){
                payda=kcarp(payda,kfark(*(kokler+i),*(kokler+j)));

        }


        for(int k = i-1;k>=0;k--){
                 payda=kcarp(payda,kfark(*(kokler+i),*(kokler+k)));
        }
        *(kokler+i)=kfark(*(kokler+i),kbol(yerinekoy(karakDenklem,*(kokler+i),N),payda));

        fprintf(dosya,"%d. adim   %d.kok  =%f   %fi\n",f+1,i+1,(kokler+i)->reel,(kokler+i)->i);
    }
        fprintf(dosya, "\n\n");
}
    printf("\n\nOzdegerler \n\n");
    fprintf(dosya, "\n\nOzdegerler\n\n");

    for(int i =0 ;i<N;i++){
         printf("%f   %fi\n\n",(kokler+i)->reel,(kokler+i)->i);
        fprintf(dosya,"%f   %fi\n\n",(kokler+i)->reel,(kokler+i)->i);
    }

return kokler;

}
karmasik kcarp(karmasik a,karmasik b){
    karmasik ret;
    ret.reel=a.reel*b.reel-a.i*b.i;
    ret.i=a.reel*b.i+a.i*b.reel;
    return ret;
}
karmasik kpower(karmasik a ,int pow){
    if(pow==0){
            karmasik x;
            x.reel=1;
            x.i=0;
            return x;
            }
    if(a.reel==0&&a.i==0)
        return a;

        karmasik ret=a;
        while(--pow){
            ret=kcarp(ret,a);
        }
        return ret;
}
karmasik yerinekoy(int * denklem,karmasik a,int N){




    karmasik ret;
    ret.i=0;
    ret.reel=0;
    karmasik temp;
    temp.i=0;
    temp.reel=0;
    for(int i=0;i<N+1;i++)
{
    temp.reel=*(denklem+i);
    ret=ktopla(ret,kcarp(temp,kpower(a,N-i)));
}

return ret;
}
karmasik ktopla(karmasik a,karmasik b){
    karmasik ret;
    ret.i=a.i+b.i;
    ret.reel=a.reel+b.reel;
    return ret;
}
karmasik kfark(karmasik a, karmasik b){
    karmasik ret;
    ret.i=a.i-b.i;
    ret.reel=a.reel-b.reel;

    return ret;
}
karmasik kbol(karmasik a,karmasik b){
    karmasik ret;
    ret.i=(a.i*b.reel-a.reel*b.i)/(b.reel*b.reel+b.i*b.i);
    ret.reel=(a.reel*b.reel+a.i*b.i)/(b.reel*b.reel+b.i*b.i);
    return ret;
}

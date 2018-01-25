/*						prolab2 kayit bilgileri duzenleme
		Furkan ŞAHİN 170201112								Burak YAZICI 170201106
"ornek_ogrenci_kayit_bilgileri.txt" dosyasindaki kayit bilgilerini duzenleyip siniflama islemini yapan program .

*/

#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <string.h>



FILE *dosya, *dosya1, *dosya2;

typedef struct {
    int numara;
    char ad[15];
    char soyad[15];
    int kayit;
    char ogretim[2];
}ogrenci;

ogrenci oku(FILE *dosyaoku); //satır 40-53
void ekranaYaz(ogrenci ogr); //satır 55-57
void dosyayayaz(FILE *dosyayaz, ogrenci ogr); //satır 59-61
void kopyasil();        //satır 63-102 - güncellenecek
void sirala();       //satır 105 - 196
void sinifla();
void numaralandir(ogrenci ogr, int basla, int n); //satır 199-225
void sortt(int * a,int n);
void esit(int bir,int iki,int * a,int n);
void minn(int bir,int iki,int * a,int n);
int ogrSayisi1=0,ogrSayisi2=0;
double topCalismaZmn=0;
int main()
{
    kopyasil();
    sirala();

    sinifla();
    return 0;
}

ogrenci oku (FILE *dosyaoku){

    ogrenci ogr;
    char data[50];
    fscanf(dosyaoku,"%s",data);
    ogr.numara = atoi(data);
    fscanf(dosyaoku,"%s",ogr.ad);
    fscanf(dosyaoku,"%s",ogr.soyad);
    fscanf(dosyaoku,"%s",data);
    ogr.kayit = atoi(data);
    fscanf(dosyaoku,"%s",ogr.ogretim);

    return ogr;
}

void ekranayaz(ogrenci ogr){
printf("%-13d%-12s%-11s%-5d%13s\n",ogr.numara,ogr.ad,ogr.soyad,ogr.kayit,ogr.ogretim);
}

void dosyayaz(FILE *dosyayaz, ogrenci ogr){
fprintf(dosyayaz,"%-13d%-12s%-11s%-5d%13s\n",ogr.numara,ogr.ad,ogr.soyad,ogr.kayit,ogr.ogretim);
}

void kopyasil()
{
    clock_t bas=clock();
    printf("\nAyni kayitlar siliniyor...\n");
    ogrenci ogrenci1, ogrenci2;
    int sil=0;

    dosya = (fopen("ornek_ogrenci_kayit_bilgileri.txt","r"));
    dosya2 = (fopen("yeni.txt","w"));

    char data[50];
    fgets (data, 50, dosya);
    fprintf (dosya2,"%s",data);

    ogrenci1 = oku(dosya);

    while(1)
    {
        ogrenci2 = oku(dosya);
        if(feof(dosya))
        break;

        if(strcmp(ogrenci1.ad,ogrenci2.ad) == 0&&strcmp(ogrenci1.soyad,ogrenci2.soyad) == 0)
        {
            printf("Kayit Silindi:\n");
            ekranayaz(ogrenci2);
            sil++;
        }

        else
        {
            dosyayaz(dosya2,ogrenci1);
            if(ogrenci1.ogretim[1]=='I')
                ogrSayisi2++;
            else
                ogrSayisi1++;
            ogrenci1=ogrenci2;
        }
    }
    dosyayaz(dosya2,ogrenci1);
    if(ogrenci1.ogretim[1]=='I')
                ogrSayisi2++;
            else
                ogrSayisi1++;
    printf("%d Kayit silindi\n\n", sil);

    fclose(dosya);
    fclose(dosya2);
    clock_t son=clock();
    double zaman = (double)(son-bas)/CLOCKS_PER_SEC;
    printf("Kayit silme zamani = %f\n",zaman);
    topCalismaZmn+=zaman;

}


void sirala()
{
    clock_t bas=clock();
    printf("Kayitlar siralaniyor...\n");

    dosya = (fopen("yeni.txt","r"));
    dosya1 = (fopen("ogr1.txt","w+"));
    dosya2 = (fopen("ogr2.txt","w+"));

    ogrenci ogrenci1, ogrenci2;
    char data[50];


    fgets (data, 50, dosya);
    fprintf(dosya1, "%s",data);
    fprintf (dosya2,"%s",data);

    int basla = ftell(dosya);

    int ogrson=0;
    ogrenci2.numara=9999999;


    while(1){

        while(1){
            ogrenci1 = oku(dosya);

            if(feof(dosya))
                    break;

            if (ogrenci1.numara>ogrson){
                if(ogrenci1.numara<ogrenci2.numara)
                    ogrenci2=ogrenci1;
            }
        }

        if(ogrenci2.numara==ogrson){break;}
        if(ogrenci2.numara!=9999999)
        {//ekranayaz(ogrenci2);
         if(strcmp(ogrenci2.ogretim,"I"))
            {dosyayaz(dosya2,ogrenci2);
            }
         else
            {dosyayaz(dosya1,ogrenci2);
            }
        }

        ogrson=ogrenci2.numara;
        ogrenci2.numara=9999999;
        fseek(dosya,basla,SEEK_SET);
    }

    printf("Numarali olanlar siralandi\n");

    int n=0;
    ogrson=0;
    ogrenci2.kayit=9999;
    fseek(dosya,basla,SEEK_SET);

    while(1){

        while(1){

            ogrenci1 = oku(dosya);

            if(feof(dosya))
                    break;

            if (ogrenci1.kayit>ogrson&&ogrenci1.kayit<ogrenci2.kayit){
                    ogrenci2=ogrenci1;
            }

        }

        if(ogrenci2.kayit-ogrson!=1){n++;}  // Eksik olan kayıt numarasını bulmak için

        if(ogrenci2.kayit==ogrson){break;}

        if(ogrenci2.kayit!=9999){
            numaralandir(ogrenci2, basla, n);
        }
        ogrson=ogrenci2.kayit;
        ogrenci2.kayit=9999;
        fseek(dosya,basla,SEEK_SET);

    }
    printf("Numarasiz olanlar siralandi, kayit numaralari guncellendi ve numara verildi.\n");

    fclose(dosya);
    fclose(dosya1);
    fclose(dosya2);
    clock_t son=clock();
    double zaman= (double)(son-bas)/CLOCKS_PER_SEC;
    printf("Numaralandirma ve siralama zamani = %f\n",zaman);
    topCalismaZmn+=zaman;

}


void numaralandir(ogrenci ogr, int basla, int n){
    clock_t bas=clock();
    static int num1 = 1701001, num2 = 1702001;
    ogrenci ogr2;
    ogr.kayit -= n;


    switch (ogr.ogretim[1]){

        case('I'): //2. öğretim
           // printf("2. ogretim\t");
            ogr.numara=num2;
            dosyayaz(dosya2,ogr);
            num2++;

        break;

        default: // 1. öğretim
           // printf("1. ogretim\t");
            ogr.numara=num1;
            dosyayaz(dosya1,ogr);

            num1++;

        break;
    }
    //ekranayaz(ogr);
}
void sinifla(){

    int sinifAdet;
    printf("Sinif Sayisini Giriniz : ");
    scanf("%d",&sinifAdet);
    int *sinifKapasite=(int*)malloc(sinifAdet*sizeof(int));
    printf("Sinif Kapasitelerini giriniz : ");

    while(1) {
        int topKapasite=0;
    for(int i=0;i<sinifAdet;i++){
        scanf("%d",(sinifKapasite+i));
        topKapasite+=*(sinifKapasite+i);
    }
    if(topKapasite>=ogrSayisi1 &&  topKapasite>=ogrSayisi2)break;
    else printf("Sinif kapasiteleri yetersiz tekrar girin \n");

    printf("Dersi Alan Ogrenci Sayisi 1. Ogretim : %d 2. Ogretim : %d \n",ogrSayisi1,ogrSayisi2);
    }





    int esitormin=0;
    printf("Esit dagitim icin 0 girin , en az sinif icin herhangi bir deger :");
    scanf("%d",&esitormin);
    clock_t bas =clock();
    sortt(sinifKapasite,sinifAdet);
    /*
        for(int i=0;i<sinifAdet;i++)
        printf("%d \n",*(sinifKapasite+i));
    */
    if(!esitormin){
        esit(ogrSayisi1,ogrSayisi2,sinifKapasite,sinifAdet);
    }
    else{
        minn(ogrSayisi1,ogrSayisi2,sinifKapasite,sinifAdet);
    }
    clock_t son=clock();
    double zaman = (double)(son-bas)/CLOCKS_PER_SEC;
    printf("Siniflara dagitma zamani = %f\n",zaman);
    topCalismaZmn+=zaman;
    printf("Toplam calisma zamani = %f",topCalismaZmn);
}
void sortt(int *a,int n){
    int min;
    int indis=0;
    for(int i=0;i<n-1;i++){
            min=*(a+i);
            indis=i;
        for(int j=i+1;j<n;j++){
        if(*(a+j) < min){
            min=*(a+j);
            indis=j;
    }
    }
    *(a+indis)=*(a+i);
    *(a+i)=min;

}

}



void minn(int bir,int iki,int * a,int n){
    dosya1 = (fopen("ogr1.txt","r"));
    dosya2 = (fopen("ogr2.txt","r"));
    ogrenci temp;
    char baslik[50];
    fgets (baslik, 50, dosya1);
    fgets(baslik,50,dosya2);
    int indis=1;

    for(int i=n-1;bir>0;i--){
            bir-=*(a+i);
            int mevcut=*(a+i);
            char buf[50]="I .ogretim sinif  .txt";
            buf[17]=indis+48;
            indis++;
            buf[23]='\0';




            dosya= fopen(buf, "w+");

            fprintf (dosya,"%s",baslik);
            ogrenci temp;

            while(1){
                    if(feof(dosya1))break;

                    temp=oku(dosya1);
                    if(temp.numara<1000000)break;
                dosyayaz( dosya,temp);


                if(!(--mevcut))break;

            }
            fclose(dosya);


    }
    indis =1;
    for(int i=n-1;iki>0;i--){
            iki-=*(a+i);
            int mevcut=*(a+i);
            char buf[50]="II.ogretim sinif  .txt";

            buf[17]=indis+48;
            indis++;
            buf[23]='\0';


            dosya= fopen(buf, "w+");

            fprintf (dosya,"%s",baslik);
            ogrenci temp;

            while(1){
                    if(feof(dosya2))break;

                    temp=oku(dosya2);
                    if(temp.numara<1000000)break;
                dosyayaz( dosya,temp);
               // ekranayaz(temp);

                if(!(--mevcut))break;

            }
            fclose(dosya);


    }



    fclose(dosya2);
    fclose(dosya1);
}

void esit(int bir,int iki,int *a,int n){
    dosya1 = (fopen("ogr1.txt","r"));
    dosya2 = (fopen("ogr2.txt","r"));
    ogrenci temp;
    char baslik[50];
    fgets (baslik, 50, dosya1);
    fgets(baslik,50,dosya2);
    int i=0;
    int indis=1;
    int ort;
    for(;i<n;i++){
            ort=bir/(n-i);
            if(*(a+i)<ort){
            bir-=*(a+i);
            int mevcut=*(a+i);
            char buf[50]="I .ogretim sinif  .txt";

            buf[17]=indis+48;
            indis++;
            buf[23]='\0';
            dosya= fopen(buf, "w+");

            fprintf (dosya,"%s",baslik);
            ogrenci temp;

            while(1){
                    if(feof(dosya1))break;

                    temp=oku(dosya1);
                    if(temp.numara<1000000)break;
                dosyayaz( dosya,temp);
              //  ekranayaz(temp);

                if(!(--mevcut))break;

            }
            fclose(dosya);



            }
            else
                {
                    bir-=ort;

                    char buf[50]="I .ogretim sinif  .txt";

                    buf[17]=indis+48;
                    indis++;
                    buf[23]='\0';


                    dosya= fopen(buf, "w+");

                    fprintf (dosya,"%s",baslik);
                    ogrenci temp;

            while(1){
                    if(feof(dosya1))break;

                    temp=oku(dosya1);
                    if(temp.numara<1000000)break;
                    dosyayaz( dosya,temp);
                  //  ekranayaz(temp);

                    if(!(--ort))break;

            }
                    fclose(dosya);


                }


    }
    indis =1;
    i=0;
    for(;i<n;i++){
            ort=iki/(n-i);
            if(*(a+i)<ort){
            iki-=*(a+i);
            int mevcut=*(a+i);
            char buf[50]="II.ogretim sinif  .txt";

            buf[17]=indis+48;
            indis++;
            buf[23]='\0';


            dosya= fopen(buf, "w+");

            fprintf (dosya,"%s",baslik);
            ogrenci temp;

            while(1){
                    if(feof(dosya1))break;

                    temp=oku(dosya2);
                    if(temp.numara<1000000)break;
                dosyayaz( dosya,temp);
               // ekranayaz(temp);

                if(!(--mevcut))break;

            }
            fclose(dosya);



            }
            else
                {
                    iki-=ort;

                    char buf[50]="II.ogretim sinif  .txt";

                    buf[17]=indis+48;
                    indis++;
                    buf[23]='\0';


                    dosya= fopen(buf, "w+");

                    fprintf (dosya,"%s",baslik);
                    ogrenci temp;

            while(1){
                    if(feof(dosya2))break;

                    temp=oku(dosya2);
                    if(temp.numara<1000000)break;
                    dosyayaz( dosya,temp);
                   // ekranayaz(temp);

                    if(!(--ort))break;

            }
                    fclose(dosya);


                }


    }
    fclose(dosya1);
    fclose(dosya2);


}





/*
 * utils.c
 *
 *  Created on: Apr 25, 2018
 *      Author: brk
 */
#include "myutils.h"
extern int banknots[9];
extern char * strings[];
extern int intVals[];

extern int girdi[4];

extern int banknots[9];
extern int virgulVar;


extern char put[6];
void bozdur(void)
{

    int para = bToI();
    int i = 0;
    for (; i < 9; i++)
    {
        banknots[i] = para / intVals[i];
        para = para % intVals[i];
    }
}
void banknotYaz(int* banknot)
{
    while(banknots[++(*banknot)] == 0);
    if(*banknot !=9)
    {
        Lcd_Temizle();
        Lcd_Goto(1, 1);
        Lcd_Putch(banknots[*banknot] + '0');
        Lcd_Puts(" adet ");
        Lcd_Puts(strings[*banknot]);
        Lcd_Goto(2, 1);
                getSayi(put);
                Lcd_Puts(put);
        int girdi=  bToI();
        girdi-= banknots[*banknot] * intVals[*banknot];
        iToB(girdi);
    }
}


int power(int base, int pow)
{
    int ret = 1;
    int i = 0;
    for (; i < pow; i++)
        ret *= base;
    return ret;
}
int bToI()
{
    int girdii = 0;
    int indis = 0;
    for (; indis < 4; indis++)
        girdii += girdi[indis] * power(10, 3 - indis);
    return girdii;
}
void iToB(int intt)
{
    int i = 0;
    for (; i < 4; i++)
    {
        girdi[i] = intt / power(10, 3 - i);
        intt -= girdi[i] * power(10, 3 - i);
    }
}
void getSayi(char put[])
{
    int i = 0;
    for (; i < 3; i++)
    {
        if (i == 2)
        {
            put[i] = (virgulVar) ? ',' : ' ';
            continue;
        }
        put[i] = '0' + girdi[i];
    }
    for (; i < 5; i++)
        put[i] = '0' + girdi[i - 1];
    put[i] = '\0';
}
void resetPara()
{
    int i = 0;
    for (; i < 4; i++)
        girdi[i] = 0;
}


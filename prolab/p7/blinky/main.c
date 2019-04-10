


#include <stdint.h>
#include <stdbool.h>
#include "inc/hw_memmap.h"
#include "inc/hw_types.h"
#include "driverlib/gpio.h"
#include "driverlib/pin_map.h"
#include "driverlib/rom.h"
#include "driverlib/sysctl.h"
#include "lcd.h"
#include "myutils.h"

char * strings[] = { "yirmilik", "onlira", "beslira", "birlira", "yarimlira",
                           "cyrklira", "10krs", "5krs", "1krs" };
int calculate = 1000000;
int writeAll = 1500000;

int intVals[] = { 2000, 1000, 500, 100, 50, 25, 10, 5, 1 };

int virgulleReset = 5000000;
int yavaslaBiraz = 2000000;
int banknots[9];
int virgulVar = 0;


uint32_t g_ui32Flags;

#ifdef DEBUG
void
__error__(char *pcFilename, uint32_t ui32Line)
{
}
#endif




int girdi[4] = { 0, 0, 0, 0 };
char put[6];



int main(void)
{
    int totTime = 0;
       int virgulTime = 0;
       int iiiii = -1;
       int yazdirmaMod = 0;
       int clicked = 0;




    ROM_SysCtlClockSet(SYSCTL_SYSDIV_4 | SYSCTL_OSC_MAIN | SYSCTL_USE_PLL |
    SYSCTL_XTAL_16MHZ);







    SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOA);

    GPIOPinTypeGPIOInput(
            GPIO_PORTA_BASE,
            GPIO_PIN_2 | GPIO_PIN_3 | GPIO_PIN_4 | GPIO_PIN_5 | GPIO_PIN_6);

    GPIOPadConfigSet(
            GPIO_PORTA_BASE,
            GPIO_PIN_2 | GPIO_PIN_3 | GPIO_PIN_4 | GPIO_PIN_5 | GPIO_PIN_6,
            GPIO_STRENGTH_2MA, GPIO_PIN_TYPE_STD_WPU);
    Lcd_init();
    Lcd_Goto(1, 1);
    Lcd_Puts("!!!!!!!!!!!!");


    while (1)
    {


        if(yazdirmaMod == 1 )goto SIFIRLAKONTROLU;
        if (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_2) == 0)
        {

            clicked = 1;
            totTime = 0;
            if (girdi[0] == 9)
                girdi[0] = 0;
            else
                girdi[0]++;
            Lcd_Temizle();
            Lcd_Goto(1, 11);
            getSayi(put);
            Lcd_Puts(put);

            while (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_2) == 0)
                ;
            SysCtlDelay(yavaslaBiraz);
        }
        if (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_3) == 0)
        {
            clicked = 1;
            totTime = 0;
            if (girdi[1] == 9)
                girdi[1] = 0;
            else
                girdi[1]++;
            Lcd_Temizle();
            Lcd_Goto(1, 11);
            getSayi(put);
            Lcd_Puts(put);

            while (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_3) == 0)
                ;
            SysCtlDelay(yavaslaBiraz);

        }

        if (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_5) == 0)
        {
            clicked = 1;
            totTime = 0;
            if (girdi[2] == 9)
                girdi[2] = 0;
            else
                girdi[2]++;
            Lcd_Temizle();
            Lcd_Goto(1, 11);
            getSayi(put);
            Lcd_Puts(put);
            while (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_5) == 0)
                ;
            SysCtlDelay(yavaslaBiraz);

        }
        if (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_6) == 0)
        {
            clicked = 1;
            totTime = 0;
            if (girdi[3] == 9)
                girdi[3] = 0;
            else
                girdi[3]++;
            Lcd_Temizle();
            Lcd_Goto(1, 11);
            getSayi(put);
            Lcd_Puts(put);
            while (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_6) == 0)
                ;

            SysCtlDelay(yavaslaBiraz);

        }


        SIFIRLAKONTROLU:
        if (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_4) == 0)
               {
                   totTime = 0;
                   virgulVar ^= 1;

                   while (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_4) == 0)
                   {
                       virgulTime++;
                       if (virgulTime > virgulleReset)
                       {


                           resetPara();
                           virgulTime = 0;
                           iiiii = -1;
                           yazdirmaMod = 0;
                           Lcd_Temizle();
                           Lcd_Goto(1, 11);
                           getSayi(put);
                           Lcd_Puts(put);
                           break;

                       }

                   };


                   Lcd_Temizle();
                   Lcd_Goto(1, 11);
                   getSayi(put);
                   Lcd_Puts(put);
                   SysCtlDelay(yavaslaBiraz);


               }
        totTime++;

        if (clicked == 1 && totTime > calculate )
        {
            clicked = 0;

            bozdur();
            yazdirmaMod = 1;
            totTime = 0;

        }
        if (yazdirmaMod == 1 && totTime > writeAll)
        {
            totTime = 0;

            if (iiiii == 9)
            {
                iiiii = -1;
                yazdirmaMod = 0;
                resetPara();
                SysCtlDelay(10000000);
                Lcd_Temizle();
                Lcd_Goto(1, 11);
                getSayi(put);
                Lcd_Puts(put);
            }
            else{
                banknotYaz(&(iiiii));
            }
        }
    }
}



package prolab4;

import java.util.Scanner;

public class Tictactoe {

	public static void main(String[] args) {
		
		OyunTahtasi t1= new OyunTahtasi();
		Oyuncu o1 =new Oyuncu();
		Oyuncu o2 =new Oyuncu(false);
		
		t1.oyunTahtasiniYazdir();
		
		
		
		
		while(true){
			t1.hamleyiYaz(o1, o1.karakterAl());
			t1.oyunTahtasiniYazdir();
			t1.hamleyiYaz(o2, o2.karakterAl());
			t1.oyunTahtasiniYazdir();
		}
	
	}

}

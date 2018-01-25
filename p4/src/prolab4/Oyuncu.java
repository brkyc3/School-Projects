package prolab4;

import java.util.Scanner;

public class Oyuncu {
	char XorO;
	boolean insanMi;
	String name = "adsiz";

	public Oyuncu() {
		insanMi = true;
		XorO = 'X';
	}

	public Oyuncu(boolean inMi) {
		insanMi = inMi;
		XorO = (inMi) ? 'X' : 'O';

	}

	public Oyuncu(boolean inMi, char karakter) {
		insanMi = inMi;
		XorO = karakter;
	}

	public Oyuncu(boolean inMi, char karakter, String name) {
		insanMi = inMi;
		XorO = karakter;
		this.name = name;
	}

	public char karakterAl() {

		return XorO;
	}

	public boolean oyuncununTurunuAl() {
		return insanMi;
	}

	public String oyuncununHamlesiniAl(int boyut) {

		return (insanMi) ? insanOyuncuHamlesiKontrol() : BilgisayarHamlesiUret(boyut);
	}

	public String insanOyuncuHamlesiKontrol() {
		System.out.print("Hamlenizi girin :");
		Scanner scc = new Scanner(System.in);
		String s="";
				s+=scc.nextLine();
				
		return s;
	}

	public String BilgisayarHamlesiUret(int boyut) {
		String s =""+(char)(65+Math.random()*boyut);
		s+=(char)(Math.random()*boyut+49);
		return s;
	}
}

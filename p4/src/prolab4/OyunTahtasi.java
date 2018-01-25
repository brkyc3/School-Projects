package prolab4;

import java.util.Scanner;

public class OyunTahtasi {

	private int tahta[][];
	 int boyut;

	public OyunTahtasi() {
		this.tahta = oyunTahtasiniAl();
	}

	public OyunTahtasi(int tahta[][]) {
		this.tahta = tahta;
	}

	public int[][] oyunTahtasiniAl() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Tahta boyutunu giriniz : ");
		int n = sc.nextInt();
		boyut = n;
		
		int t[][] =new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				t[i][j]=-1;
			}
		}
			
				
		return t ;
	}

	public void oyunTahtasiniYazdir() {

		for (int i = 0; i < boyut; i++)
			System.out.print("\t"+(char) (65 + i)+"   ");
		System.out.println();
		for (int i = 0; i < boyut; i++) {
			System.out.print(" " + (i + 1) + " |");
			for (int j = 0; j < boyut; j++) {
				
				switch (tahta[i][j]) {
				case -1:
					System.out.print("\t    |");
					break;

				case 0:
					System.out.print("\tO   |");
					break;
				case 1:
					System.out.print("\tX   |");
					break;
				}

			}
			System.out.println();
		}
	}
	
	public boolean hamleyiYaz(Oyuncu o,char oyuncu){
		String koor=o.oyuncununHamlesiniAl(boyut);
		int a= (oyuncu=='X') ? 1:0;
		char c1=koor.charAt(0),c2=koor.charAt(1);
		int x,y;
		if(Character.isAlphabetic(c1)){
			c1=Character.toUpperCase(c1);
			y=c1-65;
			x=c2-49;
		}
		else{
			c2=Character.toUpperCase(c2);
			x=c1-49;
			y=c2-65;
		}
		System.out.println("x "+x+" y "+y);
		
		if(tahta[x][y]==-1){
			tahta[x][y]=a;
			if(kazanan(oyuncu,x,y))return true;
			
		}else{
			hamleyiYaz(o, oyuncu);
			return true;
		}
		
			
			
		return false;
		
	}
	
	public boolean kazanan(char oyuncu,int x,int y){
		int c= (oyuncu=='X') ? 1:0;
		
		return true;
	}
	public boolean beraberlikKontrol(){
		return true;
	}
}

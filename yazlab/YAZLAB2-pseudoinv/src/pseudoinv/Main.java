package pseudoinv;



public class Main {
	
	
	public static void test() {
		Matrix m = new Matrix(5, 4);
		Double ar[][] = new Double[5][4];
		ar[0][0]=0.;
		ar[0][1]=2.;
		ar[0][2]=1.;
		ar[0][3]=0.;
		ar[1][0]=3.;
		ar[1][1]=2.;
		ar[1][2]=5.;
		ar[1][3]=0.;
		ar[2][0]=2.;
		ar[2][1]=2.;
		ar[2][2]=3.;
		ar[2][3]=6.;
		ar[3][0]=7.;
		ar[3][1]=2.;
		ar[3][2]=1.;
		ar[3][3]=2.;
		ar[4][0]=1.;
		ar[4][1]=3.;
		ar[4][2]=3.;
		ar[4][3]=9.;
		
		m.setData(ar);
		m.print();
		
		m.transpose().print();
			
		try {
			m.pinv().print();
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		test();
		
		

	}

}

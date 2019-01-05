package pseudoinv;
// KULLANILAN YONTEM MOORE PENROSE PSEUDO INVERSE
public class Matrix {
	private Double data[][];

	public void setData(Double[][] data) {
		this.data = data;
	}

	private int r;
	private int c;

	public Matrix(int m, int n) {
		this.r = m;
		this.c = n;
		data = new Double[m][n];

	}

	public Matrix(Double dt[][]) {
		this.r = dt.length;
		this.c = dt[0].length;
		data = dt;
	}

	public Matrix print() {
		System.out.println();
		for (Double d[] : this.data) {
			for (Double dd : d) {
				System.out.print(dd + " ");
			}
			System.out.println();
		}
		System.out.println();
		return this;
	}

	private void replaceRow(int r1, int r2) {
		Double row[];
		row = this.data[r1];
		this.data[r1] = this.data[r2];
		this.data[r2] = row;
	}

	
	// 2 * sutun sayisi      kadar carpma               sutun sayisi kadar toplama
	private void rowOperation(double d1, int r1, double d2, int r2, int r3) {

		for (int i = 0; i < this.c; i++) {
			this.data[r3][i] = d1 * this.data[r1][i] + d2 * this.data[r2][i];
		}
	}


//satirsayisi * satirsayisi * rowOperation
	public Matrix inverse() throws Throwable {
		
		
		
		//mevcut matrisin yanina birim matris ekleme
		Double ar[][] = new Double[r][2 * c];
		for (int i = 0; i < r; i++)
			for (int j = 0; j < 2 * c; j++) {
				if (j < c)
					ar[i][j] = this.data[i][j];
				else if ((i + c) == j)
					ar[i][j] = 1.;
				else
					ar[i][j] = 0.;
			}
		Matrix mt = new Matrix(ar);
		
		 
		for (int i = 0; i < r; i++) {

			if (mt.data[i][i] == 0) {
				for (int j = i + 1; j < r; j++) {
					if (mt.data[j][i] != 0) {
						mt.replaceRow(i, j);
						break;
					}
					if (j == (r - 1))
						throw new Throwable("not invertable");

				}
			}

			
			// row operation
			mt.rowOperation(1 / mt.data[i][i], i, 0, 0, i);

			
			
			// satirsayisi -1   * rowOperation
			for (int j = 0; j < r; j++) {
				if (j != i) {
					mt.rowOperation(-mt.data[j][i], i, 1, j, j);
				}
				
				
				
				// her seferde satirsayisi * rowOperation
			}
			
			// toplam satirsayisi * satirsayisi * rowOperation
		}
		
		
		

		return mt.subMatrix(0, c, r - 1, 2 * c - 1);
	}

	public Matrix subMatrix(int r1, int c1, int r2, int c2) {

		Double d[][] = new Double[r2 - r1 + 1][c2 - c1 + 1];
		for (int i = r1; i < r2 + 1; i++) {
			for (int j = c1; j < c2 + 1; j++) {
				d[i - r1][j - c1] = data[i][j];
			}
		}
		return new Matrix(d);
	}

	public Matrix transpose() {
		Double d[][] = new Double[this.c][this.r];
		for (int i = 0; i < this.r; i++) {
			for (int j = 0; j < this.c; j++) {
				d[j][i] = this.data[i][j];
			}
		}

		return new Matrix(d);
	}

	
	
	// n1 *n2 *m1   carpma   ve toplama
	public Matrix matMul(Matrix r) {

		Matrix ret = new Matrix(this.r, r.c);

		for (int i = 0; i < this.r; i++) {
			for (int j = 0; j < r.c; j++) {
				ret.data[i][j] = 0.;
				for (int k = 0; k < r.r; k++) {
					ret.data[i][j] += this.data[i][k] * r.data[k][j];
				}
			}
		}

		return ret;
	}

	// Aright=AT(AAT)−1 wide matrix // 
	// Aleft=(ATA)−1AT tall matrix
	public Matrix pinv() throws Throwable {
		if (this.r < this.c) {//right pseudo inverse
			return this.transpose().matMul(this.matMul(this.transpose()).inverse());

		} else if (this.r == this.c)
			return this.inverse();
		else//left pseudo inverse
			return this.transpose().matMul(this).inverse().matMul(this.transpose());
	}
	
	
	
	
	
	// YAPILAN CARPMA VE TOPLAMA ISLEM SAYISI GOSTERILMESI GEREKIYOR
	public int totalMul() {
		if (this.r < this.c) {//right pseudo inverse
			
			//a*at -> r*r lik matris    at * inv -> c*r lik ma
			return c*r*r+ r*r*2*r + r*c*c;

		} else if (this.r == this.c)
			return r*r*2*r;
		else//left pseudo inverse
			
			//at*a -> c*c lik matris    inv * at -> c*r lik ma
			return r*c*c +c*c*2*c + c*r*c;
		
	}
	public int totalAdd() {
		if (this.r < this.c) {//right pseudo inverse
			
			//a*at -> r*r lik matris    at * inv -> c*r lik ma
			return c*r*r+ r*r*r + r*c*c;

		} else if (r == c) 
			return r*r*r;
		else//left pseudo inverse
			
			//at*a -> c*c lik matris    inv * at -> c*r lik ma
			return r*c*c +c*c*c + c*r*c;
		
	}
}

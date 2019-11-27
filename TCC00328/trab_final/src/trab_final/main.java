package trab_final;


import java.util.ArrayList;


public class main {
	
	
	
	
	public static void main(String[] args) {
		cliente c1;
		cliente c2;
		produto p1;
		manager m1;
		render r1;
		ArrayList<produto> prod_db = new ArrayList<>();
		ArrayList<cliente> cliente_db = new ArrayList<>();
		
		c1 = new cliente("default", 00000, "#####", "12345");
		c2 = new cliente("user", 11111, "#####", "11111");
		p1 = new produto("generico", 22222, 1.99f);
		
		prod_db.add(p1);
		cliente_db.add(c1);
		cliente_db.add(c2);
		
		m1 = new manager(cliente_db, prod_db);
		r1 = new render();
		
		r1.update(m1);
		
	}
	

	

	

}

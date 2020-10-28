package principal;

import datos.Objeto;
import datos.Usuario;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Objeto a = new Objeto(1,"aa");
		Objeto b = new Objeto(2,"bb");
		Objeto c = new Objeto(3,"cc");
		Objeto d = new Objeto(4,"dd");
		Objeto e = new Objeto(5,"ee");
		Objeto f = new Objeto(6,"ff");
		
		Usuario h = new Usuario("hh","hh");
		
		Videoclub.aniadirObjeto(a);
		Videoclub.aniadirObjeto(b);
		Videoclub.aniadirObjeto(c);
		Videoclub.aniadirObjeto(d);
		Videoclub.aniadirObjeto(e);
		Videoclub.aniadirObjeto(f);
		Videoclub.eliminarObjeto(3);
		
		
		Videoclub.aniadirUsuario(h);
		
		Videoclub.printMap(Videoclub.getObjetos());
		Videoclub.printTreeMap(Videoclub.getUsuarios());
	
		
		
	}
}

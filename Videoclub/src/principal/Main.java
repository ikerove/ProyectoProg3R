package principal;

import java.util.logging.Level;
import java.util.logging.Logger;

import baseDatos.BDException;
import datos.Multimedia;
import datos.Usuario;
import ventanas.VentanaUsuario;


//Clase Main donde se inicia el projecto
public class Main {
	
	private static Logger logger = Logger.getLogger( Main.class.getName() );
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			new VentanaUsuario();
		} catch (BDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*Multimedia a = new Multimedia(1,"aa");
		Multimedia b = new Multimedia(2,"bb");
		Multimedia c = new Multimedia(3,"cc");
		Multimedia d = new Multimedia(4,"dd");
		Multimedia e = new Multimedia(5,"ee");
		Multimedia f = new Multimedia(6,"ff");
		*/
		Usuario h = new Usuario("hh","hh");
		
		/*Videoclub.aniadirObjeto(a);
		Videoclub.aniadirObjeto(b);
		Videoclub.aniadirObjeto(c);
		Videoclub.aniadirObjeto(d);
		Videoclub.aniadirObjeto(e);
		Videoclub.aniadirObjeto(f);
		Videoclub.eliminarObjeto(3);*/
		
		
		Videoclub.aniadirUsuario(h);
		
		logger.log(Level.INFO, "Llamadas a los componentes del sistema");
		
		Videoclub.printMap(Videoclub.getObjetos());
		Videoclub.printTreeMap(Videoclub.getUsuarios());
	
		
		
	}
}

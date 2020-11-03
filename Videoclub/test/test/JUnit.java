package test;

import java.util.HashMap;
import java.util.TreeMap;

import baseDatos.BD;
import datos.Objeto;
import datos.Usuario;
import principal.Videoclub;
import junit.framework.TestCase;


public class JUnit  extends TestCase{
public void testconexionBD() {
	assertNotNull("No se ha establecido conexion", BD.initBD("videoclub.sqlite3"));
}



public void testaniadirUsuario() {
	TreeMap<String, Usuario> map = new TreeMap<>();
	Usuario u1 = new Usuario("nombre", "contrasenia");
	Usuario u2 = new Usuario("nombre", "contrasenia");
	Usuario u3 = new Usuario("nombre", "contrasenia");

	map.put(u1.getNick(), u1);
	map.put(u2.getNick(), u2);
	
	assertEquals(false, Videoclub.aniadirUsuario(u1));
	assertEquals(false, Videoclub.aniadirUsuario(u2));
	assertEquals(true, Videoclub.aniadirUsuario(u3));
}



public void testeliminarUsuario() {
	
	HashMap<Integer, Objeto> map = new HashMap<>();
	
	Usuario u1 = new Usuario("nombre", "contrasenia");
	Usuario u2 = new Usuario("nombre", "contrasenia");
	Usuario u3 = new Usuario("nombre", "contrasenia");
	
	map.remove(0,u1);
	
	assertEquals(null, map.remove(0, u1));
	
}

}

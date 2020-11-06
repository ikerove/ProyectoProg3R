package test;

import java.util.HashMap;
import java.util.TreeMap;

import baseDatos.BD;
import datos.Multimedia;
import datos.Usuario;
import principal.Videoclub;
import junit.framework.TestCase;

//Pruebas unitarias
public class JUnit  extends TestCase{
public void testconexionBD() {
	assertNotNull("Se ha establediconexcion", BD.initBD("videoclub.sqlite3"));
}



public void testaniadirUsuario() {
	TreeMap<String, Usuario> map = new TreeMap<>();
	
	Usuario u1 = new Usuario("nombre", "contrasenia");
	Usuario u2 = new Usuario("nombre", "contrasenia");
	Usuario u3 = new Usuario("nombre", "contrasenia");
	
	assertEquals(true, Videoclub.aniadirUsuario(u1));

	map.put(u2.getNick(), u2);
	assertEquals(false, Videoclub.aniadirUsuario(u2));

	
	
	
	
	
	
}



public void testeliminarUsuario() {
	
	HashMap<Integer, Multimedia> map = new HashMap<>();
	
	Usuario u1 = new Usuario("nombre", "contrasenia");
	Usuario u2 = new Usuario("nombre", "contrasenia");
	Usuario u3 = new Usuario("nombre", "contrasenia");
	
	map.remove(0,u1);
	
	assertEquals(false, map.remove(0, u1));
	
}

}

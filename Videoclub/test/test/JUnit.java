package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import baseDatos.BD;
import datos.Documental;
import datos.Multimedia;
import datos.Pelicula;
import datos.Serie;
import datos.Usuario;
import principal.Videoclub;
import junit.framework.TestCase;
import principal.Videoclub;

//Pruebas unitarias
public class JUnit extends TestCase {
	public void testconexionBD() {
		assertEquals("Se ha establediconexcion", BD.initBD("videoclub.sqlite3"));
	}

	public void testaniadirUsuario() {
		TreeMap<String, Usuario> map = new TreeMap<>();

		Usuario u1 = new Usuario("nombre", "contrasenia");
		Usuario u2 = new Usuario("nombre", "contrasenia");
		Usuario u3 = new Usuario("nombre", "contrasenia");

		assertEquals(true, Videoclub.aniadirUsuario(u1));

		map.put(u2.getNick(), u2);
		assertEquals(true, Videoclub.aniadirUsuario(u2));
	}

	public void testeliminarUsuario() {

		HashMap<Integer, Multimedia> map = new HashMap<>();

		Usuario u1 = new Usuario("nombre", "contrasenia");
		Usuario u2 = new Usuario("nombre", "contrasenia");
		Usuario u3 = new Usuario("nombre", "contrasenia");

		map.remove(0, u1);

		assertEquals(false, map.remove(0, u1));

	}

	public void testgetSerie() {
		Serie serie = new Serie();
		serie.setCodigo(1);
		serie.setTitulo("Arraow");

		assertEquals("Arrow", serie.getTitulo());
		assertEquals(1, serie.getCapitulos());
	}
	
	
	public void testgetDocumental(){
		Documental documento = new Documental();
		 documento.setCodigo(1);
		 
		 assertEquals(1, documento.getCodigo());
		
	
	}
	
	public void testgetPelicula(){
		Pelicula pelicula=new Pelicula();
		pelicula.setCodigo(1);
		assertEquals(1, pelicula.getCodigo());
	
	}
	public void testoToString(){
	Usuario usuario=new Usuario();
	usuario.setNick("Alfonso");
	usuario.setCon("123");
	assertEquals("Alfonso,123", usuario.toString());
	
	}
}	

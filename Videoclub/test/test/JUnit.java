	package test;
	
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.TreeMap;
	
	import baseDatos.BD;
	import datos.Multimedia;
import datos.Serie;
import datos.Usuario;
	import principal.Videoclub;
	import junit.framework.TestCase;
	import principal.Videoclub;
	
	//Pruebas unitarias
	public class JUnit  extends TestCase{
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
		public void testgetSerie() {
			Serie serie= new Serie();
			serie.setCodigo(1);
			serie.setTitulo("Arraow");
			
			assertEquals("Arrow", serie.getTitulo());
			assertEquals(1, serie.getCapitulos()); 
			
		
		}
		
		}

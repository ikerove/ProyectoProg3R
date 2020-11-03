package principal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import java.util.logging.Level;
import java.util.logging.Logger;



import datos.Objeto;
import datos.Usuario;


public class Videoclub {
	private static HashMap<Integer, Objeto> objetos = new HashMap<>();
	private static TreeMap<String,Usuario> usuarios  = new TreeMap<>();
	private static HashMap<String, ArrayList<Objeto>> favoritos = new HashMap<>();
	private static Logger logger = Logger.getLogger( Videoclub.class.getName() );
	
	
	
	
	
	public static HashMap<Integer, Objeto> getObjetos() {
		return objetos;
	}

	public static void setObjetos(HashMap<Integer, Objeto> objetos) {
		Videoclub.objetos = objetos;
	}

	public static TreeMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(TreeMap<String, Usuario> usuarios) {
		Videoclub.usuarios = usuarios;
	}

	public static HashMap<String, ArrayList<Objeto>> getFavoritos() {
		return favoritos;
	}

	public static void setFavoritos(HashMap<String, ArrayList<Objeto>> favoritos) {
		Videoclub.favoritos = favoritos;
	}
	
	
	
	

	public static boolean aniadirObjeto(Objeto o) {
		if(objetos.containsKey(o.getCodigo())) {
			logger.log(Level.WARNING,"Objeto previamente añadido");
			return false;
			
		}else {
			objetos.put(o.getCodigo(), o);
			logger.log(Level.INFO,"Objeto correctamente añadido");
			return true;
		}
		
	}
	
	public static Objeto eliminarObjeto(int codigo) {
		return objetos.remove(codigo);
	}
	
	public static void printMap(HashMap mapa) {
	       Iterator it = mapa.entrySet().iterator();
	       while (it.hasNext()) {
	    	   HashMap.Entry pair = (HashMap.Entry)it.next();
	           System.out.println(pair.getKey() + " = " + pair.getValue());
	           it.remove(); // avoids a ConcurrentModificationException
	       }
	}
	
	
	public static boolean aniadirUsuario(Usuario u) {
		if(usuarios.containsKey(u.getNick())) {
			logger.log(Level.WARNING,"Usuario previamente añadido");
			return false;
		
		}else {
			usuarios.put(u.getNick(), u);
			logger.log(Level.INFO,"Usuario añadido correctamente");
			return true;
		}
	}
	
	public static Objeto eliminarUsuario(String nick) {
		return objetos.remove(nick);
		
	}
	
	public static void printTreeMap(TreeMap mapa) {
	       Iterator it = mapa.entrySet().iterator();
	       while (it.hasNext()) {
	    	   HashMap.Entry pair = (HashMap.Entry)it.next();
	           System.out.println(pair.getKey() + " = " + pair.getValue());
	           it.remove(); // avoids a ConcurrentModificationException
	       }
	}
}

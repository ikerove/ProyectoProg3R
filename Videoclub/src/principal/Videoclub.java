package principal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import java.util.logging.Level;
import java.util.logging.Logger;



import datos.Multimedia;
import datos.Usuario;

//En esta clase tenemos diferentes "listas" para almacenar los objetos, usuarios y favoritos
//Ademas tenemos diferentes metodos para interectuar con cada lista
public class Videoclub {
	private static HashMap<Integer, Multimedia> objetos = new HashMap<>();
	private static TreeMap<String,Usuario> usuarios  = new TreeMap<>();
	private static HashMap<String, ArrayList<Multimedia>> favoritos = new HashMap<>();
	private static Logger logger = Logger.getLogger( Videoclub.class.getName() );
	
	
	
	
	
	public static HashMap<Integer, Multimedia> getObjetos() {
		return objetos;
	}

	public static void setObjetos(HashMap<Integer, Multimedia> objetos) {
		Videoclub.objetos = objetos;
	}

	public static TreeMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(TreeMap<String, Usuario> usuarios) {
		Videoclub.usuarios = usuarios;
	}

	public static HashMap<String, ArrayList<Multimedia>> getFavoritos() {
		return favoritos;
	}

	public static void setFavoritos(HashMap<String, ArrayList<Multimedia>> favoritos) {
		Videoclub.favoritos = favoritos;
	}
	
	public static boolean aniadirObjeto(Multimedia o) {
		if(objetos.containsKey(o.getCodigo())) {
			logger.log(Level.WARNING,"Objeto previamente a�adido");
			return false;
			
		}else {
			objetos.put(o.getCodigo(), o);
			logger.log(Level.INFO,"Objeto correctamente a�adido");
			return true;
		}
		
	}
	/**
	 * Elimina un objeto
	 * @param codigo
	 * @return
	 */
	public static Multimedia eliminarObjeto(int codigo) {
		logger.log(Level.INFO,"Objeto eliminado");
		return objetos.remove(codigo);
	}
	/**
	 * Imprimir un mapa
	 * @param mapa
	 */
	public static void printMap(HashMap mapa) {
	       Iterator it = mapa.entrySet().iterator();
	       while (it.hasNext()) {
	    	   HashMap.Entry pair = (HashMap.Entry)it.next();
	           System.out.println(pair.getKey() + " = " + pair.getValue());
	           it.remove(); // avoids a ConcurrentModificationException
	       }
	}
	
	/**
	 * A�adir un usuario
	 * @param u
	 * @return true or false
	 */
	public static boolean aniadirUsuario(Usuario u) {
		if(usuarios.containsKey(u.getNick())) {
			logger.log(Level.WARNING,"Usuario previamente a�adido");
			return false;
		
		}else {
			usuarios.put(u.getNick(), u);
			logger.log(Level.INFO,"Usuario a�adido correctamente");
			return true;
		}
	}
	/**
	 * Eliminar un usuario
	 * @param nick
	 * @return objetos.remove(nick)
	 */
	public static Multimedia eliminarUsuario(String nick) {
		logger.log(Level.INFO,"Usuario eliminado");
		return objetos.remove(nick);
		
	}
	/**
	 * Imprimir un Treemap
	 * @param mapa
	 */
	public static void printTreeMap(TreeMap mapa) {
	       Iterator it = mapa.entrySet().iterator();
	       while (it.hasNext()) {
	    	   HashMap.Entry pair = (HashMap.Entry)it.next();
	           System.out.println(pair.getKey() + " = " + pair.getValue());
	           it.remove(); // avoids a ConcurrentModificationException
	       }
	       logger.log(Level.INFO,"Mapa ordenado");
	}
	
	/**
	 * A�adir cualquier tde multimedia a favoritos
	 * @param nombre
	 * @param o
	 */
	public static void aniadirAFavoritos(String nombre,Multimedia o) {
		if(!favoritos.containsKey(nombre)) {
			favoritos.put(nombre, new ArrayList<>());
		}
		favoritos.get(nombre).add(o);
		logger.log(Level.INFO,"A�adido a favoritos");
	}
	/**
	 * Eliminar de favoritos el objeto a�adido
	 * @param nombre
	 * @param codigo
	 * @return
	 */
	public static Multimedia eliminarDeFavoritos(String nombre, int codigo) {
		boolean enc=false;
		int pos = 0;
		Multimedia eliminado;
		
		while(!enc && pos<favoritos.get(nombre).size()) {
			if(favoritos.get(nombre).get(pos).getCodigo()==codigo)
				enc = true;
			else
				pos++;
		}
		if(enc) {
			eliminado = favoritos.get(nombre).get(pos);
			favoritos.get(nombre).remove(pos);
			logger.log(Level.INFO,"Eliminado de favoritos");
			return eliminado;
			
		}
		return null;
		
	}
	/**
	 * Obtener las compras realizadas por un cliente
	 * @param nick
	 * @return
	 */
	public static ArrayList<Multimedia> obtenerComprasCliente(String nick){
		logger.log(Level.INFO,"Clientes obtenidos");
		
		return favoritos.get(nick);
		
	}
}

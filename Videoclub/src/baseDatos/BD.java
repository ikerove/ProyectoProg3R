package baseDatos;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import datos.Documental;
import datos.Multimedia;
import datos.Pelicula;
import datos.Serie;
import datos.Usuario;
import principal.Main;
import principal.Videoclub;
public class BD {
	
	private static Logger logger = Logger.getLogger( Main.class.getName() );

	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 * @throws BDException 
	 */
	public static Connection initBD( String nombreBD ) throws BDException {
		
		logger.log(Level.INFO, "Tratando de conectar al servidor");
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
		    logger.log(Level.INFO, "Conexion realizada");
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			logger.log(Level.SEVERE, "No se ha podido realizar la conexion ");
			throw new BDException("Error conectando a la BD", e);
			
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual. Devuelve un statement para trabajar con esa base de datos
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 * @throws BDException 
	 */
	public static Statement usarCrearTablasBD( Connection con ) throws BDException {
		
		//statement.executeUpdate : Cuando queramos hacer create, insert, delete, update, drop
		//statement.executeQuery : Cuando queramos hacer select
		
		logger.log(Level.INFO, "Creando tablas...");
		try {
			Statement statement = con.createStatement();
			try {
				statement.executeUpdate("create table if not exists Serie "+
						   "(codigo integer, "+
						   " titulo string, "+
						   " director string, "+
						   " genero string, "+
						   " duracion integer, "+
						   " distribuidora string,"+
						   " fecha string,"+
						   " calificacion string,"+
						   " formato string,"+
						   " temporadas integer,"+
						   " capitulos integer,"+
						   " duracionCap integer," + 
						   " rutaFoto string)");
			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Serie ya existente");
				throw new BDException("Error creando tablade serie a la BD", ex);
			} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Pelicula "+
						   "(codigo integer, "+
						   " titulo string, "+
						   " director string, "+
						   " genero string, "+
						   " duracion integer, "+
						   " distribuidora string,"+
						   " fecha string,"+
						   " calificacion integer,"+
						   " guion string,"+
						   " musica string,"+
						   " oscars boolean, " +
						   " rutaFoto string,"+
						   "tiempoReserva float)");
			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Pelicula ya existente");
				throw new BDException("Error creando tabla de pelicula a la BD", ex);

			} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Documental "+
						   "(codigo integer, "+
						   " titulo string, "+
						   " director string, "+
						   " genero string, "+
						   " duracion integer, "+
						   " distribuidora string,"+
						   " fecha string,"+
						   " calificacion integer,"+
						   " animales boolean,"+
						   " rutaFoto string)");

			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Documental ya existente");
				throw new BDException("Error creando tabla de documental a la BD", ex);

			} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Usuario "+
						   "(nick string, "+
						   " con string)");

			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Usuario ya existente");
				throw new BDException("Error creando tabla de usuario a la BD", ex);

			} //Si la tabla ya existe, no hacemos nada

			try {
				statement.executeUpdate("create table if not exists Admin "+
						   "(nick string, "+
						   " con string)");

			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Admin ya existente");
				throw new BDException("Error creando tabla de admin a la BD", ex);

			} //Si la tabla ya existe, no hacemos nada
			return statement;
		} catch (SQLException e) {
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE MËTODO CON PRECAUCIÓN. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 * @throws BDException 
	 */
	public static Statement reiniciarBD( Connection con ) throws BDException {
		logger.log(Level.INFO, "Reiniciando la base de datos...");

		try {
			Statement statement = con.createStatement();
			statement.executeUpdate("drop table if exists Serie");
			statement.executeUpdate("drop table if exists Pelicula");
			statement.executeUpdate("drop table if exists Documental");
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "No se ha podido reiniar la base de datos");
			throw new BDException("Error al reiniciar la BD", e);
			
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexión abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 * @throws BDException 
	 */
	public static void cerrarBD( Connection con, Statement st ) throws BDException {
		logger.log(Level.INFO, "Cerrando la base de datos");

		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "No se ha podido cerrar la BD");
			throw new BDException("Error al cerrar la BD", e);

		}
	}
	
	/**
	 * Comprobamos si el usuario está añadido en la base de datos
	 * @param nick
	 * @param contrasenia
	 * @return
	 * @throws BDException
	 */

	public static int existeUsuario(String nick, String contrasenia) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Usuario WHERE nick='"+nick+"'";
		logger.log(Level.INFO,"Seleccionando usuario: "+nick);
		Statement st;
		int resultado=0;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next()) {
				resultado = 0;
				logger.log(Level.WARNING,"Usuario no exixtente");

			}else {
				String c = rs.getString(2);
				if(c.equals(contrasenia)) {
					resultado = 2;
					logger.log(Level.FINE,"Usuario existente");
				
				}else {
					resultado = 1;
				logger.log(Level.WARNING,"Contrase�a incorrecta");
				}}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al mirar si existe usuarios en BD", e);
		}
		return resultado;
	}
	
	/**
	 * Añadimos el usuario a la base de datos
	 * @param nick
	 * @param contrasenia
	 * @throws BDException
	 */

	public static void insertarUsuario(String nick, String contrasenia) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Usuario VALUES('"+nick+"','"+contrasenia+"')";
		Statement st;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Usuario a�adido correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al insertar usuario en la BD", e);
		}
	}
	
	/**
	 * Comprobamos si el administrador está en la base de datos
	 * @param nick
	 * @param contrasenia
	 * @return
	 * @throws BDException
	 */

	public static int existeAdmin(String nick, String contrasenia) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Admin WHERE nick='"+nick+"'";
		logger.log(Level.INFO,"Seleccionando admin: "+nick);
		Statement st;
		int resultado=0;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next()) {
				resultado = 0;
				logger.log(Level.WARNING,"Admin no exixtente");
			}else {
				String c = rs.getString(2);
				if(c.equals(contrasenia)) {
					resultado = 2;
					logger.log(Level.FINE,"Admin existente");
				}else {
					resultado = 1;
					logger.log(Level.WARNING,"Contrase�a incorrecta");
				}}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al mirar si existe admin en la BD", e);
		}
		return resultado;
	}
	
	/**
	 * Añadimos el administrador a la base de datos
	 * @param nick
	 * @param contrasenia
	 * @throws BDException
	 */

	public static void insertarAdmin(String nick, String contrasenia) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Admin VALUES('"+nick+"','"+contrasenia+"')";
		Statement st;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Admin a�adido correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al insertar nuevo admin en la BD", e);
		}
	}
	
	/**
	 * Insertamos la serie en la base de datos
	 * @param codigo
	 * @param titulo
	 * @param director
	 * @param genero
	 * @param duracion
	 * @param distribuidora
	 * @param fecha
	 * @param calificacion
	 * @param formato
	 * @param temporadas
	 * @param capitulos
	 * @param duracionCap
	 * @param rutaFoto
	 * @throws BDException
	 */

	public static void insertarSerie(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			String fecha, String calificacion, String formato, int temporadas, int capitulos, int duracionCap, String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Serie VALUES('"+codigo+"','"+titulo+"','"+director+"','"+genero+"','"+duracion+"','"+distribuidora+"','"+fecha+"','"
		+calificacion+"','"+formato+"','"+temporadas+"','"+capitulos+"','"+duracionCap+"','"+rutaFoto+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Serie a�adida correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al insertar serie en la BD", e);
		}
		
		
		cerrarBD(con, st);
	}
	
	/**
	 * Insertamos la pelicula en la base de datos
	 * @param codigo
	 * @param titulo
	 * @param director
	 * @param genero
	 * @param duracion
	 * @param distribuidora
	 * @param fecha
	 * @param calificacion
	 * @param guion
	 * @param musica
	 * @param oscars
	 * @param rutaFoto
	 * @param tiempoReserva
	 * @throws BDException
	 */

	public static void insertarPelicula(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			String fecha, String calificacion, String guion, String musica, boolean oscars, String rutaFoto,float tiempoReserva) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Pelicula VALUES('"+codigo+"','"+titulo+"','"+director+"','"+genero+"','"+duracion+"','"+distribuidora+ "','"+fecha+ "','"+calificacion+"','"+guion+"','"+musica+"','"+oscars+"','"+rutaFoto+"','"+tiempoReserva+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Pel�cula a�adida correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.SEVERE, "No se ha podido a�adido la Pel�cula ");
			throw new BDException("Error al insertar pelicula en la BD", e);
		}
		
		cerrarBD(con, st);
	}
	
	/**
	 * Insertamos un documental en la base de datos
	 * @param codigo
	 * @param titulo
	 * @param director
	 * @param genero
	 * @param duracion
	 * @param distribuidora
	 * @param fecha
	 * @param calificacion
	 * @param animales
	 * @param rutaFoto
	 * @throws BDException
	 */

	public static void insertarDocumental(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			String fecha, String calificacion,boolean animales, String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Documental VALUES('"+codigo+"','"+titulo+"','"+director+"','"+genero+"','"+duracion+"','"+distribuidora+ "','"+fecha+ "','"+calificacion+"','"+animales+"','"+rutaFoto+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Documental a�adido correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al insertar documental en la BD", e);
		}
		
		cerrarBD(con, st);
	}
	
	/**
	 * Borramos las series
	 * @throws BDException
	 */

	
	public static void borrarSeries() throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "DELETE FROM Serie";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"serie borrada correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al borrar serie en la BD", e);
		}
		
		cerrarBD(con, st);	
	}
	
	/**
	 * Borramos las peliculas
	 * @throws BDException
	 */

	
	public static void borrarPeliculas() throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "DELETE FROM Pelicula";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Pelcula borrada correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al borrar pelicula en la BD", e);
		}
		
		cerrarBD(con, st);	
	}
	
	/**
	 * Borramos los documentales
	 * @throws BDException
	 */

	public static void borrarDocumentales() throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "DELETE FROM Documental";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Documental borrado correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al borrar documental en la BD", e);
		}
		
		cerrarBD(con, st);	
	}
	
	/**
	 * Actualizamos el historial de de compras
	 * @param nick
	 * @param codigo
	 * @param unidades
	 * @param fecha
	 * @throws BDException
	 */

	public static void actualizarHistorial(String nick,int codigo, int unidades, String fecha) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO historial VALUES ('"+nick+"',"+codigo+","+unidades+",'"+fecha+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
			logger.log(Level.INFO,"Historial actualizado correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(Level.SEVERE, "No se ha actualizado el historial ");
			throw new BDException("Error al actualizar historial en la BD", e);
		}
		
		cerrarBD(con, st);	
	}
	
	/**
	 * Obtenemos la serie elegida de la base de datos
	 * @param rutaFoto
	 * @return
	 * @throws BDException
	 */	
	public static Serie obtenerSerie(String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM serie WHERE rutaFoto='"+rutaFoto+"'";
		Statement st = null;	
		Serie s = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				String formato = rs.getString("formato");
				int temporadas = rs.getInt("temporadas");
				int capitulos = rs.getInt("capitulos");
				int duracionCap = rs.getInt("duracionCap");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				s = new Serie(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, formato, temporadas, capitulos, duracionCap, rutaFoto);
				logger.log(Level.INFO,"Serie obtenida correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener serie en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarBD(con, st);
		return s;
	}
	
	/**
	 * Obtenemos la serie elegida con otros parametros
	 * @param rutaFoto
	 * @return
	 * @throws BDException
	 */

	public static String obtenerSerie2(String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM serie WHERE rutaFoto='"+rutaFoto+"'";
		Statement st = null;	
		String s = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				String formato = rs.getString("formato");
				int temporadas = rs.getInt("temporadas");
				int capitulos = rs.getInt("capitulos");
				int duracionCap = rs.getInt("duracionCap");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				s = "Titulo: " + titulo+ "\n" + "Director: "+ director+ "\n" + "Género: "+ genero+ "\n" + "Duración: " + duracion+ " minutos\n" + "Distribuidora:  " + distribuidora+ "\n" + "Calificación: " + calificacion+" años";
				logger.log(Level.INFO,"Serie obtenida correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener serie en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarBD(con, st);
		return s;
	}
	
	/**
	 * Obtenemos la pelicula elegida de la base de datos
	 * @param rutaFoto
	 * @return
	 * @throws BDException
	 */

	public static Pelicula obtenerPelicula(String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM pelicula WHERE rutaFoto='"+rutaFoto+"'";
		Statement st = null;	
		Pelicula p = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				String guion = rs.getString("guion");
				String musica = rs.getString("musica");
				boolean oscars = rs.getBoolean("oscars");				
				float tiempoReserva=rs.getFloat("tiempoReserva");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				p = new Pelicula(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, guion, musica, oscars,  rutaFoto,tiempoReserva);
				logger.log(Level.INFO,"Pel�cula obtenida correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener pelicula en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarBD(con, st);
		return p;
	}
	
	/**
	 * Obtenemos la serie elegida con otros parametros
	 * @param rutaFoto
	 * @return
	 * @throws BDException
	 */

	public static String obtenerPelicula2(String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM pelicula WHERE rutaFoto='"+rutaFoto+"'";
		Statement st = null;	
		String p = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				String guion = rs.getString("guion");
				String musica = rs.getString("musica");
				boolean oscars = rs.getBoolean("oscars");				
				float tiempoReserva=rs.getFloat("tiempoReserva");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				p = "Titulo: " + titulo+ "\n" + "Director: "+ director+ "\n" + "Género: "+ genero+ "\n" + "Duración: " + duracion+ " minutos\n" + "Distribuidora: " + distribuidora+ "\n" + "Calificación" + calificacion  +   " años\n";
				logger.log(Level.INFO,"Pel�cula obtenida correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener pelicula en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarBD(con, st);
		return p;
	}
	
	/**
	 * Obtenemos el documental elegido con otros parametros
	 * @param rutaFoto
	 * @return
	 * @throws BDException
	 */

	public static String obtenerDocumental2(String rutaFoto) throws BDException {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM documental WHERE rutaFoto='"+rutaFoto+"'";
		Statement st = null;	
		String doc = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				boolean animales = rs.getBoolean("animales");				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				doc = "Titulo: " + titulo+ "\n" + "Director: "+ director+ "\n" + "Género:  "+ genero+ "\n" + "Duración: " + duracion+ " minutos\n" + "Distribuidora: " + distribuidora+ "\n" + "Calificación: " + calificacion  +   " años\n";
				logger.log(Level.INFO,"PDocumental obtenido correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener documental en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarBD(con, st);
		return doc;
	}
	
	/**
	 * Obtenemos el usuario solicitado
	 * @return
	 * @throws BDException
	 */

	public static Usuario obtenerUsuario() throws BDException{
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Usuario";
		Statement st = null;	
		Usuario u = null;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				String usuario = rs.getString("nick");
				String contrasenia = rs.getString("con");
				
				u = new Usuario(usuario, contrasenia);
				logger.log(Level.INFO,"Usuario obtenido correctamente");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener usuario en la BD", e);
		} 
		cerrarBD(con, st);
		return u;
	}
	
	/**
	 * Devuelve un arraylist con todas las series que estan en la tabla de serie
	 * @return
	 * @throws BDException
	 */
	public static ArrayList<Serie> obtenerSeries() throws BDException{
		ArrayList<Serie> series = new ArrayList<Serie>();
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Serie";
		Statement st = null;	
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				String formato = rs.getString("formato");
				int temporadas = rs.getInt("temporadas");
				int capitulos = rs.getInt("capitulos");
				int duracionCap = rs.getInt("duracionCap");
				String rutaFoto = rs.getString("rutaFoto");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				Serie s = new Serie(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, formato, temporadas, capitulos, duracionCap, rutaFoto);
				series.add(s);
				
				logger.log(Level.INFO,"Serie obtenida correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
			throw new BDException("Error al obtener la lista de series en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
		return series;
	}
	
	/**
	 * Devuelve todas las peliculas que están en la tabla de pelicula
	 * @return
	 * @throws BDException
	 */
	
	public static ArrayList<Pelicula> obtenerPeliculas() throws BDException{
		ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Pelicula";
		Statement st = null;	
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				String guion = rs.getString("guion");
				String musica = rs.getString("musica");
				boolean oscars = rs.getBoolean("oscars");
				String rutaFoto = rs.getString("rutaFoto");
				float tiempoReserva=rs.getFloat("tiempoReserva");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				Pelicula p = new Pelicula(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, guion, musica, oscars,  rutaFoto,tiempoReserva);
				peliculas.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener la lista de peliculas en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
		return peliculas;
	}
	
	/**
	 * Devuelve todos los documentales que hay en la tabla de documental
	 * @return
	 * @throws BDException
	 */
	public static ArrayList<Documental> obtenerDocumentales() throws BDException{
		ArrayList<Documental> documentales = new ArrayList<Documental>();
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Documental";
		Statement st = null;	
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt("codigo");
				String titulo = rs.getString("titulo");
				String director = rs.getString("director");
				String genero = rs.getString("genero");
				int duracion = rs.getInt("duracion");
				String distribuidora = rs.getString("distribuidora");
				String fecha = rs.getString("fecha");
				String calificacion = rs.getString("calificacion");
				boolean animales = rs.getBoolean("animales");
				String rutaFoto = rs.getString("rutaFoto");
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				Documental doc = new Documental(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, animales,  rutaFoto);
				
				documentales.add(doc);
				logger.log(Level.INFO,"Documental obtenido correctamente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			throw new BDException("Error al obtener la lista de documentales en la BD", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
		return documentales;
	}
	
	
	
	
	
	/**
	 * Devuelve todos los objetos multimedia que existen en las tablas serie, pelicula y documental
	 * @return
	 * @throws BDException
	 */
	
	public static ArrayList<Multimedia> obtenerObjetos() throws BDException{
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Serie";
		Statement st = null;
		ArrayList<Multimedia> objetos = new ArrayList<>();
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt(1);
				String titulo = rs.getString(2);
				String director = rs.getString(3);
				String genero = rs.getString(4);
				int duracion = rs.getInt(5);
				String  distribuidora = rs.getString(6);
				String fecha = rs.getString(7);
				String calificacion = rs.getString(8);
				String formato = rs.getString(9);
				int temporadas = rs.getInt(10);
				int capitulos = rs.getInt(11);
				int duracionCap = rs.getInt(12);
				String rutaFoto = rs.getString(13);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d;
				try {
					d = sdf.parse(fecha);
					objetos.add(new Serie(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, formato, temporadas, capitulos, duracionCap, rutaFoto));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();					
				}
			    
			    
			}
			rs.close();
			sql = "SELECT * FROM Pelicula";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt(1);
				String titulo = rs.getString(2);
				String director = rs.getString(3);
				String genero = rs.getString(4);
				int duracion = rs.getInt(5);
				String  distribuidora = rs.getString(6);
				String fecha = rs.getString(7);
				String calificacion = rs.getString(8);
				String guion = rs.getString(9);
				String musica = rs.getString(10);
				boolean oscars = rs.getBoolean(11);
				String rutaFoto = rs.getString(12);
				float tiempoReserva=rs.getFloat(13);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d ;
				try {
					d = sdf.parse(fecha);
					objetos.add(new Pelicula(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, guion, musica, oscars, rutaFoto,tiempoReserva));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //Convierte de String a Date
				
				
			}
			rs.close();
			sql = "SELECT * FROM Documental";
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo = rs.getInt(1);
				String titulo = rs.getString(2);
				String director = rs.getString(3);
				String genero = rs.getString(4);
				int duracion = rs.getInt(5);
				String  distribuidora = rs.getString(6);
				String fecha = rs.getString(7);
				String calificacion = rs.getString(8);
				boolean animales = rs.getBoolean(9);
				String rutaFoto = rs.getString(10);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d ;
				try {
					d = sdf.parse(fecha);
					objetos.add(new Documental(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, animales, rutaFoto));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //Convierte de String a Date
				
				
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BDException("Error al obtener la lista de objetos en la BD", e);
		}
		cerrarBD(con, st);
		return objetos;
	}
}

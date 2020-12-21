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
	 */
	public static Connection initBD( String nombreBD ) {
		
		logger.log(Level.INFO, "Tratando de conectar al servidor");
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
		    logger.log(Level.INFO, "Conexion realizada");
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			logger.log(Level.SEVERE, "No se ha podido realizar la conexion ");
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual. Devuelve un statement para trabajar con esa base de datos
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		
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
						   " rutaFoto string)");
			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Pelicula ya existente");

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

			} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Usuario "+
						   "(nick string, "+
						   " con string)");

			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Usuario ya existente");

			} //Si la tabla ya existe, no hacemos nada

			try {
				statement.executeUpdate("create table if not exists Admin "+
						   "(nick string, "+
						   " con string)");

			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Admin ya existente");

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
	 */
	public static Statement reiniciarBD( Connection con ) {
		logger.log(Level.INFO, "Reiniciando la base de datos...");

		try {
			Statement statement = con.createStatement();
			statement.executeUpdate("drop table if exists Serie");
			statement.executeUpdate("drop table if exists Pelicula");
			statement.executeUpdate("drop table if exists Documental");
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "No se ha podido reiniar la base de datos");

			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexión abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public static void cerrarBD( Connection con, Statement st ) {
		logger.log(Level.INFO, "Cerrando la base de datos");

		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "No se ha podido cerrar la BD");

		}
	}
	
	
	public static int existeUsuario(String nick, String contrasenia) {
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
				
				}else
					resultado = 1;
				logger.log(Level.WARNING,"Contrase�a incorrecta");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static void insertarUsuario(String nick, String contrasenia) {
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
		}
	}
	
	public static int existeAdmin(String nick, String contrasenia) {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "SELECT * FROM Admin WHERE nick='"+nick+"'";
		Statement st;
		int resultado=0;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next())
				resultado = 0;
			else {
				String c = rs.getString(2);
				if(c.equals(contrasenia))
					resultado = 2;
				else
					resultado = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public static void insertarAdmin(String nick, String contrasenia) {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Admin VALUES('"+nick+"','"+contrasenia+"')";
		Statement st;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertarSerie(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			String fecha, String calificacion, String formato, int temporadas, int capitulos, int duracionCap, String rutaFoto) {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Serie VALUES('"+codigo+"','"+titulo+"','"+director+"','"+genero+"','"+duracion+"','"+distribuidora+"','"+fecha+"','"
		+calificacion+"','"+formato+"','"+temporadas+"','"+capitulos+"','"+duracionCap+"','"+rutaFoto+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		cerrarBD(con, st);
	}
	
	public static void insertarPelicula(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			String fecha, String calificacion, String guion, String musica, boolean oscars, String rutaFoto) {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Pelicula VALUES('"+codigo+"','"+titulo+"','"+director+"','"+genero+"','"+duracion+"','"+distribuidora+ "','"+fecha+ "','"+calificacion+"','"+guion+"','"+musica+"','"+oscars+"','"+rutaFoto+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);
	}
	
	public static void insertarDocumental(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			String fecha, String calificacion,boolean animales, String rutaFoto) {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO Documental VALUES('"+codigo+"','"+titulo+"','"+director+"','"+genero+"','"+duracion+"','"+distribuidora+ "','"+fecha+ "','"+calificacion+"','"+animales+"','"+rutaFoto+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);
	}
	
	
	
	public static void borrarSeries() {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "DELETE FROM Serie";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
	}
	
	public static void borrarPeliculas() {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "DELETE FROM Pelicula";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
	}
	
	public static void borrarDocumentales() {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "DELETE FROM Documental";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
	}
	
	public static void actualizarHistorial(String nick,int codigo, int unidades, String fecha) {
		Connection con = initBD("videoclub.sqlite3");
		String sql = "INSERT INTO historial VALUES ('"+nick+"',"+codigo+","+unidades+",'"+fecha+"')";
		Statement st = null;	
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
	}
	
	
	public static Serie obtenerSerie(String rutaFoto) {
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarBD(con, st);
		return s;
	}

	public static Usuario obtenerUsuario(){
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		cerrarBD(con, st);
		return u;
	}
	
	public static ArrayList<Serie> obtenerSeries(){
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
		return series;
	}
	
	
	
	public static ArrayList<Pelicula> obtenerPeliculas(){
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
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d = sdf.parse(fecha);
				Pelicula p = new Pelicula(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, guion, musica, oscars,  rutaFoto);
				peliculas.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
		return peliculas;
	}
	
	public static ArrayList<Documental> obtenerDocumentales(){
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cerrarBD(con, st);	
		return documentales;
	}
	
	
	
	
	
	
	
	public static ArrayList<Multimedia> obtenerObjetos(){
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
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date d ;
				try {
					d = sdf.parse(fecha);
					objetos.add(new Pelicula(codigo, titulo, director, genero, duracion, distribuidora, d, calificacion, guion, musica, oscars, rutaFoto));
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
		}
		cerrarBD(con, st);
		return objetos;
	}
}

package baseDatos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import principal.Main;
import principal.Videoclub;
public class BD {
	
	private static Logger logger = Logger.getLogger( Main.class.getName() );

	/** Inicializa una BD SQLITE y devuelve una conexi贸n con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexi贸n con la base de datos indicada. Si hay alg煤n error, se devuelve null
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
	 * @param con	Conexi贸n ya creada y abierta a la base de datos
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
									   " formato string,"+
									   " temporadas integer,"+
									   " capitulos integer,"+
									   " duracionCap integer)");
			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Serie ya existente");
			} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Pelicula "+
						   "(codigo integer, "+
						   " titulo string, "+						  
						   " guion string,"+
						   " musica string,"+
						   " oscars boolean, ");

			}catch(SQLException ex) {
				logger.log(Level.WARNING, "Tabla Pelicula ya existente");

			} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Documental "+
						 "(codigo integer, "+
						   " titulo string, "+						  
						   " animales boolean,");

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
	 * UTILIZAR ESTE M脣TODO CON PRECAUCI脫N. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexi贸n ya creada y abierta a la base de datos
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
	 * @param con	Conexi贸n abierta de la BD
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
				String c = rs.getString(1);
				if(c.equals(contrasenia)) {
					resultado = 2;
					logger.log(Level.FINE,"Usuario existente");
				
				}else
					resultado = 1;
				logger.log(Level.WARNING,"Contrase馻 incorrecta");
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
			logger.log(Level.INFO,"Usuario a馻dido correctamente");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

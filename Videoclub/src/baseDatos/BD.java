package baseDatos;

import java.sql.*;
public class BD {
	
	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
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
			}catch(SQLException ex) {} //Si la tabla ya existe, no hacemos nada
			
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

			}catch(SQLException ex) {} //Si la tabla ya existe, no hacemos nada
			
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

			}catch(SQLException ex) {} //Si la tabla ya existe, no hacemos nada
			
			try {
				statement.executeUpdate("create table if not exists Usuario "+
						   "(nick string, "+
						   " con string)");

			}catch(SQLException ex) {} //Si la tabla ya existe, no hacemos nada

			try {
				statement.executeUpdate("create table if not exists Admin "+
						   "(nick string, "+
						   " con string)");

			}catch(SQLException ex) {} //Si la tabla ya existe, no hacemos nada
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
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate("drop table if exists Serie");
			statement.executeUpdate("drop table if exists Pelicula");
			statement.executeUpdate("drop table if exists Documental");
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexión abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public static void cerrarBD( Connection con, Statement st ) {
		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
		} catch (SQLException e) {
		}
	}
}

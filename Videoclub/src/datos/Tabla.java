package datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;



public class Tabla {
	
		// Auxiliares estáticas
		
		public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
		public static SimpleDateFormat sdfDMY = new SimpleDateFormat( "dd/MM/yyyy" );

		// Atributos
		
		protected ArrayList<String> cabeceras; // Nombres de las cabeceras-columnas
		protected ArrayList<Class<?>> tipos; // Tipos de cada una de las columnas (inferidos de los datos strings)
		protected ArrayList<ArrayList<Object>> dataO; // Datos de la tabla (en el orden de las columnnas), implementados todos como objects (en lugar de strings)
		
		
		// Métodos de instancia

		
		public Tabla() {
			cabeceras = new ArrayList<>();
			tipos = new ArrayList<>();
			dataO = new ArrayList<>();
		}
		
		public Tabla( ArrayList<String> cabeceras, ArrayList<Class<?>> tipos ) {
			this.cabeceras = cabeceras;
			this.tipos = tipos;
			dataO = new ArrayList<>();
		}
		
		
		public void clear() {
			if (dataO!=null) dataO.clear();
		}
		
		
		public int getColumnaConCabecera( String cabecera, boolean exact ) {
			String headerUp = cabecera.toUpperCase();
			int ret = -1;
			for (int col=0; col<cabeceras.size();col++) {
				if (exact && cabeceras.get(col).equals( cabecera ) || !exact && cabeceras.get(col).toUpperCase().contains( headerUp )) {
					ret = col;
					break;
				}
			}
			return ret;
		}
		
		
		public ArrayList<String> getCabeceras() {
			return cabeceras;
		}
		
		
		public String getCabecera( int col ) {
			return cabeceras.get(col);
		}
		
		
		public String getCabecerasTabs() {
			String ret = "";
			for (int c=0; c<getWidth(); c++) {
				ret += (getCabecera(c) + "\t");
			}
			return ret;
		}
		
		
		public ArrayList<Class<?>> getTipos() {
			return tipos;
		}
		
		
		public Class<?> getType( int col ) {
			return tipos.get(col);
		}
		
		public void setCabecerasYTipos( ArrayList<String> cabeceras, ArrayList<Class<?>> tipos ) {
			this.cabeceras = cabeceras;
			this.tipos = tipos;
			dataO.clear();
		}
		
		
		public int size() {
			return dataO.size();
		}
		
		
		public int getWidth() {
			return cabeceras.size();
		}
		
		
		public int searchFila( Object valor, int numCol ) {
			for (int fila=0; fila<size(); fila++) {
				if (valor!=null && valor.equals( get( fila, numCol ) )) {
					return fila;
				}
			}
			return -1;
		}
		
		
		public void addColumna( String cabecera, Class<?> tipo, Object valPorDefecto ) {
			cabeceras.add( cabecera );
			tipos.add( tipo );
			for (ArrayList<Object> line : dataO) {
				line.add( valPorDefecto );
			}
		}
		
		
		public void addColumna( String cabecera, Object valPorDefecto ) {
			cabeceras.add( cabecera );
			tipos.add( valPorDefecto.getClass() );
			for (ArrayList<Object> line : dataO) {
				line.add( valPorDefecto );
			}
		}
		
		public void addDataLine( ArrayList<Object> linea ) {
			dataO.add( linea );
			cambioEnTabla( dataO.size()-1, TableModelEvent.INSERT );  // Evento de cambio para posible JTable asociada
		}
		
		public void addDataLine() {
			ArrayList<Object> line = new ArrayList<>();
			for (int i=0; i<cabeceras.size(); i++) line.add( null );
			dataO.add( line );
		}
		public void addLineas( Tabla tabla2 ) {
			if (tabla2.dataO==null || dataO==null) return;
			for (int fila = 0; fila<tabla2.dataO.size(); fila++) {
				addDataLine();
				for (int col=0; col<tabla2.cabeceras.size(); col++) {
					String nomCol = tabla2.cabeceras.get(col);
					if (cabeceras.contains(nomCol)) {
						set( nomCol, tabla2.get( fila, col ) );
					}
				}
			}
		}
		
		
		public Object get( int row, int col ) {
			return dataO.get( row ).get( col );
		}
		
		public Object get( int row, String nomCol ) throws IndexOutOfBoundsException {
			int col = cabeceras.indexOf( nomCol );
			if (col==-1) throw new IndexOutOfBoundsException( "Columna no encontrada: " + nomCol );
			return get( row, col );
		}
		
		
		public int getInt( int row, int col ) {
			try {
				Object o = dataO.get( row ).get( col );
				if (o instanceof Integer) return ((Integer)o).intValue();
				return Integer.parseInt( o.toString() );
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		
		
		public double getDouble( int row, int col ) {
			try {
				Object o = dataO.get( row ).get( col );
				if (o instanceof Double) return ((Double)o).doubleValue();
				return Double.parseDouble( o.toString() );
			} catch (Exception e) {
				return Double.NaN;
			}
		}
		
		
		public Date getDate( int row, int col ) {
			try {
				Object o = dataO.get( row ).get( col );
				try {
					if (o instanceof Date) return ((Date)o);
					return sdf.parse( o.toString() );
				} catch (Exception e) {}
				return sdfDMY.parse( o.toString() );
			} catch (Exception e) {
				return null;
			}
		}
		
		public void set( int row, int col, Object valor ) throws ClassCastException {
			if (tipos.get(col)!=null && valor!=null && !tipos.get(col).isAssignableFrom( valor.getClass() )) {  // Control de tipo incorrecto
				throw new ClassCastException( "Error en set: intentando asignar [" + valor + "] un " + valor.getClass().getSimpleName() + " en un " + tipos.get(col).getSimpleName() + " en columna " + col );
			}
			dataO.get( row ).set( col, valor );
			cambioEnTabla( row, col, row, col );  // Evento de cambio para posible JTable asociada
		}
		
		
		public void set( int row, String nomCol, Object valor ) {
			int col = cabeceras.indexOf( nomCol );
			if (col==-1) throw new IndexOutOfBoundsException( "Columna no encontrada: " + nomCol );
			set( row, col, valor );
		}
		
		
		public void set( String nomCol, Object value ) {
			set( dataO.size()-1, nomCol, value );
		}
		
		
		public void set( int col, Object value ) {
			set( dataO.size()-1, col, value );
		}
		
		public ArrayList<Object> getFila( int row ) {
			return dataO.get(row);
		}
		
		public String getFilaTabs( int row ) {
			String ret = "";
			for (int c=0; c<getWidth(); c++) {
				Object o = get(row,c);
				if (o==null) ret += "\t";
				else if (o instanceof Date) ret += (sdf.format( (Date) o ) + "\t");
				else ret += (o.toString().replaceAll("\n", "") + "\t");
			}
			return ret;
		}
		
		public int recalcTipos() {
			// 1.- calcula errores
			int ok = 0;
			int lin = 1;
			for (ArrayList<Object> line : dataO) if (line.size()!=cabeceras.size()) {
				ok++;
				System.err.println( "Error en línea " + lin + ": " + line.size() + " valores en vez de " + cabeceras.size() );
				lin++;
			}
			if (ok>0) return ok;
			// 2.- calcula tipos de datos y los redefine en la tabla si procede
			Class<?> tipo = null;
			for (int col=0; col<cabeceras.size(); col++) {
				tipo = tipos.get(col);
				if (tipo==String.class && dataO.size()>0) {
					tipo = null;
					for (int row=0; row<dataO.size(); row++) {
						Class<?> nextType = calcDataType( (String) dataO.get(row).get(col) );
						if (nextType==String.class) {
							tipo = String.class;
							break; // El tipo es String, no se puede recalcular a otro diferente
						} else if (nextType!=null) {  // Si el tipo es nulo no ayuda, tenemos que seguir mirando. Si no es nulo comprobamos:
							if (tipo==null || tipo==nextType) {  // Tipo homogéneo, lo guardamos
								tipo = nextType;
							} else if (tipo!=nextType) {  // Tipo heterogéneo, vamos a ver si encaja
								if (tipo==Integer.class && nextType==Double.class) {  // El int sí puede ser double
									tipo = Double.class;
								} else if (tipo==Double.class && nextType==Integer.class) { // El int sí puede ser double
									// Nada (tipo = Double)
								} else { // Tipo diferente: debe considerarse String
									tipo = String.class;
									break;
								}
							}
						}
					}
					if (tipo!=null && tipo!=String.class) {  // Hay una columna entera que es de otro tipo convirtiendo de String
						tipos.set( col, tipo );
						for (int row=0; row<dataO.size(); row++) {
							String val = (String) dataO.get(row).get(col);
							Object nuevoVal = null;
							if (val==null || val.isEmpty()) { 
								nuevoVal = null;
							} else if (tipo==Integer.class) {
								try {
									nuevoVal = new Integer( Integer.parseInt( val ) );
								} catch (NumberFormatException e) { } // No debería ocurrir - en este caso se anula el valor (no es convertible)
							} else if (tipo==Double.class) {
								try {
									nuevoVal = new Double( Double.parseDouble( val ) );
								} catch (NumberFormatException e) { } // No debería ocurrir - en este caso se anula el valor (no es convertible)
							} else if (tipo==Date.class) {
								try {
									nuevoVal = sdf.parse( val );
								} catch (ParseException e) {
									try {
										nuevoVal = sdfDMY.parse( val );
									} catch (ParseException e2) { } // No debería ocurrir - en este caso se anula el valor (no es convertible)
								}
							}
							dataO.get(row).set( col, nuevoVal ); // Modifica el valor por el nuevo tipo
						}
					}
				}
			}
			return ok;
		}
			// Devuelve suposición de tipo de valor String (si value es un string), null si es nulo o no se puede suponer (indefinido)
			// Comprueba solo tipos Integer, Double o Date
			private Class<?> calcDataType( String valor ) {
				if (valor==null) return null;
				if (valor.isEmpty()) return null;
				Class<?> ret = String.class;
				try {
					Integer.parseInt( valor );
					ret = Integer.class;
				} catch (NumberFormatException e) {
					try {
						Double.parseDouble( valor.replaceAll(",", ".") );
						ret = Double.class;
					} catch (NumberFormatException e2) {
						try {
							sdf.parse( valor );
							ret = Date.class;
						} catch (ParseException e3) {
							try {
								sdfDMY.parse( valor );
								ret = Date.class;
							} catch (ParseException e4) {
							}
						}
					}
				}
				return ret;
			}

			
		public void cambioEnTabla( int fila1, int col1, int fila2, int col2 ) {
			if (miModelo==null || miModelo.lListeners==null) return;
			for (int col=col1; col<=col2; col++) {
				for (TableModelListener l : miModelo.lListeners) {
					l.tableChanged( new TableModelEvent( miModelo, fila1, fila2, col, TableModelEvent.UPDATE ) );
				}
			}
		}
			
		
		public void cambioEnTabla( int fila1, int tipoC ) {
			if (miModelo==null || miModelo.lListeners==null) return;
			for (TableModelListener l : miModelo.lListeners) {
				l.tableChanged( new TableModelEvent( miModelo, fila1, fila1, TableModelEvent.ALL_COLUMNS, tipoC ) );
			}
		}
			
			
		// toString
		@Override
		public String toString() {
			String ret = "";
			boolean ini = true;
			for (String header : cabeceras) {
				if (!ini) ret += "\t";
				ret += header;
				ini = false;
			}
			ret += "\n";
			for (ArrayList<Object> lin : dataO) {
				ini = true;
				for (Object val : lin) {
					if (!ini) ret += "\t";
					ret += val;
					ini = false;
				}
				ret += "\n";
			}
			return ret;
		}
		
		public void generarCSV( File file, boolean comasEnVezDePuntos ) 
		throws IOException // Error de E/S
		{
			PrintStream ps = new PrintStream( file, "UTF-8" );
			for (int i=0; i<cabeceras.size()-1; i++) {
				ps.print( cabeceras.get(i) + ";" );
			}
			ps.println( cabeceras.get(cabeceras.size()-1) );
			for (int lin=0; lin<size(); lin++) {
				for (int col=0; col<getWidth(); col++) {
					Object o = get( lin, col );
					if (o==null) o = " ";
					else if (o instanceof Double && comasEnVezDePuntos) o = o.toString().replaceAll( "\\.", "," );
					ps.print( o + (col==getWidth()-1 ? "" : ";") );
				}
				ps.println();
			}
			ps.close();
		}
		
		// =================================================
		// Métodos estáticos
		
			protected static boolean LOG_CONSOLE_CSV = false;  // Log en consola de la lectura del csv
			
		// Método de carga de tabla desde CSV
		
		public static Tabla processCSV( File file ) 
		throws IOException // Error de I/O
		{
			Tabla tabla = processCSV( file.toURI().toURL() );
			return tabla;
		}
		
		
		public static Tabla processCSV( URL url, int... lineaCabs ) 
		throws MalformedURLException,  // URL incorrecta 
		 IOException, // Error al abrir conexión
		 UnknownHostException, // servidor web no existente
		 FileNotFoundException, // En algunos servidores, acceso a página inexistente
		 ConnectException // Error de timeout
		{
			int linCab = 1;
			if (lineaCabs.length>0) linCab = lineaCabs[0];
			Tabla tabla = new Tabla();
			BufferedReader input = null;
			InputStream inStream = null;
			try {
			    URLConnection connection = url.openConnection();
			    connection.setDoInput(true);
			    inStream = connection.getInputStream();
			    input = new BufferedReader(new InputStreamReader( inStream, "UTF-8" ));  // Supone utf-8 en la codificación de texto
			    String line = "";
			    int numLine = 0;
			    while ((line = input.readLine()) != null) {
			    	numLine++;
			    	if (LOG_CONSOLE_CSV) System.out.println( numLine + "\t" + line );
			    	try {
				    	ArrayList<Object> l = processCSVLine( input, line, numLine );
				    	if (LOG_CONSOLE_CSV) System.out.println( "\t" + l.size() + "\t" + l );
				    	if (numLine==linCab) {
					    	ArrayList<Class<?>> tipos = new ArrayList<>();
				    		ArrayList<String> cabs = new ArrayList<>();
					    	for (Object cab : l) {
					    		cabs.add( (String) cab );
					    		tipos.add( String.class ); // Por defecto se ponen los tipos a Strings
					    	}
				    		tabla.setCabecerasYTipos( cabs, tipos );
				    	} else {
				    		if (!l.isEmpty())
				    			tabla.addDataLine( l );
				    	}
			    	} catch (StringIndexOutOfBoundsException e) {
			    		/* if (LOG_CONSOLE_CSV) */ System.err.println( "\tError: " + e.getMessage() );
			    	}
			    }
			} finally {
				try {
					inStream.close();
					input.close();
				} catch (Exception e2) {
				}
			}
			// Recálculo de tipos
			int errs = tabla.recalcTipos();
			if (errs>0) {  // Quitar filas erróneas si las hay
				for (int fila=tabla.size()-1; fila>=0; fila--) {  // Iteramos de arriba abajo porque vamos a ir quitando filas
					if (tabla.getFila(fila).size()!=tabla.cabeceras.size()) {
						tabla.dataO.remove( fila );
					}
				}
				tabla.recalcTipos();  // Recalcular otra vez
			}
		    return tabla;
		}
		
			
			private static ArrayList<Object> processCSVLine( BufferedReader input, String line, int numLine ) throws StringIndexOutOfBoundsException {
				ArrayList<Object> ret = new ArrayList<>();
				int posCar = 0;
				boolean inString = false;
				boolean finString = false;
				String stringActual = "";
				char separador = 0;
				while (line!=null && (posCar<line.length() || line.isEmpty() && posCar==0)) {
					if (line.isEmpty() && posCar==0) {
						if (!inString) return ret;  // Línea vacía
					} else {
						char car = line.charAt( posCar );
						if (car=='"') {
							if (inString) {
								if (nextCar(line,posCar)=='"') {  // Doble "" es un "
									posCar++;
									stringActual += "\"";
								} else {  // " de cierre
									inString = false;
									finString = true;
								}
							} else {  // !inString
								if (stringActual.isEmpty()) {  // " de apertura
									inString = true;
								} else {  // " después de valor - error
									throw new StringIndexOutOfBoundsException( "\" after data in char " + posCar + " of line [" + line + "]" );
								}
							}
						} else if (car==',' || car==';') {
							if (inString) {  // separador dentro de string
								stringActual += car;
							} else {  // separador que separa valores
								if (separador==0) { // Si no se había encontrado separador hasta ahora
									separador = car;
									ret.add( stringActual );
									stringActual = "";
									finString = false;
								} else { // Si se había encontrado, solo vale el mismo (, o ;)
									if (separador==car) {  // Es un separador
										ret.add( stringActual );
										stringActual = "";
										finString = false;
									} else {  // Es un carácter normal
										if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor después de string - error
										stringActual += car;
									}
								}
							}
						} else {  // Carácter dentro de valor
							if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor después de string - error
							stringActual += car;
						}
						posCar++;
					}
					if (posCar>=line.length() && inString) {  // Se ha acabado la línea sin acabarse el string. Eso es porque algún string incluye salto de línea. Se sigue con la siguiente línea
						line = null;
					    try {
							line = input.readLine();
					    	if (LOG_CONSOLE_CSV) System.out.println( "  " + numLine + " (add)\t" + line );
							posCar = 0;
							stringActual += "\n";
						} catch (IOException e) {}  // Si la línea es null es que el fichero se ha acabado ya o hay un error de I/O
					}
				}
				if (inString) throw new StringIndexOutOfBoundsException( "String not closed in line " + numLine + ": [" + line + "]");
				ret.add( stringActual );
				return ret;
			}

				// Devuelve el siguiente carácter (car 0 si no existe el siguiente carácter)
				private static char nextCar( String line, int posCar ) {
					if (posCar+1<line.length()) return line.charAt( posCar + 1 );
					else return Character.MIN_VALUE;
				}

		
		// =================================================
		// Métodos relacionados con el modelo de tabla (cuando se quiere utilizar esta tabla en una JTable)
		
		protected TablaTableModel miModelo = null;
		/** Devuelve un modelo de tabla de este objeto tabla para poderse utilizar como modelo de datos en una JTable
		 * @return	modelo de datos de la tabla
		 */
		public TablaTableModel getTableModel() {
			if (miModelo==null) {
				miModelo = new TablaTableModel();
			}
			return miModelo;
		}
		
		public class TablaTableModel implements TableModel {
			@Override
			public int getRowCount() {
				return size();
			}
			@Override
			public int getColumnCount() {
				return cabeceras.size();
			}
			@Override
			public String getColumnName(int columnIndex) {
				return cabeceras.get(columnIndex);
			}
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (tipos!=null && tipos.size()>=columnIndex+1) return tipos.get(columnIndex);
				else return String.class;
			}
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) { // Estas tablas de datos no son editables por defecto
				return false;
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return get(rowIndex,columnIndex);
			}
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				for (TableModelListener l : lListeners) {
					l.tableChanged( new TableModelEvent( this, rowIndex, rowIndex, columnIndex, TableModelEvent.UPDATE ) );
				}
			}
			
			private ArrayList<TableModelListener> lListeners = new ArrayList<>();
			@Override
			public void addTableModelListener(TableModelListener l) {
				lListeners.add( l );
			}
			@Override
			public void removeTableModelListener(TableModelListener l) {
				lListeners.remove( l );
			}
			
		}
}

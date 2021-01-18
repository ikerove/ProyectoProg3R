package datos;


	import java.io.*;
	import java.net.*;
	import java.text.*;
	import java.util.*;
	import javax.swing.event.TableModelEvent;
	import javax.swing.event.TableModelListener;
	import javax.swing.table.TableModel;


	public class Tabla {
		
		// =================================================
		// Auxiliares est�ticas
		
		public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
		public static SimpleDateFormat sdfDMY = new SimpleDateFormat( "dd/MM/yyyy" );

		// =================================================
		// Atributos
		
		protected ArrayList<String> cabeceras; // Nombres de las cabeceras-columnas
		protected ArrayList<Class<?>> tipos; // Tipos de cada una de las columnas (inferidos de los datos strings)
		protected ArrayList<ArrayList<Object>> dataO; // Datos de la tabla (en el orden de las columnnas), implementados todos como objects (en lugar de strings)
		
		// =================================================
		// M�todos de instancia

		/** Crea una tabla de datos vac�a (sin cabeceras ni datos)
		 */
		public Tabla() {
			cabeceras = new ArrayList<>();
			tipos = new ArrayList<>();
			dataO = new ArrayList<>();
		}
		
		/** Crea una tabla de datos vac�a con cabeceras y tipos
		 * @param cabeceras	Nombres de las cabeceras de datos
		 */
		public Tabla( ArrayList<String> cabeceras, ArrayList<Class<?>> tipos ) {
			this.cabeceras = cabeceras;
			this.tipos = tipos;
			dataO = new ArrayList<>();
		}
		
		/** Borra la tabla de datos (mantiene las cabeceras y los tipos)
		 */
		public void clear() {
			if (dataO!=null) dataO.clear();
		}
		
		/** Chequea si una cabecera particular existe, y devuelve su n�mero de columna
		 * @param cabecera	Nombre de cabecera a encontrar
		 * @param exact	true si el match debe ser exacto, false si vale con que sea parcial
		 * @return	n�mero de la columna de primera cabecera que encaja con el nombre pedido, -1 si no existe ninguna
		 */
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
		
		/** Devuelve las cabeceras actuales
		 * @return	Lista de cabeceras
		 */
		public ArrayList<String> getCabeceras() {
			return cabeceras;
		}
		
		/** Devuelve una cabecera concreta
		 * @param col	N�mero de columna
		 * @return	Cabecera de esa columna
		 */
		public String getCabecera( int col ) {
			return cabeceras.get(col);
		}
		
		/** Devuelve una fila completa de cabeceras separada por tabs
		 * @return	Serie de cabeceras separadas por tabs y acabadas en tab
		 */
		public String getCabecerasTabs() {
			String ret = "";
			for (int c=0; c<getWidth(); c++) {
				ret += (getCabecera(c) + "\t");
			}
			return ret;
		}
		
		/** Devuelve los tipos actuales
		 * @return	Lista de tipos de las columnas
		 */
		public ArrayList<Class<?>> getTipos() {
			return tipos;
		}
		
		/** Devuelve un tipo de columna
		 * @param col	N�mero de columna
		 * @return	Tipo de esa columna
		 */
		public Class<?> getType( int col ) {
			return tipos.get(col);
		}
		
		/** Cambia las cabeceras y tipos de la tabla, BORRANDO los datos si los hubiera
		 * @param cabeceras	Nombres de las cabeceras de datos
		 * @param tipos	Tipos de las columnas (debe tener la misma longitud)
		 */
		public void setCabecerasYTipos( ArrayList<String> cabeceras, ArrayList<Class<?>> tipos ) {
			this.cabeceras = cabeceras;
			this.tipos = tipos;
			dataO.clear();
		}
		
		/** Devuelve tama�o de la tabla (n�mero de filas de datos)
		 * @return	N�mero de filas de datos, 0 si no hay ninguno
		 */
		public int size() {
			return dataO.size();
		}
		
		/**  Devuelve el n�mero de columnas de la tabla
		 * @return	N�mero de columnas
		 */
		public int getWidth() {
			return cabeceras.size();
		}
		
		/** Busca un valor en la tabla, y devuelve la fila donde se encuentra la primera ocurrencia 
		 * @param valor	Valor que se busca
		 * @param numCol	Columna donde se busca ese valor
		 * @return	Fila de datos donde se encuentra, -1 si no se encuentra
		 */
		public int searchFila( Object valor, int numCol ) {
			for (int fila=0; fila<size(); fila++) {
				if (valor!=null && valor.equals( get( fila, numCol ) )) {
					return fila;
				}
			}
			return -1;
		}
		
		/** A�ade una columna al final de las existentes
		 * @param cabecera	Nuevo nombre de cabecera para la columna
		 * @param tipo	Nuevo tipo para la columna
		 * @param valPorDefecto	Valor por defecto para asignar a cada fila existente en esa nueva columna
		 */
		public void addColumna( String cabecera, Class<?> tipo, Object valPorDefecto ) {
			cabeceras.add( cabecera );
			tipos.add( tipo );
			for (ArrayList<Object> line : dataO) {
				line.add( valPorDefecto );
			}
		}
		
		/** A�ade una columna al final de las existentes
		 * @param cabecera	Nuevo nombre de cabecera para la columna
		 * @param valPorDefecto	Valor por defecto para asignar a cada fila existente en esa nueva columna (infiere el tipo del valor por defecto, que NO DEBE ser null
		 */
		public void addColumna( String cabecera, Object valPorDefecto ) {
			cabeceras.add( cabecera );
			tipos.add( valPorDefecto.getClass() );
			for (ArrayList<Object> line : dataO) {
				line.add( valPorDefecto );
			}
		}
		
		/** A�ade una l�nea de datos al final de la tabla (no hace nada si la tabla es una tabla enlazada a lista)
		 * @param linea	Nueva l�nea de datos (debe tener la longitud correspondiente al n�mero de columnas)
		 */
		public void addDataLine( ArrayList<Object> linea ) {
			dataO.add( linea );
			cambioEnTabla( dataO.size()-1, TableModelEvent.INSERT );  // Evento de cambio para posible JTable asociada
		}
		
		/** A�ade una l�nea de datos vac�a (rellena a nulos) al final de la tabla
		 */
		public void addDataLine() {
			ArrayList<Object> line = new ArrayList<>();
			for (int i=0; i<cabeceras.size(); i++) line.add( null );
			dataO.add( line );
		}
		
		/** A�ade a la actual las l�neas de valores de una segunda tabla
		 * @param tabla2	Segunda tabla. Solo se a�aden aquellos valores cuyas columnas ya existan en la tabla original
		 */
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
		
		/** Devuelve un valor de dato de la tabla
		 * @param row	N�mero de fila
		 * @param col	N�mero de columna
		 * @return	Dato de ese valor
		 */
		public Object get( int row, int col ) {
			return dataO.get( row ).get( col );
		}
		
		/** Devuelve un valor de dato de la tabla
		 * @param row	N�mero de fila
		 * @param nomCol	Nombre de columna
		 * @return	Dato de ese valor
		 * @throws IndexOutOfBoundsException si el nombre de columna no se encuentra en las cabeceras
		 */
		public Object get( int row, String nomCol ) throws IndexOutOfBoundsException {
			int col = cabeceras.indexOf( nomCol );
			if (col==-1) throw new IndexOutOfBoundsException( "Columna no encontrada: " + nomCol );
			return get( row, col );
		}
		
		/** Devuelve un valor de dato de la tabla en forma de entero
		 * @param row	N�mero de fila
		 * @param col	N�mero de columna
		 * @return	Dato entero de ese valor (convertido a entero si es un string), Integer.MAX_VALUE si no es un entero o no es convertible
		 */
		public int getInt( int row, int col ) {
			try {
				Object o = dataO.get( row ).get( col );
				if (o instanceof Integer) return ((Integer)o).intValue();
				return Integer.parseInt( o.toString() );
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		
		/** Devuelve un valor de dato de la tabla en forma de doble
		 * @param row	N�mero de fila
		 * @param col	N�mero de columna
		 * @return	Dato doble de ese valor (convertido a doble si es un string), Double.NaN si no es un double o no es convertible
		 */
		public double getDouble( int row, int col ) {
			try {
				Object o = dataO.get( row ).get( col );
				if (o instanceof Double) return ((Double)o).doubleValue();
				return Double.parseDouble( o.toString() );
			} catch (Exception e) {
				return Double.NaN;
			}
		}
		
		/** Devuelve un valor de dato de la tabla en forma de fecha dd/mm/aaaa o dd/mm/aaaa hh:mm:ss
		 * @param row	N�mero de fila
		 * @param col	N�mero de columna
		 * @return	Dato fecha de ese valor (convertido a fecha si es un string), null si no es una fecha o no es convertible con el formato indicado
		 * @return	Dato de ese valor, null si es una fecha incorrecta
		 */
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
		
		/** Modifica un valor de dato de la tabla
		 * @param row	N�mero de fila
		 * @param col	N�mero de columna
		 * @param valor	Valor a modificar en esa posici�n
		 * @throws ClassCastException	Error lanzado si se intenta asignar un valor incompatible con el tipo de la columna
		 */
		public void set( int row, int col, Object valor ) throws ClassCastException {
			if (tipos.get(col)!=null && valor!=null && !tipos.get(col).isAssignableFrom( valor.getClass() )) {  // Control de tipo incorrecto
				throw new ClassCastException( "Error en set: intentando asignar [" + valor + "] un " + valor.getClass().getSimpleName() + " en un " + tipos.get(col).getSimpleName() + " en columna " + col );
			}
			dataO.get( row ).set( col, valor );
			cambioEnTabla( row, col, row, col );  // Evento de cambio para posible JTable asociada
		}
		
		/** Modifica un valor de dato de la tabla
		 * @param row	N�mero de fila
		 * @param nomCol	Nombre de columna
		 * @param valor	Valor a modificar en esa posici�n
		 */
		public void set( int row, String nomCol, Object valor ) {
			int col = cabeceras.indexOf( nomCol );
			if (col==-1) throw new IndexOutOfBoundsException( "Columna no encontrada: " + nomCol );
			set( row, col, valor );
		}
		
		/** Modifica un valor de dato de la tabla en la �ltima fila a�adida
		 * @param col	Nombre de columna
		 * @param valor	Valor a modificar en esa posici�n
		 */
		public void set( String nomCol, Object value ) {
			set( dataO.size()-1, nomCol, value );
		}
		
		/** Modifica un valor de dato de la tabla en la �ltima fila a�adida
		 * @param col	N�mero de de columna
		 * @param valor	Valor a modificar en esa posici�n
		 */
		public void set( int col, Object value ) {
			set( dataO.size()-1, col, value );
		}
		
		/** Devuelve una fila completa de la tabla
		 * @param row	N�mero de fila
		 * @return	Lista de valores de esa fila
		 */
		public ArrayList<Object> getFila( int row ) {
			return dataO.get(row);
		}
		
		/** Devuelve una fila completa de la tabla separada por tabs y quitando saltos de l�nea
		 * @param row	N�mero de fila
		 * @return	Serie de valores de esa fila separados por tabs y acabados en tab
		 */
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
		
		/** Procesa tabla de datos con los datos ya existentes. Recalcula los tipos de datos que sean strings (inferidos de los valores que tienen esos strings) comprobando que puedan ser integer, double o date.<br/>
		 * Si no hay congruencia en todos los valores, se mantienen los tipos string.
		 * @return	0 si el c�lculo es correcto, n>0 si hay alguna fila (n) con un n�mero de datos no coherente con la tabla
		 */
		public int recalcTipos() {
			// 1.- calcula errores
			int ok = 0;
			int lin = 1;
			for (ArrayList<Object> line : dataO) if (line.size()!=cabeceras.size()) {
				ok++;
				System.err.println( "Error en l�nea " + lin + ": " + line.size() + " valores en vez de " + cabeceras.size() );
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
							if (tipo==null || tipo==nextType) {  // Tipo homog�neo, lo guardamos
								tipo = nextType;
							} else if (tipo!=nextType) {  // Tipo heterog�neo, vamos a ver si encaja
								if (tipo==Integer.class && nextType==Double.class) {  // El int s� puede ser double
									tipo = Double.class;
								} else if (tipo==Double.class && nextType==Integer.class) { // El int s� puede ser double
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
								} catch (NumberFormatException e) { } // No deber�a ocurrir - en este caso se anula el valor (no es convertible)
							} else if (tipo==Double.class) {
								try {
									nuevoVal = new Double( Double.parseDouble( val ) );
								} catch (NumberFormatException e) { } // No deber�a ocurrir - en este caso se anula el valor (no es convertible)
							} else if (tipo==Date.class) {
								try {
									nuevoVal = sdf.parse( val );
								} catch (ParseException e) {
									try {
										nuevoVal = sdfDMY.parse( val );
									} catch (ParseException e2) { } // No deber�a ocurrir - en este caso se anula el valor (no es convertible)
								}
							}
							dataO.get(row).set( col, nuevoVal ); // Modifica el valor por el nuevo tipo
						}
					}
				}
			}
			return ok;
		}
			// Devuelve suposici�n de tipo de valor String (si value es un string), null si es nulo o no se puede suponer (indefinido)
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

			
		/** Informa a la tabla y sus escuchadores que se ha producido un cambio en los datos internos<br/>
		 * Es necesario hacerlo si est� enlazado al modelo de datos de JTable
		 * @param fila1	Fila inicial del cambio
		 * @param col1	Columna inicial del cambio
		 * @param fila2	Fila final del cambio (incluida)
		 * @param col2	Columna final del cambio (incluida)
		 */
		public void cambioEnTabla( int fila1, int col1, int fila2, int col2 ) {
			if (miModelo==null || miModelo.lListeners==null) return;
			for (int col=col1; col<=col2; col++) {
				for (TableModelListener l : miModelo.lListeners) {
					l.tableChanged( new TableModelEvent( miModelo, fila1, fila2, col, TableModelEvent.UPDATE ) );
				}
			}
		}
			
		/** Informa a la tabla que se ha producido un cambio en los datos internos<br/>
		 * Es necesario hacerlo si est� enlazado al modelo de datos de JTable
		 * @param fila1	Fila del cambio
		 * @param tipoC	Tipo del cambio (TableModelEvent.UPDATE, TableModelEvent.INSERT, TableModelEvent.DELETE)
		 */
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
		
		/** Genera un fichero csv (codificado UTF-8) partiendo de los datos actuales de la tabla
		 * @param file	Fichero de salida
		 * @param comasEnVezDePuntos	Si se pone true, los dobles se generan con coma decimal en vez de punto decimal
		 * @throws IOException
		 */
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
		// M�todos est�ticos
		
			protected static boolean LOG_CONSOLE_CSV = false;  // Log en consola de la lectura del csv
			
		// M�todo de carga de tabla desde CSV
		/** Procesa un fichero csv (codificado UTF-8) y lo carga devolvi�ndolo en una nueva tabla
		 * @param file	Fichero del csv
		 * @return	Nuevo objeto tabla con los contenidos de ese csv
		 * @throws IOException
		 */
		public static Tabla processCSV( File file ) 
		throws IOException // Error de I/O
		{
			Tabla tabla = processCSV( file.toURI().toURL() );
			return tabla;
		}
		
		/** Procesa un fichero csv (codificado UTF-8) y lo carga devolvi�ndolo en una nueva tabla
		 * @param urlCompleta	URL del csv
		 * @param lineaCabs	Por defecto es 1 (n�mero de l�nea que incluye las cabeceras) pero si es otra se puede indicar (>= 1)
		 * @return	Nuevo objeto tabla con los contenidos de ese csv
		 * @throws IOException
		 * @throws UnknownHostException
		 * @throws FileNotFoundException
		 * @throws ConnectException
		 * @return
		 */
		public static Tabla processCSV( URL url, int... lineaCabs ) 
		throws MalformedURLException,  // URL incorrecta 
		 IOException, // Error al abrir conexi�n
		 UnknownHostException, // servidor web no existente
		 FileNotFoundException, // En algunos servidores, acceso a p�gina inexistente
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
			    input = new BufferedReader(new InputStreamReader( inStream, "UTF-8" ));  // Supone utf-8 en la codificaci�n de texto
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
			// Rec�lculo de tipos
			int errs = tabla.recalcTipos();
			if (errs>0) {  // Quitar filas err�neas si las hay
				for (int fila=tabla.size()-1; fila>=0; fila--) {  // Iteramos de arriba abajo porque vamos a ir quitando filas
					if (tabla.getFila(fila).size()!=tabla.cabeceras.size()) {
						tabla.dataO.remove( fila );
					}
				}
				tabla.recalcTipos();  // Recalcular otra vez
			}
		    return tabla;
		}
		
			/** Procesa una l�nea de entrada de csv	
			 * @param input	Stream de entrada ya abierto
			 * @param line	La l�nea YA LE�DA desde input
			 * @param numLine	N�mero de l�nea ya le�da
			 * @return	Lista de strings procesados en el csv. Si hay alg�n string sin acabar en la l�nea actual, lee m�s l�neas del input hasta que se acaben los strings o el input
			 * @throws StringIndexOutOfBoundsException
			 */
			private static ArrayList<Object> processCSVLine( BufferedReader input, String line, int numLine ) throws StringIndexOutOfBoundsException {
				ArrayList<Object> ret = new ArrayList<>();
				int posCar = 0;
				boolean inString = false;
				boolean finString = false;
				String stringActual = "";
				char separador = 0;
				while (line!=null && (posCar<line.length() || line.isEmpty() && posCar==0)) {
					if (line.isEmpty() && posCar==0) {
						if (!inString) return ret;  // L�nea vac�a
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
								} else {  // " despu�s de valor - error
									throw new StringIndexOutOfBoundsException( "\" after data in char " + posCar + " of line [" + line + "]" );
								}
							}
						} else if (car==',' || car==';') {
							if (inString) {  // separador dentro de string
								stringActual += car;
							} else {  // separador que separa valores
								if (separador==0) { // Si no se hab�a encontrado separador hasta ahora
									separador = car;
									ret.add( stringActual );
									stringActual = "";
									finString = false;
								} else { // Si se hab�a encontrado, solo vale el mismo (, o ;)
									if (separador==car) {  // Es un separador
										ret.add( stringActual );
										stringActual = "";
										finString = false;
									} else {  // Es un car�cter normal
										if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor despu�s de string - error
										stringActual += car;
									}
								}
							}
						} else {  // Car�cter dentro de valor
							if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor despu�s de string - error
							stringActual += car;
						}
						posCar++;
					}
					if (posCar>=line.length() && inString) {  // Se ha acabado la l�nea sin acabarse el string. Eso es porque alg�n string incluye salto de l�nea. Se sigue con la siguiente l�nea
						line = null;
					    try {
							line = input.readLine();
					    	if (LOG_CONSOLE_CSV) System.out.println( "  " + numLine + " (add)\t" + line );
							posCar = 0;
							stringActual += "\n";
						} catch (IOException e) {}  // Si la l�nea es null es que el fichero se ha acabado ya o hay un error de I/O
					}
				}
				if (inString) throw new StringIndexOutOfBoundsException( "String not closed in line " + numLine + ": [" + line + "]");
				ret.add( stringActual );
				return ret;
			}

				// Devuelve el siguiente car�cter (car 0 si no existe el siguiente car�cter)
				private static char nextCar( String line, int posCar ) {
					if (posCar+1<line.length()) return line.charAt( posCar + 1 );
					else return Character.MIN_VALUE;
				}

		
		// =================================================
		// M�todos relacionados con el modelo de tabla (cuando se quiere utilizar esta tabla en una JTable)
		
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

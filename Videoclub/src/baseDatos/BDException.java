package baseDatos;

public class BDException extends Exception{
	
	
	private static final long serialVersionUID = 1L;
	/**
	 * Devuelve la excepcion de la base de datos con un mensaje
	 * @param message
	 */
	public BDException(String message) {
		super(message);
	}
	
	public BDException(String message, Throwable e) {
		super(message, e);
	}
}

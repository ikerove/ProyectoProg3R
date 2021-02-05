package datos;


public class Posicion implements Comparable {

	private String nombre;
	int posicion;
	
	public Posicion(String nombre, int posicion) {
		super();
		this.nombre = nombre;
		this.posicion = posicion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	@Override
	public int compareTo(Object arg0) {
		Posicion pos = (Posicion) arg0;
		return this.nombre.compareTo(pos.nombre);
	}
	
	
}

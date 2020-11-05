package datos;

import java.io.Serializable;

public class Documental extends Multimedia implements Serializable{
	private boolean animales;

	public Documental() {
		super();
	}

	public Documental(int codigo, String titulo,boolean animales) {
		super(codigo, titulo);
		this.animales = animales;
	}

	public boolean isAnimales() {
		return animales;
	}

	public void setAnimales(boolean animales) {
		this.animales = animales;
	}

	@Override
	public String toString() {
		return "Documental [animales=" + animales + "]";
	}
	
	
	
}

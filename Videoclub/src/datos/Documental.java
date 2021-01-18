package datos;

import java.io.Serializable;
import java.util.Date;


//Esta clase es para los Domunetales y los atributos que tienen
public class Documental extends Multimedia implements Serializable{
	private boolean animales;

	public Documental() {
		super();
	}

	public Documental(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			Date fecha, String calificacion, boolean animales, String rutaFoto) {
		super(codigo, titulo, director, genero, duracion, distribuidora, fecha, calificacion, rutaFoto);
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
		return "Documental [codigo=" + codigo + ", titulo=" + titulo + "]";
	}

	@Override
	public String getTexto() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}

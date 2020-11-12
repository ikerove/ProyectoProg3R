package datos;

import java.io.Serializable;


//Esta clase es para las Series y los atributos que tienen
public class Serie extends Multimedia implements Serializable{
	private String formato;
	private int temporadas;
	private int capitulos;
	private int duracionCap;
	
	

	public Serie() {
		super();
	}

	public Serie(int codigo, String titulo, String formato, int temporadas, int capitulos, int duracionCap) {
		super(codigo, titulo);
		this.formato = formato;
		this.temporadas = temporadas;
		this.capitulos = capitulos;
		this.duracionCap = duracionCap;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(int temporadas) {
		this.temporadas = temporadas;
	}

	public int getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(int capitulos) {
		this.capitulos = capitulos;
	}

	public int getDuracionCap() {
		return duracionCap;
	}

	public void setDuracionCap(int duracionCap) {
		this.duracionCap = duracionCap;
	}

	@Override
	public String toString() {
		return "Serie [codigo=" + codigo + ", titulo=" + titulo + "]";
	}

	
	

	

	
	
	
	
}

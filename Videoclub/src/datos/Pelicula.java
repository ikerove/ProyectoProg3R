package datos;

import java.io.Serializable;

public class Pelicula extends Objeto implements Serializable{
	private String guion;
	private String musica;
	private boolean oscars;
	
	public Pelicula() {
		super();
	}

	public Pelicula(int codigo, String titulo,String guion, String musica, boolean oscars) {
		super(codigo, titulo);
		this.guion = guion;
		this.musica = musica;
		this.oscars = oscars;		
	}

	public String getGuion() {
		return guion;
	}

	public void setGuion(String guion) {
		this.guion = guion;
	}

	public String getMusica() {
		return musica;
	}

	public void setMusica(String musica) {
		this.musica = musica;
	}

	public boolean isOscars() {
		return oscars;
	}

	public void setOscars(boolean oscars) {
		this.oscars = oscars;
	}

	@Override
	public String toString() {
		return "Pelicula [guion=" + guion + ", musica=" + musica + ", oscars=" + oscars + "]";
	}
	
	
	
}

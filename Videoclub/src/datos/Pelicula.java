package datos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//Esta clase es para las Peliculas y los atributos que tienen
public class Pelicula extends Multimedia implements Serializable, InterfaceReserva{
	private String guion;
	private String musica;
	private boolean oscars;
	
	public Pelicula() {
		super();
	}

	public Pelicula(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			Date fecha, String calificacion, String guion, String musica, boolean oscars, String rutaFoto) {
		super(codigo, titulo, director, genero, duracion, distribuidora, fecha, calificacion, rutaFoto);
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

	@Override
	public String getTexto() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
}

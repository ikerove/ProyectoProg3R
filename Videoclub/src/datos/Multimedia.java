package datos;

import java.text.SimpleDateFormat;
import java.util.Date;

//Esta clase es la clase padre de todos los contenidos multimedia y atributos que heredan las clases hijas
public abstract class Multimedia implements InterfaceReserva {
	protected int codigo;
	protected String titulo;
	protected String director;
	protected String genero;
	protected int duracion;
	protected String distribuidora;	
	protected Date fecha;
	protected String calificacion;
	protected String rutaFoto;
	//prueba
	public Multimedia() {
		super();
	}

	public Multimedia(int codigo, String titulo, String director, String genero, int duracion, String distribuidora,
			Date fecha, String calificacion, String rutaFoto) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.director = director;
		this.genero = genero;
		this.duracion = duracion;
		this.distribuidora = distribuidora;
		this.fecha = fecha;
		this.calificacion = calificacion;
		this.rutaFoto = rutaFoto;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getDistribuidora() {
		return distribuidora;
	}

	public void setDistribuidora(String distribuidora) {
		this.distribuidora = distribuidora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public abstract String getTexto() ;

	public SimpleDateFormat reservar() {
		// TODO Auto-generated method stub
		return null;
	}
		
	
	
}
	
	
	


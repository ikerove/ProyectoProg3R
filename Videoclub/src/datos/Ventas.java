package datos;


import java.io.Serializable;
import java.util.Date;


//Esta clase es para las Series y los atributos que tienen
public class Ventas {
	private int codigo;
	private String objeto;
	private String fecha;
	private String usuario;
	private String ruta;

	public Ventas() {
		super();
	}


	public Ventas(int codigo, String objeto, String fecha, String usuario, String ruta) {
		super();
		this.codigo = codigo;
		this.objeto = objeto;
		this.fecha = fecha;
		this.usuario = usuario;
		this.ruta = ruta;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getObjeto() {
		return objeto;
	}


	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	

	
	
	
	
}

package datos;

//Esta clase es la clase padre de todos los contenidos multimedia y atributos que heredan las clases hijas
public class Multimedia {
	protected int codigo;
	protected String titulo;
	//prueba
	public Multimedia() {
		super();
	}

	public Multimedia(int codigo, String titulo) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
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

	@Override
	public String toString() {
		return "Objeto [codigo=" + codigo + ", titulo=" + titulo + "]";
	}
	
	
	
}

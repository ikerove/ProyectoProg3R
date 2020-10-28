package datos;

public class Objeto {
	protected int codigo;
	protected String titulo;
	
	public Objeto() {
		super();
	}

	public Objeto(int codigo, String titulo) {
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

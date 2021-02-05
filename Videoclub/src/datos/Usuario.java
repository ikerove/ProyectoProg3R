package datos;

public class Usuario {
	private String nick;
	private String con;
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Usuario(String nick, String con) {
		super();
		this.nick = nick;
		this.con = con;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	@Override
	public String toString() {
		return "Usuario [nick=" + nick + ", con=" + con + "]";
	}
}

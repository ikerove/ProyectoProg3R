package paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


//Fondo para la VentanaUsuari
public class PanelFondo extends JPanel {
	private String fondo;
	public PanelFondo(String fondo){
		
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    setSize(ancho,alto);
		this.setLayout(new BorderLayout());
		this.fondo = fondo;
	}
	
	public void paintComponent(Graphics g){
		Dimension tamanio = getSize();
		ImageIcon imagenFondo = new ImageIcon(fondo);
		g.drawImage(imagenFondo.getImage(), 0, 0, tamanio.width, tamanio.height, this);
		setOpaque(false);
		super.paintComponent(g);
		
	}
}

package ventanas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import paneles.PanelDocumental;
import paneles.PanelPelicula;
import paneles.PanelSerie;

public class VentanaUtilidades extends JFrame{
	private JPanel pNorte, pSur, pCentro;
	private JScrollPane pCentroArriba, pCentroMedio, pCentroAbajo;
	public VentanaUtilidades() {
		super();
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    setSize(ancho,alto);
	    setExtendedState(MAXIMIZED_BOTH); //Maximizar la ventana	    
	    setTitle("ADMINISTRACIÓN");
	    setIconImage(new ImageIcon("imagenes/newton.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    //CREACIÓN DE LOS PANELES
	    pNorte = new JPanel();
	    pSur = new JPanel();
	    pCentro = new JPanel(new GridLayout(3,1));
	    
	    pCentroArriba = new JScrollPane(new PanelSerie());	   
	    pCentro.add(pCentroArriba);
	    
	    pCentroMedio = new JScrollPane(new PanelPelicula());
	    pCentro.add(pCentroMedio);
	    
	    pCentroAbajo = new JScrollPane(new PanelDocumental());
	    pCentro.add(pCentroAbajo);

	    
	  
	    
	    
	    
	    getContentPane().add(pNorte, BorderLayout.NORTH);
	    getContentPane().add(pSur, BorderLayout.SOUTH);
	    getContentPane().add(pCentro, BorderLayout.CENTER);
		
	    
	    setVisible(true);
	}
}

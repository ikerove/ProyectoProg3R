package ventanas;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicLookAndFeel;

import baseDatos.BD;
import datos.Multimedia;
import datos.Pelicula;
import datos.Serie;

public class VentanaPago extends JFrame{
	
	
	private JTextArea txtTexto;
	private String nick;
	private JButton btnPagar;
	private JPanel pBotonera;
	
	public VentanaPago(String nick) {
	super();
	this.nick = nick;
	int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    setSize(ancho,alto);
    setExtendedState(MAXIMIZED_BOTH); //Maximizar la ventana
    //setResizable(false);
    //setAlwaysOnTop(true); //Siempre por encima del resto de ventanas (mientras esté visible)
    setTitle("CLIENTES");
    setIconImage(new ImageIcon("imagenes/newton.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	
    //CREACIÓN DE COMPONENTES
    txtTexto =  new JTextArea();
    btnPagar = new JButton("PAGAR");
    
    //CREACIÓN DE PANELES
    JScrollPane scrollTexto = new JScrollPane(txtTexto);
    
    pBotonera =  new JPanel();
    pBotonera.add(btnPagar);
    
    getContentPane().add(scrollTexto, BorderLayout.CENTER);
    getContentPane().add(pBotonera, BorderLayout.SOUTH);
}
}

package ventanas;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import paneles.PanelFondo;

public class VentanaUsuario extends JFrame {
	private JPanel panelCentro, panelBase, panelBotonera ;
	private JTextField txtNombre;
	private JPasswordField txtContrasenia;
	private JLabel usuario, contrasenia;
	private JButton btnEntrar, btnSalir, btnRegistrar, btnAdmin, btnFavoritos;
	public static String nick;

	public VentanaUsuario() {
		super();
		
		
		setSize(500,250);
		setTitle("Inicio de sesion");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//setExtendedState(MAXIMIZED_BOTH);
		
		panelCentro = new PanelFondo("imagenes/fondo.jpg");
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
		panelCentro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10, true));
		    
		
	}
	
}

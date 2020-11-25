package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import baseDatos.BD;
import paneles.PanelFondo;


//Ventana de inicio de sesión donde se meten usuario y contraseña
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(MAXIMIZED_BOTH);
		
		panelCentro = new PanelFondo("imagenes/fondo.jpg");
		panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
		panelCentro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10, true));
		
		
		panelBase = new JPanel();
		panelBase.setBackground(Color.DARK_GRAY);
		    
		panelBotonera = new JPanel();
		panelBotonera.setBackground(Color.DARK_GRAY);
		    
		getContentPane().add(panelCentro,BorderLayout.CENTER);
		getContentPane().add(panelBase, BorderLayout.NORTH);
		getContentPane().add(panelBotonera, BorderLayout.SOUTH);
		
		
		//usuario = new JLabel();
		//usuario.setText("Introduzca el nombre de usuario");
		
		txtNombre = new JTextField();
		txtNombre.setPreferredSize(new Dimension (200, 50));
		posicionaLinea(panelCentro, "Introduzca el usuario", txtNombre);
		
		 
		//contrasenia = new JLabel();
		//contrasenia.setText("Introduzca la contrasenia");
		
		txtContrasenia = new JPasswordField();
		txtContrasenia.setPreferredSize(new Dimension (200, 50));
		posicionaLinea(panelCentro, "Introduzca la contrasenia", txtContrasenia);
		
		
		btnEntrar = new JButton();
		btnEntrar.setToolTipText("Añade los parametros pedidos y pulsa el boton");
		btnEntrar.setText("Iniciar sesion");
		panelBotonera.add(btnEntrar);
		
		btnSalir = new JButton();
		btnSalir.setToolTipText("Pulsa para salir");
		btnSalir.setText("Salir");
		panelBotonera.add(btnSalir);
		
		btnRegistrar = new JButton();
		btnRegistrar.setToolTipText("Pulsa para registrarte");
		btnRegistrar.setText("Registro");
		btnRegistrar.setVisible(false);
		panelBotonera.add(btnRegistrar);
			
		
		
		btnAdmin = new JButton();
		btnAdmin.setToolTipText("Administracion");
		btnAdmin.setText("Admin");
		panelBotonera.add(btnAdmin);
		
		btnFavoritos = new JButton();
		btnFavoritos.setToolTipText("Haz una lista de favoritos");
		btnFavoritos.setText("Favoritos");
		btnFavoritos.setVisible(false);
		panelBotonera.add(btnFavoritos);
		
		
		
		
		
		btnEntrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String nick = txtNombre.getText();
				String contrasenia = txtContrasenia.getText();				
				int resultado = BD.existeUsuario(nick, contrasenia);
				
				if(resultado == 2) {
					JOptionPane.showMessageDialog(null, "BIENVENIDO AL VIDEOCLUB");
					//new VentanaCliente(nick);
					new VentanaMain();
					btnFavoritos.setVisible(true);
				}else if(resultado == 1) {
					JOptionPane.showMessageDialog(null, "La contraseña no es correcta", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Para poder acceder, primero tienes que registrarte");
					btnRegistrar.setVisible(true);

				}
				vaciarCampos();
			}
		});
		
		
		setVisible(true);
		
	}
	
	public void vaciarCampos(){
		txtNombre.setText("");
		txtContrasenia.setText("");
	}
	
	
	private void posicionaLinea(Container cont, String etiqueta, Component campo) {
		JPanel tempPanel = new JPanel();
		tempPanel.setOpaque(false);
		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // flow ajustado a la izquierda
		JLabel l = new JLabel(etiqueta);
		l.setPreferredSize(new Dimension(250, 50));
		l.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		l.setForeground(Color.WHITE);
		tempPanel.add(l);
		tempPanel.add(campo);
		cont.add(tempPanel);
	}
	
}

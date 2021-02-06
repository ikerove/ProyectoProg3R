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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import baseDatos.BD;
import baseDatos.BDException;
import paneles.PanelFondo;


//Ventana de inicio de sesi�n donde se meten usuario y contrase�a
//Ventana de inicio de sesi�n donde se meten usuario y contrase�a
public class VentanaUsuario extends JFrame {
	private JPanel panelCentro, panelBase, panelBotonera ;
	private JTextField txtNombre;
	private JPasswordField txtContrasenia;
	private JLabel usuario, contrasenia;
	private JButton btnEntrar, btnSalir, btnRegistrar, btnAdmin, btnFavoritos;
	public static String nick;

	
	public static Properties properties;
	
	public VentanaUsuario() throws BDException {
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
		
		
		String usuario = "";
		try {
			properties = new Properties();
			properties.loadFromXML( new FileInputStream( "proyectoprog3r.properties" ) );
			usuario = properties.getProperty( "USUARIO" );
		} catch (Exception e1) {}  // Cuando el xml a�n no existe no se hace nada
		
		if (!usuario.equals("")) {
			txtNombre.setText(usuario);
			this.pack();  //Realize the components.
		    //This button will have the initial focus.
			txtContrasenia.requestFocusInWindow(); 
		}
		
		 
		
		btnEntrar = new JButton();
		btnEntrar.setToolTipText("A�ade los parametros pedidos y pulsa el boton");
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
		
		
		
		
		
		
		
		btnEntrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String nick = txtNombre.getText();
				String contrasenia = txtContrasenia.getText();				
				int resultado;
				try {
					resultado = BD.existeUsuario(nick, contrasenia);
					if(resultado == 2) {
						JOptionPane.showMessageDialog(null, "BIENVENIDO AL VIDEOCLUB");
						
						
						try {
							properties.setProperty( "USUARIO", nick );
							properties.storeToXML( new FileOutputStream( new java.io.File("proyectoprog3r.properties") ), "Configuraci�n de Videoclub" );
						} catch (Exception ex) { }  // No se ha podido guardar el fichero de configuraci�n
						
						//new VentanaCliente(nick);
						new VentanaMain();
						
					}else if(resultado == 1) {
						JOptionPane.showMessageDialog(null, "La contrase�a no es correcta", "ERROR!", JOptionPane.ERROR_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Para poder acceder, primero tienes que registrarte");
						btnRegistrar.setVisible(true);

					}
					vaciarCampos();
				} catch (BDException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		btnRegistrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vaciarCampos();
				String nick = JOptionPane.showInputDialog("Introduce tu nick: ");
				String contrasenia = JOptionPane.showInputDialog("Introduce la contrase�a: ");
				if(nick!=null && contrasenia!=null) {
					int resultado;
					try {
						resultado = BD.existeUsuario(nick, contrasenia);
						if(resultado!=0) {
							JOptionPane.showMessageDialog(null, "Ese nick ya est� en uso", "ERROR!", JOptionPane.ERROR_MESSAGE);
						}else {
							BD.insertarUsuario(nick, contrasenia);
							ImageIcon im = new ImageIcon("imagenes/ok.jpg");
							JOptionPane.showMessageDialog(null, "Te has registrado correctamente","REGISTRO",JOptionPane.INFORMATION_MESSAGE,im);
							btnRegistrar.setVisible(false);
						}
					} catch (BDException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		btnAdmin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nickAdmin = JOptionPane.showInputDialog("Introduce tu nick: ");
				String contraseniaAdmin = JOptionPane.showInputDialog("Introduce la contrase�a: ");
				//String contrasenia2 = "VIDEOCLUB";
				if(nickAdmin!=null && contraseniaAdmin!=null){
					int resultado;
					try {
						resultado = BD.existeAdmin(nickAdmin, contraseniaAdmin);
						if(resultado!=0) {
							//JOptionPane.showMessageDialog(null, "Ese admin ya existe", "ERROR!", JOptionPane.ERROR_MESSAGE);
							new VentanaUtilidades();
						}else {
							JOptionPane.showMessageDialog(null, "Admin incorrecto", "ERROR!", JOptionPane.ERROR_MESSAGE);
							String contrasenia2 = JOptionPane.showInputDialog("Inserte contrasenia de administrador");
							if(contrasenia2.equals("VIDEOCLUB")) {
								BD.insertarAdmin(nickAdmin, contraseniaAdmin);
								ImageIcon im = new ImageIcon("imagenes/ok.jpg");
								JOptionPane.showMessageDialog(null, "Nuevo administrador registrado","REGISTRO",JOptionPane.INFORMATION_MESSAGE,im);
								new VentanaUtilidades();
							}else{
								JOptionPane.showMessageDialog(null, "Contrasenia incorrecta", "ERROR!", JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (BDException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			
		});
		
		Connection con = BD.initBD("videoclub.sqlite3");
		Statement st = BD.usarCrearTablasBD(con);
		BD.cerrarBD(con, st);
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

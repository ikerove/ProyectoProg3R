package ventanas;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import datos.Posicion;
import baseDatos.BD;
import baseDatos.BDException;
import datos.Documental;
import datos.Pelicula;
import datos.Serie;
import datos.Tabla;
import datos.Ventas;

import java.awt.Font;
import javax.swing.JTable;

public class VentanaMain extends JFrame{
	private JPanel panCentro, panSur ;
	private JScrollPane panScrol;
	private JMenuBar menuBar;
	private JMenu menuS, menuP, menuD, menuE, menuF;
	private JMenuItem mi1, mi2, mi3, mi4,mi5, mi6;
	private JFrame v;
	private JButton reserv;
	private JLabel Favoritos;
	private JLabel Estrenos;
	private JTable table;
	private JTable table2;
	
	
	private String tipo_venta;
	private JMenu menuV;
	private JMenuItem mi7;
		
	private ArrayList<Pelicula> peliculas;
	
	
	public VentanaMain() {
		super();
		v = this;
		this.setSize(500,500);
		this.setTitle("Videoclub");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Datos de prueba para comprobar el m�todo recursivo buscarPeliculas()
				peliculas = new ArrayList<Pelicula>();
				peliculas.add(new Pelicula(1, "Pelicula A", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(2, "Pelicula B", "Pablo Dosio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(3, "Pelicula C", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(4, "Pelicula D", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(5, "Pelicula E", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(6, "Pelicula F", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(7, "Pelicula G", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(8, "Pelicula H", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(9, "Pelicula I", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(10, "Pelicula J", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(11, "Pelicula K", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				peliculas.add(new Pelicula(12, "Pelicula L", "Pablo Unio", "Comedia", 120, "", new Date(), "", "", "", false, "", 0));
				
		
		panCentro = new JPanel();
		panSur = new JPanel();
		
		
		
		panScrol = new JScrollPane(panCentro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//panCentro.setLayout(null);
		

		menuBar = new JMenuBar();
		
		menuS = new JMenu("Series");		
		menuP = new JMenu("Peliculas");		
		menuD = new JMenu("Documentales");		
		menuF = new JMenu("Favoritos");
		menuE= new JMenu("Estrenos");
		
		menuV= new JMenu("Ventas");
		mi7= new JMenuItem("Listar Ventas");
		
		

		mi7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tipo_venta = "Ventas";
					cargaVentas();
				} catch (BDException e1) {
				}
			}
		});
			
		mi1 = new JMenuItem("Series");
		mi2 = new JMenuItem("Peliculas");
		mi3 = new JMenuItem("Documentales");
		mi4 = new JMenuItem("Favoritos");
		mi5 = new JMenuItem("Estrenos");
		mi6= new JMenuItem("Buscar");
				
		//reserv =new JButton("Reservar");
		//getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		menuS.add(mi1);
		menuP.add(mi2);
		menuP.add(mi6);
		menuD.add(mi3);
		menuF.add(mi4);
		menuE.add(mi5);		
		menuV.add(mi7);
		menuBar.add(menuS);
		menuBar.add(menuP);
		menuBar.add(menuD);
		menuBar.add(menuE);
		menuBar.add(menuF);		
		menuBar.add(menuV);
		
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		this.getContentPane().add(panScrol);
		this.getContentPane().add(panSur, BorderLayout.SOUTH);
		
		/**
		 * Esto hace que al clicar al mi1(un JMenuItem) llame al metodo de la BD cargaSeries()
		 */
		mi1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				try {
					
					
					tipo_venta = "Serie";
					
					cargaSeries();
				} catch (BDException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		/**
		 * Esto hace que al clicar al mi2(un JMenuItem) llame al metodo de la BD cargaPeliculas()
		 */
		mi2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					
					tipo_venta = "Pelicula";
					
					cargaPeliculas();
				} catch (BDException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		/**
		 * Esto hace que al clicar al mi3(un JMenuItem) llamado documentales, llame al metodo de la BD cargaDocumentales()
		 */
		mi3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					
					tipo_venta = "Documental";
					
					
					cargaDocumentales();
				} catch (BDException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mi4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new VentanaCliente();
				
			}
		});
		
		/**
		 * Esto hace que al clicar al mi5(un JMenuItem) llamado estrenos, se crea el dise�o de el panel, se crea una tabla y 
		 dentro de la tabla se metera el fichero estrenos.csv
		 */
		mi5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panCentro.removeAll();
				Estrenos = new JLabel("Estrenos: ");
				Estrenos.setBounds(10, 11, 97, 21);
				Estrenos.setFont(new Font("AR DARLING", Font.PLAIN, 16));
				panCentro.add(Estrenos, BorderLayout.NORTH);
				
				table2 = new JTable();
				JScrollPane scrollPane = new JScrollPane(table2);
				scrollPane.setBounds(0, 34, 382, 270);
				panCentro.add(scrollPane, BorderLayout.CENTER);

				Tabla ta2 = null;
				File fichero = new File("estrenos.csv");
				try {
					ta2 = Tabla.processCSV(fichero);
					table2.setModel( ta2.getTableModel());
					JScrollBar sc = scrollPane.getVerticalScrollBar();
					sc.setValue(0);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				panCentro.updateUI(); 
			}
		
		});
		
		
		/**
		 * Esto hace que al clicar al mi6(un JMenuItem) llamado buscarPelis carga el m�todo recursivo buscarPeliculas() 
		 */
		mi6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buscarPeliculas();			
				
			}
		});
		
		
		panCentro.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p = e.getPoint();
				
				JLabel lblFotoSeleccionada = (JLabel) panCentro.getComponentAt(p);
				lblFotoSeleccionada.getClass();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Point p = e.getPoint();
				
				JLabel lblFotoSeleccionada = (JLabel) panCentro.getComponentAt(p);
				v.dispose();
				try {
					
					//new VentanaFicha(lblFotoSeleccionada,v);
					new VentanaFicha(lblFotoSeleccionada, v, tipo_venta, lblFotoSeleccionada.getName(), VentanaUsuario.properties.getProperty("USUARIO"), lblFotoSeleccionada.getText());
					
					
				} catch (BDException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				}
		});
		
		
		

		
		setVisible(true);
		
		
	}
	
	/**
	 * m�todo recursivo para buscar p�liculas  introduciendo el t�tulo de la pelicula y te dira si esta disponible o no. Para buscarlo tambi�n hace uso del 
	 * m�todo recursivo de busquedaBinaria
	 */
	
	public void buscarPeliculas() {
		// TODO Auto-generated method stub
		String tituloPeli = (String) JOptionPane.showInputDialog( null, "Introduce el titulo de la pelicula: ", "Buscar Pelicula", JOptionPane.QUESTION_MESSAGE, null, null, "" );
		if (tituloPeli ==null) {
			JOptionPane.showMessageDialog( this, "Debe teclear un titulo de pelicula.", "Mensaje", JOptionPane.WARNING_MESSAGE );
			return;
		}
		
		int i = busquedaBinaria(peliculas, tituloPeli, 0, peliculas.size() - 1);
		if (i < 0) {
			JOptionPane.showMessageDialog( this, "La pelicula no se encuentra.", "Mensaje", JOptionPane.WARNING_MESSAGE );
		} else {
			JOptionPane.showMessageDialog( this, "La pelicula esta disponible.", "Mensaje", JOptionPane.WARNING_MESSAGE );
		}
		
		panCentro.updateUI();
	}
/**
 * es un algoritmo eficiente para encontrar un elemento en una lista ordenada de elementos. Funciona al dividir repetidamente
 *  a la mitad la porci�n de la lista que podr�a contener al elemento, hasta reducir las ubicaciones posibles a solo una
 * @param a
 * @param x
 * @param start
 * @param end
 * @return
 */
	private int busquedaBinaria(ArrayList<Pelicula> a, String x, int start, int end)  {
		int medio;
		if (start > end) {
			return -1;
		}
		medio = (start + end) / 2;
		
		if (a.get(medio).getTitulo().toUpperCase().compareTo(x.toUpperCase()) < 0) {
			return busquedaBinaria(a, x, medio + 1, end);
		} else {
			if (a.get(medio).getTitulo().toUpperCase().compareTo(x.toUpperCase()) > 0) {
				return busquedaBinaria(a, x, start, medio - 1); 
			} else {
				return medio; 
			}
		}
	}
	
	
	private void cargaVentas() throws BDException {
		ArrayList<Ventas> ventas = BD.obtenerVentas(VentanaUsuario.properties.getProperty("USUARIO"));
		panCentro.removeAll();
		for(Ventas v: ventas) {
			JLabel lblFoto = new JLabel();
			lblFoto.setSize(100,100);
			ImageIcon im = new ImageIcon(v.getRuta());
			ImageIcon imagenConDimensiones = new ImageIcon(im.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(),Image.SCALE_DEFAULT));
			imagenConDimensiones.setDescription(v.getRuta());
			lblFoto.setIcon(imagenConDimensiones); 
			
			panCentro.add(lblFoto);
		}
		panCentro.updateUI();
	}
	
	private void cargaSeries() throws BDException {
		ArrayList<Serie> series = BD.obtenerSeries();
		panCentro.removeAll();
		for(Serie s: series) {
			JLabel lblFoto = new JLabel();
			lblFoto.setSize(100,100);
			ImageIcon im = new ImageIcon(s.getRutaFoto());
			ImageIcon imagenConDimensiones = new ImageIcon(im.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(),Image.SCALE_DEFAULT));
			imagenConDimensiones.setDescription(s.getRutaFoto());
			lblFoto.setIcon(imagenConDimensiones); 
			
					
			panCentro.add(lblFoto);
		}
		panCentro.updateUI();
	}
	
	
	private void cargaPeliculas() throws BDException {
		ArrayList<Pelicula> peliculas = BD.obtenerPeliculas();
		panCentro.removeAll();
		for(Pelicula p: peliculas) {
			JLabel lblFoto = new JLabel();
			lblFoto.setSize(100,100);
			ImageIcon im = new ImageIcon(p.getRutaFoto());
			ImageIcon imagenConDimensiones = new ImageIcon(im.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(),Image.SCALE_DEFAULT));
			imagenConDimensiones.setDescription(p.getRutaFoto());
			lblFoto.setIcon(imagenConDimensiones); 
			
		
			panCentro.add(lblFoto);
		}
		panCentro.updateUI();
	}
	
	private void cargaDocumentales() throws BDException {
		ArrayList<Documental> documentales = BD.obtenerDocumentales();
		panCentro.removeAll();
		for(Documental d: documentales) {
			JLabel lblFoto = new JLabel();
			lblFoto.setSize(100,100);
			ImageIcon im = new ImageIcon(d.getRutaFoto());
			ImageIcon imagenConDimensiones = new ImageIcon(im.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(),Image.SCALE_DEFAULT));
			imagenConDimensiones.setDescription(d.getRutaFoto());
			lblFoto.setIcon(imagenConDimensiones); 
			
			panCentro.add(lblFoto);
		}
		panCentro.updateUI();
	}
	
	
	
	
	
	}
	





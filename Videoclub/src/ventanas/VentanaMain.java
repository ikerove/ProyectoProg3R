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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import baseDatos.BD;
import datos.Documental;
import datos.Pelicula;
import datos.Serie;
import java.awt.Font;
import javax.swing.JTable;

public class VentanaMain extends JFrame{
	private JPanel panCentro, panSur ;
	private JScrollPane panScrol;
	private JMenuBar menuBar;
	private JMenu menuS, menuP, menuD, menuE;
	private JMenuItem mi1, mi2, mi3, mi4;
	private JFrame v;
	private JButton reserv;
	private JLabel Estrenos;
	private JTable table;
	
	
	public VentanaMain() {
		super();
		v = this;
		this.setSize(400,400);
		this.setTitle("Videoclub");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		panCentro = new JPanel();
		panSur = new JPanel();
		panSur.setBounds(0, 328, 384, 33);
		
		
		panScrol = new JScrollPane(panCentro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panScrol.setBounds(0, 22, 384, 306);
		//panCentro.setLayout(null);
		

		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 384, 22);
		
		menuS = new JMenu("Series");		
		menuP = new JMenu("Peliculas");		
		menuD = new JMenu("Documentales");		
		menuE = new JMenu("Estrenos");
		
		mi1 = new JMenuItem("Series");
		mi2 = new JMenuItem("Peliculas");
		mi3 = new JMenuItem("Documentales");
		mi4 = new JMenuItem("Estrenos");
		
		reserv =new JButton("Reservar");
		getContentPane().setLayout(null);
		
		
		menuS.add(mi1);
		menuP.add(mi2);
		menuD.add(mi3);
		menuE.add(mi4);
		menuBar.add(menuS);
		menuBar.add(menuP);
		menuBar.add(menuD);
		menuBar.add(menuE);
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
		this.getContentPane().add(panScrol,BorderLayout.CENTER);
		this.getContentPane().add(panSur,BorderLayout.SOUTH);
		
		
		
		mi1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				cargaSeries();
			}
		});
		
		
		mi2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargaPeliculas();
			}
		});
		
		mi3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargaDocumentales();
			}
		});
		
		mi4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Estrenos = new JLabel("ESTRENOS: ");
				Estrenos.setBounds(10, 11, 97, 21);
				Estrenos.setFont(new Font("AR DARLING", Font.PLAIN, 16));
				panCentro.add(Estrenos);
				
				table = new JTable();
				JScrollPane scrollPane = new JScrollPane(table);
				scrollPane.setBounds(0, 34, 382, 270);
				panCentro.add(scrollPane);
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
				new VentanaFicha(lblFotoSeleccionada,v);
				
				}
		});
		
		panSur.add(reserv);
		reserv.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cargarReservas();
			}
		});
		

		
		setVisible(true);
		
		
	}
	
	
	private void cargaSeries() {
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
	
	
	private void cargaPeliculas() {
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
	
	private void cargaDocumentales() {
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
	private void cargarReservas() {
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
		ArrayList<Pelicula> peliculas = BD.obtenerPeliculas();
		
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
		ArrayList<Documental> documentales = BD.obtenerDocumentales();
		
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
	




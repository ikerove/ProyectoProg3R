package ventanas;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import baseDatos.BD;
import principal.Videoclub;
import datos.Multimedia;
import datos.Pelicula;
import datos.Serie;

public class VentanaFicha extends JFrame{
	private JPanel pSur, pCentro, pSegundaColumna;
	private JButton btnVolver, btnPagar, btnCarrito;
	private JLabel lblFoto, lblTitulo,lblPrecio;
	private JFrame ventanaAnterior;
	private JComboBox<String> comboCalidad;
	private Serie s;
	private JTextArea txtTexto;
	private String nick;
	
	
	public VentanaFicha(JLabel lblFotoSeleccionada, JFrame va)  {
		super();
		JFrame v = this;
		ventanaAnterior = va;
 		this.setSize(400,200);
 		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
 		Container cp = this.getContentPane();


 		pSur = new JPanel();
 		btnVolver = new JButton("VOLVER");
 		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				v.dispose();
				ventanaAnterior.setVisible(true);
			}
		});
		
		btnPagar= new JButton("PAGAR");
		btnPagar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//generarFactura();
				new VentanaThreadPago();
			/*	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				BD.borrarSeries();
				BD.borrarPeliculas();
				BD.borrarDocumentales();
				//DefaultListModel<Objeto> m = (DefaultListModel<Objeto>) VentanaCliente.listaDisponibles.getModel();
				//DefaultListModel<Objeto> m1 = listaDisponibles.
				for(int i=0;i<m.getSize();i++) {
					Objeto ob = m.getElementAt(i);
					if(ob instanceof Serie) {
						Serie s = (Serie)ob;
						
						//BD.insertarSerie(s.getCodigo(), s.getTitulo(),s.getDirector(),s.getGenero(),s.getDuracion(),s.getDistribuidora(),s.getFecha(),s.getCalificacion(),s.getFormato(),s.getTemporadas(),s.getCapitulos(),s.getDuracionCap(),s.getRutaFoto());
						BD.insertarSerie(s.getCodigo(), s.getTitulo(),s.getDirector(),s.getGenero(),s.getDuracion(),s.getDistribuidora(),sdf.format(s.getFecha()),s.getCalificacion(),s.getFormato(),s.getTemporadas(),s.getCapitulos(),s.getDuracionCap(),s.getRutaFoto());
					}else if(ob instanceof Pelicula) {
						Pelicula p = (Pelicula)ob;
						BD.insertarPelicula(p.getCodigo(), p.getTitulo(),p.getDirector(),p.getGenero(),p.getDuracion(),p.getDistribuidora(),sdf.format(p.getFecha()),p.getCalificacion(), p.getGuion(),p.getMusica(),p.isOscars(), p.getRutaFoto());
					}
				}
			/*	DefaultListModel<Objeto> ms = (DefaultListModel<Objeto>) VentanaCliente.listaFavoritos.getModel();
				for(int i=0;i<ms.getSize();i++) {
					Objeto ob = ms.getElementAt(i);
					int unidades = 1;
					if(ob instanceof Libro)
						unidades = ((Libro)ob).getUnidades();
					Date actual = new Date(System.currentTimeMillis());
					BD.actualizarHistorico(VentanaPrincipal.nick, ob.getCodigo(), unidades, sdf.format(actual));
				}
				txtTexto.setText("");
				Biblioteca.vaciarCarrito(nick);
				new VentanaThreadPago();*/
			}
		});
		btnCarrito = new JButton("Añadir al carrito");
		btnCarrito.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		pSur.add(btnVolver);
 		pSur.add(btnPagar);		
 		pSur.add(btnCarrito);
 		pCentro = new JPanel(new GridLayout(1, 2));
 		//lblFoto = lblFotoSeleccionada;
 		lblFoto = new JLabel();
 		//pCentro.add(lblFoto);

 		pSegundaColumna = new JPanel(new GridLayout(4,1));
		//lblTitulo = new JLabel(s.getTitulo());

 		//lblPrecio = new JLabel(BD.obtenerSerie(lblFoto.toString()).getTitulo());

 		ImageIcon imagen = (ImageIcon) lblFotoSeleccionada.getIcon();
 		lblFoto.setIcon(imagen);
 		String datosSerie = BD.obtenerSerie2(imagen.getDescription());
 		String datosPelicula = BD.obtenerPelicula2(imagen.getDescription());
 		String datosDocumental = BD.obtenerDocumental2(imagen.getDescription());
 		if(imagen.getDescription().contains("serie")) {
 			posicionaLinea(pCentro,datosSerie, lblFoto);
 		}else if(imagen.getDescription().contains("pelicula")) {
 			posicionaLinea(pCentro,datosPelicula, lblFoto);
 		}else if(imagen.getDescription().contains("documental")) {
 			posicionaLinea(pCentro,datosDocumental, lblFoto);
 		}
 		

 		//pCentro.add(lblFoto);
 		//lblTitulo = new JLabel(BD.obtenerSerie("a"));


 		//String items[] = {"720p","1080p","4k"};
 		//String items[] = {"720p","1080p","4k"};
 		//comboCalidad = new JComboBox<String>(items);
 		//pSegundaColumna.add(lblTitulo);
 		//pSegundaColumna.add(imagen, BorderLayout.NORTH);
 		//pSegundaColumna.add(lblPrecio);
 		//pSegundaColumna.add(comboCalidad);
 		//pCentro.add(pSegundaColumna);
 		//pSegundaColumna.add(comboCalidad);
 		//pCentro.add(pSegundaColumna);
 		cp.add(pSur, BorderLayout.SOUTH);
 		cp.add(pCentro, BorderLayout.CENTER);



 		this.setVisible(true);
 	}

	
	
	private String crearTexto() {
		double total = 0;
		String texto = "";
		for(Multimedia sel: Videoclub.obtenerComprasCliente(nick)) {
			texto = texto + sel.getTexto() + "\r\n";
			if(sel instanceof Serie) {
				total = total + 5;
			}
		}
		texto = texto + "TOTAL A PAGAR: "+ total+ " €";
		return texto;
	}
	
	private void generarFactura() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaActual = new Date(System.currentTimeMillis());
		PrintWriter pw = null;
		try {
			//Creamos un fichero de texto con el nick del cliente y la fecha actual
			pw = new PrintWriter(VentanaUsuario.nick+" "+sdf.format(fechaActual)+".txt");
			//pw.println(crearTexto());
			pw.println();
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pw!=null) {
				pw.flush();
				pw.close();
			}
		}
	}
	
	private void cargarCarrito() {
		/*double total = 0;
		String texto = "";
		for(Objeto sel: Biblioteca.obtenerComprasCliente(nick)) {
			texto = texto + sel.getTexto() + "\n";
			if(sel instanceof Libro) {
				total = total + ((Libro)sel).getPrecio() * ((Libro)sel).getUnidades();
			}
		}
		texto = texto + "TOTAL A PAGAR: "+ total+ " €";
 		txtTexto.setText(texto);*/
 		txtTexto.setText(crearTexto());
 	}

 	private void posicionaLinea(Container cont, String etiqueta, Component campo) {
 		JPanel tempPanel = new JPanel();
 		tempPanel.setOpaque(false);
 		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // flow ajustado a la izquierda
 		etiqueta = "<html><body>" + etiqueta;
 		etiqueta = etiqueta.replaceAll("\n", "<br>");
 		etiqueta = etiqueta + "</body></html>";
 		JLabel l = new JLabel(etiqueta);
 		//l.setPreferredSize(new Dimension(250, 50));
 		l.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
 		l.setForeground(Color.BLACK);
 		tempPanel.add(l);
 		tempPanel.add(campo);
 		cont.add(tempPanel);
 	}
 }

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
	private JButton btnVolver, btnPagar;
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
		this.setSize(467,334);
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
				generarFactura();
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
		
		
		
		pSur.add(btnVolver);
		pSur.add(btnPagar);		
		pCentro = new JPanel();
		//lblFoto = lblFotoSeleccionada;
		lblFoto = new JLabel();
		//pCentro.add(lblFoto);
		
		//pSegundaColumna = new JPanel(new GridLayout(4,1));
		//lblTitulo = new JLabel(s.getTitulo());


		//lblPrecio = new JLabel(BD.obtenerSerie(lblFoto.toString()).getTitulo());

		ImageIcon imagen = (ImageIcon) lblFotoSeleccionada.getIcon();
		lblFoto.setIcon(imagen);
		String datos = BD.obtenerPelicula2(imagen.getDescription());
		if(imagen.getDescription().contains("serie")) {
			
		}else if(imagen.getDescription().contains("pelicula")) {
			posicionaLinea(pCentro,datos, lblFoto);
			
		}else if(imagen.getDescription().contains("documental")){
			
		}
		
		
		
		//pCentro.add(lblFoto);
		//lblTitulo = new JLabel(BD.obtenerSerie("a"));

		//String items[] = {"720p","1080p","4k"};
		//comboCalidad = new JComboBox<String>(items);
		//pSegundaColumna.add(lblTitulo);
		//pSegundaColumna.add(imagen, BorderLayout.NORTH);
		//pSegundaColumna.add(lblPrecio);
		//pSegundaColumna.add(comboCalidad);
		//pCentro.add(pSegundaColumna);
		cp.add(pSur, BorderLayout.SOUTH);
		cp.add(pCentro, BorderLayout.CENTER);
		pCentro.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(datos);
		lblNewLabel.setBounds(40, 23, 83, 24);
		pCentro.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(40, 59, 61, 16);
		pCentro.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(40, 87, 61, 16);
		pCentro.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(40, 115, 61, 16);
		pCentro.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(40, 143, 61, 16);
		pCentro.add(lblNewLabel_4);
		
		
		
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

			pw.println(crearTexto());
	
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
		//tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // flow ajustado a la izquierda
		JLabel l = new JLabel(etiqueta);
		//l.setPreferredSize(new Dimension(200, 250));
		l.setSize(200, 200);
		l.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		l.setForeground(Color.BLACK);
		tempPanel.add(l);
		tempPanel.add(campo);
		cont.add(tempPanel, BorderLayout.WEST);
	}
}

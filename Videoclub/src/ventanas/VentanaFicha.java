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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import baseDatos.BD;
import baseDatos.BDException;
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
	private static Logger logger = Logger.getLogger( Videoclub.class.getName() );

	
	//public VentanaFicha(JLabel lblFotoSeleccionada, JFrame va) throws BDException  {
	private String tipo_venta;
	private String codigo_venta;
	private String usuario;
	private String ruta;
	

	
	//public VentanaFicha(JLabel lblFotoSeleccionada, JFrame va) throws BDException  {
	public VentanaFicha(JLabel lblFotoSeleccionada, JFrame va, String tipo_venta, String codigo_venta, 
						String usuario, String ruta) throws BDException  {
		super();
		this.tipo_venta = tipo_venta;
		this.codigo_venta = codigo_venta;
		this.usuario = usuario;
		this.ruta = ruta;
	
		
		JFrame v = this;
		ventanaAnterior = va;
 		this.setSize(500,200);
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
		
		
		pSur.add(btnVolver);

 		
 		if (!tipo_venta.equals("Ventas")) {
 		
			btnPagar= new JButton("PAGAR");
			btnPagar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//generarFactura();
					new VentanaThreadPago();
				
					
					SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				    String dateFormatted = fmt.format(new Date());
	
					try {
						BD.insertarVenta(Integer.parseInt(codigo_venta), tipo_venta, dateFormatted, usuario, ruta);
						logger.log(Level.INFO, "Venta registrada correctamente");
					} catch (Exception e1) {
						logger.log(Level.SEVERE, "Venta erronea: "+e1.getMessage());
						e1.printStackTrace();
					}
					
					ventanaAnterior.setVisible(true);
					
				}
			});
			
	 		pSur.add(btnPagar);	
 		}
		
		
	
 		
 		pCentro = new JPanel(new GridLayout(1, 2));
 		lblFoto = new JLabel();


 		pSegundaColumna = new JPanel(new GridLayout(4,1));


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
		texto = texto + "TOTAL A PAGAR: "+ total+ " �";
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
		logger.log(Level.INFO,"Factura realizada");
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
		texto = texto + "TOTAL A PAGAR: "+ total+ " �";
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
 		logger.log(Level.INFO,"Posicion lineal obtenida");
 	}
 }
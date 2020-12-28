package ventanas;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
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
		pCentro = new JPanel(new GridLayout(1, 2));
		//lblFoto = lblFotoSeleccionada;
		//pCentro.add(lblFoto);
		
		pSegundaColumna = new JPanel(new GridLayout(4,1));
		//lblTitulo = new JLabel(s.getTitulo());


		//lblPrecio = new JLabel(BD.obtenerSerie(lblFoto.toString()).getTitulo());



		String items[] = {"720p","1080p","4k"};
		comboCalidad = new JComboBox<String>(items);
		//pSegundaColumna.add(lblTitulo);
		//pSegundaColumna.add(lblPrecio);
		pSegundaColumna.add(comboCalidad);
		pCentro.add(pSegundaColumna);
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
}

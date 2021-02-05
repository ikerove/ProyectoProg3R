package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import baseDatos.BD;
import baseDatos.BDException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class PanelPelicula extends JPanel{
	JPanel panel;
	JTextField txtCodigo, txtTitulo,txtDirector,txtGenero,txtDuracion,txtDistribuidora, txtFecha, txtCalificacion,txtGuion,txtMusica, txtRutaFoto,txtTiempoReserva;
	JSpinner unidades;
	JCheckBox txtOscars;
	
	JButton btnAniadir;
	
	public PanelPelicula() {
		
		panel = this;
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		
		txtCodigo = new JTextField();
		txtCodigo.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce el codigo: ", txtCodigo);
		
		txtTitulo = new JTextField();
		txtTitulo.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce el titulo: ", txtTitulo);
		
	
		txtDirector = new JTextField();
		txtDirector.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce el director: ", txtDirector);
		
		txtGenero = new JTextField();
		txtGenero.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce el genero: ", txtGenero);
		
		txtDuracion = new JTextField();
		txtDuracion.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce la duracion: ", txtDuracion);
		
		txtDistribuidora = new JTextField();
		txtDistribuidora.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce la distribuidora: ", txtDistribuidora);
		
		txtFecha = new JTextField();
		txtFecha.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce la fecha: ", txtFecha);
		
		txtCalificacion = new JTextField();
		txtCalificacion.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce la calificacion: ", txtCalificacion);
		
		txtGuion = new JTextField();
		txtGuion.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce los guionistas: ", txtGuion);
		
		txtMusica = new JTextField();
		txtMusica.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce el compositor: ", txtMusica);
		
		txtOscars = new JCheckBox();
		txtOscars.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Tiene oscars: ", txtOscars);
		
		
		txtRutaFoto = new JTextField();
		txtRutaFoto.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce la ruta de la foto: ", txtRutaFoto);
		
		txtTiempoReserva = new JTextField();
		txtTiempoReserva.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Tiempo de reserva restante: ", txtTiempoReserva);
		
		
		
		btnAniadir = new JButton("AÑADIR");
		posicionaLinea(panel, "", btnAniadir);
		
		
		//EVENTOS
		/**
		 * Añade los parametros insertados en cada texfield a la tabla pelicula de la base de datos
		 */
		btnAniadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//SpinnerNumberModel snm = (SpinnerNumberModel) unidades.getModel();
				boolean oscars = false;
				if(txtOscars.isSelected()) {
					oscars = true;
				}
				try {
					BD.insertarPelicula(Integer.parseInt(txtCodigo.getText()), txtTitulo.getText(),  txtDirector.getText(), txtGenero.getText(),Integer.parseInt(txtDuracion.getText()),txtDistribuidora.getText(),txtFecha.getText(),txtCalificacion.getText(),txtGuion.getText(),txtMusica.getText(),oscars, txtRutaFoto.getText(),Float.parseFloat(txtTiempoReserva.getText()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}

	private void posicionaLinea(Container cont, String etiqueta, Component campo) {
		
		JPanel tempPanel = new JPanel();
		tempPanel.setBounds(0, 0, 450, 60);
		tempPanel.setOpaque(false);
		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // flow ajustado a la izquierda
		JLabel l = new JLabel(etiqueta);
		l.setPreferredSize(new Dimension(300, 50));
		l.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		l.setForeground(Color.WHITE);
		tempPanel.add(l);
		tempPanel.add(campo);
		cont.add(tempPanel);
	}
}

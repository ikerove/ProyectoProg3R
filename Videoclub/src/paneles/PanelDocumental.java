package paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import baseDatos.BD;
import baseDatos.BDException;

public class PanelDocumental extends JPanel{
	JPanel panel;
	JTextField txtCodigo, txtTitulo,txtDirector,txtGenero,txtDuracion,txtDistribuidora, txtFecha, txtCalificacion, txtRutaFoto;
	JSpinner unidades;
	JCheckBox txtAnimales;
	
	
	JButton btnAniadir;
	
	public PanelDocumental() {
		
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
		
		txtAnimales = new JCheckBox();
		txtAnimales.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce si es de  animales: ", txtAnimales);
		
		txtRutaFoto = new JTextField();
		txtRutaFoto.setPreferredSize(new Dimension(100, 50));
		posicionaLinea(panel, "Introduce la ruta de la foto: ", txtRutaFoto);
		
		

		
		btnAniadir = new JButton("AÑADIR");
		posicionaLinea(panel, "", btnAniadir);
		
		
		//EVENTOS
		btnAniadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				boolean animales = false;
				if(txtAnimales.isSelected()) {
					animales = true;
				}
				try {
					BD.insertarDocumental(Integer.parseInt(txtCodigo.getText()), txtTitulo.getText(),  txtDirector.getText(), txtGenero.getText(),Integer.parseInt(txtDuracion.getText()),txtDistribuidora.getText(),txtFecha.getText(),txtCalificacion.getText(),animales, txtRutaFoto.getText());
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

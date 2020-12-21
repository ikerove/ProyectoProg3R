package ventanas;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaThreadPago extends JFrame{
	
	private JLabel lblTexto;
	private JPanel pNorte, pCentro;
	
	public VentanaThreadPago() {
		super();
		setResizable(false);
	    setAlwaysOnTop(true); //Siempre por encima del resto de ventanas (mientras esté visible)
	    setBounds(350, 300, 800, 200);
	    setTitle("Procesando pago");
	    
	    //CREACIÓN DE PANELES
	    pNorte = new JPanel();
	    pCentro = new JPanel();
	    getContentPane().add(pNorte, BorderLayout.NORTH);
	    getContentPane().add(pCentro, BorderLayout.CENTER);
	    
	    //CREACIÓN DE COMPONENTES
	    lblTexto = new JLabel("Este proceso puede tardar unos segundos. Disculpe la espera.");
	    lblTexto.setFont(new Font("Courier New", Font.BOLD, 16));
	    pNorte.add(lblTexto);
	    
	    //CREACIÓN DEL THREAD
	    Runnable r = new Runnable() {
			
			@Override
			public void run() {
				// Aquí es donde escribimos todo lo que queremos que haga el thread
				for(int i=0;i<10;i++) {
					try {
						pCentro.add(new JLabel(new ImageIcon("imagenes/dolar.jpg")));
						pCentro.updateUI();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//Cerramos todas las ventanas excepto la de inicio
				Window [] ventanas = Window.getWindows();
				for(int i=1;i<ventanas.length;i++)
					ventanas[i].dispose();
				
			}
		};
		
		Thread t = new Thread(r);
		t.start();
		
		setVisible(true);
	}
}

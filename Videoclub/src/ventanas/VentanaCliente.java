package ventanas;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import baseDatos.BD;
import datos.Multimedia;
import datos.Pelicula;
import datos.Serie;
import datos.Usuario;
import principal.Videoclub;

public class VentanaCliente extends JFrame{
	
	private JPanel pCentral, pIzquierda, pDerecha, pBotonera;
    private JList<Multimedia> listaDisponibles, listaFavoritos;
    private DefaultListModel<Multimedia> modeloDisponibles, modeloFavoritos;
    private JButton btnAniadir, btnEliminar, btnGuardar;
    private Usuario usuario = BD.obtenerUsuario();
    private String nick;

public VentanaCliente(String nick) {
	super();
	this.nick = nick;
	int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    setSize(ancho,alto);
    setExtendedState(MAXIMIZED_BOTH); //Maximizar la ventana
    //setResizable(false);
    //setAlwaysOnTop(true); //Siempre por encima del resto de ventanas (mientras esté visible)
    setTitle("CLIENTES");
    //setIconImage(new ImageIcon("imagenes/newton.jpg").getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
   // setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    
    //CREACIÓN PANELES
    pCentral = new JPanel(new GridLayout(1,2));
    //pIzquierda = new JPanel();
    //pDerecha = new JPanel();
    //pCentral.add(pIzquierda);
    //pCentral.add(pDerecha);
    pBotonera = new JPanel();
    
    getContentPane().add(pCentral, BorderLayout.CENTER);
    getContentPane().add(pBotonera, BorderLayout.SOUTH);
    
    //CREACIÓN DE LOS COMPONENTES
    modeloDisponibles = new DefaultListModel<>();
    modeloFavoritos = new DefaultListModel<>();
    listaDisponibles = new JList<>(modeloDisponibles);
    listaFavoritos = new JList<>(modeloFavoritos);
    JScrollPane scrollDisponibles = new JScrollPane(listaDisponibles);
    JScrollPane scrollSeleccionados = new JScrollPane(listaFavoritos);
    scrollDisponibles.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollDisponibles.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollSeleccionados.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollSeleccionados.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    pCentral.add(scrollDisponibles);
    pCentral.add(scrollSeleccionados);
    
    btnAniadir = new JButton("Aniadir a favoritos");
    btnEliminar = new JButton("Eliminar de favoritos");
    btnGuardar = new JButton("Guardar favoritos");
    pBotonera.add(btnAniadir);
    pBotonera.add(btnEliminar);
    pBotonera.add(btnGuardar);
    cargarListaDisponibles();
    
    //EVENTOS
    btnAniadir.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int pos = listaDisponibles.getSelectedIndex();
			if(pos!=-1) {
				modeloDisponibles = (DefaultListModel<Multimedia>) listaDisponibles.getModel();
				modeloFavoritos = (DefaultListModel<Multimedia>) listaFavoritos.getModel();
				Multimedia sel = modeloDisponibles.getElementAt(pos);
				if(sel instanceof Serie) {
					
					modeloFavoritos.addElement(sel);
					modeloDisponibles.removeElementAt(pos);
                }else if(sel instanceof Pelicula){
                    modeloFavoritos.addElement(sel);
					modeloDisponibles.removeElementAt(pos);
                }else{
                    modeloFavoritos.addElement(sel);
					modeloDisponibles.removeElementAt(pos);
                }
				listaDisponibles.setModel(modeloDisponibles);
				listaFavoritos.setModel(modeloFavoritos);
			}
		}
		
	});
    
    setVisible(true);

    btnEliminar.addActionListener(new ActionListener() {
			
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            int pos = listaFavoritos.getSelectedIndex();
            if(pos!=-1) {//El usuario ha seleccionado un producto
                modeloDisponibles = (DefaultListModel<Multimedia>) listaDisponibles.getModel();
                modeloFavoritos = (DefaultListModel<Multimedia>) listaFavoritos.getModel();
                Multimedia sel = modeloFavoritos.getElementAt(pos);
                if(sel instanceof Serie) {
                   /* Libro lSel = (Libro)sel;
                    int p = 0;
                    boolean enc = false;
                    Libro l = null;
                    while(!enc && p<modeloDisponibles.size()) {
                        l = (Libro) modeloDisponibles.getElementAt(p);
                        if(l.getISBN().equals(lSel.getISBN()))
                            enc = true;
                        else
                            p++;
                    }
                    if(enc) {
                        l.setUnidades(l.getUnidades()+1);
                        modeloDisponibles.setElementAt(l, p);
                    }*/
                    modeloDisponibles.addElement(sel);
                    modeloFavoritos.removeElementAt(pos);
                }else {
                    modeloDisponibles.addElement(sel);
                    modeloFavoritos.removeElementAt(pos);
                }
                listaDisponibles.setModel(modeloDisponibles);
                listaFavoritos.setModel(modeloFavoritos);
            }
        }
    });



    btnGuardar.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            // TODO Auto-generated method stub
            modeloFavoritos = (DefaultListModel<Multimedia>) listaFavoritos.getModel();
            Multimedia sel = null;
            Multimedia cont = null;
				for(int i=0;i<modeloFavoritos.size();i++) {
					 sel = modeloFavoritos.getElementAt(i);
                    //Videoclub.aniadirAFavoritos(nick, sel);
                    cont = sel;
                }
                //guardarJuegoEnFichero2(nick, cont);
                guardarJuegoEnFicheroTexto(usuario, modeloFavoritos);
        }
    });
}


//ESTO ES LO DE LOS COLORES

/*listaDisponibles.setCellRenderer(new DefaultListCellRenderer() {
	public Component getListCellRendererComponent(JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		if(value instanceof Serie)
			if(((Libro)value).getUnidades()==0)
				c.setBackground(Color.RED);
		return c;
	}
});*/
private void cargarListaDisponibles() {
	ArrayList<Multimedia> objetos = BD.obtenerObjetos();
	for(Multimedia ob: objetos)
		modeloDisponibles.addElement(ob);
	listaDisponibles.setModel(modeloDisponibles);
}

public static void guardarJuegoEnFicheroBinario(String nick, Multimedia sel) {
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    try {
        fos = new FileOutputStream("juego.dat");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(nick);
        oos.writeObject(sel);
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } finally {
        if(oos!=null) {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
    
}

public static void guardarJuegoEnFicheroTexto(Usuario usuario,/* Objeto sel*/DefaultListModel modeloFavoritos) {
    PrintWriter pw = null;
		try {
			//Creamos un fichero de texto con el nick del cliente y la fecha actual
			pw = new PrintWriter("favoritos.txt");

            pw.println(usuario);
            //pw.println(sel);
            pw.println(modeloFavoritos);
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
}

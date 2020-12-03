package ventanas;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaMain extends JFrame{
	private JPanel panCentro ;
	private JScrollPane panScrol;
	private JMenuBar menuBar;
	private JMenu menuS, menuP, menuD;
	private JMenuItem mi1, mi2, mi3;
	private JFrame v;
	
	
	
	public VentanaMain() {
		this.setSize(400,400);
		this.setTitle("Videoclub");
		setVisible(true);
	}

}

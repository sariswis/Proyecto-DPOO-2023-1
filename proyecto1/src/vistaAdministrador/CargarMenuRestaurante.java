package vistaAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import catalogo.Restaurante;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class CargarMenuRestaurante extends JPanel {
	private VentanaAdministrador ventana;
	//botones y demas//
	private JTextField rutaMenu;
	private JButton enviar;
	private JPanel menu;
	private JScrollPane scroll;

	public CargarMenuRestaurante(VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
		this.rutaMenu=VentanaInicio.crearTextField();
		rutaMenu.setText(Restaurante.getMenu());
		this.enviar=VentanaInicio.crearBoton("Enviar");
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String[] textos = ventana.cargarMenuRestaurante();
	        	for(String texto: textos) {
	        		menu.add(VentanaInicio.crearMensaje(texto));
	        	}
	        	scroll = new JScrollPane(menu);
	    		add(scroll);
	    		add(Box.createVerticalStrut(10));
	    		revalidate();
	    		repaint();
	        }
	    }); 
		
		this.menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

		add(new JLabel("Ruta menú restaurante"));
		add(Box.createVerticalStrut(10));
		add(rutaMenu);
		add(Box.createVerticalStrut(10));
		add(enviar);
		add(Box.createVerticalStrut(10));
	}
}

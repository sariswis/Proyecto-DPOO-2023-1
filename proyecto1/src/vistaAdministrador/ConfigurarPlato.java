package vistaAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vista.VentanaInicio;

@SuppressWarnings("serial")
public class ConfigurarPlato extends JPanel {
	private VentanaAdministrador ventana;
	//Botones y demas//
	private JTextField idProducto;
	private JFormattedTextField nuevoPrecio;
	private JRadioButton servicioCuarto;
	private JButton enviar;
	private JLabel mensaje;
	private JPanel menu;
	private JScrollPane scroll;
	
	public ConfigurarPlato(VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana=ventana;
		
		this.idProducto = VentanaInicio.crearTextField();
		this.nuevoPrecio = VentanaInicio.crearNumberField();
		this.servicioCuarto = new JRadioButton("Servicio al cuarto");
		
		this.enviar=VentanaInicio.crearBoton("Enviar");
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String idSeleccionado = idProducto.getText();
	        	int tarifaSeleccionado = Integer.parseInt(nuevoPrecio.getText());
	        	boolean servicioSeleccionado = servicioCuarto.isSelected();
	        	String respuesta = ventana.configurarPlato(idSeleccionado, tarifaSeleccionado, servicioSeleccionado);
                mensaje.setOpaque(true);
                mensaje.setText(respuesta);
                
                menu.removeAll();
            	String[] textos = ventana.cargarMenuRestaurante();
            	for(String texto: textos) {
            		menu.add(VentanaInicio.crearMensaje(texto));
            	}
                revalidate();
                repaint();
	        }
	    }); 
		
		mensaje = VentanaInicio.crearMensaje("");
		this.menu= new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
    	String[] textos = ventana.cargarMenuRestaurante();
    	for(String texto: textos) {
    		menu.add(VentanaInicio.crearMensaje(texto));
    	}
    	scroll = new JScrollPane(menu);

		add(new JLabel("Id plato"));
		add(Box.createVerticalStrut(10));
		add(idProducto);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Precio"));
		add(Box.createVerticalStrut(10));
		add(nuevoPrecio);
		add(Box.createVerticalStrut(10));
		add(servicioCuarto);
		add(Box.createVerticalStrut(10));
		add(enviar);
		add(Box.createVerticalStrut(10));
		add(scroll);
		add(Box.createVerticalStrut(10));
		add(mensaje);
		add(Box.createVerticalStrut(10));
	}
	

}

package vistaAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import inventario.Inventario;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class CargarHabitaciones extends JPanel{
	private VentanaAdministrador ventana;
	//Botones y demas//
	private JTextField rutaInventario;
	private JTextField rutaCamas;
	private JButton enviar;
	private JLabel mensaje;
	
	public CargarHabitaciones(VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
		this.rutaInventario=VentanaInicio.crearTextField();
		rutaInventario.setText(Inventario.getInventario());
		this.rutaCamas=VentanaInicio.crearTextField();
		rutaCamas.setText(Inventario.getCamas());
		this.enviar=VentanaInicio.crearBoton("Enviar");
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String texto = ventana.cargarHabitaciones();
	        	mensaje.setOpaque(true);
	        	mensaje.setText(texto);
	    		revalidate();
	    		repaint();
	        }
	    }); 
		mensaje = VentanaInicio.crearMensaje("");

		add(new JLabel("Ruta inventario"));
		add(Box.createVerticalStrut(10));
		add(rutaInventario);
		add(Box.createVerticalStrut(10));
		add(new JLabel("Ruta camas"));
		add(Box.createVerticalStrut(10));
		add(rutaCamas);
		add(Box.createVerticalStrut(10));
		add(enviar);
		add(Box.createVerticalStrut(10));
		add(mensaje);
		add(Box.createVerticalStrut(10));
	}
	
	

}

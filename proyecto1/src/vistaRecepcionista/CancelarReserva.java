package vistaRecepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vista.VentanaInicio;


@SuppressWarnings("serial")
public class CancelarReserva extends JPanel {
	private VentanaRecepcionista ventana;
	private JTextField idHabitacion;
	private JButton cancelar;
	
	public CancelarReserva(VentanaRecepcionista ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
		idHabitacion  = VentanaInicio.crearTextField();
	    cancelar = VentanaInicio.crearBoton("Cancelar");
	    cancelar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                ventana.cancelarReserva(idHabitacion.getText());
	            	JOptionPane.showMessageDialog(null, "Se eliminó con éxito la reseva", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	            } catch (Exception e1) {
	            	JOptionPane.showMessageDialog(null, e1.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    
		add(new JLabel("Id reserva: "));
	    add(Box.createVerticalStrut(10));	
	    add(idHabitacion);
	    add(Box.createVerticalStrut(10));	
	    add(cancelar);
	}
	
	
}

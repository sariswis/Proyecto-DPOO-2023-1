package vistaAdministrador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import inventario.Tipo;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class CargarCama extends JPanel {
	private VentanaAdministrador ventana;
	//Botones y demas//
	private JTextField idHabitacion;
	private JComboBox<Tipo> tipoHabitacion;
	private JTextField tamaño;
	private JFormattedTextField personas;
	private JRadioButton niños;
	private JButton enviar;
	private JLabel mensajeFinal;
	
	
	public CargarCama(VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana=ventana;
		this.mensajeFinal = VentanaInicio.crearMensaje("");
		this.idHabitacion=VentanaInicio.crearTextField();
		this.tamaño=VentanaInicio.crearTextField();
		this.personas=VentanaInicio.crearNumberField();
		this.niños=new JRadioButton();
		this.tipoHabitacion= new JComboBox<Tipo>();
		tipoHabitacion.addItem(Tipo.ESTANDAR);
		tipoHabitacion.addItem(Tipo.SUITE);
		tipoHabitacion.addItem(Tipo.SUITE_DOBLE);
		Dimension preferredSize = tipoHabitacion.getPreferredSize();
	    preferredSize.width = 200; // Establecer el ancho deseado
	    tipoHabitacion.setPreferredSize(preferredSize);
	    tipoHabitacion.setMinimumSize(preferredSize);
	    tipoHabitacion.setMaximumSize(preferredSize);
		this.enviar=VentanaInicio.crearBoton("Enviar");
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String idSeleccionado=idHabitacion.getText();
	        	Tipo tipoSeleccionado = (Tipo) tipoHabitacion.getSelectedItem();
	        	String tamañoSeleccionado= tamaño.getText();
	        	int capacidadSeleccionado=Integer.parseInt(personas.getText());
	        	boolean niñosSeleccionado=niños.isSelected();
	        	String texto=ventana.cargarCama(idSeleccionado, tipoSeleccionado, tamañoSeleccionado, capacidadSeleccionado, niñosSeleccionado);
	        	mensajeFinal.setOpaque(true);
	            mensajeFinal.setText(texto);
	            revalidate();
	            repaint();;;;
	        }
	    }); 
		add(new JLabel("¿Cual es el id de la habitación?"));
		add(Box.createVerticalStrut(10));
		add(idHabitacion);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es el tipo de habitación?"));
		add(Box.createVerticalStrut(10));
		add(tipoHabitacion);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es el tamaño de la cama?"));
		add(Box.createVerticalStrut(10));
		add(tamaño);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es el la capacidad de la cama?"));
		add(Box.createVerticalStrut(10));
		add(personas);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿La cama solo para niños?"));
		add(Box.createVerticalStrut(10));
		add(niños);
		add(Box.createVerticalStrut(10));
		add(enviar);
		add(mensajeFinal);
	}
}

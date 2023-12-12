package vistaRecepcionista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import inventario.Tipo;
import vista.VentanaInicio;


@SuppressWarnings("serial")
public class ConsultarInventario extends JPanel {
	private VentanaRecepcionista ventana;
	private JComboBox<Tipo> rol;
	private JButton enviarTipo;
	private JScrollPane scroll;
	private VentanaHabitacion habitacion;
	
	public ConsultarInventario(VentanaRecepcionista ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
	    JComboBox<Tipo> rol = new JComboBox<Tipo>();
	    rol.addItem(Tipo.ESTANDAR);
	    rol.addItem(Tipo.SUITE);
	    rol.addItem(Tipo.SUITE_DOBLE);

	    Dimension preferredSize = rol.getPreferredSize();
	    preferredSize.width = 200;
	    rol.setPreferredSize(preferredSize);
	    rol.setMinimumSize(preferredSize);
	    rol.setMaximumSize(preferredSize);
	    
	    this.enviarTipo = VentanaInicio.crearBoton("Enviar");
	    enviarTipo.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	    	    Tipo opcionSeleccionada = (Tipo) rol.getSelectedItem();
	        	listarHabitaciones(opcionSeleccionada);
	        }
	    });   
	    
	    scroll = new JScrollPane();
	    add(new JLabel("Seleccione el tipo de habitación a consultar"));
	    add(Box.createVerticalStrut(10));
	    add(rol);
	    add(Box.createVerticalStrut(10));
	    add(enviarTipo);
	    add(Box.createVerticalStrut(10));
		add(scroll);
	}
	
	public void listarHabitaciones(Tipo tipo) {
		String[] mensaje = ventana.consultarInventario(tipo);
		
		JList<String> lista = new JList<String>(mensaje);
	    lista.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent evt) {
	                String selectedElement = (String) lista.getSelectedValue();
	                int inicioSubcadena = 11; // posición inicial de la subcadena
	                int finSubcadena = selectedElement.indexOf(":", inicioSubcadena); // posición final de la subcadena
	                String subcadena = selectedElement.substring(inicioSubcadena, finSubcadena);
	                
	                String[] infoHabitacion = ventana.consultarHabitacion(tipo, subcadena);
	                ArrayList<String[]> infoCamas = ventana.consultarCamas(tipo, subcadena);
	                String[] infoHotel = ventana.consultarHotel();
	                habitacion = new VentanaHabitacion(infoHabitacion, infoCamas, infoHotel);
	        }
	    });
		
	    remove(scroll);
		scroll = new JScrollPane(lista);
		add(scroll);
	    revalidate();
	    repaint();
	}
	
	
}

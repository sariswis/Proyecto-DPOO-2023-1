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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import inventario.Tipo;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class CargarHabitacion extends JPanel {
	private VentanaAdministrador ventana;

	private JPanel panel;
	private JTextField id;
	private JTextField ubicacion;
	private JComboBox<Tipo> tipo;
	private JRadioButton balcon;
	private JRadioButton vistas;
	private JRadioButton cocina;
	private JFormattedTextField tamanio;
	private JRadioButton aire;
	private JRadioButton calefaccion;
	private JRadioButton tv;
	private JRadioButton cafetera;
	private JRadioButton ropaCama;
	private JRadioButton plancha;
	private JRadioButton secador;
	private JRadioButton voltaje;
	private JRadioButton usbA;
	private JRadioButton usbC;
	private JRadioButton desayuno;
	private JButton enviar;
	private JLabel mensajeFinal;
	private JScrollPane scroll;
	
	public CargarHabitacion(VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		this.ventana=ventana;
		this.id=VentanaInicio.crearTextField();
		this.ubicacion=VentanaInicio.crearTextField();
		this.tipo=new JComboBox<Tipo>();
		tipo.addItem(Tipo.ESTANDAR);
		tipo.addItem(Tipo.SUITE);
		tipo.addItem(Tipo.SUITE_DOBLE);
		Dimension dimension = new Dimension(200, 30);
	    tipo.setMaximumSize(dimension);
	    
		this.balcon = new JRadioButton("Tiene balcón");
		this.vistas = new JRadioButton("Tiene vista");
		this.cocina = new JRadioButton("Tiene cocina integrada");
		this.tamanio = VentanaInicio.crearNumberField();
		
		this.aire = new JRadioButton("Tiene aire acondicionado");
		this.calefaccion = new JRadioButton("Tiene calefacción");
		this.tv = new JRadioButton("Tiene TV");
		this.cafetera = new JRadioButton("Tiene cafetera");
		this.ropaCama = new JRadioButton("Tiene ropa de cama y tapetes hipoalergénicos");
		this.plancha = new JRadioButton("Tiene plancha");
		this.secador = new JRadioButton("Tiene secador de cabello");
		this.voltaje = new JRadioButton("Tiene voltaje AC");
		this.usbA = new JRadioButton("Tiene tomas USB-A");
		this.usbC = new JRadioButton("Tiene tomas USB-C");
		this.desayuno = new JRadioButton("Incluye desayuno");
		
		this.enviar= VentanaInicio.crearBoton("Enviar");
		this.mensajeFinal = VentanaInicio.crearMensaje("");
		
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String idSeleccionado = id.getText();
	        	Tipo tipoSeleccionado = (Tipo) tipo.getSelectedItem();
	        	String ubicacionSeleccionado = ubicacion.getText();
	        	boolean balconSeleccionado = balcon.isSelected();
	        	boolean vistasSeleccionado = vistas.isSelected();
	        	boolean cocinaSeleccionado = cocina.isSelected();
	        	int tamanioSeleccionado = Integer.parseInt(tamanio.getText());
	        	boolean aireSeleccionado = aire.isSelected();
	        	boolean calefaccionSeleccionado = calefaccion.isSelected();
	        	boolean tvSeleccionado = tv.isSelected();
	        	boolean cafeteraSeleccionado = cafetera.isSelected();
	        	boolean ropaCamaSeleccionado = ropaCama.isSelected();
	        	boolean planchaSeleccionado = plancha.isSelected();
	        	boolean secadorSeleccionado = secador.isSelected();
	        	boolean voltajeSeleccionado = voltaje.isSelected();
	        	boolean usbASeleccionado = usbA.isSelected();
	        	boolean usbCSeleccionado = usbC.isSelected();
	        	boolean desayunoSeleccionado = desayuno.isSelected();
	        	String texto = ventana.cargarHabitacion(idSeleccionado, tipoSeleccionado, ubicacionSeleccionado, balconSeleccionado, 
	        			vistasSeleccionado, cocinaSeleccionado, tamanioSeleccionado, aireSeleccionado, calefaccionSeleccionado, tvSeleccionado, 
	        			cafeteraSeleccionado, ropaCamaSeleccionado, planchaSeleccionado, secadorSeleccionado, voltajeSeleccionado, 
						usbASeleccionado, usbCSeleccionado, desayunoSeleccionado);
	        	mensajeFinal.setOpaque(true);
	            mensajeFinal.setText(texto);
	            revalidate();
	            repaint();
	        }
	    }); 
		
		panel.add(new JLabel("Id habitación"));
		panel.add(Box.createVerticalStrut(10));
		panel.add(id);
		panel.add(Box.createVerticalStrut(10));
		panel.add(new JLabel("Ubicación"));
		panel.add(Box.createVerticalStrut(10));
		panel.add(ubicacion);
		panel.add(Box.createVerticalStrut(10));
		panel.add(new JLabel("Tipo"));
		panel.add(Box.createVerticalStrut(10));
		panel.add(tipo);
		panel.add(Box.createVerticalStrut(10));
		panel.add(balcon);
		panel.add(Box.createVerticalStrut(10));
		panel.add(vistas);
		panel.add(Box.createVerticalStrut(10));
		panel.add(cocina);
		panel.add(Box.createVerticalStrut(10));
		panel.add(new JLabel("Tamaño en metros cuadrados"));
		panel.add(Box.createVerticalStrut(10));
		panel.add(tamanio);
		panel.add(Box.createVerticalStrut(10));
		panel.add(aire);
		panel.add(Box.createVerticalStrut(10));
		panel.add(calefaccion);
		panel.add(Box.createVerticalStrut(10));
		panel.add(tv);
		panel.add(Box.createVerticalStrut(10));
		panel.add(cafetera);
		panel.add(Box.createVerticalStrut(10));
		panel.add(ropaCama);
		panel.add(Box.createVerticalStrut(10));
		panel.add(plancha);
		panel.add(Box.createVerticalStrut(10));
		panel.add(secador);
		panel.add(Box.createVerticalStrut(10));
		panel.add(voltaje);
		panel.add(Box.createVerticalStrut(10));
		panel.add(usbA);
		panel.add(Box.createVerticalStrut(10));
		panel.add(usbC);
		panel.add(Box.createVerticalStrut(10));
		panel.add(desayuno);
		panel.add(Box.createVerticalStrut(10));
		panel.add(enviar);
		panel.add(Box.createVerticalStrut(10));
		panel.add(mensajeFinal);
		
		this.scroll = new JScrollPane(panel);
		add(scroll);
	}
	
	

}

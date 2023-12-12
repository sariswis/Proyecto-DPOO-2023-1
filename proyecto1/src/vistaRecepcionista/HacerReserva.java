package vistaRecepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import huespedes.Reserva;
import inventario.Habitacion;
import inventario.Inventario;
import inventario.Tipo;
import inventario.TipoHabitacion;
import vista.VentanaInicio;


@SuppressWarnings("serial")
public class HacerReserva extends JPanel {
	private VentanaRecepcionista ventana;
	private JTextField inicio;
	private JTextField fin;
	private JFormattedTextField niniosEsperados;
	private JFormattedTextField adultosEsperados;
	private JTextField nombre;
	private JFormattedTextField documento;
	private JTextField correo;
	private JFormattedTextField celular;
	private JFormattedTextField edad;
	private JButton enviar;
	
	public HacerReserva(VentanaRecepcionista ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
	    inicio = VentanaInicio.crearTextField();
	    fin = VentanaInicio.crearTextField();
	    niniosEsperados = VentanaInicio.crearNumberField();
	    adultosEsperados = VentanaInicio.crearNumberField();
	    nombre = VentanaInicio.crearTextField();
	    documento = VentanaInicio.crearNumberField();
	    correo = VentanaInicio.crearTextField();
	    celular = VentanaInicio.crearNumberField();
	    edad = VentanaInicio.crearNumberField();	    
	    
	   enviar = VentanaInicio.crearBoton("Enviar");
	    enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String fInicial = inicio.getText();
	        	String fFinal = fin.getText();
	        	int niniosE = Integer.parseInt(niniosEsperados.getText());
	        	int adultosE = Integer.parseInt(adultosEsperados.getText());
	        	
	        	String nomb = nombre.getText();
	        	String doc = documento.getText();
	        	String corr = correo.getText();
	        	String cel = celular.getText();
	        	int ed = Integer.parseInt(edad.getText());
	        	
	        	try {
	        		Reserva nuevaReserva = ventana.crearReserva(fInicial, fFinal, adultosE, niniosE, nomb, doc, ed, corr, cel);
	        		ejecutarHacerReserva(nuevaReserva);
	        	} catch (Exception e1) {
	        		JOptionPane.showMessageDialog(null, e1.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
	        	}
	        	
	        }
	    });
	
	    add(new JLabel("Información de la reserva: "));
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Fecha inicial (YYYY-MM-DD): ej: 2023-05-10"));
	    add(Box.createVerticalStrut(10));
	    add(inicio);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Fecha final (YYYY-MM-DD): ej: 2023-05-10"));
	    add(Box.createVerticalStrut(10));
	    add(fin);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Niños esperados: "));
	    add(Box.createVerticalStrut(10));
	    add(niniosEsperados);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Adultos esperados: "));
	    add(Box.createVerticalStrut(10));
	    add(adultosEsperados);
	    add(Box.createVerticalStrut(10));

	    add(new JLabel("Información del huésped:"));
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Nombre: "));
	    add(Box.createVerticalStrut(10));
	    add(nombre);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Documento: "));
	    add(Box.createVerticalStrut(10));
	    add(documento);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Correo: "));
	    add(Box.createVerticalStrut(10));
	    add(correo);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Celular: "));
	    add(Box.createVerticalStrut(10));
	    add(celular);
	    add(Box.createVerticalStrut(10));
	    add(new JLabel("Edad: "));
	    add(Box.createVerticalStrut(10));
	    add(edad);
	    add(Box.createVerticalStrut(10));
	    add(enviar);
	}
	
	public void ejecutarHacerReserva(Reserva nueva) {
		LocalDateTime fechaInicio = nueva.getFechaInicio();
		LocalDateTime fechaFin= nueva.getFechaFin();
		int adultosFaltan = nueva.getAdultosEsperados();
		int niniosFaltan = nueva.getNiniosEsperados();
		
		JComboBox<Tipo> comboBox = new JComboBox<Tipo>();
		comboBox.addItem(Tipo.ESTANDAR);
		comboBox.addItem(Tipo.SUITE);
		comboBox.addItem(Tipo.SUITE_DOBLE);
	    JTextField idHabitacion = VentanaInicio.crearTextField();
    	try {
			while(adultosFaltan  > 0 || niniosFaltan > 0) {
				
	    		String mensaje = "";
	    		if(adultosFaltan > 0) {
	    			mensaje += "Faltan " + adultosFaltan + " adultos. ";
	    		} if (niniosFaltan > 0) {
	    			mensaje += "Faltan " + niniosFaltan + " niños.";
	    		}
	    		
	            JComponent[] formulario = new JComponent[] {
	            		new JLabel(mensaje),
	                    new JLabel("Seleccione una opción: "),
	                    comboBox,
	                    new JLabel("Id de la habitación"),
	                    idHabitacion
	           };
				
				int opcion = JOptionPane.showConfirmDialog(this, formulario, "Registrar habitación", JOptionPane.DEFAULT_OPTION);
				if (opcion == JOptionPane.OK_OPTION) {
				    String id = idHabitacion.getText();
				    Tipo tipo = (Tipo) comboBox.getSelectedItem();
					TipoHabitacion tipoHab = Inventario.getTipoHabitacion(tipo);
					Habitacion habitacion = tipoHab.getHabitacion(id);
					
					if (habitacion != null) {
						if (habitacion.getDisponibleEntreFechas(fechaInicio, fechaFin)) {
							int tarifa = tipoHab.getTarifaEntreFechas(fechaInicio.toLocalDate(), fechaFin.toLocalDate());
							habitacion.setTarifa(tarifa);
							nueva.agregarHabitacion(habitacion, tarifa);
							adultosFaltan -= habitacion.getCapacidadAdultos();
							niniosFaltan -= habitacion.getCapacidadNinios();
						} else {
							JOptionPane.showMessageDialog(null, "La habitación ya está reservada para las fechas seleccionadas", "Mensaje", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "La habitación no existe", "Mensaje", JOptionPane.ERROR_MESSAGE);
					}
					
			} else {
	            break;
			}
				
			}
			
			if(niniosFaltan < 1 && adultosFaltan < 1) {
				JOptionPane.showMessageDialog(null, "Reserva exitosa. Id: " + nueva.getId(),  "Mensaje", JOptionPane.INFORMATION_MESSAGE);
				ventana.agregarReserva(nueva);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
 
	}
	
}

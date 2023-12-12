package vistaRecepcionista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import huespedes.Grupo;
import huespedes.Huesped;
import huespedes.Reserva;
import inventario.Inventario;
import vista.VentanaInicio;


@SuppressWarnings("serial")
public class HacerCheckIn extends JPanel {
	private VentanaRecepcionista ventana;
	private JFormattedTextField idReserva;
	private JButton buscar;
	private JButton continuar;
	private JTextField nombre;
	private JFormattedTextField documento;
	private JFormattedTextField edad;
	
	public HacerCheckIn(VentanaRecepcionista ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
		idReserva  = VentanaInicio.crearNumberField();
		
		buscar = VentanaInicio.crearBoton("Buscar");
		buscar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
    			try {
		        	if(!Inventario.getTieneGrupo(idReserva.getText())) {
		    				Reserva reserva = Inventario.getReserva(idReserva.getText());
		    				Grupo nuevo = new Grupo(reserva);
		    				Huesped aCargo = reserva.getACargo();
		    				
		    				continuar = VentanaInicio.crearBoton("Continuar");
		    				continuar.addActionListener(new ActionListener() {
			      		        @Override
			      		        public void actionPerformed(ActionEvent e) {
			      		        	try {
										checkIn(idReserva.getText(), nuevo,  reserva, aCargo); 	
			      		        	} catch (Exception e1) {
			      		        		JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			      		        	}
			      		        }
			      		    });   	
		    				
		    				add(Box.createVerticalStrut(10));
		    				add(new JLabel("Información huésped:"));
		    				add(Box.createVerticalStrut(10));
		    				for(String info: aCargo.getInfo()) {
			    				add(new JLabel(info));
		      			    }
		      			    add(Box.createVerticalStrut(10));
		      			    add(continuar);
		      			    add(Box.createVerticalStrut(10));
		      			    revalidate();
		      			  	repaint(); 
		    		} else {
	    				JOptionPane.showMessageDialog(null, "Ya se hizo check in" , "Error", JOptionPane.ERROR_MESSAGE);
		    		}
    			} catch (Exception e1) {
    				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    			}
	        }
	    });   	

	    add(new JLabel("Id de la reserva: "));
	    add(Box.createVerticalStrut(10));
	    add(idReserva);
	    add(Box.createVerticalStrut(10));	
	    add(buscar);
	    add(Box.createVerticalStrut(10));
	}
	 	
	public void checkIn(String id, Grupo nuevo, Reserva reserva, Huesped aCargo ) throws Exception {
		boolean exitoso = true;
		nuevo.agregarHuesped(aCargo);
   		removeAll();

		for(int i = 2; i <= reserva.getAdultosEsperados(); i++) {
			nombre = VentanaInicio.crearTextField();
			documento = VentanaInicio.crearNumberField();
			edad = VentanaInicio.crearNumberField();
			
			JComponent[] formulario = new JComponent[] {
					new JLabel("Adulto " + i + " de " + reserva.getAdultosEsperados()),
					new JLabel("Nombre"),
					nombre,
					new JLabel("Documento"),
					documento,
					new JLabel("Edad"),
					edad
			};

			int opcion = JOptionPane.showConfirmDialog(this, formulario, "Registrar adultos", JOptionPane.DEFAULT_OPTION);
			if (opcion == JOptionPane.OK_OPTION) {
				String n = nombre.getText();
				String d = documento.getText();
				int e = Integer.parseInt(edad.getText());
				if (!n.isEmpty() && !d.isEmpty() && e >= 12) {
						Huesped acompaniante = new Huesped(n, d, e);
						try {
							nuevo.agregarHuesped(acompaniante);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"No se pudo registrar el adulto", "Error", JOptionPane.ERROR_MESSAGE);
							i--;
						}
				} else {
					JOptionPane.showMessageDialog(null,"Registra un adulto válido", "Error", JOptionPane.ERROR_MESSAGE);
					i--;	
				}	    
			} else {
				exitoso = false;
				break;
			}
		}
		
		for(int i = 1; i <= reserva.getNiniosEsperados(); i++) {
			nombre = VentanaInicio.crearTextField();
			documento = VentanaInicio.crearNumberField();
			edad = VentanaInicio.crearNumberField();
			
			JComponent[] formulario = new JComponent[] {
					new JLabel("Niño " + i  + " de " + reserva.getNiniosEsperados()),
					new JLabel("Nombre"),
					nombre,
					new JLabel("Documento"),
					documento,
					new JLabel("Edad"),
					edad
			};

			int opcion = JOptionPane.showConfirmDialog(this, formulario, "Registrar niños", JOptionPane.DEFAULT_OPTION);
			if (opcion == JOptionPane.OK_OPTION) {
				String n = nombre.getText();
				String d = documento.getText();
				int e = Integer.parseInt(edad.getText());
				if (!n.isEmpty() && !d.isEmpty() && e < 12) {
						Huesped acompaniante = new Huesped(n, d, e);
						try {
							nuevo.agregarHuesped(acompaniante);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"No se pudo registrar el niño", "Error", JOptionPane.ERROR_MESSAGE);
							i--;
						}
				} else {
					JOptionPane.showMessageDialog(null,"Registra un niño válido", "Error", JOptionPane.ERROR_MESSAGE);
					i--;	
				}	    
			}  else {
				exitoso = false;
				break;
			}
		}
	
		if(exitoso) {
			ventana.hacerCheckIn(nuevo);
			boolean pagoPrevio = reserva.getPagado();
			JOptionPane.showMessageDialog(null,"Se agregó el grupo al hotel", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			String mensaje = ventana.registrarConsumo(nuevo.getId(), aCargo.getDocumento(), pagoPrevio);
			JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,"Se omitieron huéspedes", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
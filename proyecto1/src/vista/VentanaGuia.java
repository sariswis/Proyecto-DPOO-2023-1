package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import hotel.Hotel;
import huespedes.Grupo;
import huespedes.Huesped;
import usuarios.Guia;

import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class VentanaGuia extends VentanaUsuario {
	private Guia guia;
	private JButton registrarConsumo;
	private JFormattedTextField idGrupo;
	private JButton buscar;
	private JPanel huespedes;
	private JRadioButton[] documentos;
	private JScrollPane scroll;
	private JRadioButton registrarPago;
	private JButton enviar;
	private JLabel mensaje;
	
	public VentanaGuia(Guia guia) {
		super();
		crearPanelSuperior("GUÍA");
		crearPanelCentro(guia.getLogin());
		crearPanelIzquierdo();
		this.guia = guia;
	}
	
	public void crearPanelIzquierdo() {
		this.registrarConsumo = VentanaInicio.crearBoton("Registrar consumo");
	    registrarConsumo.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	registrarConsumo();
	        }
	    });
		pIzquierdo.add(registrarConsumo);
		add(pIzquierdo, BorderLayout.WEST);
	}
	
	public void registrarConsumo() {
		idGrupo = VentanaInicio.crearNumberField();
		buscar = VentanaInicio.crearBoton("Buscar huéspedes");
	    buscar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String tId = idGrupo.getText();
	        	Grupo grupo = Hotel.getGrupo(tId);
	        	if (grupo != null) {
	        		buscarHuespedes(grupo);
	        	} else {
		        	mensaje.setOpaque(true);
		        	mensaje.setText("El grupo no existe");
		    		revalidate();
		    		repaint();
	        	}
	        }
	    });
	    mensaje = VentanaInicio.crearMensaje("");
	    
	    pCentro.removeAll();
		pCentro.add(new JLabel("Número grupo"));
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(idGrupo);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(buscar);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(mensaje);
		pCentro.add(Box.createVerticalStrut(10));
		revalidate();
		repaint();
	}
	
	public void buscarHuespedes(Grupo grupo) {
		huespedes = new JPanel();
		huespedes.setLayout(new BoxLayout(huespedes, BoxLayout.Y_AXIS));
		huespedes.setAlignmentX(Component.CENTER_ALIGNMENT);
		huespedes.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JLabel id = new JLabel("Grupo " + grupo.getId());
		id.setFont(new Font("Arial", Font.BOLD, 18));
		ArrayList<Huesped> lista = grupo.getHuespedes();
		int tamaño = lista.size();
		documentos = new JRadioButton[tamaño];
		for (int i=0; i<tamaño; i++) {
			documentos[i] = new JRadioButton(lista.get(i).getDocumento());
			huespedes.add(documentos[i]);
		}
		scroll = new JScrollPane(huespedes);
		registrarPago = new JRadioButton("Registrar pago");
		
		enviar = VentanaInicio.crearBoton("Enviar");
	    enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String tId = idGrupo.getText();
	        	ArrayList<Huesped> huespedes = obtenerHuespedes(lista);
	        	boolean pagado = registrarPago.isSelected();
	        	String respuesta = guia.registrarConsumo(tId, huespedes, pagado);
	        	mensaje.setOpaque(true);
	        	mensaje.setText(respuesta);
	    		revalidate();
	    		repaint();
	        }
	    });
	    mensaje = VentanaInicio.crearMensaje("");
	    
	    pCentro.removeAll();
		pCentro.add(id);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(new JLabel("Seleccione los huéspedes por su documento"));
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(scroll);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(registrarPago);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(enviar);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(mensaje);
		pCentro.add(Box.createVerticalStrut(10));
		revalidate();
		repaint();
	}
	
	public ArrayList<Huesped> obtenerHuespedes(ArrayList<Huesped> original){
		ArrayList<Huesped> nueva = new ArrayList<Huesped>();
		for (int i=0; i<documentos.length; i++) {
			if (documentos[i].isSelected()) {
				nueva.add(original.get(i));
			}
		}
		return nueva;
	}
	
}

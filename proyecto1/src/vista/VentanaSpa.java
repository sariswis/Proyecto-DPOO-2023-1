package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import usuarios.EncargadoSpa;

@SuppressWarnings("serial")
public class VentanaSpa extends VentanaUsuario {
	private EncargadoSpa encargado;
	private JButton registrarConsumo;
	private JFormattedTextField idGrupo;
	private JTextField documento;
	private JRadioButton registrarPago;
	private JButton enviar;
	private JLabel mensaje;
	
	public VentanaSpa(EncargadoSpa encargado) {
		super();
		crearPanelSuperior("ENCARGADO SPA");
		crearPanelCentro(encargado.getLogin());
		crearPanelIzquierdo();
		this.encargado = encargado;

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
		documento = VentanaInicio.crearTextField();
		registrarPago = new JRadioButton("Registrar pago");
		
		enviar = VentanaInicio.crearBoton("Enviar");
	    enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String tId = idGrupo.getText();
	        	String tDocumento = documento.getText();
	        	boolean pagado = registrarPago.isSelected();
	        	String respuesta = encargado.registrarConsumo(tId, tDocumento, pagado);
	        	mensaje.setOpaque(true);
	        	mensaje.setText(respuesta);
	    		revalidate();
	    		repaint();
	        }
	    });
	    mensaje = VentanaInicio.crearMensaje("");
	    
	    pCentro.removeAll();
		pCentro.add(new JLabel("Número grupo"));
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(idGrupo);
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(new JLabel("Documento"));
		pCentro.add(Box.createVerticalStrut(10));
		pCentro.add(documento);
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
	
}

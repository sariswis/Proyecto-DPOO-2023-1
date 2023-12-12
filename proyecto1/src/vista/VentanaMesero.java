package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import catalogo.Pedido;
import catalogo.Plato;
import catalogo.Restaurante;
import usuarios.Mesero;

@SuppressWarnings("serial")
public class VentanaMesero extends VentanaUsuario {
	private Mesero mesero;
	private JButton registrarConsumo;
	private JFormattedTextField idGrupo;
	private JTextField documento;
	private JPanel productos;
	private JFormattedTextField[] casillas;
	private JScrollPane scroll;
	private JRadioButton registrarPago;
	private JButton enviar;
	private JLabel mensaje;
	private int cantidad;
	
	public VentanaMesero(Mesero mesero) {
		super();
		crearPanelSuperior("MESERO");
		crearPanelCentro(mesero.getLogin());
		crearPanelIzquierdo();
		this.mesero = mesero;
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
		
		productos = new JPanel();
		cantidad = Restaurante.getCantidadPlatos();
		String[] platos = Restaurante.getPlatos();
		productos.setLayout(new GridLayout(cantidad, 2));
		casillas = new JFormattedTextField[cantidad];
		JLabel etiqueta;
		for(int i=0; i<cantidad; i++) {
			casillas[i] = VentanaInicio.crearNumberFieldReducido();
			etiqueta = new JLabel(platos[i]);
			productos.add(casillas[i]);
			productos.add(etiqueta);
			productos.add(Box.createHorizontalGlue());
		}
		scroll = new JScrollPane(productos);
		
		registrarPago = new JRadioButton("Registrar pago");
		enviar = VentanaInicio.crearBoton("Enviar");
	    enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String tId = idGrupo.getText();
	        	String tDocumento = documento.getText();
	        	boolean pagado = registrarPago.isSelected();
	        	Pedido pedido = hacerPedido();
	        	String respuesta = mesero.registrarConsumo(tId, tDocumento, pagado, pedido);
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
		pCentro.add(new JLabel("Escriba la cantidad de artículos que desea al frente de cada producto"));
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
	
	public Pedido hacerPedido() {
		Pedido pedido = new Pedido();
		for(int i=0; i<cantidad; i++) {
			int articulos = Integer.parseInt(casillas[i].getText());
			if (articulos > 0) {
				Plato plato = Restaurante.getPlato(String.valueOf(i+1));
				if (plato != null) {
					for(int j=0; j<articulos; j++) {
						try {
							pedido.agregarPlato(plato);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
		return pedido;
	}
	
}

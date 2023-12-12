package vistaRecepcionista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import catalogo.Alojamiento;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class HacerCheckOut extends JPanel {
	private VentanaRecepcionista ventana;
	private JFormattedTextField idGrupo;
	private JButton buscar;
	private JComboBox<String> pasarelasP;
	private JButton pagar;
	
	public HacerCheckOut(VentanaRecepcionista ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		
		idGrupo  = VentanaInicio.crearNumberField();

	    buscar = VentanaInicio.crearBoton("Buscar");
	    buscar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String id = idGrupo.getText();
	        	try {
	        		ArrayList<String> consumos = ventana.getConsumos(id);
	        		checkOut(id, consumos);
	        	} catch (Exception e1) {
	        		JOptionPane.showMessageDialog(null, e1.getMessage(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        }
	    }); 

	    add(new JLabel("Id de la reserva: "));
	    add(Box.createVerticalStrut(10));	
	    add(idGrupo);
	    add(Box.createVerticalStrut(10));	
	    add(buscar);
	}
	
	public void checkOut(String id, ArrayList<String> consumos) {
			DefaultListModel<String> modelo = new DefaultListModel<String>();
		    for (String consumo : consumos) {
		        modelo.addElement(consumo);
		    }
		    
		    JList<String> lista = new JList<String>(modelo);
			JScrollPane scroll = new JScrollPane(lista);
			
			pasarelasP = new JComboBox<String>();
			pasarelasP.setMaximumSize(new Dimension(200, 30));
		    ArrayList<String> pasarelas = Alojamiento.getPasarelas();
		    for(String p: pasarelas) {
		    	pasarelasP.addItem(p);
		    }
			
			pagar = VentanaInicio.crearBoton("Pagar");
		    pagar.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	String pasarela = (String) pasarelasP.getSelectedItem();
		        	JFormattedTextField tarjeta = VentanaInicio.crearNumberField();
		        	JTextField nombre = new JTextField();
		        	JFormattedTextField documento = VentanaInicio.crearNumberField();
		        	JComponent[] formulario = new JComponent[] {
		        			new JLabel("Número tarjeta"),
		        			tarjeta,
		        			new JLabel("Nombre dueño"),
		        			nombre,
		        			new JLabel("Documento dueño"),
		        			documento,
		        	};
		        	       
		        	int resultado = JOptionPane.showConfirmDialog(null, formulario, "Registrarse", JOptionPane.DEFAULT_OPTION);
		        	String tTarjeta = tarjeta.getText();
		        	String tNombre = nombre.getText();
		        	String tDocumento = documento.getText();
		        	if (resultado == JOptionPane.OK_OPTION && (tTarjeta.equals("") || tNombre.equals("") || tDocumento.equals(""))) {
		        	      JOptionPane.showMessageDialog(null, new JLabel("Tienes que completar todos tus datos."), "Error", JOptionPane.ERROR_MESSAGE);
		        	 } else if (resultado == JOptionPane.OK_OPTION) {
		        	     String mensaje = ventana.hacerCheckOut(id, tTarjeta, tNombre, tDocumento, pasarela);
		        	     JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		        	}
		       }
		    });  
			
		    add(Box.createVerticalStrut(10));	
			add(scroll);
		    add(Box.createVerticalStrut(10));	
		    add(pasarelasP);
		    add(Box.createVerticalStrut(10));	
			add(pagar);
		    revalidate();
		    repaint();
	}
	
}

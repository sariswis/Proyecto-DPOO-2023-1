package VistaHuesped;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

import catalogo.Alojamiento;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class PagarInmediatamente extends JPanel{
	private VentanaHuesped ventana;
	private JTextField id;
	private JButton pagar; 
	private JComboBox<String> pasarelasP;

public PagarInmediatamente(VentanaHuesped ventana) {
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	setBorder(new EmptyBorder(10, 10, 10, 10));
	this.ventana = ventana;
	id = VentanaInicio.crearTextField();
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
        	String idReserva= id.getText();
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
        	    String mensaje = ventana.realizarPagoInmediato(idReserva,tTarjeta, tNombre, tDocumento, pasarela);
        	    JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        	}
       }
    });  
    add(Box.createVerticalStrut(10));	
    add(new JLabel("ID de la reserva "));
    add(id);
    add(Box.createVerticalStrut(10));	
    add(pasarelasP);
    add(Box.createVerticalStrut(10));	
    add(pasarelasP);
    add(Box.createVerticalStrut(10));	
	add(pagar);
    revalidate();
    repaint();

}
}
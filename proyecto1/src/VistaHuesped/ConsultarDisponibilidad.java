package VistaHuesped;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vista.VentanaInicio;


@SuppressWarnings("serial")
public class ConsultarDisponibilidad extends JPanel {
	private VentanaHuesped ventana;
	private JTextField fecha1;
	private JTextField fecha2;
	private JButton consultar;
	private JScrollPane scroll;

	public ConsultarDisponibilidad(VentanaHuesped ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana = ventana;
		fecha1  = VentanaInicio.crearTextField();
		fecha2  = VentanaInicio.crearTextField();
		
	    this.consultar = VentanaInicio.crearBoton("Consultar Disponibilidad");
	    consultar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String f1 = fecha1.getText();
	        	String f2 = fecha2.getText();
	        	try {
	        		String[] cadenas = ventana.consultarDisponibilidad(f1, f2);
	 	    	   JList<String> lista = new JList<String>(cadenas);
		    	   	scroll = new JScrollPane(lista);
		    	    add(Box.createVerticalStrut(10));	
		   			add(scroll);
		   			revalidate();
		   			repaint();
	        	} catch (Exception e1) {
	        		JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        	}
	        }
	    }); 

	    add(new JLabel("Fecha inicial (YYYY-MM-DD): ej: 2023-05-10"));
	    add(Box.createVerticalStrut(10));	
	    add(fecha1);
	    add(Box.createVerticalStrut(10));	
	    add(new JLabel("Fecha final (YYYY-MM-DD): ej: 2023-05-10"));
	    add(Box.createVerticalStrut(10));	
	    add(fecha2);
	    add(Box.createVerticalStrut(10));	
	    add(consultar);  
}
}
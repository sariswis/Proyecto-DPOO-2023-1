package vistaRecepcionista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import vista.VentanaInicio;
import vista.VentanaUsuario;

@SuppressWarnings("serial")
public class VentanaHabitacion extends VentanaUsuario {
	private JButton ConsultarHabitacion;
	private JButton ConsultarCamas;
	private JButton ConsultarHotel;
	private String[] infoHabitacion;
	private ArrayList<String[]> infoCamas;
	private String[] infoHotel;
	private JLabel titulo;
	private JPanel panel;
	private JScrollPane scroll;
	
	public VentanaHabitacion(String[] infoHabitacion, ArrayList<String[]> infoCamas, String[] infoHotel) {
		super();
		setSize(750, 500);
		crearPanelSuperior("INFORMACIÓN ADICIONAL");
		crearPanelCentro("recepcionista");
		crearPanelIzquierdo(); 
		
		this.infoHabitacion = infoHabitacion;
		this.infoCamas = infoCamas;
		this.infoHotel = infoHotel;
	}
	
	public void crearPanelIzquierdo() {
		this.pIzquierdo = new JPanel(new GridLayout(0, 1));
	    titulo = new JLabel();
		titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		this.ConsultarHabitacion = VentanaInicio.crearBoton("Consultar Habitación");
	    ConsultarHabitacion.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	consultarHabitacion();
	        }
	    });
	    
		this.ConsultarCamas = VentanaInicio.crearBoton("Consultar Camas");
	    ConsultarCamas.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	consultarCamas();
	        }
	    });
	    
		this.ConsultarHotel = VentanaInicio.crearBoton("Consultar Hotel");
	    ConsultarHotel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	consultarHotel();
	        }
	    });
	    
		pIzquierdo.add(Box.createVerticalGlue());
		pIzquierdo.add(ConsultarHabitacion);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(ConsultarCamas);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(ConsultarHotel);
		pIzquierdo.add(Box.createVerticalGlue());

		add(pIzquierdo, BorderLayout.WEST);
		revalidate();
	    repaint();
	}
	
	public void consultarHabitacion() {
	    pCentro.removeAll();
	    panel.removeAll();
	    titulo.setText("Información de la habitación");
	    panel.add(titulo);
    	panel.add(Box.createVerticalStrut(10));
	    for(String info: infoHabitacion) {
	    	panel.add(new JLabel(info));
	    	panel.add(Box.createVerticalStrut(10));
	    }
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
	public void consultarCamas() {
	    pCentro.removeAll();
	    panel.removeAll();
	    titulo.setText("Información de las camas");
	    panel.add(titulo);
	    panel.add(Box.createVerticalStrut(20));
	    for(String[] info: infoCamas) {
		    for(String i: info) {
		    	panel.add(new JLabel(i));
		    	panel.add(Box.createVerticalStrut(10));
		    }
		    panel.add(Box.createVerticalStrut(20));
	    }
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
	public void consultarHotel() {
	    pCentro.removeAll();
	    panel.removeAll();
	    titulo.setText("Información del hotel");
	    panel.add(titulo);
    	panel.add(Box.createVerticalStrut(10));
	    for(String info: infoHotel) {
	    	panel.add(new JLabel(info));
	    	panel.add(Box.createVerticalStrut(10));
	    }
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
}

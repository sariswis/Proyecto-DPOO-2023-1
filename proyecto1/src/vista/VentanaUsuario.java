package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public abstract class VentanaUsuario extends JFrame {
	private JPanel pSuperior;
	protected JPanel pIzquierdo;
	protected JPanel pCentro;
	
	public VentanaUsuario() {	
		pIzquierdo = new JPanel();
		pIzquierdo.setLayout(new BoxLayout(pIzquierdo, BoxLayout.Y_AXIS));
		pIzquierdo.setAlignmentX(Component.CENTER_ALIGNMENT);
		pIzquierdo.setAlignmentY(Component.CENTER_ALIGNMENT);
		pIzquierdo.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(pIzquierdo, BorderLayout.WEST);
		
		setLayout(new BorderLayout());
		setTitle("Sesión abierta");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setSize(900, 750);
		setLocationRelativeTo(null);
	}
	
	public void crearPanelSuperior(String nombre) {
		pSuperior = new JPanel(new FlowLayout());
		pSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel rol = new JLabel(nombre);
		rol.setForeground(Color.BLACK);
		rol.setFont(new Font("Arial", Font.BOLD, 20));
		
		JButton salir = VentanaInicio.crearBoton("Salir");
	    salir.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(salir);
	            frame.dispose();
	        }
	    });
		
		pSuperior.add(rol);
		pSuperior.add(Box.createHorizontalStrut(50));
		pSuperior.add(salir);
		add(pSuperior, BorderLayout.NORTH);
	}

	public void crearPanelCentro(String nombre) {
		pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		pCentro.setAlignmentX(Component.LEFT_ALIGNMENT);
		pCentro.setAlignmentY(Component.LEFT_ALIGNMENT);
		pCentro.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel mensaje = new JLabel("¡Bienvenido " + nombre + "!");
		pCentro.add(mensaje);
		add(pCentro, BorderLayout.CENTER);
	}
	
}

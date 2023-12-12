package VistaHuesped;

 
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import hotel.Hotel;
import usuarios.Rol;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class VentanaInicioHuesped extends VentanaInicio {
	
	public VentanaInicioHuesped(Hotel hotel) {
		super(hotel);
	}
	public JPanel crearPanelInferior() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel bienvenido = new JLabel("¡Bienvenido!");
		bienvenido.setForeground(Color.BLACK);
		bienvenido.setFont(new Font("Arial", Font.BOLD, 18));
		bienvenido.setAlignmentX(CENTER_ALIGNMENT);
		JButton registrarse = crearBoton("Crear Usuario");
		registrarse.setAlignmentX(CENTER_ALIGNMENT);
		JButton iniciarS = crearBoton("Autenticarse");
		iniciarS.setAlignmentX(CENTER_ALIGNMENT);
		
	    registrarse.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	registrarse();
	        }
	    });
	    
	    iniciarS.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	iniciarSesion();
	        }
	    });
		
		panel.add(bienvenido);
		panel.add(Box.createVerticalStrut(10));
		panel.add(registrarse);
		panel.add(Box.createVerticalStrut(10));
		panel.add(iniciarS);
		panel.add(Box.createVerticalStrut(10));
		return panel;
	}
	public void registrarse() {
		JTextField login = new JTextField();
		JPasswordField contraseña = new JPasswordField();
		JTextField documento = new JTextField();
        JComponent[] formulario = new JComponent[] {
                new JLabel("Login"),
                login,
                new JLabel("Contraseña"),
                contraseña,
                new JLabel("Documento"),
                documento,
       };
       
        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Registrarse", JOptionPane.DEFAULT_OPTION);
        String tLogin = login.getText();
        String tContraseña = new String(contraseña.getPassword());
        String tDocumento = documento.getText();;
        if (resultado == JOptionPane.OK_OPTION && (tLogin.equals("") || tContraseña.equals("") || tDocumento.equals(""))) {
            JOptionPane.showMessageDialog(this, new JLabel("Tienes que completar todos tus datos."), "Error", JOptionPane.ERROR_MESSAGE);
        } else if (resultado == JOptionPane.OK_OPTION) {
        	try {
        		hotel.crearUsuario(tLogin, tContraseña, tDocumento, Rol.HUESPED);
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(this, new JLabel(e.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
	}
	public void iniciarSesion() {
		JTextField login = new JTextField();
		JPasswordField contraseña = new JPasswordField();
		
        JComponent[] formulario = new JComponent[] {
                new JLabel("Login"),
                login,
                new JLabel("Contraseña"),
                contraseña,
       };
       
        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Iniciar sesión", JOptionPane.DEFAULT_OPTION);
        String tLogin = login.getText();
        String tContraseña =  new String(contraseña.getPassword());
        if (resultado == JOptionPane.OK_OPTION && (tLogin.equals("") || tContraseña.equals(""))) {
            JOptionPane.showMessageDialog(this, new JLabel("Tienes que completar todos tus datos."), "Error", JOptionPane.ERROR_MESSAGE);
        } else if (resultado == JOptionPane.OK_OPTION) {
        	try {
        		hotel.iniciarSesion(tLogin, tContraseña);
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(this, new JLabel(e.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
	}
	public static void main(String[] args) {
		Hotel hotel = new Hotel();
		try {
			hotel.cargarHuespedes();
		} catch (Exception e) {
			System.out.println("No se pudieron cargar los huéspedes existentes");
		}
		new VentanaInicioHuesped(hotel);
	}
}
	
	
	
	
	
	
	
	

	

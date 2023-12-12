package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import hotel.Hotel;
import inventario.Inventario;
import usuarios.Rol;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class VentanaInicio extends JFrame {
	protected JPanel pSuperior;
	protected JPanel pInferior;
	protected Hotel hotel;
	
	public VentanaInicio(Hotel hotel) {
		this.pSuperior = crearPanelSuperior();
		this.pInferior = crearPanelInferior();
		this.hotel = hotel;
		
		setLayout(new BorderLayout());
		add(pSuperior, BorderLayout.NORTH);
		add(pInferior, BorderLayout.CENTER);
		
		setTitle("¡Bienvenido!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
	}
	
	public JPanel crearPanelSuperior() {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		JLabel hotel = new JLabel(Inventario.getNombreHotel());
		hotel.setForeground(Color.BLACK);
		hotel.setFont(new Font("Arial", Font.BOLD, 40));
		panel.add(hotel);
		return panel;
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
		JButton registrarse = crearBoton("Registrarse");
		registrarse.setAlignmentX(CENTER_ALIGNMENT);
		JButton iniciarS = crearBoton("Iniciar sesión");
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
	
	public static JLabel crearMensaje(String mensaje) {
		JLabel label = new JLabel(mensaje);
		label.setForeground(Color.BLACK);
		label.setBackground(new Color (140,200,254));
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		label.setFont(new Font("Arial", Font.BOLD, 16));
		return label;
	}
	
	public static  JButton crearBoton(String nombre) {
		JButton boton = new JButton(nombre);
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color (3,135,255));
		boton.setFont(new Font("Arial", Font.BOLD, 16));
		return boton;
	}
	
	public static JTextField crearTextField() {
		JTextField text = new JTextField();
		Dimension dimension = new Dimension(500, 25);
		text.setPreferredSize(dimension);
		text.setMaximumSize(dimension);
		return text;
	}
	
	public static JFormattedTextField crearNumberField() {
	    DecimalFormat format = new DecimalFormat("#,##0");
	    format.setParseIntegerOnly(true);
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setAllowsInvalid(false);
	    formatter.setMinimum(0);
	    formatter.setMaximum(2000000000);
	    JFormattedTextField number = new JFormattedTextField(formatter);
	    Dimension dimension = new Dimension(500, 25);
	    number.setPreferredSize(dimension);
	    number.setMaximumSize(dimension);
	    number.setText("0");
	    return number;
	}


	
	public static JFormattedTextField crearNumberFieldReducido() {
	    DecimalFormat format = new DecimalFormat("#,##0");
	    format.setParseIntegerOnly(true);
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setAllowsInvalid(false);
	    formatter.setMinimum(0);
	    formatter.setMaximum(2000000000);
	    JFormattedTextField number = new JFormattedTextField(formatter);
	    Dimension dimension = new Dimension(30, 25);
	    number.setPreferredSize(dimension);
	    number.setMaximumSize(dimension);
	    number.setText("0");
	    return number;
	}
	
	public void registrarse() {
		JTextField login = new JTextField();
		JPasswordField contraseña = new JPasswordField();
		JTextField documento = new JTextField();
		JComboBox<Rol> rol = new JComboBox<Rol>();
		rol.addItem(Rol.ADMINISTRADOR);
		rol.addItem(Rol.RECEPCIONISTA);
		rol.addItem(Rol.MESERO);
		rol.addItem(Rol.ENCARGADO_SPA);
		rol.addItem(Rol.GUIA);
		
        JComponent[] formulario = new JComponent[] {
                new JLabel("Login"),
                login,
                new JLabel("Contraseña"),
                contraseña,
                new JLabel("Documento"),
                documento,
                new JLabel("Rol"),
                rol,
       };
       
        int resultado = JOptionPane.showConfirmDialog(this, formulario, "Registrarse", JOptionPane.DEFAULT_OPTION);
        String tLogin = login.getText();
        String tContraseña = new String(contraseña.getPassword());
        String tDocumento = documento.getText();
        Rol tRol = (Rol) rol.getSelectedItem();
        if (resultado == JOptionPane.OK_OPTION && (tLogin.equals("") || tContraseña.equals("") || tDocumento.equals(""))) {
            JOptionPane.showMessageDialog(this, new JLabel("Tienes que completar todos tus datos."), "Error", JOptionPane.ERROR_MESSAGE);
        } else if (resultado == JOptionPane.OK_OPTION) {
        	try {
        		hotel.crearUsuario(tLogin, tContraseña, tDocumento, tRol);
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

	/**
	 * Inicia la app
	 */
	public static void main(String[] args) {
		Hotel hotel = new Hotel();
		try {
			hotel.cargarUsuarios();
		} catch (Exception e) {
			System.out.println("No se pudieron cargar los usuarios existentes");
		}
		new VentanaInicio(hotel);
	}
	
}

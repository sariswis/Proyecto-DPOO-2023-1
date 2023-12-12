package vistaAdministrador;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import catalogo.Nombre;
import inventario.Tipo;
import usuarios.Administrador;
import vista.VentanaInicio;
import vista.VentanaUsuario;

@SuppressWarnings("serial")
public class VentanaAdministrador extends VentanaUsuario{
	//Administrador//
	private Administrador admin;
	//Botones panelIzquierdo//
	private JButton cargarHabitacion;
	private JButton cargarCama;
	private JButton cambiarTarifaHabitacion;
	private JButton cargarHabitaciones;
	private JButton cambiarTarifaServicio;
	private JButton cargarMenuRestaurante;
	private JButton configurarPlato;
	private JButton verEstadisticas;
	private VentanaEstadisticas estadisticas;
	
	public VentanaAdministrador(Administrador admin) {
		super();
		crearPanelSuperior("ADMINISTRADOR");
		crearPanelCentro(admin.getLogin());
		crearPanelIzquierdo();
		this.admin=admin;
	}
	
	public void crearPanelIzquierdo() {
        this.pIzquierdo = new JPanel(new GridLayout(0, 1));
        VentanaAdministrador ventana = this;
		//CargarHabitacion//
		this.cargarHabitacion=VentanaInicio.crearBoton("Cargar Habitación");
		cargarHabitacion.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CargarHabitacion(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//Cargar Cama//
		this.cargarCama=VentanaInicio.crearBoton("Cargar Cama");
		cargarCama.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CargarCama(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//cambiar Tarifa de un tipo de Habitación//
		this.cambiarTarifaHabitacion=VentanaInicio.crearBoton("Cambiar Tarifa Habitación");
		cambiarTarifaHabitacion.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CambiarTarifaHabitacion(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//Cargar Habitaciones de un archivo//
		this.cargarHabitaciones=VentanaInicio.crearBoton("Cargar Habitaciones");
		cargarHabitaciones.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CargarHabitaciones(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//Cambiar Tarifa Servicios//
		this.cambiarTarifaServicio=VentanaInicio.crearBoton("Cambiar Tarifa Servicio");
		cambiarTarifaServicio.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CambiarTarifaServicio(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//cargar menu restaurante//
		this.cargarMenuRestaurante=VentanaInicio.crearBoton("Cargar Menú Restaurante");
		cargarMenuRestaurante.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CargarMenuRestaurante(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//Configurar un plato del menu//
		this.configurarPlato=VentanaInicio.crearBoton("Configurar Plato");
		configurarPlato.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new ConfigurarPlato(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		
		this.verEstadisticas = VentanaInicio.crearBoton("Ver Estadísticas");
		verEstadisticas.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	estadisticas = new VentanaEstadisticas();
	        }
	    }); 

		pIzquierdo.add(Box.createVerticalGlue());
		pIzquierdo.add(cargarHabitacion);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(cargarCama);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(cambiarTarifaHabitacion);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(cargarHabitaciones);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(cambiarTarifaServicio);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(cargarMenuRestaurante);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(configurarPlato);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(verEstadisticas);
		pIzquierdo.add(Box.createVerticalGlue());

		add(pIzquierdo, BorderLayout.WEST);
		revalidate();
	    repaint();
	}

	public String cargarHabitaciones() {
		return admin.cargarHabitaciones();
	}
	
	public String cargarTarifaHabitacion(Tipo tipo, int tarifa, String inicio, String fin,ArrayList<DayOfWeek> dias) {
	String mensaje=admin.cargarTarifaHabitacion(tipo, tarifa, inicio, fin, dias);
	return mensaje;
	}
	
	public String cambiarTarifaServicio(Nombre nombre, int tarifa) {
		String mensaje=admin.cambiarTarifaServicio(nombre, tarifa);
		return mensaje;
	}
	
	public String cargarCama(String id, Tipo tipo, String tamaño, int capacidad, boolean niños) {
		String mensaje=admin.cargarCama(id, tipo, id, capacidad, niños);
		return mensaje;
	}
	
	public String cargarHabitacion(String id, Tipo tipo, String ubicacion, boolean balcon, boolean vistas, 
			boolean cocina, int tamanioM2, boolean aireAcondicionado, boolean calefaccion, boolean tv, boolean cafetera, 
			boolean ropaCamaTapetes, boolean plancha, boolean secador, boolean voltajeAC, boolean usbA, boolean usbC, 
			boolean desayuno) {
		String mensaje = admin.cargarHabitacion(id, tipo, ubicacion, balcon, vistas, cocina, 
				tamanioM2, aireAcondicionado, calefaccion, tv, cafetera, ropaCamaTapetes, 
				plancha, secador, voltajeAC, usbA, usbC, desayuno);
		return mensaje;
	}

	public String[] cargarMenuRestaurante() {
		return admin.cargarMenuRestaurante();
	}
	
	public String configurarPlato(String id, int precio, boolean servicio) {
		return admin.configurarPlato(id, precio, servicio);
	}

}

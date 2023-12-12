package vistaRecepcionista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import huespedes.Grupo;
import huespedes.Reserva;
import inventario.Tipo;
import usuarios.Recepcionista;
import vista.VentanaInicio;
import vista.VentanaUsuario;

@SuppressWarnings("serial")
public class VentanaRecepcionista extends VentanaUsuario {
	private Recepcionista recepcionista;
	private JButton ConsultarInventario;
	private JButton HacerReserva;
	private JButton CancelarReserva;
	private JButton HacerCheckIn;
	private JButton HacerCheckOut;
	private JButton VerOcupacionHotel;
	
	public VentanaRecepcionista(Recepcionista recepcionista) {
		super();
		crearPanelSuperior("RECEPCIONISTA");
		crearPanelCentro(recepcionista.getLogin());
		crearPanelIzquierdo();
		this.recepcionista = recepcionista;  	  		
	}
	
	public void crearPanelIzquierdo() {
		this.pIzquierdo = new JPanel(new GridLayout(0, 1));
		VentanaRecepcionista ventana = this;

		this.ConsultarInventario = VentanaInicio.crearBoton("Consultar Inventario");
	    ConsultarInventario.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new ConsultarInventario(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    });
	    
	    this.HacerReserva = VentanaInicio.crearBoton("Hacer Reserva");
	    HacerReserva.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new HacerReserva(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    });
	    
	    this.CancelarReserva = VentanaInicio.crearBoton("Cancelar Reserva");
	    CancelarReserva.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new CancelarReserva(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    });
	    
	    this.HacerCheckIn = VentanaInicio.crearBoton("Hacer Check In");
	    HacerCheckIn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new HacerCheckIn(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    });
	    
	    this.HacerCheckOut = VentanaInicio.crearBoton("Hacer Check Out");
	    HacerCheckOut.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new HacerCheckOut(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    });
	    
	    this.VerOcupacionHotel = VentanaInicio.crearBoton("Ver Ocupacion Hotel");
	    VerOcupacionHotel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new VerOcupacion();
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    });
	    
		pIzquierdo.add(Box.createVerticalGlue());
		pIzquierdo.add(ConsultarInventario);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(HacerReserva);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(CancelarReserva);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(HacerCheckIn);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(HacerCheckOut);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(VerOcupacionHotel);
		pIzquierdo.add(Box.createVerticalGlue());

		add(pIzquierdo, BorderLayout.WEST);
		revalidate();
	    repaint();
	}

	public String[] consultarInventario(Tipo tipo) {
		return recepcionista.consultarInventario(tipo);
	}
	
	public String[] consultarHabitacion(Tipo tipo, String habitacion) {
		return recepcionista.consultarHabitacion(tipo, habitacion);
	}
	
	public ArrayList<String[]> consultarCamas(Tipo tipo, String habitacion) {
		return recepcionista.consultarCamas(tipo, habitacion);
	}
	
	public String[] consultarHotel() {
		return recepcionista.consultarHotel();
	}
	
	public Reserva crearReserva(String fInicio, String fFin, int adultosE, int niniosE, String nombre, String documento, int edad, String correo, String celular) throws Exception {
		return Recepcionista.crearReserva(fInicio, fFin, adultosE, niniosE, nombre, documento, edad, correo, celular);
	}
	
	public void agregarReserva(Reserva reserva) throws IOException {
		Recepcionista.agregarReserva(reserva);
	}
	
	public void cancelarReserva(String id) throws Exception {
		recepcionista.cancelarReserva(id);
	}
	
	public void hacerCheckIn(Grupo nuevo) throws Exception {
		recepcionista.hacerCheckIn(nuevo);
	}
	
	public String registrarConsumo(String idGrupo, String documento, boolean pagoPrevio) {
		return recepcionista.registrarConsumo(idGrupo, documento, pagoPrevio);
	}
	
	public ArrayList<String> getConsumos(String id) throws Exception {
		return recepcionista.getConsumos(id);
	}

	public String hacerCheckOut(String id, String tarjeta, String nombre, String documento, String pasarela) {
		return recepcionista.hacerCheckOut(id, tarjeta, nombre, documento, pasarela);
	}
}
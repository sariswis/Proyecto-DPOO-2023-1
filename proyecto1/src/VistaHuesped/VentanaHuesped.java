package VistaHuesped;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import huespedes.Reserva;
import usuarios.HuespedUsuario;
import usuarios.Recepcionista;
import vista.VentanaInicio;
import vista.VentanaUsuario;

@SuppressWarnings("serial")
public class VentanaHuesped extends VentanaUsuario {
	private HuespedUsuario huesped;
	private JButton ConsultarDisponibilidadPorFecha;
	private JButton ReservarHabitacion;
	private JButton PagarInmediatamente;
	
	public VentanaHuesped(HuespedUsuario huesped) {
		super();
		crearPanelSuperior("HUÉSPED");
		crearPanelCentro(huesped.getLogin());
		crearPanelIzquierdo();
		this.huesped = huesped;
	}
	
	public void crearPanelIzquierdo() {
		this.pIzquierdo = new JPanel(new GridLayout(0, 1));
        VentanaHuesped ventana = this;
        
		//Consultar Disponibilidad por Fecha//
		this.ConsultarDisponibilidadPorFecha=VentanaInicio.crearBoton("Consultar habitaciones");
		ConsultarDisponibilidadPorFecha.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new ConsultarDisponibilidad(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//ReservarHabitacionFechas//
		this.ReservarHabitacion=VentanaInicio.crearBoton("Hacer una reserva");
		ReservarHabitacion.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new ReservarHabitacionHuesped(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		//PagarInmediatamente//
		this.PagarInmediatamente=VentanaInicio.crearBoton("Pagar reserva");
		PagarInmediatamente.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	remove(pCentro);
	        	pCentro = new PagarInmediatamente(ventana);
	    		add(pCentro, BorderLayout.CENTER);
	        	revalidate();
	    	    repaint();
	        }
	    }); 
		
		pIzquierdo.add(Box.createVerticalGlue());
		pIzquierdo.add(ConsultarDisponibilidadPorFecha);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(ReservarHabitacion);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(PagarInmediatamente);
		pIzquierdo.add(Box.createVerticalGlue());

		add(pIzquierdo, BorderLayout.WEST);
		revalidate();
	    repaint();
		
	}
	
	public String[] consultarDisponibilidad(String fInicio, String fFin) throws Exception {
		return huesped.consultarDisponibilidad(fInicio, fFin);
	}
	
	public Reserva crearReserva(String fInicio, String fFin, int adultosE, int niniosE, String nombre, String documento, int edad, String correo, String celular) throws Exception {
		return Recepcionista.crearReserva(fInicio, fFin, adultosE, niniosE, nombre, documento, edad, correo, celular);
	}
	
	public void agregarReserva(Reserva reserva) throws IOException {
		Recepcionista.agregarReserva(reserva);
	}
	
	public String realizarPagoInmediato(String idReserva,String tarjeta, String nombreDueño, String documentoDueño, String pasarela) {
		return huesped.realizarPagoInmediato(idReserva, tarjeta, nombreDueño, documentoDueño, pasarela);
	}
	
	
}
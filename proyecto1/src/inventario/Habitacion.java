package inventario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import huespedes.Huesped;
import huespedes.Reserva;

public class Habitacion {
	private String id;
	private Tipo tipo;
	private String ubicacion;
	private int capacidadAdultos = 0;
	private int capacidadNinios = 0;
	private boolean tieneBalcon;
	private boolean tieneVista;
	private boolean tieneCocinaIntegrada;
	private boolean ocupada = false;
	private int ocupantesAdultos = 0;
	private int ocupantesNinios = 0;
	private int tarifa = 0;
	private int tamanioM2;
	private boolean aireAcondicionado;
	private boolean calefaccion;
	private boolean tv;
	private boolean cafetera;
	private boolean ropaCamaTapetes;
	private boolean plancha;
	private boolean secador;
	private boolean voltajeAC;
	private boolean usbA;
	private boolean usbC;
	private boolean desayuno;
	private ArrayList<Cama> camas = new ArrayList<Cama>();
	private HashMap<String, Reserva> reservas = new HashMap<String, Reserva>();
	private ArrayList<Huesped> ocupantes = new ArrayList<Huesped>();

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Habitacion(String id, Tipo tipo, String ubicacion, boolean tieneBalcon, boolean tieneVista,
			boolean tieneCocinaIntegrada, int tamanioM2, boolean aireAcondicionado, boolean calefaccion, 
			boolean tv, boolean cafetera, boolean ropaCamaTapetes, boolean plancha, boolean secador, 
			boolean voltajeAC, boolean usbA, boolean usbC, boolean desayuno) {
		this.id = id;
		this.tipo = tipo;
		this.ubicacion = ubicacion;
		this.tieneBalcon = tieneBalcon;
		this.tieneVista = tieneVista;
		this.tieneCocinaIntegrada = tieneCocinaIntegrada;
		this.tamanioM2 = tamanioM2;
		this.aireAcondicionado = aireAcondicionado;
		this.calefaccion = calefaccion;
		this.tv = tv;
		this.cafetera = cafetera;
		this.ropaCamaTapetes = ropaCamaTapetes;
		this.plancha = plancha;
		this.secador = secador;
		this.voltajeAC = voltajeAC;
		this.usbA = usbA;
		this.usbC = usbC;
		this.desayuno = desayuno;
	}

	/**
	 * Obtiene el id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Obtiene el tipo
	 */
	public Tipo getTipo() {
		return this.tipo;
	}

	/**
	 * Obtiene todas las reservas
	 */
	public ArrayList<Reserva> getReservas() {
		return new ArrayList<Reserva>(this.reservas.values());
	}

	/**
	 * Obtiene los huéspedes ocupantes
	 */
	public ArrayList<Huesped> getOcupantes() {
		return this.ocupantes;
	}

	/**
	 * Obtiene la capacidad máxima de adultos
	 */
	public int getCapacidadAdultos() {
		return this.capacidadAdultos;
	}

	/**
	 * Obtiene la capacidad máxima de niños
	 */
	public int getCapacidadNinios() {
		return this.capacidadNinios;
	}

	/**
	 * Obtiene si está ocupada
	 */
	public boolean getOcupada() {
		return this.ocupada;
	}
	
	/**
	 * Obtiene si está disponible para reservar entre dos fechas
	 */
	public boolean getDisponibleEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
		 boolean disponible = true;
		ArrayList<Reserva> lista = getReservas();
		 for(Reserva reserva: lista) {
			 if(reserva.getEntreFechas(inicio, fin)) {
				 disponible = false;
				 break;
			 }
		 }
		 return disponible;
	}
	
	/**
	 * Obtiene la tarifa
	 */
	public int getTarifa() {
		return this.tarifa;
	}
	
	/**
	 * Cambia la tarifa
	 */
	public void setTarifa(int tarifa) {
		this.tarifa = tarifa;
	}

	/**
	 * Agrega una cama
	 */
	public void agregarCama(Cama cama) {
		this.camas.add(cama);
		if (cama.getSoloNinios()) {
			this.capacidadNinios += cama.getCapacidad();
		} else {
			this.capacidadAdultos += cama.getCapacidad();
		}

	}

	/**
	 * Agrega un ocupante
	 */
	public void agregarOcupante(Huesped huesped) throws Exception {
		this.ocupantes.add(huesped);
		if (huesped.getMenorDosAnios()) {
			this.ocupantes.add(huesped);
		} else if (huesped.getNinio()) {
			if (ocupantesNinios < capacidadNinios) {
				this.ocupantes.add(huesped);
				ocupantesNinios++;
			} else {
				throw new Exception("No se pueden agregar más niños");
			}
		} else {
			if (ocupantesAdultos < capacidadAdultos) {
				this.ocupantes.add(huesped);
				ocupantesAdultos++;
			} else {
				throw new Exception("No se pueden agregar más adultos");
			}
		}
	}

	/**
	 * Elimina los ocupantes
	 */
	public void eliminarOcupantes() {
		this.ocupantes.clear();
	}

	/**
	 * Agrega una reserva
	 */
	public void agregarReserva(Reserva reserva) {
		this.reservas.put(reserva.getId(), reserva);
	}

	/**
	 * Elimina una reserva
	 */
	public void eliminarReserva(String id) {
		this.reservas.remove(id);
	}

	/**
	 * Obtiene los datos primordiales de la habitación
	 */
	public String[] consultarHabitacion() {
		String[] mensaje = new String[21];
		mensaje[0] = "Id: " + this.id;
		mensaje[1] = "Tipo: " + this.tipo.toString();
		mensaje[2] = "Ubicación: " + this.ubicacion;
		mensaje[3] = "Capacidad adultos: " + this.capacidadAdultos;
		mensaje[4] = "Capacidad niños: " + this.capacidadNinios;
		mensaje[5] = "Ocupada: " + Inventario.convertirBoolean(this.ocupada);
		mensaje[6] = "Tiene balcón: " + Inventario.convertirBoolean(this.tieneBalcon);
		mensaje[7] = "Tiene vista: " + Inventario.convertirBoolean(this.tieneVista);
		mensaje[8] = "Tiene cocina integrada: " + Inventario.convertirBoolean(this.tieneCocinaIntegrada);
		mensaje[9] = "Tamaño en metros cuadrados: " + this.tamanioM2;
		mensaje[10] = "Tiene aire acondicionado: " + Inventario.convertirBoolean(this.aireAcondicionado);
		mensaje[11] = "Tiene calefacción: " + Inventario.convertirBoolean(this.calefaccion);
		mensaje[12] = "Tiene TV: " + Inventario.convertirBoolean(this.tv);
		mensaje[13] = "Tiene cafetera: " + Inventario.convertirBoolean(this.cafetera);
		mensaje[14] = "Tiene ropa de cama y tapetes hipoalergénicos: " + Inventario.convertirBoolean(this.ropaCamaTapetes);
		mensaje[15] = "Tiene plancha: " + Inventario.convertirBoolean(this.plancha);
		mensaje[16] = "Tiene secador de cabello: " + Inventario.convertirBoolean(this.secador);
		mensaje[17] = "Tiene voltaje AC: " + Inventario.convertirBoolean(this.voltajeAC);
		mensaje[18] = "Tiene tomas USB-A: " + Inventario.convertirBoolean(this.usbA);
		mensaje[19] = "Tiene tomas USB-C: " + Inventario.convertirBoolean(this.usbC);
		mensaje[20] = "Incluye desayuno: " + Inventario.convertirBoolean(this.desayuno);
		return mensaje;
	}
	
	public ArrayList<String[]> consultarCamas() {
		ArrayList<String[]> infoCamas = new ArrayList<String[]>();
		for(Cama cama: camas) {
			infoCamas.add(cama.consultarCama());
		}
		return infoCamas;
	}
	
}

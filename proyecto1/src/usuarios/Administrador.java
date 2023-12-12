package usuarios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import catalogo.*;
import inventario.Inventario;
import inventario.Tipo;
import inventario.TipoHabitacion;
import vistaAdministrador.VentanaAdministrador;

public class Administrador extends Usuario {
	private VentanaAdministrador interfaz;
	
	/**
	 * Se asignan los valores de los parametros.
	 */
	public Administrador(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}

	/**
	 * Se pide al usuario (administrador) que seleccione una de las posibles opciones a ejecutar.
	 */
	public void ejecutarOpciones() {
		interfaz = new VentanaAdministrador(this);
	}

	/**
	 * Carga una habitacion
	 */
	public String cargarHabitacion(String id, Tipo tipo, String ubicacion, boolean tieneBalcon, boolean tieneVista, 
			boolean tieneCocina, int tamanioM2, boolean aireAcondicionado, boolean calefaccion, boolean tv, boolean cafetera, 
			boolean ropaCamaTapetes, boolean plancha, boolean secador, boolean voltajeAC, boolean usbA, boolean usbC, 
			boolean desayuno) {
		String mensaje="Se cargó la habitacion correctamente";
		try {
			Inventario.registrarHabitacion(id, tipo, ubicacion, tieneBalcon, tieneVista, tieneCocina,
					tamanioM2, aireAcondicionado, calefaccion, tv, cafetera, ropaCamaTapetes, 
					plancha, secador, voltajeAC, usbA, usbC, desayuno);
		} catch (Exception e) {
			mensaje= e.getMessage();
		}
		return mensaje;
	}
	
	/**
	 * Carga una cama
	 */
	public String cargarCama(String idHab, Tipo tipoHab, String tamanio, int capacidad, boolean soloNinios) {
		String mensaje="Se cargó la cama correctamente";
		try {
			Inventario.registrarCama(idHab, tipoHab, tamanio, capacidad, soloNinios);
		} catch (Exception e) {
			mensaje= e.getMessage();
		}
		return mensaje;
	}

	/**
	 * Carga varias habitaciones desde un archivo determinado
	 */
	public String cargarHabitaciones() {
		String mensaje = "";
		try {
			Inventario.cargarHabitaciones();
			Inventario.cargarCamas();
			Inventario.cargarReservas();
			mensaje = "¡Habitaciones y camas cargadas!";
		} catch (Exception e) {
			mensaje = e.getMessage();
		}
		return mensaje;
	}

	/**
	 * Carga la tarifa de un tipo de habitacion para ciertas fechas.
	 */
	public String cargarTarifaHabitacion(Tipo tipo, int tarifa, String fInicio, String fFin, ArrayList<DayOfWeek> dias) {
		// Se puede usar DayOfWeek.of(int) para sacar un DayOfWeek por número
		String mensaje="Se cargó la habitación correctamente";
		try {
			TipoHabitacion tipoHab = Inventario.getTipoHabitacion(tipo);
			// Vista
			System.out.println(tipoHab.hayTarifa());
			// Vista
			LocalDate inicio = LocalDate.parse(fInicio);
			LocalDate fin = LocalDate.parse(fFin);

			while (inicio.isBefore(fin) || inicio.equals(fin)) {
				DayOfWeek diaSemana = inicio.getDayOfWeek();
				if (dias.contains(diaSemana)) {
					Inventario.agregarTarifa(tipo, inicio, tarifa);
				}
				inicio = inicio.plusDays(1);
			}
		} catch (Exception e) {
			mensaje = "No se ingresó una fecha válida";
		}
		return mensaje;
		
	}

	/**
	 * Identifica el tipo de servicio y cabia la tarifa.
	 */
	public String cambiarTarifaServicio(Nombre nombre, int tarifa) {
		String mensaje=Catalogo.setTarifa(nombre, tarifa);
		return mensaje;
	}

	/**
	 * Carga el menu del restaurante desde un archivo determinado.
	 */
	public String[] cargarMenuRestaurante() {
		String[] mensaje;
		try {
			Restaurante.cargarPlatos();
			mensaje = Restaurante.getPlatos();
		} catch (Exception e) {
			mensaje = new String[1];
			mensaje[0] = e.getMessage();
		}
		return mensaje;
	}

	/**
	 * Configura las caracteristicas de un plato.
	 */
	public String configurarPlato(String id, int precio, boolean servicio) {
		String mensaje = "";
		try {
			Restaurante.setPlato(id, precio, servicio);
			mensaje = "Se ajustó el plato";
		} catch (Exception e) {
			mensaje =  e.getMessage();
		}
		return mensaje;
	}
	
	//Para las pruebas//
	public String[] cargarMenuRestaurantePrueba(String CSV) {
		String[] mensaje;
		try {
			Restaurante.cargarPlatosPrueba(CSV);
			mensaje = Restaurante.getPlatos();
		} catch (Exception e) {
			mensaje = new String[1];
			mensaje[0] = e.getMessage();
		}
		return mensaje;
	}
	public String cargarHabitacionesPrueba(String rutaHabitaciones,String rutaCamas, String rutaReservas) {
		String mensaje = "";
		try {
			Inventario.cargarHabitacionesPrueba(rutaHabitaciones);
			Inventario.cargarCamasPrueba(rutaCamas);
			Inventario.cargarReservasPrueba(rutaReservas);
			mensaje = "¡Habitaciones y camas cargadas!";
		} catch (Exception e) {
			mensaje = e.getMessage();
		}
		return mensaje;
	}

}

package usuarios;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import catalogo.Catalogo;
import catalogo.Nombre;
import hotel.Hotel;
import inventario.Habitacion;
import inventario.Inventario;
import inventario.Tipo;
import inventario.TipoHabitacion;
import vistaRecepcionista.VentanaRecepcionista;
import huespedes.*;
import pasarelasPago.PasarelaPago;

public class Recepcionista extends Usuario {
	private VentanaRecepcionista interfaz;
	private static final String HORA_CHECK_IN = "T14:00:00";

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Recepcionista(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}
	
	public static String getHoraCheckIn() {
		return HORA_CHECK_IN;
	}
	
	public void ejecutarOpciones() {
		interfaz = new VentanaRecepcionista(this);
	}
	
	public String[] consultarInventario(Tipo tipo) {
		TipoHabitacion tipoHab = Inventario.getTipoHabitacion(tipo);
		return tipoHab.consultarHabitaciones();
	}

	/**
	 * Consulta las habitaciones de un tipo de habitación
	 */
	public String[] consultarHabitacion(Tipo tipo, String id) {
		TipoHabitacion tipoHab = Inventario.getTipoHabitacion(tipo);
		Habitacion habitacion = tipoHab.getHabitacion(id);
		return habitacion.consultarHabitacion();
	}
	
	public ArrayList<String[]> consultarCamas(Tipo tipo, String id) {
		TipoHabitacion tipoHab = Inventario.getTipoHabitacion(tipo);
		Habitacion habitacion = tipoHab.getHabitacion(id);
		ArrayList<String[]> infoCamas = habitacion.consultarCamas();
		return infoCamas;
	}
	
	public String[] consultarHotel() {
		return Inventario.consultarHotel();
	}

	/**
	 * Hace una reserva a nombre de un huésped
	 */
	public static Reserva crearReserva(String fInicio, String fFin, int adultosE, int niniosE, String nombre, String documento, int edad, String correo, String celular) throws Exception {
			LocalDateTime fechaInicio, fechaFin;
			try {
				fechaInicio = LocalDateTime.parse(fInicio + HORA_CHECK_IN);
				fechaFin = LocalDateTime.parse(fFin + HORA_CHECK_IN);
				if(fechaFin.isBefore(fechaInicio)) {
					throw new Exception("La fecha inicial debe ser igual o previa a la final");
				}
			} catch (Exception e) {
				throw new Exception("Ingrese la fecha en formato año-mes-día (YYYY-MM-DD)");
			}
			
			if(edad >= 12) {
				if (!nombre.isEmpty() && !correo.isEmpty()) {
					Huesped aCargo = new Huesped(nombre, documento, edad, correo, celular);
					if(adultosE < 1) {
						throw new Exception("Debe haber mínimo un adulto esperado");
					}
					Reserva nueva = new Reserva(fechaInicio, fechaFin, adultosE, niniosE, aCargo);
					return nueva;
				} else {
					throw new Exception("Todos los campos deben estar completos");
				}
			} else {
				throw new Exception("Debe ser mayor de 12 años para ser un huésped a cargo");
			}
	}
	
	public static void agregarReserva(Reserva reserva) throws IOException {
		Inventario.agregarReserva(reserva);
	}

	/**
	 * Cancela una reserva
	 * @throws Exception 
	 */
	public void cancelarReserva(String id) throws Exception {
		Inventario.eliminarReserva(id);
	}
	
	public void hacerCheckIn(Grupo nuevo) throws Exception {
		Inventario.actualizarTieneGrupo(nuevo.getId());
		Hotel.agregarGrupo(nuevo);
	}
	
	/**
	 * Registra un consumo de alojamiento
	 */
	public String registrarConsumo(String idGrupo, String documento, boolean pagado) {
		String mensaje = "";
		try {
			if (Catalogo.getDisponibleAhora(Nombre.ALOJAMIENTO)) {
				Grupo grupo = Hotel.getGrupo(idGrupo);
				if (grupo != null) {
					Huesped huesped = grupo.getHuesped(documento);
					if (huesped != null) {
						String tipo = Catalogo.getNombre(Nombre.ALOJAMIENTO);
						Reserva reserva = grupo.getReserva();
						String descripcion = reserva.getDescription();
						int tarifa = reserva.getTarifa();
						grupo.agregarConsumo(tipo, descripcion, tarifa, huesped, pagado);
						Hotel.agregarGrupo(grupo);
						mensaje = "¡Consumo Registrado!";
					} else {
						mensaje = "El huésped no existe en el grupo";
					}
				} else {
					mensaje = "El grupo no existe";
				}
			} else {
				mensaje = "El servicio no está disponible en este momento del día";
			}
		} catch (Exception e) {
			mensaje = "No se pudo actualizar el registro de consumo";
		}	
		return mensaje;
	}
	
	public ArrayList<String> getConsumos(String id) throws Exception {
		Grupo grupo = Hotel.getGrupo(id);
		if(grupo != null) {
			return grupo.getStringsConsumos();
		} else {
			throw new Exception("El grupo no existe");
		}
	}
	
	public String hacerCheckOut(String id, String tarjeta, String nombre, String documento, String pasarela) {
		String mensaje = "";
		Grupo grupo = Hotel.getGrupo(id);
		if(grupo != null) {
			try {
				Class clase = Class.forName("pasarelasPago." + pasarela);
				PasarelaPago pasarelaPago = (PasarelaPago) clase.getDeclaredConstructor(String.class).newInstance(Inventario.getNumeroCuentaHotel());
				mensaje = pasarelaPago.hacerTransaccion(tarjeta, nombre, documento, grupo.getValorConsumos());
				if (mensaje.equals("Exitoso")){
					grupo.pagarTodosConsumos();
					Inventario.actualizarTieneGrupo(id);
					Hotel.eliminarGrupo(id);
				}
			} catch (ClassNotFoundException e){
				mensaje = "La pasarela no está disponible";
			}catch (Exception e1){
				mensaje = "Ocurrió un error en el check-out";
			}
		} else {
			mensaje = "El grupo no existe";
		}
		return mensaje;
	}
	
}

package huespedes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import inventario.Habitacion;

public class Reserva {
	private static int numReservas = 0;
	private String id;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private int adultosEsperados;
	private int niniosEsperados;
	private int tarifaTotal = 0;
	private HashMap<String, Habitacion> habitaciones = new HashMap<String, Habitacion>();
	private Huesped aCargo;
	private boolean tieneGrupo;
	private boolean pagado;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Reserva(LocalDateTime fechaInicio, LocalDateTime fechaFin, int adultosEsperados, int niniosEsperados,
			Huesped aCargo) {
		numReservas++;
		this.id = String.valueOf(numReservas);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.adultosEsperados = adultosEsperados;
		this.niniosEsperados = niniosEsperados;
		this.aCargo = aCargo;
		this.tieneGrupo = false;
		this.pagado = false;
	}
	
	public Reserva(LocalDateTime fechaInicio, LocalDateTime fechaFin, int adultosEsperados,
			int niniosEsperados, int tarifaTotal, Huesped aCargo, boolean tieneGrupo, boolean pagado) {
		numReservas++;
		this.id = String.valueOf(numReservas);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.adultosEsperados = adultosEsperados;
		this.niniosEsperados = niniosEsperados;
		this.tarifaTotal = tarifaTotal;
		this.aCargo = aCargo;
		this.tieneGrupo = tieneGrupo;
		this.pagado = pagado;
	}
	
	public static int getNumReservas() {
		return numReservas;
	}

	/**
	 * Obtiene el ID de reserva
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Obtiene la descripcion de la reserva.
	 */
	public String getDescription() {
		String descripcion = "";
		ArrayList<Habitacion> lista = getHabitaciones();
		for(Habitacion habitacion: lista) {
			descripcion += "\n- " + habitacion.getTipo() + ": " + habitacion.getId() + " - $" + habitacion.getTarifa();
		}
		return descripcion;
	}

	/**
	 * Obtiene el huesped a cargo de la reserva
	 */
	public Huesped getACargo() {
		return this.aCargo;
	}

	/**
	 * Obtiene la tarifa de la reserva
	 */
	public int getTarifa() {
		return this.tarifaTotal;
	}
	
	public LocalDateTime getFechaInicio() {
		return this.fechaInicio;
	}
	
	public LocalDateTime getFechaFin() {
		return this.fechaFin;
	}
	
	/**
	 * Obtiene los adultos esperados
	 */
	public int getAdultosEsperados() {
		return this.adultosEsperados;
	}
	
	/**
	 * Obtiene los niños esperados
	 */
	public int getNiniosEsperados() {
		return this.niniosEsperados;
	}
	
	/**
	 * Obtiene si la reserva se encuentra entre dos fechas
	 */
	public boolean getEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
		boolean esta = false;
		while(inicio.isBefore(fin) || inicio.equals(fin)) {
			if(this.fechaInicio.isBefore(inicio) && this.fechaFin.isAfter(inicio)) {
				esta = true;
				break;
			}
			inicio = inicio.plusDays(1);
		}
		return esta;
	}
	
	public boolean getFechaEntreFechas(LocalDate fecha) {
		LocalDate inicio = fechaInicio.toLocalDate();
		LocalDate fin = fechaFin.toLocalDate();
		return (inicio.equals(fecha) || inicio.isBefore(fecha)) && (fin.equals(fecha) ||fin.isAfter(fecha));
	}
	
	public ArrayList<LocalDate> getFechas() {
		LocalDate inicio = fechaInicio.toLocalDate();
		LocalDate fin = fechaFin.toLocalDate();
		ArrayList<LocalDate> fechas = new ArrayList<LocalDate>();
		while(inicio.isBefore(fin) || inicio.equals(fin)) {
			fechas.add(inicio);
			inicio = inicio.plusDays(1);
		}
		return fechas;
	}

	/**
	 * Obtiene el formato de la reserva para CSV
	 */
	public String getLineaCSV() {
		String linea = this.id + "," + this.fechaInicio.toString() + "," + this.fechaFin.toString() + ","
				+ this.adultosEsperados + "," + this.niniosEsperados + "," + this.tarifaTotal + ",true," + this.tieneGrupo + ",";
		linea += aCargo.getNombre() + "#" + aCargo.getDocumento() + "#" + aCargo.getEdad() + "#" + aCargo.getCorreo()
				+ "#" + aCargo.getCelular() + ",";
		ArrayList<Habitacion> listaHab = getHabitaciones();
		for (Habitacion habitacion : listaHab) {
			linea += habitacion.getTipo() + "-" + habitacion.getId() + "-" + habitacion.getTarifa() + "#";
		}
		linea += "," + pagado;
		return linea;
	}
	
	/**
	 * Obtiene si la reserva ya tiene un grupo asignado
	 */
	public boolean getTieneGrupo() {
		return this.tieneGrupo;
	}

	/**
	 * Obtiene las habitaciones en forma de lista
	 */
	public ArrayList<Habitacion> getHabitaciones() {
		return new ArrayList<Habitacion>(this.habitaciones.values());
	}
	
	public boolean getPagado() {
		return pagado;
	}
	
	/**
	 * Obtiene si la reserva puede cancelarse (Si no quedan 48 horas)
	 */
	public boolean cancelable(){
		LocalDateTime ahora = LocalDateTime.now();
		ahora = ahora.plusHours(48);
		return ahora.isBefore(this.fechaInicio) || ahora.equals(this.fechaInicio);
	}
	
	/**
	 * Actualiza el valor tieneGrupo
	 */
	public void actualizarTieneGrupo() {
		this.tieneGrupo = !this.tieneGrupo;
	}

	/**
	 * Agrega habitaciones con su respectiva tarifa
	 */
	public void agregarHabitacion(Habitacion habitacion, int tarifa) {
		this.habitaciones.put(habitacion.getId(), habitacion);
		this.tarifaTotal += tarifa;
	}

	/**
	 * Agrega habitaciones a la reserva
	 */
	public void agregarHabitacion(Habitacion habitacion) {
		this.habitaciones.put(habitacion.getId(), habitacion);
	}
	
	public void pagarReserva() {
		pagado = true;
	}

}

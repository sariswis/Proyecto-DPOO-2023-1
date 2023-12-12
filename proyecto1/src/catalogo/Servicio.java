package catalogo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class Servicio {
	protected Nombre nombre;
	protected String ubicacion;
	protected String descripcion;
	protected int tarifa;
	protected HashMap<DayOfWeek, DiaDisponibilidad> disponibilidad = new HashMap<DayOfWeek, DiaDisponibilidad>();

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Servicio(Nombre nombreR, String ubicacionR, String descripcionR, int tarifaR) {
		nombre = nombreR;
		ubicacion = ubicacionR;
		descripcion = descripcionR;
		tarifa = tarifaR;
	}

	/**
	 * Se obtiene el nombre del servicio.
	 */
	public String getNombre() {
		return nombre.toString().toLowerCase();
	}

	/**
	 * Se obtiene la descripcion del servicio.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Se obtiene la informacion del servicio.
	 */
	public int getTarifa() {
		return tarifa;
	}

	/**
	 * Se obtiene la disponibilidad del servicio en el momento.
	 */
	public boolean getDisponibleAhora() {
		boolean disponible = false;
		LocalDate fecha = LocalDate.now();
		DayOfWeek diaSemana = fecha.getDayOfWeek();
		LocalTime hora = LocalTime.now();
		DiaDisponibilidad dia = disponibilidad.get(diaSemana);
		if (dia != null && dia.getEntreHoras(hora)) {
			disponible = true;
		}
		return disponible;
	}

	/**
	 * Agrega el dia de disponibilidad de un servicio.
	 */
	public void agregarDiaDisponibilidad(DayOfWeek diaSemana, DiaDisponibilidad dia) {
		disponibilidad.put(diaSemana, dia);
	}

	/**
	 * Se cambia la tarifa a un servicio.
	 */
	public void setTarifa(int nuevaTarifa) {
		tarifa = nuevaTarifa;
	}
}

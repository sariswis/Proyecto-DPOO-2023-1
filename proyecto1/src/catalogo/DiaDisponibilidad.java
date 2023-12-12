package catalogo;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class DiaDisponibilidad {
	private DayOfWeek diaSemana;
	private LocalTime horaInicio;
	private LocalTime horaFin;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public DiaDisponibilidad(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFin) {
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	/**
	 * Obtiene el dia de la semana de disponibilidad.
	 */
	public DayOfWeek getDiaSemana() {
		return this.diaSemana;
	}

	/**
	 * Obtiene la hora de inicio de disponibilidad.
	 */
	public LocalTime getHoraInicio() {
		return this.horaInicio;
	}

	/**
	 * Obtiene la hora de fin de disponibilidad.
	 */
	public LocalTime getHoraFin() {
		return this.horaFin;
	}

	/**
	 * Obtiene el resultado de los valores entre las horas de inicio y de fin.
	 */
	public boolean getEntreHoras(LocalTime ahora) {
		return (horaInicio.isBefore(ahora) || horaInicio.equals(ahora))
				&& (horaFin.isAfter(ahora) || horaFin.equals(ahora));
	}

}

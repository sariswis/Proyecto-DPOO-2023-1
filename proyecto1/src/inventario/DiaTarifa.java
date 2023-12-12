package inventario;

import java.time.LocalDate;
import java.time.DayOfWeek;

public class DiaTarifa {
	private LocalDate fecha;
	private DayOfWeek diaSemana;
	private int tarifa;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public DiaTarifa(LocalDate fecha, int tarifa) {
		this.fecha = fecha;
		this.diaSemana = fecha.getDayOfWeek();
		this.tarifa = tarifa;
	}

	/**
	 * Obtiene la fecha
	 */
	public LocalDate getFecha() {
		return this.fecha;
	}

	/**
	 * Obtiene el dia de la semana.
	 */
	public DayOfWeek getDiaSemana() {
		return this.diaSemana;
	}

	/**
	 * Obtiene la tarifa del dia.
	 */
	public int getTarifa() {
		return this.tarifa;
	}

	/**
	 * Obtiene la información para CSV
	 */
	public String getLineaCSV() {
		return this.fecha.toString() + "," + this.diaSemana.toString() + "," + this.tarifa;
	}

	/**
	 * Asigna una tarifa al dia.
	 */
	public void setTarifa(int tarifa) {
		if (tarifa < this.tarifa) {
			this.tarifa = tarifa;
		}
	}

}

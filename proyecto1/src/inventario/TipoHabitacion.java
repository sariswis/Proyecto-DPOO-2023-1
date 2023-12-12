package inventario;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import hotel.Hotel;
import huespedes.Reserva;

import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TipoHabitacion {
	private  Tipo tipo;
	private  String infoTarifas;
	private  LinkedHashMap<LocalDate, DiaTarifa> tarifas = new LinkedHashMap<LocalDate, DiaTarifa>();
	private  HashMap<String, Habitacion> habitaciones = new HashMap<String, Habitacion>();

	/**
	 * Crea un tipo de habitación
	 */
	public TipoHabitacion(Tipo tipo) {
		this.tipo = tipo;
		this.infoTarifas = "datos/tarifas_" + tipo + ".csv";
		try {
			cargarTarifas();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Obtiene el tipo
	 */
	public Tipo getTipo() {
		return this.tipo;
	}

	/**
	 * Obtiene un día de tarifa por fecha
	 */
	public DiaTarifa getDiaTarifa(LocalDate fecha) {
		return tarifas.get(fecha);
	}
	
	/**
	 * Obtiene la tarifa total entre dos fechas por noches
	 */
	public int getTarifaEntreFechas(LocalDate inicio, LocalDate fin) throws Exception {
		int tarifa = 0;
		while(inicio.isBefore(fin) || inicio.equals(fin)) {
			DiaTarifa diaTarifa = getDiaTarifa(inicio);
			if (diaTarifa != null) {
				tarifa += diaTarifa.getTarifa();
			} else {
				throw new Exception("No hay tarifa para " + inicio.toString());
			}
			inicio = inicio.plusDays(1);
		}
		return tarifa;
	}
	

	/**
	 * Obtiene una habitación por id
	 */
	public  Habitacion getHabitacion(String id) {
		return habitaciones.get(id);
	}
	
	public  HashMap<String, Habitacion> getHabitaciones() {
		return habitaciones;
	}

	/**
	 * Carga una habitación
	 */
	public void cargarHabitacion(String id, Habitacion nueva) {
		habitaciones.put(id, nueva);
	}
	
	/**
	 * Carga una cama
	 */
	public void cargarCama(String idHabitacion, Cama nueva) {
		Habitacion habitacion = getHabitacion(idHabitacion);
		if (habitacion != null) {
			habitacion.agregarCama(nueva);
			habitaciones.put(idHabitacion, habitacion);
		}
	}
	
	/**
	 * Carga tarifas desde un archivo
	 */
	public void cargarTarifas() throws NumberFormatException, IOException {
		FileReader filereader = new FileReader(infoTarifas, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			LocalDate fecha = LocalDate.parse(line[0]);
			int tarifa = Integer.parseInt(line[2]);
			cargarTarifa(fecha, tarifa);
		}
		csvreader.close();
	}
	
	/**
	 * Carga una tarifa
	 */
	public void cargarTarifa(LocalDate fecha, int tarifa) {
		DiaTarifa diaTarifa;
		if (tarifas.containsKey(fecha)) {
			diaTarifa = getDiaTarifa(fecha);
			diaTarifa.setTarifa(tarifa);
		} else {
			diaTarifa = new DiaTarifa(fecha, tarifa);
		}
		tarifas.put(fecha, diaTarifa);
	}
	
	/**
	 * Agrega una tarifa al CSV y al sistema
	 */
	public void agregarTarifa(LocalDate fecha, int tarifa) throws IOException, CsvException {
		DiaTarifa diaTarifa;
		if (tarifas.containsKey(fecha)) {
			int searchColumn = 0;
			int replaceColumn = 2;
			diaTarifa = getDiaTarifa(fecha);
			diaTarifa.setTarifa(tarifa);
			Hotel.searchUpdateCSV(infoTarifas, fecha.toString(), String.valueOf(tarifa), searchColumn, replaceColumn);
		} else {
			diaTarifa = new DiaTarifa(fecha, tarifa);
			FileWriter filewriter = new FileWriter(infoTarifas, StandardCharsets.UTF_8, true);
			CSVWriter writer = new CSVWriter(filewriter);
			String line = diaTarifa.getLineaCSV();
			String[] record = line.split(",");
			writer.writeNext(record, false);
			writer.close();
		}
		
		tarifas.put(fecha, diaTarifa);
	}

	/**
	 * Obtiene si hay tarifa para los próximos 365 días
	 */
	public String hayTarifa() {
		String mensaje;
		LocalDate fecha = LocalDate.now();
		LocalDate fechaUnAnio = fecha.plusDays(365);
		LocalDate fechaSinTarifa = checkTarifa(fecha);
		if (fechaSinTarifa.isBefore(fechaUnAnio)) {
			mensaje = "La próxima fecha sin tarifa es " + fechaSinTarifa.toString();
		} else {
			mensaje = "Dentro de los próximos 365 días sí hay tarifa";
		}
		return mensaje;
	}

	/**
	 * Obtiene la primera fecha para la que no hay tarifa
	 */
	public LocalDate checkTarifa(LocalDate fecha) {
		for (int i = 0; i < 365; i++) {
			if (!tarifas.containsKey(fecha)) {
				break;
			}
			fecha = fecha.plusDays(1);
		}
		return fecha;
	}
	
	/**
	 * Agrega una reserva
	 */
	public void agregarReserva(String idHabitacion, Reserva reserva) {
		Habitacion habitacion = habitaciones.get(idHabitacion);
		if (habitacion != null) {
			habitacion.agregarReserva(reserva);
			habitaciones.put(idHabitacion, habitacion);
		}
	}
	
	/**
	 * Elimina una reserva
	 */
	public void eliminarReserva(String idHabitacion, String idReserva) {
		Habitacion habitacion = habitaciones.get(idHabitacion);
		if (habitacion != null) {
			habitacion.eliminarReserva(idReserva);
			habitaciones.put(idHabitacion, habitacion);
		}
	}
	
	/**
	 * Consulta todas las habitaciones
	 */
	public String[] consultarHabitaciones() {
		ArrayList<Habitacion> lista = new ArrayList<Habitacion>(habitaciones.values());
		int tamaño = lista.size();
		String[] mensajes = new String[tamaño];
		String mensaje;
		Habitacion habitacion;
		for(int i=0; i<tamaño; i++) {
			habitacion = lista.get(i);
			mensaje =  "Habitación " + habitacion.getId() + ": ";
			if (habitacion.getOcupada()) {
				mensaje += "Ocupada";
			} else {
				mensaje += "Libre";
			}
			mensajes[i] = mensaje;
		}
		return mensajes;
	}
	
	public ArrayList<String> consultarDisponibilidad(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		ArrayList<Habitacion> lista = new ArrayList<Habitacion>(habitaciones.values());
		ArrayList<String> disponibilidad = new ArrayList<String>();
		String mensaje;
		for(Habitacion habitacion: lista) {
			if(habitacion.getDisponibleEntreFechas(fechaInicio, fechaFin)) {
				mensaje = "Id " + habitacion.getId() + ": Habitación " + tipo.toString().replace('_', ' ');
				disponibilidad.add(mensaje);
			}
		}
		return disponibilidad;
	}
}

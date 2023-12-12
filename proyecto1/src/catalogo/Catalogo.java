package catalogo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;

import com.opencsv.CSVReader;

import hotel.Hotel;

public class Catalogo {
	private static String catalogo;
	private static String horarios;
	private static Restaurante restaurante;
	private static Servicio alojamiento;
	private static Servicio spa;
	private static Servicio guiaTuristico;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Catalogo() {
		catalogo = "datos/catalogo.csv";
		horarios = "datos/horarios.csv";
		try {
			cargarServicios();
			cargarHorarios();
		} catch (Exception e) {
			System.out.println("Ocurrió un error al cargar el catálogo");
		}
	}

	/**
	 * Obtiene el nombre
	 */
	public static String getNombre(Nombre nombre) {
		String n = null;
		if (nombre.equals(Nombre.ALOJAMIENTO)) {
			n = alojamiento.getNombre();
		} else if (nombre.equals(Nombre.RESTAURANTE)) {
			n = restaurante.getNombre();
		} else if (nombre.equals(Nombre.SPA)) {
			n = spa.getNombre();
		} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
			n = guiaTuristico.getNombre();
		}
		return n;
	}

	/**
	 * Obtiene la tarifa dependiendo del tipo de servicio.
	 */
	public static int getTarifa(Nombre nombre) {
		int n = 0;
		if (nombre.equals(Nombre.ALOJAMIENTO)) {
			n = alojamiento.getTarifa();
		} else if (nombre.equals(Nombre.RESTAURANTE)) {
			n = restaurante.getTarifa();
		} else if (nombre.equals(Nombre.SPA)) {
			n = spa.getTarifa();
		} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
			n = guiaTuristico.getTarifa();
		}
		return n;
	}

	/**
	 * Obtienen la descripcion dependiendo del tipo de servicio.
	 */
	public static String getDescripcion(Nombre nombre) {
		String n = "";
		if (nombre.equals(Nombre.ALOJAMIENTO)) {
			n = alojamiento.getDescripcion();
		} else if (nombre.equals(Nombre.RESTAURANTE)) {
			n = restaurante.getDescripcion();
		} else if (nombre.equals(Nombre.SPA)) {
			n = spa.getDescripcion();
		} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
			n = guiaTuristico.getDescripcion();
		}
		return n;
	}

	/**
	 * Obtiene la disponibilidad en el momento del servicio.
	 */
	public static boolean getDisponibleAhora(Nombre nombre) {
		boolean n = false;
		if (nombre.equals(Nombre.ALOJAMIENTO)) {
			n = alojamiento.getDisponibleAhora();
		} else if (nombre.equals(Nombre.RESTAURANTE)) {
			n = restaurante.getDisponibleAhora();
		} else if (nombre.equals(Nombre.SPA)) {
			n = spa.getDisponibleAhora();
		} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
			n = guiaTuristico.getDisponibleAhora();
		}
		return n;
	}

	/**
	 * Carga al sistema los diferentes tipos de servicos en base a un archivo.
	 */
	public static void cargarServicios() throws IOException {
		FileReader filereader = new FileReader(catalogo, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			Nombre nombre = Nombre.valueOf(line[0]);
			String ubicacion = line[1];
			String descripcion = line[2];
			int tarifa = Integer.parseInt(line[3]);

			if (nombre.equals(Nombre.ALOJAMIENTO)) {
				alojamiento = new Alojamiento(nombre, ubicacion, descripcion, tarifa);
			} else if (nombre.equals(Nombre.RESTAURANTE)) {
				restaurante = new Restaurante(nombre, ubicacion, descripcion, tarifa);
			} else if (nombre.equals(Nombre.SPA)) {
				spa = new Servicio(nombre, ubicacion, descripcion, tarifa);
			} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
				guiaTuristico = new Servicio(nombre, ubicacion, descripcion, tarifa);
			}
		}
		csvreader.close();
	}

	/**
	 * Carga los horarios de disponibilidadd de los servicios en base a un archivo
	 */
	public static void cargarHorarios() throws IOException {
		FileReader filereader = new FileReader(horarios, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			Nombre nombre = Nombre.valueOf(line[0]);
			DayOfWeek diaSemana = DayOfWeek.valueOf(line[1]);
			LocalTime horaInicio = LocalTime.parse(line[2]);
			LocalTime horaFin = LocalTime.parse(line[3]);
			DiaDisponibilidad dia = new DiaDisponibilidad(diaSemana, horaInicio, horaFin);

			if (nombre.equals(Nombre.ALOJAMIENTO)) {
				alojamiento.agregarDiaDisponibilidad(diaSemana, dia);
			} else if (nombre.equals(Nombre.RESTAURANTE)) {
				restaurante.agregarDiaDisponibilidad(diaSemana, dia);
			} else if (nombre.equals(Nombre.SPA)) {
				spa.agregarDiaDisponibilidad(diaSemana, dia);
			} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
				guiaTuristico.agregarDiaDisponibilidad(diaSemana, dia);
			}
		}
		csvreader.close();
	}

	/**
	 * Asigna las tarifas dependiendo de el servicio.
	 */
	public static String setTarifa(Nombre nombre, int tarifa) {
		String mensaje="Se cambio correctamente la tarifa";
		int row = 0;
		int column = 3;
		if (nombre.equals(Nombre.ALOJAMIENTO)) {
			alojamiento.setTarifa(tarifa);
			row = 1;
		} else if (nombre.equals(Nombre.RESTAURANTE)) {
			restaurante.setTarifa(tarifa);
			row = 2;
		} else if (nombre.equals(Nombre.SPA)) {
			spa.setTarifa(tarifa);
			row = 3;
		} else if (nombre.equals(Nombre.GUIA_TURISTICO)) {
			guiaTuristico.setTarifa(tarifa);
			row = 4;
		}

		if (row != 0) {
			try {
				Hotel.updateCSV(catalogo, String.valueOf(tarifa), row, column);
			} catch (Exception e) {
				mensaje= "No se pudo actualizar el archivo del catálogo";
			}
		}
		return mensaje;
	}

}

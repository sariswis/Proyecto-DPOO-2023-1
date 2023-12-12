package inventario;

import java.io.*;
import java.time.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import hotel.Hotel;
import huespedes.Grupo;
import huespedes.Huesped;
import huespedes.Reserva;
import usuarios.Recepcionista;

public class Inventario {
	private static String info;
	private static String inventario;
	private static String camas;
	private static String infoReservas;
	private static HashMap<String, Reserva> reservas = new HashMap<String, Reserva>();
	private static HashMap<LocalDate, Integer> reservasFecha = new HashMap<LocalDate, Integer>();
	private static InfoHotel infoHotel;
	private static TipoHabitacion estandares = new TipoHabitacion(Tipo.ESTANDAR);
	private static TipoHabitacion suites = new TipoHabitacion(Tipo.SUITE);
	private static TipoHabitacion suitesDobles = new TipoHabitacion(Tipo.SUITE_DOBLE);

	/**
	 * Crea el inventario
	 */
	public Inventario() {
		info = "datos/infoHotel.csv";
		inventario = "datos/inventario.csv";
		camas = "datos/camas.csv";
		infoReservas = "datos/reservas.csv";
		try {
			cargarInfo();
			cargarHabitaciones();
			cargarCamas();
			cargarReservas();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static String getNombreHotel() {
		return infoHotel.getNombreHotel();
	}
	
	public static String getNumeroCuentaHotel() {
		return infoHotel.getNumeroCuenta();
	}
	
	public static int[][][] getMatriz() {
		int[][][] ocupada = new int[12][31][3];
		int year = LocalDate.now().getYear();
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year, 12, 31);

		LocalDate currentDate = startDate;
		Integer consulta;
		int mes, dia;
		while (!currentDate.isAfter(endDate)) {
	    	mes = currentDate.getMonthValue() - 1;
	    	dia = currentDate.getDayOfMonth() - 1;
	    	consulta = reservasFecha.get(currentDate);
			ocupada[mes][dia] = getColor(consulta);
		    currentDate = currentDate.plusDays(1);
		}
		return ocupada;
	}
	
	public static int[] getColor(Integer consulta) {
		int [] color = new int[3];
		if (consulta != null) {
			int porcentaje = (consulta * 100) / Reserva.getNumReservas();

			if (0 <= porcentaje && porcentaje <= 20) {
				color[0] = 182;
				color[1] = 231;
				color[2] = 198;
			} else if (20 < porcentaje && porcentaje <= 40) {
				color[0] = 147;
				color[1] = 231;
				color[2] = 175;
			} else if (40 < porcentaje && porcentaje <= 60) {
				color[0] = 101;
				color[1] = 231;
				color[2] = 144;
			} else if (60 < porcentaje && porcentaje <= 80) {
				color[0] = 49;
				color[1] = 230;
				color[2] = 109;
			} else {
				color[0] = 2;
				color[1] = 228;
				color[2] = 77;
			}
			
		} else {
			color[0] = 223;
			color[1] = 223;
			color[2] = 223;
		}
		
		return color;
	}

	/**
	 * Obtiene una reserva
	 */
	public static Reserva getReserva(String id) {
		return reservas.get(id);
	}
	
	/**
	 * Obtiene un tipo de habitación
	 */
	public static TipoHabitacion getTipoHabitacion(Tipo tipo) {
		if (tipo.equals(Tipo.ESTANDAR)) {
			return estandares;
		} else if (tipo.equals(Tipo.SUITE)) {
			return suites;
		} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
			return suitesDobles;
		} else {
			return estandares;
		}
	}
	
	/**
	 * Obtiene el archivo del inventario
	 */
	public static String getInventario() {
		return inventario;
	}
	
	/**
	 * Obtiene el archivo de camas
	 */
	public static String getCamas() {
		return camas;
	}
	
	/**
	 * Obtiene si tiene grupo la reserva
	 * @throws Exception 
	 */
	public static boolean getTieneGrupo(String id) throws Exception {
		Reserva reserva = getReserva(id);
		if (reserva != null) {
			 return reserva.getTieneGrupo();
		} else {
			throw new Exception("La reserva no existe");
		}
	}
	
	public static String[] consultarHotel() {
		return infoHotel.consultarHotel();
	}
	
	public static void cargarInfo() throws IOException, Exception {
		FileReader filereader = new FileReader(info, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;

		if ((line = csvreader.readNext()) != null) {
			String nombre = line[0];
			boolean parqueaderoGratis = Boolean.parseBoolean(line[1]);
			boolean piscina = Boolean.parseBoolean(line[2]);
			boolean zonasHumedas = Boolean.parseBoolean(line[3]);
			boolean bbq = Boolean.parseBoolean(line[4]);
			boolean wifiGratis = Boolean.parseBoolean(line[5]);
			boolean recepcion24h = Boolean.parseBoolean(line[6]);
			boolean petFriendly = Boolean.parseBoolean(line[7]);
			String numeroCuenta = line[8];
			infoHotel = new InfoHotel(nombre, parqueaderoGratis, piscina, zonasHumedas, bbq, 
								wifiGratis, recepcion24h, petFriendly, numeroCuenta);
		} else {
			csvreader.close();
			throw new Exception("No hay información del hotel");
		}
		csvreader.close();
	}
	
	/**
	 * Carga las reservas desde un archivo
	 */
	public static void cargarReservas() throws IOException {
		FileReader filereader = new FileReader(infoReservas, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;

		while ((line = csvreader.readNext()) != null) {
			if(Boolean.parseBoolean(line[6])) {
				String id = line[0];
				LocalDateTime fechaInicio = LocalDateTime.parse(line[1]);
				LocalDateTime fechaFin = LocalDateTime.parse(line[2]);
				int adultosEsperados = Integer.parseInt(line[3]);
				int niniosEsperados = Integer.parseInt(line[4]);
				int tarifaTotal = Integer.parseInt(line[5]);
				boolean tieneGrupo = Boolean.parseBoolean(line[7]);
				
				String[] infoHuesped = line[8].split("#");
				String nombre = infoHuesped[0];
				String documento = infoHuesped[1];
				int edad = Integer.parseInt(infoHuesped[2]);
				String correo = infoHuesped[3];
				String celular = infoHuesped[4];
				Huesped aCargo = new Huesped(nombre, documento, edad, correo, celular);
				
				boolean pagado = Boolean.parseBoolean(line[10]);
			
				Reserva reserva = new Reserva(fechaInicio, fechaFin, adultosEsperados, niniosEsperados, tarifaTotal, aCargo, tieneGrupo, pagado);
				if(tieneGrupo) {
					Hotel.agregarGrupo(new Grupo(reserva));
				}
				
				String[] habs = line[9].split("#");
				Habitacion habitacion;
				for(String hab: habs) {
					String[] parts = hab.split("-");
					Tipo tipo = Tipo.valueOf(parts[0]);
					String idHabitacion = parts[1];
					int tarifa = Integer.parseInt(parts[2]);
					if (tipo.equals(Tipo.ESTANDAR)) {
						habitacion = estandares.getHabitacion(idHabitacion);
						habitacion.setTarifa(tarifa);
						reserva.agregarHabitacion(habitacion);
					} else if (tipo.equals(Tipo.SUITE)) {
						habitacion = suites.getHabitacion(idHabitacion);
						habitacion.setTarifa(tarifa);
						reserva.agregarHabitacion(habitacion);
					} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
						habitacion = suitesDobles.getHabitacion(idHabitacion);
						habitacion.setTarifa(tarifa);
						reserva.agregarHabitacion(habitacion);
					}
				}
				cargarReserva(reserva);
			}
		}
		csvreader.close();
	}
	
	/**
	 * Carga una reserva
	 */
	public static void cargarReserva(Reserva reserva) {
		reservas.put(reserva.getId(), reserva);
		ArrayList<LocalDate> fechas = reserva.getFechas();
		for(LocalDate fecha: fechas) {
			Integer cantidad = reservasFecha.get(fecha);
			if(cantidad != null) {
				reservasFecha.replace(fecha, cantidad + 1);
			} else {
				reservasFecha.put(fecha, 0);
			}
		}
		
		ArrayList<Habitacion> habitaciones = reserva.getHabitaciones();
		for(Habitacion habitacion: habitaciones) {
			Tipo tipo = habitacion.getTipo();
			if (tipo.equals(Tipo.ESTANDAR)) {
				estandares.agregarReserva(habitacion.getId(), reserva);
			} else if (tipo.equals(Tipo.SUITE)) {
				suites.agregarReserva(habitacion.getId(), reserva);
			} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
				suitesDobles.agregarReserva(habitacion.getId(), reserva);
			}
		}
	}

	/**
	 * Carga las habitaciones desde un archivo
	 */
	public static void cargarHabitaciones() throws IOException {
		FileReader filereader = new FileReader(inventario, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String id = line[0];
			Tipo tipo = Tipo.valueOf(line[1]);
			String ubicacion = line[2];
			boolean tieneBalcon = Boolean.parseBoolean(line[3]);
			boolean tieneVista = Boolean.parseBoolean(line[4]);
			boolean tieneCocina = Boolean.parseBoolean(line[5]);
			int tamanioM2 = Integer.parseInt(line[6]);
			boolean aireAcondicionado = Boolean.parseBoolean(line[7]);
			boolean calefaccion = Boolean.parseBoolean(line[8]);
			boolean tv = Boolean.parseBoolean(line[9]);
			boolean cafetera = Boolean.parseBoolean(line[10]);
			boolean ropaCamaTapetes = Boolean.parseBoolean(line[11]);
			boolean plancha = Boolean.parseBoolean(line[12]);
			boolean secador = Boolean.parseBoolean(line[13]);
			boolean voltajeAC = Boolean.parseBoolean(line[14]);
			boolean usbA = Boolean.parseBoolean(line[15]);
			boolean usbC = Boolean.parseBoolean(line[16]);
			boolean desayuno = Boolean.parseBoolean(line[17]);
			Habitacion nueva = new Habitacion(id, tipo, ubicacion, tieneBalcon, tieneVista, tieneCocina,
											tamanioM2, aireAcondicionado, calefaccion, tv, cafetera, ropaCamaTapetes, 
											plancha, secador, voltajeAC, usbA, usbC, desayuno);
			if (tipo.equals(Tipo.ESTANDAR)) {
				estandares.cargarHabitacion(id, nueva);
			} else if (tipo.equals(Tipo.SUITE)) {
				suites.cargarHabitacion(id, nueva);
			} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
				suitesDobles.cargarHabitacion(id, nueva);
			}
		}
		csvreader.close();
	}

	/**
	 * Carga las camas desde un archivo
	 */
	public static void cargarCamas() throws IOException {
		FileReader filereader = new FileReader(camas, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String idHabitacion = line[0];
			Tipo tipo = Tipo.valueOf(line[1]);
			String tamanio = line[2];
			int capacidad = Integer.parseInt(line[3]);
			boolean soloNinios = Boolean.parseBoolean(line[4]);

			Cama nueva = new Cama(tamanio, capacidad, soloNinios);
			if (tipo.equals(Tipo.ESTANDAR)) {
				estandares.cargarCama(idHabitacion, nueva);
			} else if (tipo.equals(Tipo.SUITE)) {
				suites.cargarCama(idHabitacion, nueva);
			} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
				suitesDobles.cargarCama(idHabitacion, nueva);
			}
		}
		csvreader.close();
	}
	
	/**
	 * Agrega la tarifa de un tipo de habitación
	 */
	public static void agregarTarifa(Tipo tipo, LocalDate inicio, int tarifa) throws IOException, CsvException {
		if (tipo.equals(Tipo.ESTANDAR)) {
			estandares.agregarTarifa(inicio, tarifa);
		} else if (tipo.equals(Tipo.SUITE)) {
			suites.agregarTarifa(inicio, tarifa);
		} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
			suitesDobles.agregarTarifa(inicio, tarifa);
		}
	}

	/**
	 * Registra una nueva habitación en el inventario
	 */
	public static void registrarHabitacion(String id, Tipo tipo, String ubicacion, boolean tieneBalcon, boolean tieneVista, 
			boolean tieneCocina, int tamanioM2, boolean aireAcondicionado, boolean calefaccion, boolean tv, boolean cafetera, 
			boolean ropaCamaTapetes, boolean plancha, boolean secador, boolean voltajeAC, boolean usbA, boolean usbC, 
			boolean desayuno) throws IOException {
		FileWriter filewriter = new FileWriter(inventario, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String[] record = {id, tipo.toString(), ubicacion, String.valueOf(tieneBalcon), String.valueOf(tieneVista), String.valueOf(tieneCocina), 
				String.valueOf(tamanioM2), String.valueOf(aireAcondicionado), String.valueOf(calefaccion),  String.valueOf(tv), String.valueOf(cafetera), 
				String.valueOf(ropaCamaTapetes), String.valueOf(plancha), String.valueOf(secador), String.valueOf(voltajeAC), String.valueOf(usbA), 
				String.valueOf(usbC), String.valueOf(desayuno)};
		writer.writeNext(record, false);
		writer.close();

		Habitacion nueva = new Habitacion(id, tipo, ubicacion, tieneBalcon, tieneVista, tieneCocina,
										tamanioM2, aireAcondicionado, calefaccion, tv, cafetera, ropaCamaTapetes, 
										plancha, secador, voltajeAC, usbA, usbC, desayuno);
		if (tipo.equals(Tipo.ESTANDAR)) {
			estandares.cargarHabitacion(id, nueva);
		} else if (tipo.equals(Tipo.SUITE)) {
			suites.cargarHabitacion(id, nueva);
		} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
			suitesDobles.cargarHabitacion(id, nueva);
		}
	}

	/**
	 * Registra una nueva cama en el inventario
	 */
	public static void registrarCama(String idHabitacion, Tipo tipo, String tamanio, int capacidad, boolean soloNinios) throws IOException {
		FileWriter filewriter = new FileWriter(camas, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String[] record = {idHabitacion, tipo.toString(), tamanio, String.valueOf(capacidad), String.valueOf(soloNinios)};
		writer.writeNext(record, false);
		writer.close();

		Cama nueva = new Cama(tamanio, capacidad, soloNinios);
		if (tipo.equals(Tipo.ESTANDAR)) {
			estandares.cargarCama(idHabitacion, nueva);
		} else if (tipo.equals(Tipo.SUITE)) {
			suites.cargarCama(idHabitacion, nueva);
		} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
			suitesDobles.cargarCama(idHabitacion, nueva);
		}
	}

	/**
	 * Agrega una reserva
	 */
	public static void agregarReserva(Reserva reserva) throws IOException {
		reservas.put(reserva.getId(), reserva);
		ArrayList<LocalDate> fechas = reserva.getFechas();
		for(LocalDate fecha: fechas) {
			Integer cantidad = reservasFecha.get(fecha);
			if(cantidad != null) {
				reservasFecha.replace(fecha, cantidad + 1);
			} else {
				reservasFecha.put(fecha, 0);
			}
		}
		
		FileWriter filewriter = new FileWriter(infoReservas, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String line = reserva.getLineaCSV();
		String[] record = line.split(",");
		writer.writeNext(record, false);
		writer.close();
		
		ArrayList<Habitacion> habitaciones = reserva.getHabitaciones();
		for(Habitacion habitacion: habitaciones) {
			Tipo tipo = habitacion.getTipo();
			if (tipo.equals(Tipo.ESTANDAR)) {
				estandares.agregarReserva(habitacion.getId(), reserva);
			} else if (tipo.equals(Tipo.SUITE)) {
				suites.agregarReserva(habitacion.getId(), reserva);
			} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
				suitesDobles.agregarReserva(habitacion.getId(), reserva);
			}
		}
	}

	/**
	 * Elimina una reserva
	 */
	public static void eliminarReserva(String idReserva) throws Exception {
		Reserva reserva = reservas.get(idReserva);
		if (reserva != null) {
			if(reserva.cancelable()) {
				int column = 6;
				ArrayList<Habitacion> habitaciones = reserva.getHabitaciones();
				for(Habitacion habitacion: habitaciones) {
					Tipo tipo = habitacion.getTipo();
					if (tipo.equals(Tipo.ESTANDAR)) {
						estandares.eliminarReserva(habitacion.getId(), idReserva);
					} else if (tipo.equals(Tipo.SUITE)) {
						suites.eliminarReserva(habitacion.getId(), idReserva);
					} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
						suitesDobles.eliminarReserva(habitacion.getId(), idReserva);
					}
				}
				
				reservas.remove(idReserva);
				try {
					Hotel.updateCSV(infoReservas, "false", Integer.parseInt(idReserva), column);
				} catch (Exception e) {
					throw new Exception("No se pudo actualizar el archivo de reservas");
				}
			} else {
				throw new Exception("No se puede cancelar desde las últimas 48 horas");
			}
		} else {
			throw new Exception("No existe la reserva buscada");
		}
	}
	
	/**
	 * Actualiza los valores del grupo para una reserva en el CSV
	 */
	public static Reserva actualizarTieneGrupo(String id) throws Exception {
		Reserva reserva = getReserva(id);
		if (reserva != null) {
			int column = 7;
			reserva.actualizarTieneGrupo();
			reservas.put(id, reserva);
			try {
				Hotel.updateCSV(infoReservas, String.valueOf(reserva.getTieneGrupo()), Integer.parseInt(id), column);
			} catch (Exception e) {
				System.out.println("No se pudo actualizar el archivo de reservas");
			}
		} else {
			throw new Exception("No existe la reserva buscada");
		}
		return reserva;
	}
	
	public static String convertirBoolean(boolean b) {
		if(b) {
			return "Sí";
		} else {
			return "No";
		}
	}
	
	public static String[] consultarDisponibilidad(String fInicio, String fFin) throws Exception {
		LocalDateTime fechaInicio, fechaFin;
		try {
			fechaInicio = LocalDateTime.parse(fInicio + Recepcionista.getHoraCheckIn());
			fechaFin = LocalDateTime.parse(fFin + Recepcionista.getHoraCheckIn());
		} catch (Exception e) {
			throw new Exception("Ingrese fechas válidas");
		}
		if (fechaFin.isBefore(fechaInicio)) {
			throw new Exception("La fecha final debe ser igual o posterior a la inicial");
		}
		ArrayList<String> consulta = new ArrayList<String>();
		ArrayList<String> consultaE = estandares.consultarDisponibilidad(fechaInicio, fechaFin);
		ArrayList<String> consultaS = suites.consultarDisponibilidad(fechaInicio, fechaFin);
		ArrayList<String> consultaSD = suitesDobles.consultarDisponibilidad(fechaInicio, fechaFin);
		consulta.addAll(consultaE);
		consulta.addAll(consultaS);
		consulta.addAll(consultaSD);
		String[] consultaString = consulta.toArray(new String[consulta.size()]);
		return consultaString;
	}
	
	public static void pagarReserva(String id) {
		Reserva reserva = reservas.get(id);
		if (reserva != null) {
			int column = 10;
			reserva.pagarReserva();
			reservas.put(id, reserva);
			try {
				Hotel.updateCSV(infoReservas, "true", Integer.parseInt(id), column);
			} catch (Exception e) {
				System.out.println("No se pudo actualizar el archivo de reservas");
			}
		}
	}
	//Para las Pruebas//

	/**
	 * Carga las habitaciones desde un archivo
	 */
	public static void cargarHabitacionesPrueba(String csv) throws IOException {
		FileReader filereader = new FileReader(csv, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String id = line[0];
			Tipo tipo = Tipo.valueOf(line[1]);
			String ubicacion = line[2];
			boolean tieneBalcon = Boolean.parseBoolean(line[3]);
			boolean tieneVista = Boolean.parseBoolean(line[4]);
			boolean tieneCocina = Boolean.parseBoolean(line[5]);
			int tamanioM2 = Integer.parseInt(line[6]);
			boolean aireAcondicionado = Boolean.parseBoolean(line[7]);
			boolean calefaccion = Boolean.parseBoolean(line[8]);
			boolean tv = Boolean.parseBoolean(line[9]);
			boolean cafetera = Boolean.parseBoolean(line[10]);
			boolean ropaCamaTapetes = Boolean.parseBoolean(line[11]);
			boolean plancha = Boolean.parseBoolean(line[12]);
			boolean secador = Boolean.parseBoolean(line[13]);
			boolean voltajeAC = Boolean.parseBoolean(line[14]);
			boolean usbA = Boolean.parseBoolean(line[15]);
			boolean usbC = Boolean.parseBoolean(line[16]);
			boolean desayuno = Boolean.parseBoolean(line[17]);
			Habitacion nueva = new Habitacion(id, tipo, ubicacion, tieneBalcon, tieneVista, tieneCocina,
											tamanioM2, aireAcondicionado, calefaccion, tv, cafetera, ropaCamaTapetes, 
											plancha, secador, voltajeAC, usbA, usbC, desayuno);
			if (tipo.equals(Tipo.ESTANDAR)) {
				estandares.cargarHabitacion(id, nueva);
			} else if (tipo.equals(Tipo.SUITE)) {
				suites.cargarHabitacion(id, nueva);
			} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
				suitesDobles.cargarHabitacion(id, nueva);
			}
		}
		csvreader.close();
	}

	/**
	 * Carga las camas desde un archivo
	 */
	public static void cargarCamasPrueba(String csv) throws IOException {
		FileReader filereader = new FileReader(csv, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String idHabitacion = line[0];
			Tipo tipo = Tipo.valueOf(line[1]);
			String tamanio = line[2];
			int capacidad = Integer.parseInt(line[3]);
			boolean soloNinios = Boolean.parseBoolean(line[4]);

			Cama nueva = new Cama(tamanio, capacidad, soloNinios);
			if (tipo.equals(Tipo.ESTANDAR)) {
				estandares.cargarCama(idHabitacion, nueva);
			} else if (tipo.equals(Tipo.SUITE)) {
				suites.cargarCama(idHabitacion, nueva);
			} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
				suitesDobles.cargarCama(idHabitacion, nueva);
			}
		}
		csvreader.close();
	}
	public static void cargarReservasPrueba(String csv) throws IOException {
		FileReader filereader = new FileReader(csv, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;

		while ((line = csvreader.readNext()) != null) {
			if(Boolean.parseBoolean(line[6])) {
				String id = line[0];
				LocalDateTime fechaInicio = LocalDateTime.parse(line[1]);
				LocalDateTime fechaFin = LocalDateTime.parse(line[2]);
				int adultosEsperados = Integer.parseInt(line[3]);
				int niniosEsperados = Integer.parseInt(line[4]);
				int tarifaTotal = Integer.parseInt(line[5]);
				boolean tieneGrupo = Boolean.parseBoolean(line[7]);
				
				String[] infoHuesped = line[8].split("#");
				String nombre = infoHuesped[0];
				String documento = infoHuesped[1];
				int edad = Integer.parseInt(infoHuesped[2]);
				String correo = infoHuesped[3];
				String celular = infoHuesped[4];
				Huesped aCargo = new Huesped(nombre, documento, edad, correo, celular);
				
				boolean pagado = Boolean.parseBoolean(line[10]);
			
				Reserva reserva = new Reserva(fechaInicio, fechaFin, adultosEsperados, niniosEsperados, tarifaTotal, aCargo, tieneGrupo, pagado);
				if(tieneGrupo) {
					Hotel.agregarGrupo(new Grupo(reserva));
				}
				
				String[] habs = line[9].split("#");
				Habitacion habitacion;
				for(String hab: habs) {
					String[] parts = hab.split("-");
					Tipo tipo = Tipo.valueOf(parts[0]);
					String idHabitacion = parts[1];
					int tarifa = Integer.parseInt(parts[2]);
					if (tipo.equals(Tipo.ESTANDAR)) {
						habitacion = estandares.getHabitacion(idHabitacion);
						habitacion.setTarifa(tarifa);
						reserva.agregarHabitacion(habitacion);
					} else if (tipo.equals(Tipo.SUITE)) {
						habitacion = suites.getHabitacion(idHabitacion);
						habitacion.setTarifa(tarifa);
						reserva.agregarHabitacion(habitacion);
					} else if (tipo.equals(Tipo.SUITE_DOBLE)) {
						habitacion = suitesDobles.getHabitacion(idHabitacion);
						habitacion.setTarifa(tarifa);
						reserva.agregarHabitacion(habitacion);
					}
				}
				cargarReserva(reserva);
			}
		}
		csvreader.close();
	}
	

}

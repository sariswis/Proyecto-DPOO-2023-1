package catalogo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import hotel.Hotel;

public class Restaurante extends Servicio {
	private static String menu;
	private static String infoPedidos;
	private static LinkedHashMap<String, Plato> platos = new LinkedHashMap<String, Plato>();
	private static LinkedHashMap<String, Integer> cantidadPedidos = new LinkedHashMap<String, Integer>();
	private static LinkedHashMap<String, Integer> valorPedidos = new LinkedHashMap<String, Integer>();
	private static int[] horaPedidos = new int[3];
	
	/**
	 * Se asignan los valores de los parametros.
	 */
	public Restaurante(Nombre nombre, String ubicacion, String descripcion, int tarifa) {
		super(nombre, ubicacion, descripcion, tarifa);
		menu = "datos/menu.csv";
		infoPedidos = "datos/infoPedidos.csv";
		try {
			cargarPlatos();
			cargarPedidos();
		} catch (Exception e) {
			System.out.println("No se pudo cargar el menú del restaurante");
		}
	}

	/**
	 * Obtiene el menu del restaurante.
	 */
	public static String getMenu() {
		return menu;
	}

	/**
	 * Obtiene el plato.
	 */
	public static Plato getPlato(String id) {
		return platos.get(id);
	}
	
	/**
	 * Obtiene el nombre del plato.
	 */
	public static String getNombrePlato(String id) {
		return platos.get(id).getNombre();
	}
	
	/**
	 * Obtiene el plato.
	 */
	public static int getCantidadPlatos() {
		return platos.size();
	}

	/**
	 * Carga los platos de un archivo fuente.
	 */
	public static void cargarPlatos() throws IOException {
		FileReader filereader = new FileReader(menu, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		int i = 1;
		while ((line = csvreader.readNext()) != null) {
			String id = String.valueOf(i);
			String nombre = line[0];
			int precio = Integer.parseInt(line[1]);
			boolean servicio = Boolean.parseBoolean(line[2]);
			int cantidadVendidos = Integer.parseInt(line[3]);
			int valorVendido = Integer.parseInt(line[4]);

			Plato nuevo = new Plato(id, nombre, precio, servicio, cantidadVendidos, valorVendido);
			platos.put(id, nuevo);
			i++;
		}
		csvreader.close();
	}
	
	public static void cargarPedidos() throws IOException {
		FileReader filereader = new FileReader(infoPedidos, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String fecha = line[0];
			int cantidad = Integer.parseInt(line[1]);
			int valor = Integer.parseInt(line[2]);
			int mañana = Integer.parseInt(line[3]);
			int tarde = Integer.parseInt(line[4]);
			int noche = Integer.parseInt(line[5]);
			
			horaPedidos[0] += mañana;
			horaPedidos[1] += tarde;
			horaPedidos[2] += noche;
			
			cantidadPedidos.put(fecha, cantidad);
			valorPedidos.put(fecha, valor);
		}
		csvreader.close();
	}
	
	public static String[] getPlatos() {
		int cantidad = getCantidadPlatos();
		String[] platos = new String[cantidad];
		for(int i=0; i<cantidad; i++) {
			Plato plato = getPlato(String.valueOf(i+1));
			platos[i] = plato.getLineaFactura();
		}
		return platos;
	}

	/**
	 * Agrega un plato con sus respectivas caracteristicas.
	 */
	public static void setPlato(String id, int precio, boolean servicio) throws Exception {
		Plato plato = getPlato(id);
		if (precio >= 0) {
			if (plato != null) {
				plato.setPrecio(precio);
				plato.setServicioAlCuarto(servicio);
				platos.put(id, plato);
				try {
					Hotel.updateCSV(menu, String.valueOf(precio), Integer.parseInt(id), 1);
					Hotel.updateCSV(menu, String.valueOf(servicio), Integer.parseInt(id), 2);
				} catch (Exception e) {
					throw new Exception("No se pudo actualizar el archivo del menú");
				}
			} else {
				throw new Exception("El plato no existe");
			}
		} else {
			throw new Exception("No se puede poner un precio negativo");
		}
	}
	
	public static void venderItemPlato(String id) throws Exception {
		Plato plato = getPlato(id);
		if(plato != null) {
			int cantidad = plato.getCantidadVendidos() + 1;
			int valor = plato.getValorVendido() + plato.getPrecio();
			try {
				Hotel.updateCSV(menu, String.valueOf(cantidad), Integer.parseInt(id), 3);
				Hotel.updateCSV(menu, String.valueOf(valor), Integer.parseInt(id), 4);
				plato.venderItem();
				platos.put(id, plato);
			} catch (Exception e) {
				throw new Exception("No se pudo actualizar el archivo del menú");
			}
		} else {
			throw new Exception("El plato no existe");
		}	
	}
	
	public static ArrayList<String> getNombresPlatos(){
		int cantidad = getCantidadPlatos();
		ArrayList<String> platos = new ArrayList<String>();
		for(int i=0; i<cantidad; i++) {
			Plato plato = getPlato(String.valueOf(i+1));
			platos.add(plato.getNombre());
		}
		return platos;
	}
	
	public static ArrayList<Integer> getCantidadVendidosPlatos(){
		int cantidad = getCantidadPlatos();
		ArrayList<Integer> platos = new ArrayList<Integer>();
		for(int i=0; i<cantidad; i++) {
			Plato plato = getPlato(String.valueOf(i+1));
			platos.add(plato.getCantidadVendidos());
		}
		return platos;
	}
	
	public static ArrayList<Integer> getValorVendidoPlatos(){
		int cantidad = getCantidadPlatos();
		ArrayList<Integer> platos = new ArrayList<Integer>();
		for(int i=0; i<cantidad; i++) {
			Plato plato = getPlato(String.valueOf(i+1));
			platos.add(plato.getValorVendido());
		}
		return platos;
	}
	
	public static ArrayList<String> getFechasPedidos(){
		ArrayList<String> dateTime = new ArrayList<String>(valorPedidos.keySet());
		ArrayList<String> fechasPedidos = new ArrayList<String>();
		LocalDate fecha;
		for(String date: dateTime) {
			fecha = LocalDate.parse(date);
			fechasPedidos.add(fecha.toString());
		}
		return fechasPedidos;
	}
	
	public static ArrayList<Integer> getCantidadPedidos(){
		return new ArrayList<Integer>(cantidadPedidos.values());
	}
	
	public static ArrayList<Integer> getValorPedidos(){
		return new ArrayList<Integer>(valorPedidos.values());
	}
	
	public static int[] getHorasPedidos(){
		return horaPedidos;
	}

	public static void añadirPedido(int tarifa) throws Exception {
		LocalDateTime fechaR = LocalDateTime.now();
		String fecha = fechaR.toLocalDate().toString();
		int hora = fechaR.getHour();
		
		if (valorPedidos.containsKey(fecha)) {
			int cantidadA = cantidadPedidos.get(fecha) + 1;
			int valorA = valorPedidos.get(fecha) + tarifa;
			try {
				Hotel.searchUpdateCSV(infoPedidos, fecha, String.valueOf(cantidadA), 0, 1);
				Hotel.searchUpdateCSV(infoPedidos, fecha, String.valueOf(valorA), 0, 2);
				
				if(hora >= 5 && hora < 12) {
					horaPedidos[0] = horaPedidos[0] + 1;
					Hotel.searchUpdateCSV(infoPedidos, fecha, String.valueOf(horaPedidos[0]), 0, 3);
				} else if (hora >= 12 && hora < 18) {
					horaPedidos[1] = horaPedidos[1] + 1;
					Hotel.searchUpdateCSV(infoPedidos, fecha, String.valueOf(horaPedidos[1]), 0, 4);
				} else {
					horaPedidos[2] = horaPedidos[2] + 1;
					Hotel.searchUpdateCSV(infoPedidos, fecha, String.valueOf(horaPedidos[2]), 0, 5);
				}
				
				cantidadPedidos.put(fecha, cantidadA);
				valorPedidos.put(fecha, valorA);
			} catch (Exception e) {
				throw new Exception("No se pudo actualizar el archivo de pedidos");
			}
		} else {
			try {
				FileWriter filewriter = new FileWriter(infoPedidos, StandardCharsets.UTF_8, true);
				CSVWriter writer = new CSVWriter(filewriter);
				
				int mañana = 0, tarde = 0, noche = 0;
				if(hora >= 5 && hora < 12) {
					horaPedidos[0] = horaPedidos[0] + 1;
					mañana = 1;
				} else if (hora >= 12 && hora < 18) {
					horaPedidos[1] = horaPedidos[1] + 1;
					tarde = 1;
				} else {
					horaPedidos[2] = horaPedidos[2] + 1;
					noche = 1;
				}
				
				String[] record = {fecha, "1", String.valueOf(tarifa), String.valueOf(mañana), String.valueOf(tarde), String.valueOf(noche)};
				writer.writeNext(record, false);
				writer.close();
				
				cantidadPedidos.put(fecha, 1);
				valorPedidos.put(fecha, tarifa);
			} catch (Exception e) {
				throw new Exception("No se pudo actualizar el archivo de pedidos");
			}
		}
	}
	//Para las pruebas//
	public static void cargarPlatosPrueba(String csv) throws IOException {
		FileReader filereader = new FileReader(menu, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		int i = 1;
		while ((line = csvreader.readNext()) != null) {
			String id = String.valueOf(i);
			String nombre = line[0];
			int precio = Integer.parseInt(line[1]);
			boolean servicio = Boolean.parseBoolean(line[2]);
			int cantidadVendidos = Integer.parseInt(line[3]);
			int valorVendido = Integer.parseInt(line[4]);

			Plato nuevo = new Plato(id, nombre, precio, servicio, cantidadVendidos, valorVendido);
			platos.put(id, nuevo);
			i++;
		}
		csvreader.close();
	}
	
}

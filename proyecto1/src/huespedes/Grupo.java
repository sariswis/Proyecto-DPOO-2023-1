package huespedes;
import com.opencsv.CSVReader;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import hotel.Hotel;
import inventario.Inventario;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Grupo {
	private String id;
	private int numConsumos = 0;
	private double valorConsumos = 0;
	private String infoGrupo;
	private String infoHuespedes;
	private String infoConsumos;
	private String infoHistorial;
	private Reserva reserva;
	private LinkedHashMap<String, Huesped> huespedes = new LinkedHashMap<String, Huesped>();
	private LinkedHashMap<String, Consumo> consumos = new LinkedHashMap<String, Consumo>();

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Grupo(Reserva reserva) {
		this.id = reserva.getId();
		this.reserva = reserva;
		this.infoGrupo = "grupos/" + this.id;
		this.infoHuespedes =  this.infoGrupo + "/registro.csv";
		this.infoConsumos =  this.infoGrupo + "/consumos.csv";
		this.infoHistorial = this.infoGrupo + "/historial.txt";
		try {
			cargarGrupo();
		} catch (Exception e) {
			System.out.println("No se pudo crear la carpeta del grupo correctamente");
		}
	}

	/**
	 * Se obtiene el id el grupo.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Se obtiene la reserva del grupo
	 */
	public Reserva getReserva() {
		return this.reserva;
	}

	/**
	 * Se obtiene el huesped base del grupo en base al documento.
	 */
	public Huesped getHuesped(String documento) {
		return this.huespedes.get(documento);
	}

	/**
	 * Se obtiene el consumo del grupo por id.
	 */
	public Consumo getConsumo(String id) {
		return this.consumos.get(id);
	}

	/**
	 * Se obtienen todos los consumos del grupo durante la estadia.
	 */
	public ArrayList<Consumo> getConsumos() {
		return  new ArrayList<Consumo>(this.consumos.values());
	}
	
	public ArrayList<String> getStringsConsumos() throws Exception {
		ArrayList<Consumo> consumos = getConsumos();
		ArrayList<String> mensajes = new ArrayList<String>();
		if(valorConsumos == 0) {
			Inventario.actualizarTieneGrupo(id);
			Hotel.eliminarGrupo(id);
			agregarEvento("Se realizó check-out");
			throw new Exception("¡Exitoso! No habían consumos por pagar");
		} else {
			for(Consumo consumo: consumos) {
				if(!consumo.getPagado()) {
					mensajes.add(consumo.getInfo());
				}
			}
			mensajes.add("Valor total: $" + valorConsumos);
		}
		return mensajes;
	}

	/**
	 * Se obtienen los huespedes en base a una lista.
	 */
	public ArrayList<Huesped> getHuespedes() {
		return new ArrayList<Huesped>(this.huespedes.values());
	}
	
	public double getValorConsumos() {
		return valorConsumos;
	}
	
	/**
	 * Carga los archivos de la carpeta de un grupo
	 */
	public void cargarGrupo() throws IOException {
		File directorio = new File(infoGrupo);
		if(!directorio.exists()) {
			boolean existe = directorio.mkdir();
		}
		File registro = new File(infoHuespedes);
		if(!registro.exists()) {
			registro.createNewFile();
			FileWriter filewriter = new FileWriter(infoHuespedes, StandardCharsets.UTF_8, true);
			CSVWriter writer = new CSVWriter(filewriter);
			String line = "nombre,documento,edad,correo,celular";
			String[] record = line.split(",");
			writer.writeNext(record, false);
			writer.close();
			agregarEvento("Se realizó check-in");
		} else {
			cargarHuespedes();
		}
		File consumo = new File(infoConsumos);
		if(!consumo.exists()) {
			consumo.createNewFile();
			FileWriter filewriter = new FileWriter(infoConsumos, StandardCharsets.UTF_8, true);
			CSVWriter writer = new CSVWriter(filewriter);
			String line = "fecha,tipo,descripcion(linea#),valor,pagado,huespedes(documento#)";
			String[] record = line.split(",");
			writer.writeNext(record, false);
			writer.close();
		} else {
			cargarConsumos();
		}
		File historial = new File(infoHistorial);
		if(!historial.exists()) {
			historial.createNewFile();
		}
	}
	
	/**
	 * Carga los huéspedes
	 */
	public void cargarHuespedes() throws IOException {
		FileReader filereader = new FileReader(infoHuespedes, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String nombre = line[0];
			String documento = line[1];
			int edad = Integer.parseInt(line[2]);
			String correo = line[3];
			String celular = line[4];
			
			Huesped nuevo;
			if (correo != "null") {
				nuevo = new Huesped(nombre, documento, edad, correo, celular);
			} else {
				nuevo = new Huesped(nombre, documento, edad);
			}
			this.huespedes.put(nuevo.getDocumento(), nuevo);
		}
		csvreader.close();
	}
	
	/**
	 * Carga los consumos
	 */
	public void cargarConsumos() throws IOException {
		FileReader filereader = new FileReader(infoConsumos, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			this.numConsumos++;
			LocalDate fecha = LocalDate.parse(line[0]);
			String tipo = line[1];
			String descripcion = line[2].replace('#', '\n');
			int valor = Integer.parseInt(line[3]);
			boolean pagado = Boolean.parseBoolean(line[4]);
			
			Consumo nuevo = new Consumo(String.valueOf(numConsumos), this.id, fecha, tipo, descripcion, valor, pagado);
			if(!pagado) {
				valorConsumos += nuevo.getValorTotal();
			}
			
			String[] documentos = line[5].split("#");
			for(String documento: documentos) {
				Huesped huesped = getHuesped(documento);
				if (huesped != null) {
					nuevo.agregarHuesped(huesped);
				}
			}
			this.consumos.put(nuevo.getId(), nuevo);
			
		}
		csvreader.close();
	}

	/**
	 * Agrega un evento al historial
	 */
	public void agregarEvento(String evento) {
		try {
			FileWriter fw = new FileWriter(infoHistorial, StandardCharsets.UTF_8, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(LocalDateTime.now() + " - " + evento + "\n");
	        bw.close();
		} catch (Exception e) {
			System.out.print("No se pudo actualizar el historial");
		}
	}

	/**
	 * Agrega un nuevo huesped.
	 * @throws IOException 
	 */
	public void agregarHuesped(Huesped huesped) throws IOException {
		this.huespedes.put(huesped.getDocumento(), huesped);
		FileWriter filewriter = new FileWriter(infoHuespedes, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String line = huesped.getLinea();
		String[] record = line.split(",");
		writer.writeNext(record, false);
		writer.close();
		agregarEvento("Se agregó un huésped " + huesped.getNombre());
	}

	/**
	 * Agrega un nuevo consumo de un grupo.
	 * @throws IOException 
	 */
	public void agregarConsumo(String tipo, String descripcion, int valor, Huesped huesped, boolean pagado) throws IOException {
		numConsumos++;
		Consumo consumo = new Consumo(String.valueOf(numConsumos), this.id, tipo, descripcion, valor, pagado);
		if(!pagado) {
			valorConsumos += consumo.getValorTotal();
		}
		consumo.agregarHuesped(huesped);
		this.consumos.put(consumo.getId(), consumo);
		
		FileWriter filewriter = new FileWriter(infoConsumos, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String line = consumo.getLineaCSV();
		String[] record = line.split(",");
		writer.writeNext(record, false);
		writer.close();
		agregarEvento("Se agregó el consumo " + consumo.getId());
	}

	/**
	 * Agrega un nuevo consumo de un grupo.
	 * @throws IOException 
	 */
	public void agregarConsumo(String tipo, String descripcion, int valor, ArrayList<Huesped> huespedes,
			boolean pagado) throws IOException {
		numConsumos++;
		Consumo consumo = new Consumo(String.valueOf(numConsumos), this.id, tipo, descripcion, valor, pagado);
		if(!pagado) {
			valorConsumos += consumo.getValorTotal();
		}
		consumo.agregarHuespedes(huespedes);
		this.consumos.put(consumo.getId(), consumo);
		
		FileWriter filewriter = new FileWriter(infoConsumos, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String line = consumo.getLineaCSV();
		String[] record = line.split(",");
		writer.writeNext(record, false);
		writer.close();
		agregarEvento("Se agregó el consumo " + consumo.getId());
	}
	
	/**
	 * Registra el pago de todos los consumos
	 */
	public void pagarTodosConsumos() throws IOException, CsvException {
		int column = 4;
		ArrayList<Consumo> lista = getConsumos();
		for(int i = 0; i < lista.size(); i++) {
			Consumo consumo = lista.get(i);
			if (!consumo.getPagado()) {
				consumo.registrarPago();
				valorConsumos -= consumo.getValorTotal();
				Hotel.updateCSV(infoConsumos, "true", i+1, column);
			}
		}
		agregarEvento("Se realizó check-out");
	}

}

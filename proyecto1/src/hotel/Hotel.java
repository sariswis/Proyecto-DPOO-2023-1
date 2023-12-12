package hotel;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import catalogo.Catalogo;
import huespedes.Grupo;
import inventario.Inventario;
import usuarios.*;

public class Hotel {
	private String infoUsuarios;
	private String infoHuespedes;
	private static Inventario inventario;
	private static Catalogo catalogo;
	private static HashMap<String, Grupo> grupos = new HashMap<String, Grupo>();
	private static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
	private Usuario usuarioActivo;

	/**
	 * Crea un hotel.
	 */
	public Hotel() {
		this.infoUsuarios = "datos/usuarios.csv";
		this.infoHuespedes = "datos/huespedes.csv";
		inventario = new Inventario();
		catalogo = new Catalogo();
	}

	/**
	 * Obtiene el grupo asociado a el id.
	 */
	public static Grupo getGrupo(String id) {
		return grupos.get(id);
	}

	/**
	 * Obtiene el usuario asociado a el login.
	 */
	public static Usuario getUsuario(String login) {
		return usuarios.get(login);
	}

	/**
	 * Carga los usuarios en base a los datos de el archivo de usuarios.
	 */
	public void cargarUsuarios() throws IOException, IllegalArgumentException {
		FileReader filereader = new FileReader(infoUsuarios, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String login = line[0];
			String password = line[1];
			String documento = line[2];
			Rol rol = Rol.valueOf(line[3]);
			try {
				cargarUsuario(login, password, documento, rol);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		csvreader.close();
	}
	
	public void cargarHuespedes() throws IOException, IllegalArgumentException {
		FileReader filereader = new FileReader(infoHuespedes, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String login = line[0];
			String password = line[1];
			String documento = line[2];
			try {
				cargarUsuario(login, password, documento, Rol.HUESPED);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		csvreader.close();
	}

	/**
	 * Carga el usuario con su login password documento y rol.
	 */
	public void cargarUsuario(String login, String password, String documento, Rol rol) throws Exception {
		Usuario nuevo;
		switch (rol) {
			case ADMINISTRADOR:
				nuevo = new Administrador(login, password, documento, rol);
				break;
			case RECEPCIONISTA:
				nuevo = new Recepcionista(login, password, documento, rol);
				break;
			case MESERO:
				nuevo = new Mesero(login, password, documento, rol);
				break;
			case ENCARGADO_SPA:
				nuevo = new EncargadoSpa(login, password, documento, rol);
				break;
			case GUIA:
				nuevo = new Guia(login, password, documento, rol);
				break;
			case HUESPED:
				nuevo = new HuespedUsuario(login, password, documento, rol);
				break;	
				
			default:
				throw new Exception("No existe el rol " + rol);
		}

		if (!usuarios.containsKey(login)) {
			usuarios.put(login, nuevo);
		} else {
			throw new Exception("Hay usuarios duplicados en el archivo fuente" + this.infoUsuarios);
		}
	}

	/**
	 * Crea un usuario con la información correspondiente
	 */
	public void crearUsuario(String login, String password, String documento, Rol rol) throws Exception {
		Usuario nuevo;
		switch (rol) {
			case ADMINISTRADOR:
				nuevo = new Administrador(login, password, documento, rol);
				break;
			case RECEPCIONISTA:
				nuevo = new Recepcionista(login, password, documento, rol);
				break;
			case MESERO:
				nuevo = new Mesero(login, password, documento, rol);
				break;
			case ENCARGADO_SPA:
				nuevo = new EncargadoSpa(login, password, documento, rol);
				break;
			case GUIA:
				nuevo = new Guia(login, password, documento, rol);
				break;
			case HUESPED:
				nuevo = new HuespedUsuario(login, password, documento, rol);
				break;	
				
			default:
				throw new Exception("No existe el rol " + rol);
		}

		if (!usuarios.containsKey(login)) {
			usuarios.put(login, nuevo);
			if(rol.equals(Rol.HUESPED)) {
				FileWriter filewriter = new FileWriter(infoHuespedes, StandardCharsets.UTF_8, true);
				CSVWriter writer = new CSVWriter(filewriter);
				String line = login + "," + password + "," + documento;
				String[] record = line.split(",");
				writer.writeNext(record, false);
				writer.close();
			} else {
				FileWriter filewriter = new FileWriter(infoUsuarios, StandardCharsets.UTF_8, true);
				CSVWriter writer = new CSVWriter(filewriter);
				String line = login + "," + password + "," + documento + "," + rol;
				String[] record = line.split(",");
				writer.writeNext(record, false);
				writer.close();
			}
		} else {
			throw new Exception("Ya existe un usuario con este login");
		}

	}


	/**
	 * Inicia la sesión de un usuario con el login y password
	 */
	public void iniciarSesion(String login, String password) throws Exception {
		Usuario usuario = getUsuario(login);
		if (usuario != null) {
			if (usuario.getPassword().equals(password)) {
				usuarioActivo = usuario;
				usuarioActivo.ejecutarOpciones();
			} else {
				throw new Exception("Contraseña incorrecta");
			}
		} else {
			throw new Exception("No existe ningún usuario con este login");
		}
	}

	/**
	 * Agrega a un grupo con base a su ID.
	 */
	public static void agregarGrupo(Grupo grupo) {
		grupos.put(grupo.getId(), grupo);
	}
	
	public static void eliminarGrupo(String id) {
		grupos.remove(id);
	}

	/**
	 * Actualiza una celda de un archivo CSV.
	 */ 
	public static void updateCSV(String input, String replace, int row, int col) throws IOException, CsvException {
		CSVReader reader = new CSVReader(new FileReader(input, StandardCharsets.UTF_8));
		List<String[]> csvBody = reader.readAll();
		csvBody.get(row)[col] = replace;
		reader.close();

		FileWriter filewriter = new FileWriter(input, StandardCharsets.UTF_8);
		CSVWriter writer = new CSVWriter(filewriter);
		writer.writeAll(csvBody, false);
		writer.flush();
		writer.close();
	}

	/**
	 * Realiza la busqueda en el archivo csv para efectuar un remplazo en una celda.
	 */
	public static void searchUpdateCSV(String input, String search, String replace, int searchCol, int replaceCol)
			throws IOException, CsvException {
		CSVReader reader = new CSVReader(new FileReader(input, StandardCharsets.UTF_8));
		List<String[]> csvBody = reader.readAll();
		for (String[] value : csvBody) {
			if (value[searchCol].equals(search)) {
				value[replaceCol] = replace;
				break;
			}
		}
		reader.close();

		FileWriter filewriter = new FileWriter(input, StandardCharsets.UTF_8);
		CSVWriter writer = new CSVWriter(filewriter);
		writer.writeAll(csvBody, false);
		writer.flush();
		writer.close();
	}
	//Para las Pruebas//
	public String cargarUsuariosPruebas(String Usuarios) throws IOException, IllegalArgumentException {
		String mensaje="";
		FileReader filereader = new FileReader(Usuarios, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String login = line[0];
			String password = line[1];
			String documento = line[2];
			Rol rol = Rol.valueOf(line[3]);
			try {
				cargarUsuario(login, password, documento, rol);
				mensaje="Usuarios Cargados Correctamente";
			} catch (Exception e) {
				mensaje=(e.getMessage());
				
			}
		}
		csvreader.close();
		return mensaje;
	}
	public String cargarHuespedesPrueba(String Huespedes) throws IOException, IllegalArgumentException {
		String mensaje="";
		FileReader filereader = new FileReader(Huespedes, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String login = line[0];
			String password = line[1];
			String documento = line[2];
			try {
				cargarUsuario(login, password, documento, Rol.HUESPED);
				mensaje="Huespedes Cargados Correctamente";
				
			} catch (Exception e) {
				mensaje=e.getMessage();
			}
		}
		csvreader.close();
		return mensaje;
	}

	
}

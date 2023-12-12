package catalogo;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class Alojamiento extends Servicio {
	private static String infoPasarelas;
	private static ArrayList<String> pasarelas = new ArrayList<String>();

	public Alojamiento(Nombre nombre, String ubicacion, String descripcion, int tarifa) {
		super(nombre, ubicacion, descripcion, tarifa);
		infoPasarelas = "datos/infoPasarelas.csv";
		try {
			cargarPasarelas();
		} catch (Exception e) {
			System.out.println("No se pudo cargar el archivo de las pasarelas");
		}
	}
	
	public void cargarPasarelas() throws IOException {
		FileReader filereader = new FileReader(infoPasarelas, StandardCharsets.UTF_8);
		CSVReader csvreader = new CSVReader(filereader);
		String[] header = csvreader.readNext();
		String[] line;
		while ((line = csvreader.readNext()) != null) {
			String nombre = line[0];
			pasarelas.add(nombre);
		}
		csvreader.close();
	}
	
	public static ArrayList<String> getPasarelas(){
		return pasarelas;
	}
	
}

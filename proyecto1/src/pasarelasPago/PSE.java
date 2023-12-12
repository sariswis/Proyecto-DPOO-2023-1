package pasarelasPago;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import com.opencsv.CSVWriter;

public class PSE extends PasarelaPago {
	private String infoPSE;
	
	public PSE(String numeroCuenta) {
		super(numeroCuenta);
		infoPSE = "datos/infoPSE.csv";
		try {
			cargarArchivo();
		} catch (Exception e) {
			System.out.println("No se pudo ubicar el archivo de las transacciones");
		}
	}

	public void registrarPago(String tarjeta, String nombreDueño, String documentoDueño, double monto, String resultado) throws IOException {
		FileWriter filewriter = new FileWriter(infoPSE, StandardCharsets.UTF_8, true);
		CSVWriter writer = new CSVWriter(filewriter);
		String[] record = {LocalDate.now().toString(), numeroCuenta, tarjeta, 
									nombreDueño, documentoDueño, String.valueOf(monto), resultado};
		writer.writeNext(record, false);
		writer.close();
	}

	public void cargarArchivo() throws IOException {
		File info = new File(infoPSE);
		if(!info.exists()) {
			info.createNewFile();
			FileWriter filewriter = new FileWriter(infoPSE, StandardCharsets.UTF_8, true);
			CSVWriter writer = new CSVWriter(filewriter);
			String[] record = {"Fecha", "Numero de cuenta", "Tarjeta", 
										"Nombre cliente", "Documento cliente", "Monto", "Resultado"};
			writer.writeNext(record, false);
			writer.close();
		}
	}

}

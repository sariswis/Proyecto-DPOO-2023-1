package pasarelasPago;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class PayU extends PasarelaPago {
	private String infoPayU;
	
	public PayU(String numeroCuenta) {
		super(numeroCuenta);
		infoPayU = "datos/infoPayU.txt";
		try {
			cargarArchivo();
		} catch (Exception e) {
			System.out.println("No se pudo ubicar el archivo de las transacciones");
		}
	}

	public void registrarPago(String tarjeta, String nombreDueño, String documentoDueño, double monto, String resultado) throws IOException {
		FileWriter fw = new FileWriter(infoPayU, StandardCharsets.UTF_8, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String mensaje = LocalDate.now() + ",  "  + nombreDueño +  ", "  + 
        							documentoDueño + ", " +  monto +   ", " + numeroCuenta + ",  "  +  tarjeta +  ", "  + resultado + "\n";
        bw.write(mensaje);
        bw.close();
	}

	public void cargarArchivo() throws IOException {
		File info = new File(infoPayU);
		if(!info.exists()) {
			info.createNewFile();
			FileWriter fw = new FileWriter(infoPayU, StandardCharsets.UTF_8, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        String mensaje ="Fecha, Nombre cliente, Documento cliente, Monto, Número de cuenta, Tarjeta, Resultado\n";
	        bw.write(mensaje);
	        bw.close();
		}
	}

}

package pasarelasPago;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class PayPal extends PasarelaPago {
	private String infoPayPal;

	public PayPal(String numeroCuenta) {
		super(numeroCuenta);
		infoPayPal = "datos/infoPayPal.txt";
		try {
			cargarArchivo();
		} catch (Exception e) {
			System.out.println("No se pudo ubicar el archivo de las transacciones");
		}
	}

	public void registrarPago(String tarjeta, String nombreDueño, String documentoDueño, double monto, String resultado) throws IOException {
		FileWriter fw = new FileWriter(infoPayPal, StandardCharsets.UTF_8, true);
        BufferedWriter bw = new BufferedWriter(fw);
        String mensaje =  LocalDate.now() + ",  " + numeroCuenta + ",  "  + 
        							tarjeta +  ", " + nombreDueño +  ", "  + documentoDueño +  ", " +  monto +  ", "  + resultado + "\n";
        bw.write(mensaje);
        bw.close();
	}

	public void cargarArchivo() throws IOException {
		File info = new File(infoPayPal);
		if(!info.exists()) {
			info.createNewFile();
			FileWriter fw = new FileWriter(infoPayPal, StandardCharsets.UTF_8, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        String mensaje ="Fecha, Numero de cuenta, Tarjeta, Nombre cliente, Documento cliente, Monto, Resultado\n";
	        bw.write(mensaje);
	        bw.close();
		}
	}

}

package pasarelasPago;

import java.io.IOException;
import java.util.Random;

public abstract class PasarelaPago {
	protected String numeroCuenta;
	
	public PasarelaPago(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	public String hacerTransaccion(String tarjeta, String nombreDueño, String documentoDueño, double monto) {
        Random random = new Random();
        int opcion = random.nextInt(5);
        String mensaje;
        if(opcion == 0 || opcion == 1) {
        	mensaje = "Exitoso";
        } else if (opcion == 2){
        	mensaje = "Datos incorrectos";
        } else if (opcion == 3){
        	mensaje = "Tarjeta reportada";
        } else {
        	mensaje = "Cupo insuficiente";
        }
        try {
        	registrarPago(tarjeta, nombreDueño, documentoDueño, monto, mensaje);
        } catch(Exception e) {
        	mensaje = "No se pudo registrar el movimiento";
        }
        return mensaje;
	}
	
	public abstract void cargarArchivo() throws IOException;
	public abstract void registrarPago(String tarjeta, String nombreDueño, String documentoDueño, double monto, String mensaje) throws IOException;
	
}

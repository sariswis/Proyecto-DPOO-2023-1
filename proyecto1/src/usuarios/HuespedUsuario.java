package usuarios;

import VistaHuesped.VentanaHuesped;
import huespedes.Reserva;
import inventario.Inventario;
import pasarelasPago.PasarelaPago;

public class HuespedUsuario extends Usuario{
	private VentanaHuesped interfaz;
	
	public HuespedUsuario(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}
	
	public void ejecutarOpciones() {
		interfaz = new VentanaHuesped(this);
	}
	
	public String[] consultarDisponibilidad(String fInicio, String fFin) throws Exception {
		return Inventario.consultarDisponibilidad(fInicio, fFin);
	}
	
	public String realizarPagoInmediato(String idReserva,String tarjeta, String nombreDueño, String documentoDueño, String pasarela) {
		String mensaje="";
		if(Inventario.getReserva(idReserva)!=null) {
		try {
			Reserva reserva=Inventario.getReserva(idReserva);
			double montoAjustado=Double.valueOf(reserva.getTarifa())*0.9;
			Class clase = Class.forName("pasarelasPago." + pasarela);
			PasarelaPago pasarelaPago = (PasarelaPago) clase.getDeclaredConstructor(String.class).newInstance(Inventario.getNumeroCuentaHotel());
			mensaje = pasarelaPago.hacerTransaccion(tarjeta, nombreDueño, documentoDueño, montoAjustado);
			if (mensaje.equals("Exitoso")){
				Inventario.pagarReserva(idReserva);
			}
			} catch (ClassNotFoundException e){
				mensaje = "La pasarela no está disponible";
			}catch (Exception e1){
				mensaje = "Ocurrió un error en el check-out";
		}
		
	}
		else {
			mensaje="No se encontro una reserva con ese ID";
		}
		return mensaje;		
	}

}

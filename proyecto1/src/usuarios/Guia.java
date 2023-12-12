package usuarios;

import java.util.ArrayList;
import hotel.Hotel;
import huespedes.Grupo;
import huespedes.Huesped;
import vista.VentanaGuia;
import catalogo.Catalogo;
import catalogo.Nombre;

public class Guia extends Empleado {
	private VentanaGuia interfaz;
	
	/**
	 * Se asignan los valores de los parametros.
	 */
	public Guia(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}

	/**
	 * Registra el consumo del servicio asociado (guia) a un grupo.
	 */
	public String registrarConsumo(String idGrupo, ArrayList<Huesped> huespedes, boolean pagado) {
		String mensaje = "";
		try {
			if (Catalogo.getDisponibleAhora(Nombre.GUIA_TURISTICO)) {
				Grupo grupo = Hotel.getGrupo(idGrupo);
				if (grupo != null) {
					String tipo = Catalogo.getNombre(Nombre.GUIA_TURISTICO);
					String descripcion = Catalogo.getDescripcion(Nombre.GUIA_TURISTICO);
					int tarifa = Catalogo.getTarifa(Nombre.GUIA_TURISTICO);
					grupo.agregarConsumo(tipo, descripcion, tarifa, huespedes, pagado);
					Hotel.agregarGrupo(grupo);
					mensaje = "¡Consumo Registrado!";
				} else {
					mensaje = "El grupo no existe";
				}
			} else {
				mensaje = "El servicio no está disponible en este momento del día";
			}
		} catch (Exception e) {
			mensaje = "No se pudo actualizar el registro de consumo";
		}
		return mensaje;
	}

	@Override
	public void ejecutarOpciones() {
		interfaz = new VentanaGuia(this);
	}

}
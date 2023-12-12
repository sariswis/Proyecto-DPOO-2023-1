package usuarios;

import hotel.Hotel;
import huespedes.Grupo;
import huespedes.Huesped;
import vista.VentanaSpa;
import catalogo.Catalogo;
import catalogo.Nombre;

public class EncargadoSpa extends Empleado {
	private VentanaSpa interfaz;
	
	/**
	 * Se asignan los valores de los parametros.
	 */
	public EncargadoSpa(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}

	/**
	 * Registra el consumo del servicio asociado (SPA) a un huésped.
	 */
	public String registrarConsumo(String idGrupo, String documento, boolean pagado) {
		String mensaje = "";
		try {
			if (Catalogo.getDisponibleAhora(Nombre.SPA)) {
				Grupo grupo = Hotel.getGrupo(idGrupo);
				if (grupo != null) {
					Huesped huesped = grupo.getHuesped(documento);
					if (huesped != null) {
						String tipo = Catalogo.getNombre(Nombre.SPA);
						String descripcion = Catalogo.getDescripcion(Nombre.SPA);
						int tarifa = Catalogo.getTarifa(Nombre.SPA);
						grupo.agregarConsumo(tipo, descripcion, tarifa, huesped, pagado);
						Hotel.agregarGrupo(grupo);
						mensaje = "¡Consumo Registrado!";
					} else {
						mensaje = "El huésped no existe en el grupo";
					}
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
		interfaz = new VentanaSpa(this);
	}

}

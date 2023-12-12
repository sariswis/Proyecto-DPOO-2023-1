package usuarios;

import catalogo.Catalogo;
import catalogo.Nombre;
import catalogo.Pedido;
import catalogo.Restaurante;
import hotel.Hotel;
import huespedes.Grupo;
import huespedes.Huesped;
import vista.VentanaMesero;

public class Mesero extends Empleado {
	private VentanaMesero interfaz;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Mesero(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}

	/**
	 * Registra el consumo del servicio asociado (restaurante) a un huésped 
	 */
	public String registrarConsumo(String idGrupo, String documento, boolean pagado, Pedido pedido) {
		String mensaje = "";
		try {
			if (Catalogo.getDisponibleAhora(Nombre.RESTAURANTE)) {
				Grupo grupo = Hotel.getGrupo(idGrupo);
				if (grupo != null) {
					Huesped huesped = grupo.getHuesped(documento);
					if (huesped != null) {
						String tipo = Catalogo.getNombre(Nombre.RESTAURANTE);
						String descripcion = pedido.getDescripcion();
						int tarifa = pedido.getTarifa();
						grupo.agregarConsumo(tipo, descripcion, tarifa, huesped, pagado);
						Hotel.agregarGrupo(grupo);
						Restaurante.añadirPedido(pedido.getTarifa());
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

	public void ejecutarOpciones() {
		interfaz = new VentanaMesero(this);
	}

}

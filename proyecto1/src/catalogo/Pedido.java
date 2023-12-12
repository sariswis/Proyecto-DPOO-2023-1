package catalogo;

import java.util.ArrayList;

public class Pedido {
	private String descripcion = "";
	private int tarifa = 0;
	private ArrayList<Plato> platos = new ArrayList<Plato>();

	/**
	 * Obtiene la descripcion del pedido.
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/**
	 * Obtiene la tarifa del pedido.
	 */
	public int getTarifa() {
		return this.tarifa;
	}

	/**
	 * Agrega el pedido con base en el plato, la descripcion y el precio.
	 */
	public void agregarPlato(Plato plato) throws Exception {
		this.platos.add(plato);
		this.descripcion += plato.getLineaFactura() + "#";
		this.tarifa += plato.getPrecio();
		Restaurante.venderItemPlato(plato.getId());
	}

}

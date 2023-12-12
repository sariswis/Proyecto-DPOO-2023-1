package catalogo;

public class Plato {
	private String id;
	private String nombre;
	private int precio;
	private boolean servicioAlCuarto;
	private int cantidadVendidos;
	private int valorVendido;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Plato(String id, String nombre, int precio, boolean servicioAlCuarto,
			int cantidadVendidos, int valorVendido) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.servicioAlCuarto = servicioAlCuarto;
		this.cantidadVendidos = cantidadVendidos;
		this.valorVendido = valorVendido;
	}

	/**
	 * Obtiene el ID del plato.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Obtiene el nombre del plato.
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Obtiene el precio del plato.
	 */
	public int getPrecio() {
		return this.precio;
	}

	/**
	 * Obtiene si el plato tiene servicio al cuarto.
	 */
	public boolean getServicioAlCuarto() {
		return this.servicioAlCuarto;
	}
	
	public int getCantidadVendidos() {
		return this.cantidadVendidos;
	}
	
	public int getValorVendido() {
		return this.valorVendido;
	}
	
	public void venderItem() {
		this.cantidadVendidos += 1;
		this.valorVendido += this.precio;
	}

	/**
	 * Obtiene la factura con el id nombre y precio del plato.
	 */
	public String getLineaFactura() {
		return this.id + ". " + this.nombre + " - $" + this.precio;
	}

	/**
	 * Asigna el precio del plato.
	 */
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	/**
	 * Asigna si el plato tiene servicio al cuarto.
	 */
	public void setServicioAlCuarto(boolean servicio) {
		this.servicioAlCuarto = servicio;
	}

}

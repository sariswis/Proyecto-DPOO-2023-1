package inventario;

public class Cama {
	private String tamanio;
	private int capacidad;
	private boolean soloNinios;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Cama(String tamanio, int capacidad, boolean soloNinios) {
		this.tamanio = tamanio;
		this.capacidad = capacidad;
		this.soloNinios = soloNinios;
	}

	/**
	 * Obtiene el tamaño de la cama
	 */
	public String getTamanio() {
		return this.tamanio;
	}

	/**
	 * Obtiene la capacidad de la cama
	 */
	public int getCapacidad() {
		return this.capacidad;
	}

	/**
	 * Obtiene si la cama es solo para niños
	 */
	public boolean getSoloNinios() {
		return this.soloNinios;
	}
	
	public String[] consultarCama() {
		String[] mensaje = new String[3];
		mensaje[0] = "Tamaño: " + this.tamanio;
		mensaje[1] = "Capacidad: " + this.capacidad;
		mensaje[2] = "Es solo para niños: " + Inventario.convertirBoolean(this.soloNinios);
		return mensaje;
	}
}

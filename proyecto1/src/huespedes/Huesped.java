package huespedes;

public class Huesped {
	private String nombre;
	private String documento;
	private int edad;
	private String correo;
	private String celular;

	public Huesped(String nombre, String documento, int edad) {
		this.nombre = nombre;
		this.documento = documento;
		this.edad = edad;
	}

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Huesped(String nombre, String documento, int edad, String correo, String celular) {
		this.nombre = nombre;
		this.documento = documento;
		this.edad = edad;
		this.correo = correo;
		this.celular = celular;
	}

	/**
	 * Obtiene el nombre del huesped
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Obtiene el documento del huesped
	 */
	public String getDocumento() {
		return this.documento;
	}

	/**
	 * Obtiene la edad del huesped
	 */
	public int getEdad() {
		return this.edad;
	}

	/**
	 * Obtiene el correo del huesped
	 */
	public String getCorreo() {
		return this.correo;
	}

	/**
	 * Obtiene el numero de celular del huesped.
	 */
	public String getCelular() {
		return this.celular;
	}

	/**
	 * Obtiene si es menor de dos años.
	 */
	public boolean getMenorDosAnios() {
		return this.edad <= 2;
	}

	/**
	 * Obtiene si es un niño
	 */
	public boolean getNinio() {
		return this.edad <= 12;
	}
	
	/**
	 * Obtiene toda la información del huésped
	 */
	public String[] getInfo() {
		String[] info = new String[5];
		info[0] = "Nombre: " + this.nombre;
		info[1] = "Documento: " + this.documento;
		info[2] = "Edad: " + this.edad;
		info[3] = "Correo: " + this.correo;
		info[4] = "Celular: " + this.celular;
		return info;
	}
	
	/**
	 * Obtiene la información primordial del huésped
	 */
	public String getLinea() {
		return this.nombre + "," + this.documento + "," + this.edad + ","  + this.correo + ","  + this.celular;
	}

}

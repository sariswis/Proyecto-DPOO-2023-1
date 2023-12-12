package usuarios;

public abstract class Empleado extends Usuario {
	/**
	 * Se asignan los valores de los parametros.
	 */
	public Empleado(String login, String password, String documento, Rol rol) {
		super(login, password, documento, rol);
	}

	
	/**
	 * Se pide al usuario (empleado) que seleccione una de las posibles opciones a ejecutar.
	 */
	public abstract void ejecutarOpciones();

}

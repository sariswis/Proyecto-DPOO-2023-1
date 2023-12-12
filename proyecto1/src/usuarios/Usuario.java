package usuarios;

public abstract class Usuario {
	private String login;
	private String password;
	private String documento;
	private Rol rol;

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Usuario(String login, String password, String documento, Rol rol) {
		this.login = login;
		this.password = password;
		this.documento = documento;
		this.rol = rol;
	}

	/**
	 * Obtiene el login del usuario.
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Obtiene el password del usuario.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Obtiene el rol del usuario
	 */
	public Rol getRol() {
		return this.rol;
	}

	/**
	 * Método abstracto de selecion de opcion de acuerdo al menu de opciones.
	 */
	public abstract void ejecutarOpciones();

}

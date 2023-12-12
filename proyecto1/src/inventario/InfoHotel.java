package inventario;

public class InfoHotel {
	private String nombre;
	private boolean parqueaderoGratis;
	private boolean piscina;
	private boolean zonasHumedas;
	private boolean bbq;
	private boolean wifiGratis;
	private boolean recepcion24h;
	private boolean petFriendly;
	private String numeroCuenta;
	
	public InfoHotel(String nombre, boolean parqueaderoGratis, boolean piscina, boolean zonasHumedas,
			boolean bbq, boolean wifiGratis, boolean recepcion24h, boolean petFriendly, String numeroCuenta) {
		this.nombre = nombre;
		this.parqueaderoGratis = parqueaderoGratis;
		this.piscina = piscina;
		this.zonasHumedas = zonasHumedas;
		this.bbq = bbq;
		this.wifiGratis = wifiGratis;
		this.recepcion24h = recepcion24h;
		this.petFriendly = petFriendly;
		this.numeroCuenta = numeroCuenta;
	}
	
	public String getNombreHotel() {
		return nombre;
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	
	public String[] consultarHotel() {
		String[] mensaje = new String[8];
		mensaje[0] = "Tiene parqueadero gratis: " + Inventario.convertirBoolean(this.parqueaderoGratis);
		mensaje[1] = "Tiene piscina: " + Inventario.convertirBoolean(this.piscina);
		mensaje[2] = "Tiene zonas húmedas: " + Inventario.convertirBoolean(this.zonasHumedas);
		mensaje[3] = "Tiene BBQ: " + Inventario.convertirBoolean(this.bbq);
		mensaje[4] = "Tiene Wi-Fi gratis: " + Inventario.convertirBoolean(this.wifiGratis);
		mensaje[5] = "Recepción 24 horas: " + Inventario.convertirBoolean(this.recepcion24h);
		mensaje[6] = "Es pet-friendly: " + Inventario.convertirBoolean(this.petFriendly);
		mensaje[7] = "Número de cuenta: " + this.numeroCuenta;
		return mensaje;
	}

}

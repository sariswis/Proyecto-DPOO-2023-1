package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import hotel.Hotel;
import inventario.Inventario;
import usuarios.Administrador;
import usuarios.Rol;

class PruebasCarga {
	private Hotel hotel;
	private Administrador admin;
	private Inventario inventario;
    
	@Test
	@Order(1)
	@DisplayName("Cargar Habitaciones Bien")
	public void testCargarHabitacionesAdministradorBien() {
		admin=new Administrador("prueba", "prueba", "prueba", Rol.ADMINISTRADOR);
		String rutaHabitaciones="datos/inventario.csv";
		String rutaCamas="datos/camas.csv";
		String rutaResevas="datos/reservas.csv";
		String mensaje=admin.cargarHabitacionesPrueba(rutaHabitaciones, rutaCamas, rutaResevas);
		assertEquals("¡Habitaciones y camas cargadas!", mensaje);
	}
	
	@Test
	@Order(2)
	@DisplayName("Cargar Habitaciones Mal")
	public void testCargarHabitacionesAdministradorMal() {
		admin=new Administrador("prueba", "prueba", "prueba", Rol.ADMINISTRADOR);
		String rutaHabitaciones="datos/inventariohdaslkjdhalks.csv";
		String rutaCamas="datos/camasasdl;kfjhasl;dfk.csv";
		String rutaResevas="datos/reservasaajslkdf.csv";
		String mensaje=admin.cargarHabitacionesPrueba(rutaHabitaciones, rutaCamas, rutaResevas);
		assertNotEquals("¡Habitaciones y camas cargadas!", mensaje);
	}	
	
	@Test
	@Order(3)
	@DisplayName("Cargar Menú Bien")
	public void testCargarMenuAdministradorBien() {
		admin=new Administrador("prueba", "prueba", "prueba", Rol.ADMINISTRADOR);
		String rutaMenu="datos/menu.csv";
		String[] mensaje=admin.cargarMenuRestaurantePrueba(rutaMenu);
		assertEquals("1. hamburguesa - $16000", mensaje[0]);
	}
	
	@Test
	@Order(4)
	@DisplayName("Cargar Menú Mal")
	public void testCargarMenuAdministradorMal() {
		admin=new Administrador("prueba", "prueba", "prueba", Rol.ADMINISTRADOR);
		String rutaMenu="datos/menuaa.csv";
		String[] mensaje=admin.cargarMenuRestaurantePrueba(rutaMenu);
		assertNotEquals("hamburguesa-$16000", mensaje[0]);
	}
	
	@Test
	@Order(5)
	@DisplayName("Cargar Usuarios Bien")
	public void testCargarUsuariosHotelBien() {
		hotel=new Hotel();
		String rutaUsuarios="datos/usuarios.csv";
		String mensaje;
		try {
			mensaje = hotel.cargarUsuariosPruebas(rutaUsuarios);
		} catch (IllegalArgumentException e) {
			mensaje=e.getMessage();
		} catch (IOException e) {
			mensaje=e.getMessage();
		};
		assertEquals("Usuarios Cargados Correctamente", mensaje);
	}
	
	@Test
	@Order(6)
	@DisplayName("Cargar Usuarios Mal")
	public void testCargarUsuariosHotelMal() {
		hotel=new Hotel();
		String rutaUsuarios="datos/usuariosasdfas.csv";
		String mensaje;
		try {
			mensaje = hotel.cargarUsuariosPruebas(rutaUsuarios);
		} catch (IllegalArgumentException e) {
			mensaje=e.getMessage();
		} catch (IOException e) {
			mensaje=e.getMessage();
		};
		assertNotEquals("Usuarios Cargados Correctamente", mensaje);
	}
	
	@Test
	@Order(7)
	@DisplayName("Cargar Huéspedes Bien")
	public void testCargarHuespedHotelBien() {
		hotel=new Hotel();
		String rutaHuespedes="datos/huespedes.csv";
		String mensaje;
		try {
			mensaje = hotel.cargarHuespedesPrueba(rutaHuespedes);
		} catch (IllegalArgumentException e) {
			mensaje=e.getMessage();
		} catch (IOException e) {
			mensaje=e.getMessage();
		};
		assertEquals("Huespedes Cargados Correctamente", mensaje);
	}
	
	
	@Test
	@Order(8)
	@DisplayName("Cargar Huéspedes Mal")
	public void testCargarHuespedHotelMal() {
		hotel=new Hotel();
		String rutaHuespedes="datos/huespedesasjdkflasd.csv";
		String mensaje;
		try {
			mensaje = hotel.cargarUsuariosPruebas(rutaHuespedes);
		} catch (IllegalArgumentException e) {
			mensaje=e.getMessage();
		} catch (IOException e) {
			mensaje=e.getMessage();
		};
		assertNotEquals("Huespedes Cargados Correctamente", mensaje);
	}
	
}

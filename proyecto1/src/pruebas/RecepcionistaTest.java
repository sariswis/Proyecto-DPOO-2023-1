package pruebas;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;

import org.junit.Test;
import usuarios.Recepcionista;
import usuarios.Rol;
import hotel.Hotel;
import huespedes.Grupo;
import huespedes.Huesped;
import huespedes.Reserva;
import inventario.Inventario;

public class RecepcionistaTest {
    private Hotel hotel;
    private Inventario inventario;
    private Recepcionista recepcionista= new Recepcionista("fsiuodnfbldf", "nklmkl", "4651320", Rol.RECEPCIONISTA);
    private Huesped huesped;

    private String fInicio;
    private String fFin;
    private int adultosE;
    private int niniosE;
    private String nombre;
    private String documento;
    private int edad;
    private String correo;
    private String celular;

    @BeforeEach
    public void setUp() throws Exception {
        documento = String.valueOf(1234);
        celular = String.valueOf(31055);
        fInicio = "2023-05-10";
        fFin = "2023-05-15";
        adultosE = 1;
        niniosE = 1;
        nombre = "Juan";
        edad = 20;
        correo = "Juan@gmail.com";

        hotel = new Hotel();
        inventario = new Inventario();
        huesped = new Huesped(nombre, documento, edad, correo, celular);

    }

    @Test
    @Order(1)
    @DisplayName("Crear Reserva")
    public void testReserva() {
        Reserva reserva = null;
        try {
        	reserva = Recepcionista.crearReserva(fInicio, fFin, adultosE, niniosE, nombre, documento, edad, correo, celular);
        	assertEquals(huesped, reserva.getACargo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test
    @Order(2)
    @DisplayName("Registrar Consumo")
    public void testConsumo() {
        Reserva reserva = null;
        Grupo grupo = null;
        try {
        	
            assertEquals("No se pudo actualizar el registro de consumo",
                    recepcionista.registrarConsumo("1", "48965153", false));

            assertEquals("No se pudo actualizar el registro de consumo",
                    recepcionista.registrarConsumo("498651", "41658", false));
            
        	recepcionista.cancelarReserva(reserva.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    @Order(3)
    @DisplayName("Hacer CheckOut")
    public void testCheckout() {
        // Reserva reserva =
        assertEquals("El grupo no existe",
                recepcionista.hacerCheckOut("798465", "84965165", "juan", "65841651", "PayPal"));

    }

    @Test
    @Order(4)
    @DisplayName("get consumos")
    public void testgetConsumo() throws Exception {
    	try {
            assertEquals("¡Exitoso! No habían consumos por pagar",
                    recepcionista.getConsumos("3"));
    	} catch (Exception e) {
            e.printStackTrace();
        }

    }


}

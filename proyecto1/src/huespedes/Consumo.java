package huespedes;

import java.time.LocalDate;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Consumo {
	private static final double IVA = 0.19;
	private String id;
	private String idGrupo;
	private LocalDate fecha;
	private String tipo;
	private String descripcion = "";
	private double valor;
	private double valorImpuesto;
	private double valorTotal;
	private boolean pagado = false;
	private String factura;
	private ArrayList<Huesped> huespedes = new ArrayList<Huesped>();

	/**
	 * Se asignan los valores de los parametros.
	 */
	public Consumo(String id, String idGrupo, String tipo, String descripcion, int valor, boolean pagado) {
		this.id = id;
		this.idGrupo = idGrupo;
		this.fecha = LocalDate.now();
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.valor = valor;
		this.valorImpuesto = valor * IVA;
		this.valorTotal = this.valor + this.valorImpuesto;
		this.factura = "grupos/" + idGrupo + "/factura_" + id + ".txt";
		this.pagado = pagado;
		if(pagado) {
			generarFactura();
		}
	}
	/**
	 * Se asignan los valores de los parametros.
	 */
	public Consumo(String id, String idGrupo, LocalDate fecha, String tipo, String descripcion, int valor, boolean pagado) {
		this.id = id;
		this.idGrupo = idGrupo;
		this.fecha = fecha;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.valor = valor;
		this.valorImpuesto = valor * IVA;
		this.valorTotal = this.valor + this.valorImpuesto;
		this.factura = "grupos/" + idGrupo + "/factura_" + id + ".txt";
		this.pagado = pagado;
		if(pagado) {
			generarFactura();
		}
	}

	/**
	 * Se obtiene el Id del consumo
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Se obtienen si el consumo ya fue pagado.
	 */
	public boolean getPagado() {
		return this.pagado;
	}
	
	/**
	 * Obtiene la información del consumo
	 */
	public String getInfo() {
		return "Id: " + this.id + " Tipo: " + this.tipo + " Fecha: " + fecha.toString() + " Valor : $" + this.valorTotal;
	}
	
	/**
	 * Obtiene la información para CSV
	 */
	public String getLineaCSV() {
		String descripcion = this.descripcion.replace("\n", "#");
		String documentos = "";
		int valor = (int) this.valor;
		for (Huesped huesped: this.huespedes) {
			documentos += huesped.getDocumento() + "#";
		}
		return this.fecha.toString() + "," + this.tipo + "," + descripcion + "," + valor + "," + this.pagado + "," + documentos;
	}
	
	/**
	 * Obtiene el valor total del consumo
	 */
	public double getValorTotal() {
		return this.valorTotal;
	}

	/**
	 * Agrega un huesped.
	 */
	public void agregarHuesped(Huesped huesped) {
		this.huespedes.add(huesped);
	}

	/**
	 * Agrega a los huespedes en base a una lista.
	 */
	public void agregarHuespedes(ArrayList<Huesped> huespedes) {
		this.huespedes = huespedes;
	}

	/**
	 * Registra el pago de los consumos.
	 */
	public void registrarPago() {
		this.pagado = true;
		generarFactura();
	}

	/**
	 * Se genera la factura de los consumos.
	 */
	public void generarFactura() {
		try {
			File archivo = new File(factura);
			archivo.createNewFile();
			FileWriter fw = new FileWriter(factura, StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(generarTextoFactura());
			bw.close();
		} catch (Exception e) {
			System.out.println("No se pudo generar la factura");
		}
	}

	/**
	 * Genera la infomacion y datos que conlleva la factura.
	 */
	public String generarTextoFactura() {
		String factura = "";
		factura += "FACTURA N° " + this.id + " GRUPO " + this.idGrupo + "\n";
		factura += "Tipo: " + this.tipo + " Fecha: " + this.fecha.toString() + " \n";
		factura += "\nDescripción:\n" + this.descripcion.replace("#", "\n");
		factura += "\nHuéspedes: \n";
		for (Huesped huesped : huespedes) {
			factura += "- " + huesped.getNombre() + " - " + huesped.getDocumento() + "\n";
		}
		factura += "\nValor: $" + this.valor + "\n";
		factura += "IVA (19%): $" + this.valorImpuesto + "\n";
		factura += "Valor Total: $" + this.valorTotal + "\n";
		return factura;
	}
	
	
}

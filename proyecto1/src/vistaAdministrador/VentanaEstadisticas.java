package vistaAdministrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.LegendPosition;

import catalogo.Restaurante;
import vista.VentanaInicio;
import vista.VentanaUsuario;

@SuppressWarnings("serial")
public class VentanaEstadisticas extends VentanaUsuario {
	private JButton ventasProductoCantidad;
	private JButton ventasProductoValor;
	private JButton ventasHoras;
	private JButton cantidadFacturas;
	private JButton valorFacturas;
	private JPanel panel;
	private JPanel panelC;
	private JPanel panelT;
	private JScrollPane scroll;
	
	public VentanaEstadisticas() {
		super();
		crearPanelSuperior("ESTADÍSTICAS");
		crearPanelCentro("administrador");
		crearPanelIzquierdo(); 
	}
	
	public void crearPanelIzquierdo() {
		this.pIzquierdo = new JPanel(new GridLayout(0, 1));
		
		this.ventasProductoCantidad = VentanaInicio.crearBoton("<html>Cantidad de<br>productos vendidos</html>");
		ventasProductoCantidad.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	ventasProductoCantidad();
	        }
	    });
	    
		this.ventasProductoValor = VentanaInicio.crearBoton("<html>Valor<br>productos vendidos</html>");
		ventasProductoValor.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	ventasProductoValor();
	        }
	    });
		
		this.ventasHoras = VentanaInicio.crearBoton("<html>Ventas por<br>hora</html>");
		ventasHoras.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	ventasHoras();
	        }
	    });
		
		this.cantidadFacturas = VentanaInicio.crearBoton("<html>Cantidad de<br>facturas</html>");
		cantidadFacturas.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	cantidadFacturas();
	        }
	    });
		
		this.valorFacturas = VentanaInicio.crearBoton("<html>Valor de las<br>facturas</html>");
		valorFacturas.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	valorFacturas();
	        }
	    });
	    
		pIzquierdo.add(Box.createVerticalGlue());
		pIzquierdo.add(ventasProductoCantidad);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(ventasProductoValor);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(ventasHoras);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(cantidadFacturas);
		pIzquierdo.add(Box.createVerticalStrut(10));
		pIzquierdo.add(valorFacturas);
		pIzquierdo.add(Box.createVerticalGlue());
		add(pIzquierdo, BorderLayout.WEST);
		revalidate();
	    repaint();
	}
	
	public void ventasProductoCantidad() {
	    pCentro.removeAll();
	    
	    ArrayList<String> x = Restaurante.getNombresPlatos();
	    ArrayList<Integer> y = Restaurante.getCantidadVendidosPlatos();
	    CategoryChart chart = new CategoryChartBuilder().width(2200).height(600).title("Cantidad de productos vendidos").xAxisTitle("Productos").yAxisTitle("Vendidos").build();
	    chart.addSeries("Productos", x, y);
	   
	    chart.getStyler().setSeriesColors(new Color[]{new Color(0,255,181)});
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setLabelsVisible(true);
	    chart.getStyler().setLabelsFont(new Font("Arial", Font.PLAIN, 14));
	    
	    panelC = new XChartPanel<CategoryChart>(chart);
	    
	    panelT = new JPanel(new GridLayout(0, 2));
		panelT.setBorder(new EmptyBorder(10, 10, 10, 10));
	    panelT.add(VentanaInicio.crearMensaje("Producto"));
	    panelT.add(VentanaInicio.crearMensaje("Cantidad vendidos"));
	    for(int i = 0; i< x.size(); i++) {
	    	panelT.add(new JLabel(x.get(i)));
	    	panelT.add(new JLabel(String.valueOf(y.get(i))));
	    }
	    
	    panel = new JPanel();
	    panel.add(panelC);
	    panel.add(panelT);
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
	public void ventasProductoValor() {
	    pCentro.removeAll();
	    
	    ArrayList<String> x = Restaurante.getNombresPlatos();
	    ArrayList<Integer> y = Restaurante.getValorVendidoPlatos();
	    CategoryChart chart = new CategoryChartBuilder().width(2200).height(600).title("Valor total de los productos vendidos").xAxisTitle("Productos").yAxisTitle("Valor en pesos ($)").build();
	    chart.addSeries("Productos", x, y);
	   
	    chart.getStyler().setSeriesColors(new Color[]{new Color(255,183,0)});
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setLabelsVisible(true);
	    chart.getStyler().setLabelsFont(new Font("Arial", Font.PLAIN, 14));
	    
	    panelC = new XChartPanel<CategoryChart>(chart);
	    
	    panelT = new JPanel(new GridLayout(0, 2));
		panelT.setBorder(new EmptyBorder(10, 10, 10, 10));
	    panelT.add(VentanaInicio.crearMensaje("Producto"));
	    panelT.add(VentanaInicio.crearMensaje("Valor productos vendidos"));
	    for(int i = 0; i< x.size(); i++) {
	    	panelT.add(new JLabel(x.get(i)));
	    	panelT.add(new JLabel(String.valueOf(y.get(i))));
	    }
	    
	    panel = new JPanel();
	    panel.add(panelC);
	    panel.add(panelT);
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
	public void ventasHoras() {
	    pCentro.removeAll();
	    
	    int[] horasPedidos = Restaurante.getHorasPedidos();
	    PieChart chart = new PieChartBuilder().width(600).height(400).title("Ventas por horas del día").build();
	    
	    Color[] sliceColors = new Color[] { new Color(255, 154, 0), new Color(101, 255, 0), new Color(0, 228, 255)};
	    chart.getStyler().setSeriesColors(sliceColors);
	    chart.getStyler().setLabelsVisible(true);
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    
	    chart.addSeries("Mañana (5:00-11:59)", horasPedidos[0]);
	    chart.addSeries("Tarde (12:00-17:59)", horasPedidos[1]);
	    chart.addSeries("Noche (18:00-4:59)", horasPedidos[2]);
	    
	    panelC = new XChartPanel<PieChart>(chart);
	    
	    panelT = new JPanel(new GridLayout(0, 2));
		panelT.setBorder(new EmptyBorder(10, 10, 10, 10));
	    panelT.add(VentanaInicio.crearMensaje("Horario"));
	    panelT.add(VentanaInicio.crearMensaje("Cantidad pedidos"));
	    panelT.add(new JLabel("Mañana (5:00-11:59)"));
	    panelT.add(new JLabel(String.valueOf(horasPedidos[0])));
	    panelT.add(new JLabel("Tarde (12:00-17:59)"));
	    panelT.add(new JLabel(String.valueOf(horasPedidos[1])));
	    panelT.add(new JLabel("Noche (18:00-4:59)"));
	    panelT.add(new JLabel(String.valueOf(horasPedidos[2])));
	    
	    panel = new JPanel();
	    panel.add(panelC);
	    panel.add(panelT);
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
	public void cantidadFacturas() {
	    pCentro.removeAll();
	    
	    ArrayList<String> x = Restaurante.getFechasPedidos();
	    ArrayList<Integer> y = Restaurante.getCantidadPedidos();
	    CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title("Cantidad de facturas por día").xAxisTitle("Fechas").yAxisTitle("Cantidad de facturas").build();
	    chart.addSeries("Facturas", x, y);
	   
	    chart.getStyler().setSeriesColors(new Color[]{new Color(223,0,255)});
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setLabelsVisible(true);
	    chart.getStyler().setLabelsFont(new Font("Arial", Font.PLAIN, 14));
	    
	    panelC = new XChartPanel<CategoryChart>(chart);
	    
	    panelT = new JPanel(new GridLayout(0, 2));
		panelT.setBorder(new EmptyBorder(10, 10, 10, 10));
	    panelT.add(VentanaInicio.crearMensaje("Fecha"));
	    panelT.add(VentanaInicio.crearMensaje("Cantidad facturas"));
	    for(int i = 0; i< x.size(); i++) {
	    	panelT.add(new JLabel(x.get(i)));
	    	panelT.add(new JLabel(String.valueOf(y.get(i))));
	    }
	    
	    panel = new JPanel();
	    panel.add(panelC);
	    panel.add(panelT);
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
	public void valorFacturas() {
	    pCentro.removeAll();
	    
	    ArrayList<String> x = Restaurante.getFechasPedidos();
	    ArrayList<Integer> y = Restaurante.getValorPedidos();
	    CategoryChart chart = new CategoryChartBuilder().width(1000).height(600).title("Valor de las facturas por día").xAxisTitle("Fechas").yAxisTitle("Valor en pesos ($)").build();
	    chart.addSeries("Facturas", x, y);
	   
	    chart.getStyler().setSeriesColors(new Color[]{new Color(0,160,255)});
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setLabelsVisible(true);
	    chart.getStyler().setLabelsFont(new Font("Arial", Font.PLAIN, 14));
	    
	    panelC = new XChartPanel<CategoryChart>(chart);
	    
	    panelT = new JPanel(new GridLayout(0, 2));
		panelT.setBorder(new EmptyBorder(10, 10, 10, 10));
	    panelT.add(VentanaInicio.crearMensaje("Fecha"));
	    panelT.add(VentanaInicio.crearMensaje("Valor facturas"));
	    for(int i = 0; i< x.size(); i++) {
	    	panelT.add(new JLabel(x.get(i)));
	    	panelT.add(new JLabel(String.valueOf(y.get(i))));
	    }
	    
	    panel = new JPanel();
	    panel.add(panelC);
	    panel.add(panelT);
	    scroll = new JScrollPane(panel);
	    pCentro.add(scroll);
	    revalidate();
	    repaint();
	}
	
}

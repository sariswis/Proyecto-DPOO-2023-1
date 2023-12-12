package vistaAdministrador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import catalogo.Nombre;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class CambiarTarifaServicio extends JPanel {
	private VentanaAdministrador ventana;
	//Botones y demas//
	private JComboBox<Nombre> tipo;
	private JFormattedTextField tarifa;
	private JButton enviar;
	private JLabel mensajeFinal;
	
	public CambiarTarifaServicio( VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.ventana=ventana;
		this.mensajeFinal = VentanaInicio.crearMensaje("");
		this.tipo= new JComboBox<Nombre>();
		tipo.addItem(Nombre.GUIA_TURISTICO);
		tipo.addItem(Nombre.SPA);
		Dimension dimension = new Dimension(200, 30);
	    tipo.setMaximumSize(dimension);
		this.tarifa=VentanaInicio.crearNumberField();
		this.enviar=VentanaInicio.crearBoton("Enviar");
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	Nombre tipoSeleccionado=(Nombre) tipo.getSelectedItem();
	        	int tarifaSeleccionado=Integer.parseInt(tarifa.getText());
	        	String texto=ventana.cambiarTarifaServicio(tipoSeleccionado, tarifaSeleccionado);	
	        	mensajeFinal.setOpaque(true);
	            mensajeFinal.setText(texto);
	            revalidate();
	            repaint();;;;
	        }
	    }); 
		add(new JLabel("¿Cual es el tipo de servicio?"));
		add(Box.createVerticalStrut(10));
		add(tipo);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es la nueva tarifa del servicio?"));
		add(Box.createVerticalStrut(10));
		add(tarifa);
		add(Box.createVerticalStrut(10));
		add(enviar);
		add(mensajeFinal);
	}
	

}

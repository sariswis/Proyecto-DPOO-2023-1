package vistaAdministrador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import inventario.Tipo;
import vista.VentanaInicio;

@SuppressWarnings("serial")
public class CambiarTarifaHabitacion extends JPanel{
	private VentanaAdministrador ventana;
	private JComboBox<Tipo> tipo;
	private JFormattedTextField tarifa;
	private JTextField inicio;
	private JTextField fin;
	private JRadioButton[] diasButton;
	private JButton enviar;
	private JLabel mensajeFinal;

	
	public CambiarTarifaHabitacion(VentanaAdministrador ventana) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		this.mensajeFinal = VentanaInicio.crearMensaje("");
		this.ventana=ventana;
		this.tipo=new JComboBox<Tipo>();
		this.diasButton= new JRadioButton[7];
		tipo.addItem(Tipo.ESTANDAR);
		tipo.addItem(Tipo.SUITE);
		tipo.addItem(Tipo.SUITE_DOBLE);
		Dimension preferredSize = tipo.getPreferredSize();
	    preferredSize.width = 200; // Establecer el ancho deseado
	    tipo.setPreferredSize(preferredSize);
	    tipo.setMinimumSize(preferredSize);
	    tipo.setMaximumSize(preferredSize);
		this.tarifa=VentanaInicio.crearNumberField();
		this.inicio=VentanaInicio.crearTextField();
		this.fin=VentanaInicio.crearTextField();
		JRadioButton lunes=new JRadioButton();
		diasButton[0]=lunes;
		JRadioButton martes=new JRadioButton();
		diasButton[1]=martes;
		JRadioButton miercoles=new JRadioButton();
		diasButton[2]=miercoles;
		JRadioButton jueves=new JRadioButton();
		diasButton[3]=jueves;
		JRadioButton viernes=new JRadioButton();
		diasButton[4]=viernes;
		JRadioButton sabado=new JRadioButton();
		diasButton[5]=sabado;
		JRadioButton domingo=new JRadioButton();
		diasButton[6]=domingo;
		this.enviar=VentanaInicio.crearBoton("Enviar");
		enviar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	ArrayList<DayOfWeek> dias= new ArrayList<DayOfWeek>();
	        	for(int i=0; i<7; i++) {
	        		if(diasButton[i].isSelected()) {
	        			dias.add(DayOfWeek.of(i+1));
	        		}
	        	}
	        	Tipo tipoSeleccionado = (Tipo) tipo.getSelectedItem();
	        	int tarifaSeleccionado= Integer.parseInt(tarifa.getText().replace(",", ""));
	        	String inicioSeleccionado=inicio.getText();
	        	String finSeleccionado=fin.getText();
	        	String texto=ventana.cargarTarifaHabitacion(tipoSeleccionado, tarifaSeleccionado, inicioSeleccionado, finSeleccionado, dias);
	        	mensajeFinal.setOpaque(true);
	            mensajeFinal.setText(texto);
	            revalidate();
	            repaint();
	        }
	    }); 
		
		this.setAlignmentX(LEFT_ALIGNMENT);
		add(new JLabel("¿Cual es el tipo de habitación?"));
		add(Box.createVerticalStrut(10));
		add(tipo);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es la nueva tarifa?"));
		add(Box.createVerticalStrut(10));
		add(tarifa);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es la fecha inicial de la tarifa? (YYYY-MM-DD)"));
		add(Box.createVerticalStrut(10));
		add(inicio);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Cual es la fecha final de la tarifa? (YYYY-MM-DD)"));
		add(Box.createVerticalStrut(10));
		add(fin);
		add(Box.createVerticalStrut(10));
		add(new JLabel("¿Para cuales de los dias es valida la tarifa?"));
		add(new JLabel("Lunes"));
		add(lunes);
		add(new JLabel("Martes"));
		add(martes);
		add(new JLabel("Miércoles"));
		add(miercoles);
		add(new JLabel("Jueves"));
		add(jueves);
		add(new JLabel("Viernes"));
		add(viernes);
		add(new JLabel("Sabado"));
		add(sabado);
		add(new JLabel("Domingo"));
		add(domingo);
		add(enviar);
		add(mensajeFinal);
		

	}
	
	
}

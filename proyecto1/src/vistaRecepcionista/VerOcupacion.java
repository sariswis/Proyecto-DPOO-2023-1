package vistaRecepcionista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.Month;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import inventario.Inventario;


@SuppressWarnings("serial")
public class VerOcupacion extends JPanel {
	private int[][][] ocupada;
	
	public VerOcupacion() {
		setLayout(new GridLayout(14, 32));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		ocupada = Inventario.getMatriz();
		
		add(new JLabel("N°"));
		add(crearCasilla(2,228,77));
		add(crearCasilla(49,230,109));
		add(crearCasilla(101,231,144));
		add(crearCasilla(147,231,175));
		add(crearCasilla(182,231,198));
		add(crearCasilla(223,223,223));
		for(int i = 0; i<25; i++) {
			add(Box.createGlue());
		}
		
		add(Box.createGlue());
		for(int i = 0; i<31; i++) {
			add(new JLabel(String.valueOf(i+1)));
		}

		for (int i = 0;  i<12; i++) {
			Month month = Month.of(i+1);
			String monthName = month.toString().substring(0, 2);
			add(new JLabel(monthName));
			for(int j = 0; j<31; j++) {
				if(ocupada[i][j][0] == 0) {
					add(Box.createGlue());
				} else {
					add(crearCasilla(ocupada[i][j][0], ocupada[i][j][1], ocupada[i][j][2]));
				}
			}
		}
	}
	
	public static  JButton crearCasilla(int r, int g, int b) {
		JButton boton = new JButton("");
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color (r,g,b));
		boton.setFont(new Font("Arial", Font.BOLD, 16));
		Dimension dimension = new Dimension(10, 10);
		boton.setPreferredSize(dimension);
		boton.setMaximumSize(dimension);
		return boton;
	}
	
}

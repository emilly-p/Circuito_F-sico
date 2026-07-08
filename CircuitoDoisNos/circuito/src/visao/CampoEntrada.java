package visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class CampoEntrada extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JSlider slider;
    private final JTextField campoTexto;
    private final double fatorEscala;



	/**
	 * Create the frame.
	 */
	public CampoEntrada(String titulo, double valorMinimoSlider, double valorMaximoSlider,
    double valorInicial, double fatorEscala) {
		this.fatorEscala = fatorEscala;

        setLayout(new BorderLayout(6, 6));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        JLabel rotulo = new JLabel(titulo);
        rotulo.setFont(rotulo.getFont().deriveFont(Font.BOLD, 13f));

        int minInt = (int) Math.round(valorMinimoSlider * fatorEscala);
        int maxInt = (int) Math.round(valorMaximoSlider * fatorEscala);
        int valInt = (int) Math.round(valorInicial * fatorEscala);

        slider = new JSlider(minInt, maxInt, valInt);
        slider.setBackground(Color.WHITE);

        campoTexto = new JTextField(formatarValor(valorInicial));
        campoTexto.setHorizontalAlignment(JTextField.CENTER);
        campoTexto.setPreferredSize(new Dimension(90, 28));

        slider.addChangeListener(e -> campoTexto.setText(formatarValor(getValor())));

        campoTexto.addActionListener(e -> {
            try {
                double valorDigitado = Double.parseDouble(campoTexto.getText().trim().replace(",", "."));
                int valorInt = (int) Math.round(valorDigitado * fatorEscala);
                valorInt = Math.max(slider.getMinimum(), Math.min(slider.getMaximum(), valorInt));
                slider.setValue(valorInt);
                campoTexto.setText(formatarValor(getValor()));
            } catch (NumberFormatException ex) {
                campoTexto.setText(formatarValor(getValor()));
            }
        });

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(Color.WHITE);
        painelSuperior.add(rotulo, BorderLayout.WEST);
        painelSuperior.add(campoTexto, BorderLayout.EAST);

        add(painelSuperior, BorderLayout.NORTH);
        add(slider, BorderLayout.CENTER);
    }

    private String formatarValor(double valor) {
        return String.format("%.1f", valor);
    }

    /** Valor atual do campo, já na unidade real (V ou Ohms). */
    public double getValor() {
        return slider.getValue() / fatorEscala;
    }

    public void definirValor(double valor) {
        int valorInt = (int) Math.round(valor * fatorEscala);
        valorInt = Math.max(slider.getMinimum(), Math.min(slider.getMaximum(), valorInt));
        slider.setValue(valorInt);
        campoTexto.setText(formatarValor(getValor()));
    }

	

}

package visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controle.CircuitoNos;
import controle.ValorInvalidoException;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	 private final CircuitoNos circuito = new CircuitoNos();

	    private final CampoEntrada campoE1;
	    private final CampoEntrada campoE2;
	    private final CampoEntrada campoR1;
	    private final CampoEntrada campoR2;
	    private final CampoEntrada campoR3;

	    private final PainelDiagrama painelDiagrama;

	    private final JLabel valorTensaoA;
	    private final JLabel valorCorrenteR1;
	    private final JLabel valorSentidoR1;
	    private final JLabel valorCorrenteR2;
	    private final JLabel valorSentidoR2;
	    private final JLabel valorCorrenteR3;
	    private final JLabel valorSentidoR3;

	    public JanelaPrincipal() {
	        super("Calculadora de Circuito de Dois Nós (Problema 36)");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout(10, 10));

	        Color corFundo = new Color(240, 242, 245);
	        getContentPane().setBackground(corFundo);
	        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

	        add(criarTitulo(), BorderLayout.NORTH);

	        painelDiagrama = new PainelDiagrama();
	        JPanel painelDiagramaWrapper = new JPanel(new FlowLayout());
	        painelDiagramaWrapper.setBackground(Color.WHITE);
	        painelDiagramaWrapper.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225)));
	        painelDiagramaWrapper.add(painelDiagrama);

	        // Faixas dos sliders deliberadamente mais amplas que o intervalo válido,
	        // para permitir a demonstração da validação de entrada.
	        campoE1 = new CampoEntrada("Bateria E1 (V)", 0.0, 300.0, 6.0, 10);
	        campoE2 = new CampoEntrada("Bateria E2 (V)", 0.0, 300.0, 12.0, 10);
	        campoR1 = new CampoEntrada("Resistor R1 (Ohms)", 0.0, 12000.0, 100.0, 10);
	        campoR2 = new CampoEntrada("Resistor R2 (Ohms)", 0.0, 12000.0, 200.0, 10);
	        campoR3 = new CampoEntrada("Resistor R3 (Ohms)", 0.0, 12000.0, 300.0, 10);

	        JPanel painelEntradas = new JPanel(new GridLayout(0, 2, 12, 12));
	        painelEntradas.setBackground(corFundo);
	        painelEntradas.setBorder(BorderFactory.createTitledBorder("VARIÁVEIS DE ENTRADA"));
	        painelEntradas.add(campoE1);
	        painelEntradas.add(campoE2);
	        painelEntradas.add(campoR1);
	        painelEntradas.add(campoR2);
	        painelEntradas.add(campoR3);
	        painelEntradas.add(new JLabel(""));

	        JButton botaoCalcular = new JButton("Calcular");
	        botaoCalcular.setBackground(new Color(60, 140, 80));
	        botaoCalcular.setForeground(Color.WHITE);
	        botaoCalcular.setFocusPainted(false);
	        botaoCalcular.addActionListener(e -> calcular());

	        JButton botaoLimpar = new JButton("Limpar");
	        botaoLimpar.setBackground(new Color(90, 100, 110));
	        botaoLimpar.setForeground(Color.WHITE);
	        botaoLimpar.setFocusPainted(false);
	        botaoLimpar.addActionListener(e -> limpar());

	        JPanel painelAcoes = new JPanel(new GridLayout(1, 2, 12, 12));
	        painelAcoes.setBackground(corFundo);
	        painelAcoes.setBorder(BorderFactory.createTitledBorder("AÇÕES"));
	        painelAcoes.add(botaoCalcular);
	        painelAcoes.add(botaoLimpar);

	        valorTensaoA = criarLabelResultado();
	        valorCorrenteR1 = criarLabelResultado();
	        valorSentidoR1 = criarLabelSentido();
	        valorCorrenteR2 = criarLabelResultado();
	        valorSentidoR2 = criarLabelSentido();
	        valorCorrenteR3 = criarLabelResultado();
	        valorSentidoR3 = criarLabelSentido();

	        JPanel painelResultados = new JPanel(new GridLayout(1, 4, 12, 12));
	        painelResultados.setBackground(corFundo);
	        painelResultados.setBorder(BorderFactory.createTitledBorder("RESULTADOS DO CÁLCULO"));
	        painelResultados.add(criarCartaoResultado("Tensão no nó A (VA)", valorTensaoA, null));
	        painelResultados.add(criarCartaoResultado("Corrente i1 (R1)", valorCorrenteR1, valorSentidoR1));
	        painelResultados.add(criarCartaoResultado("Corrente i2 (R2)", valorCorrenteR2, valorSentidoR2));
	        painelResultados.add(criarCartaoResultado("Corrente i3 (R3)", valorCorrenteR3, valorSentidoR3));

	        JPanel painelCentral = new JPanel();
	        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
	        painelCentral.setBackground(corFundo);
	        painelCentral.add(painelDiagramaWrapper);
	        painelCentral.add(Box.createVerticalStrut(10));
	        painelCentral.add(painelEntradas);
	        painelCentral.add(Box.createVerticalStrut(10));
	        painelCentral.add(painelAcoes);
	        painelCentral.add(Box.createVerticalStrut(10));
	        painelCentral.add(painelResultados);

	        JScrollPane scroll = new JScrollPane(painelCentral);
	        scroll.setBorder(BorderFactory.createEmptyBorder());
	        scroll.getVerticalScrollBar().setUnitIncrement(16);
	        add(scroll, BorderLayout.CENTER);

	        setMinimumSize(new Dimension(780, 800));
	        pack();
	        setLocationRelativeTo(null);
	    }

	    private JLabel criarTitulo() {
	        JLabel titulo = new JLabel("CALCULADORA DE CIRCUITO (PROBLEMA 36)", SwingConstants.CENTER);
	        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
	        return titulo;
	    }

	    private JLabel criarLabelResultado() {
	        JLabel label = new JLabel("--", SwingConstants.CENTER);
	        label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));
	        label.setOpaque(true);
	        label.setBackground(new Color(225, 225, 225));
	        label.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	        label.setAlignmentX(Component.CENTER_ALIGNMENT);
	        return label;
	    }

	    private JLabel criarLabelSentido() {
	        JLabel label = new JLabel("--", SwingConstants.CENTER);
	        label.setFont(label.getFont().deriveFont(Font.ITALIC, 12f));
	        label.setAlignmentX(Component.CENTER_ALIGNMENT);
	        return label;
	    }

	    private JPanel criarCartaoResultado(String titulo, JLabel valor, JLabel sentido) {
	        JPanel painel = new JPanel();
	        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
	        painel.setBackground(Color.WHITE);
	        painel.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(225, 225, 225)),
	                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	        JLabel rotulo = new JLabel(titulo, SwingConstants.CENTER);
	        rotulo.setAlignmentX(Component.CENTER_ALIGNMENT);
	        rotulo.setFont(rotulo.getFont().deriveFont(Font.PLAIN, 12f));

	        painel.add(rotulo);
	        painel.add(Box.createVerticalStrut(6));
	        painel.add(valor);
	        if (sentido != null) {
	            painel.add(Box.createVerticalStrut(4));
	            painel.add(sentido);
	        }
	        return painel;
	    }

	    private void calcular() {
	        try {
	            double e1 = campoE1.getValor();
	            double e2 = campoE2.getValor();
	            double r1 = campoR1.getValor();
	            double r2 = campoR2.getValor();
	            double r3 = campoR3.getValor();

	            circuito.definirValores(e1, e2, r1, r2, r3);
	            circuito.calcular();

	            valorTensaoA.setText(String.format("%.2f V", circuito.getTensaoNoA()));
	            valorTensaoA.setBackground(new Color(200, 230, 201));

	            valorCorrenteR1.setText(String.format("%.2f mA", Math.abs(circuito.getCorrenteR1()) * 1000.0));
	            valorSentidoR1.setText("Sentido: " + circuito.getSentidoR1());

	            valorCorrenteR2.setText(String.format("%.2f mA", Math.abs(circuito.getCorrenteR2()) * 1000.0));
	            valorSentidoR2.setText("Sentido: " + circuito.getSentidoR2());

	            valorCorrenteR3.setText(String.format("%.2f mA", Math.abs(circuito.getCorrenteR3()) * 1000.0));
	            valorSentidoR3.setText("Sentido: " + circuito.getSentidoR3());

	            painelDiagrama.atualizar(circuito, true);

	        } catch (ValorInvalidoException excecao) {
	            JOptionPane.showMessageDialog(
	                    this,
	                    excecao.getMessage() + "\n\nPor favor, insira um novo valor dentro do intervalo permitido.",
	                    "Valor inválido",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void limpar() {
	        valorTensaoA.setText("--");
	        valorTensaoA.setBackground(new Color(225, 225, 225));
	        valorCorrenteR1.setText("--");
	        valorSentidoR1.setText("--");
	        valorCorrenteR2.setText("--");
	        valorSentidoR2.setText("--");
	        valorCorrenteR3.setText("--");
	        valorSentidoR3.setText("--");

	        campoE1.definirValor(6.0);
	        campoE2.definirValor(12.0);
	        campoR1.definirValor(100.0);
	        campoR2.definirValor(200.0);
	        campoR3.definirValor(300.0);

	        painelDiagrama.atualizar(null, false);
	    }
}

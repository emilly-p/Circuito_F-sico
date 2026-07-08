package visao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import controle.CircuitoNos;

public class PainelDiagrama extends JPanel {

	private static final long serialVersionUID = 1L;
	 private static final Color COR_FIO = new Color(30, 30, 30);
	    private static final Color COR_CORRENTE = new Color(200, 30, 30);
	    private static final Color COR_NO = new Color(200, 30, 30);

	    private CircuitoNos circuito;
	    private boolean calculado;

	/**
	 * Create the panel.
	 */
	public PainelDiagrama() {
		 setPreferredSize(new Dimension(560, 300));
	        setBackground(Color.WHITE);
	}

	public void atualizar(CircuitoNos circuito, boolean calculado) {
        this.circuito = circuito;
        this.calculado = calculado;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(COR_FIO);
        g2.setFont(g2.getFont().deriveFont(12f));

        int xA = 280;
        int yA = 55;
        int xEsq = 90;
        int xDir = 470;
        int yBateriaCentro = 145;
        int yTerra = 235;

        // Fios horizontais superiores até o nó A
        g2.drawLine(xEsq, yA, xA - 45, yA);
        g2.drawLine(xA + 45, yA, xDir, yA);

        // Resistores R2 (esquerda) e R3 (direita), horizontais
        desenharResistorHorizontal(g2, xA - 135, xA - 45, yA, "R2");
        desenharResistorHorizontal(g2, xA + 45, xA + 135, yA, "R3");

        // Nó A
        g2.setColor(COR_NO);
        g2.fill(new Ellipse2D.Double(xA - 4, yA - 4, 8, 8));
        g2.setColor(COR_FIO);
        g2.drawString("A", xA + 8, yA - 8);

        // Ramo esquerdo: fio até a bateria E1, bateria, fio até o terra
        g2.drawLine(xEsq, yA, xEsq, yBateriaCentro - 20);
        desenharBateriaVertical(g2, xEsq, yBateriaCentro, "E1");
        g2.drawLine(xEsq, yBateriaCentro + 20, xEsq, yTerra);

        // Ramo direito: fio até a bateria E2, bateria, fio até o terra
        g2.drawLine(xDir, yA, xDir, yBateriaCentro - 20);
        desenharBateriaVertical(g2, xDir, yBateriaCentro, "E2");
        g2.drawLine(xDir, yBateriaCentro + 20, xDir, yTerra);

        // Resistor R1, vertical, ligando o nó A ao barramento de terra
        g2.drawLine(xA, yA, xA, yA + 12);
        desenharResistorVertical(g2, xA, yA + 12, yTerra - 12, "R1");
        g2.drawLine(xA, yTerra - 12, xA, yTerra);

        // Barramento de terra
        g2.drawLine(xEsq, yTerra, xDir, yTerra);
        desenharSimboloTerra(g2, xA, yTerra);

        if (calculado && circuito != null) {
            int xMeioR2 = (xA - 135 + xA - 45) / 2;
            int xMeioR3 = (xA + 45 + xA + 135) / 2;

            desenharSetaVertical(g2, xA, yA + 55, circuito.getCorrenteR1() >= 0,
                    formatarCorrente(circuito.getCorrenteR1()));
            desenharSetaHorizontal(g2, xMeioR2, yA - 22, circuito.getCorrenteR2() >= 0,
                    formatarCorrente(circuito.getCorrenteR2()));
            desenharSetaHorizontal(g2, xMeioR3, yA - 22, circuito.getCorrenteR3() < 0,
                    formatarCorrente(circuito.getCorrenteR3()));
        }
    }

    private String formatarCorrente(double correnteEmAmperes) {
        return String.format("%.2f mA", Math.abs(correnteEmAmperes) * 1000.0);
    }

    private void desenharResistorHorizontal(Graphics2D g2, int x1, int x2, int y, String rotulo) {
        Path2D caminho = criarZigueZague(x1, y, x2, y, true);
        g2.draw(caminho);
        g2.drawString(rotulo, (x1 + x2) / 2 - 8, y - 14);
    }

    private void desenharResistorVertical(Graphics2D g2, int x, int y1, int y2, String rotulo) {
        Path2D caminho = criarZigueZague(x, y1, x, y2, false);
        g2.draw(caminho);
        g2.drawString(rotulo, x + 14, (y1 + y2) / 2 + 4);
    }

    private Path2D criarZigueZague(int x1, int y1, int x2, int y2, boolean horizontal) {
        Path2D caminho = new Path2D.Double();
        caminho.moveTo(x1, y1);
        int passos = 6;
        int deslocamento = 8;
        if (horizontal) {
            int passo = (x2 - x1) / passos;
            for (int i = 0; i < passos; i++) {
                int xAtual = x1 + i * passo + passo / 2;
                int yAtual = (i % 2 == 0) ? y1 - deslocamento : y1 + deslocamento;
                caminho.lineTo(xAtual, yAtual);
            }
            caminho.lineTo(x2, y2);
        } else {
            int passo = (y2 - y1) / passos;
            for (int i = 0; i < passos; i++) {
                int yAtual = y1 + i * passo + passo / 2;
                int xAtual = (i % 2 == 0) ? x1 - deslocamento : x1 + deslocamento;
                caminho.lineTo(xAtual, yAtual);
            }
            caminho.lineTo(x2, y2);
        }
        return caminho;
    }

    private void desenharBateriaVertical(Graphics2D g2, int x, int yCentro, String rotulo) {
        g2.drawLine(x - 14, yCentro - 6, x + 14, yCentro - 6);
        g2.drawLine(x - 7, yCentro + 6, x + 7, yCentro + 6);
        g2.drawString("+", x + 18, yCentro - 2);
        g2.drawString(rotulo, x - 42, yCentro + 4);
    }

    private void desenharSimboloTerra(Graphics2D g2, int x, int y) {
        g2.drawLine(x, y, x, y + 8);
        g2.drawLine(x - 12, y + 8, x + 12, y + 8);
        g2.drawLine(x - 8, y + 13, x + 8, y + 13);
        g2.drawLine(x - 4, y + 18, x + 4, y + 18);
    }

    /** Seta vertical: sentidoPositivo == true significa "para baixo". */
    private void desenharSetaVertical(Graphics2D g2, int cx, int cy, boolean sentidoPositivo, String rotulo) {
        g2.setColor(COR_CORRENTE);
        int meiaAltura = 12;
        int direcao = sentidoPositivo ? 1 : -1;
        int yInicio = cy - direcao * meiaAltura;
        int yFim = cy + direcao * meiaAltura;

        g2.drawLine(cx, yInicio, cx, yFim);
        g2.drawLine(cx, yFim, cx - 5, yFim - direcao * 6);
        g2.drawLine(cx, yFim, cx + 5, yFim - direcao * 6);
        g2.drawString(rotulo, cx + 10, cy + 4);
        g2.setColor(COR_FIO);
    }

    /** Seta horizontal: sentidoPositivo == true significa "para a direita". */
    private void desenharSetaHorizontal(Graphics2D g2, int cx, int cy, boolean sentidoPositivo, String rotulo) {
        g2.setColor(COR_CORRENTE);
        int meiaLargura = 14;
        int direcao = sentidoPositivo ? 1 : -1;
        int xInicio = cx - direcao * meiaLargura;
        int xFim = cx + direcao * meiaLargura;

        g2.drawLine(xInicio, cy, xFim, cy);
        g2.drawLine(xFim, cy, xFim - direcao * 6, cy - 5);
        g2.drawLine(xFim, cy, xFim - direcao * 6, cy + 5);
        g2.drawString(rotulo, cx - 22, cy - 8);
        g2.setColor(COR_FIO);
    }
}

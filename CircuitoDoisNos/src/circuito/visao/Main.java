package circuito.visao;

import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da aplicação. Inicializa a interface gráfica na
 * Event Dispatch Thread, como recomendado pelo Swing.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}

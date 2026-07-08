package circuito.controle;

/**
 * Exceção lançada quando um dado de entrada do circuito (força eletromotriz
 * ou resistência) não respeita o intervalo de valores fisicamente aceitos
 * pela calculadora.
 */
public class ValorInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public ValorInvalidoException(String mensagem) {
        super(mensagem);
    }
}

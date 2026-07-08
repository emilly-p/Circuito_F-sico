package controle;

public class ValidadorEntrada {
	public static final double FEM_MINIMA = 0.0;
    public static final double FEM_MAXIMA = 220.0;

    public static final double RESISTOR_MINIMO = 0.0;
    public static final double RESISTOR_MAXIMO = 10000.0;

    private ValidadorEntrada() {
        // classe utilitária, não deve ser instanciada
    }

    public static void validarForcaEletromotriz(double valor, String nomeCampo) throws ValorInvalidoException {
        if (Double.isNaN(valor) || valor <= FEM_MINIMA || valor > FEM_MAXIMA) {
            throw new ValorInvalidoException(
                    nomeCampo + " deve ser um valor positivo (maior que 0) e no máximo "
                            + formatar(FEM_MAXIMA) + " V.\nValor informado: " + formatar(valor) + " V.");
        }
    }

    public static void validarResistor(double valor, String nomeCampo) throws ValorInvalidoException {
        if (Double.isNaN(valor) || valor <= RESISTOR_MINIMO || valor > RESISTOR_MAXIMO) {
            throw new ValorInvalidoException(
                    nomeCampo + " deve ser maior que 0 Ohms e no máximo "
                            + formatar(RESISTOR_MAXIMO) + " Ohms.\nValor informado: " + formatar(valor) + " Ohms.");
        }
    }

    private static String formatar(double valor) {
        return String.format("%.2f", valor);
    }
}

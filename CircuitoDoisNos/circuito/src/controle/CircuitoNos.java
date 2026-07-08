package controle;

public class CircuitoNos {
	 public static final String SENTIDO_CIMA = "para cima";
	    public static final String SENTIDO_BAIXO = "para baixo";
	    public static final String SENTIDO_ESQUERDA = "para a esquerda";
	    public static final String SENTIDO_DIREITA = "para a direita";

	    private double e1;
	    private double e2;
	    private double r1;
	    private double r2;
	    private double r3;

	    private double tensaoNoA;
	    private double correnteR1;
	    private double correnteR2;
	    private double correnteR3;

	    private boolean calculado = false;

	    /**
	     * Valida e armazena os valores de entrada do circuito.
	     *
	     * @throws ValorInvalidoException se algum valor estiver fora do
	     *                                intervalo permitido.
	     */
	    public void definirValores(double e1, double e2, double r1, double r2, double r3) throws ValorInvalidoException {
	        ValidadorEntrada.validarForcaEletromotriz(e1, "A força eletromotriz E1");
	        ValidadorEntrada.validarForcaEletromotriz(e2, "A força eletromotriz E2");
	        ValidadorEntrada.validarResistor(r1, "O resistor R1");
	        ValidadorEntrada.validarResistor(r2, "O resistor R2");
	        ValidadorEntrada.validarResistor(r3, "O resistor R3");

	        this.e1 = e1;
	        this.e2 = e2;
	        this.r1 = r1;
	        this.r2 = r2;
	        this.r3 = r3;
	        this.calculado = false;
	    }

	    /**
	     * Executa o cálculo do circuito a partir dos valores já definidos em
	     * {@link #definirValores(double, double, double, double, double)}.
	     */
	    public void calcular() {
	        double condutanciaTotal = (1.0 / r1) + (1.0 / r2) + (1.0 / r3);
	        double somaCorrentesDasFontes = (e1 / r2) + (e2 / r3);

	        tensaoNoA = somaCorrentesDasFontes / condutanciaTotal;

	        correnteR1 = tensaoNoA / r1;
	        correnteR2 = (e1 - tensaoNoA) / r2;
	        correnteR3 = (e2 - tensaoNoA) / r3;

	        calculado = true;
	    }

	    public boolean isCalculado() {
	        return calculado;
	    }

	    public double getTensaoNoA() {
	        return tensaoNoA;
	    }

	    public double getCorrenteR1() {
	        return correnteR1;
	    }

	    public double getCorrenteR2() {
	        return correnteR2;
	    }

	    public double getCorrenteR3() {
	        return correnteR3;
	    }

	    public String getSentidoR1() {
	        return correnteR1 >= 0 ? SENTIDO_BAIXO : SENTIDO_CIMA;
	    }

	    public String getSentidoR2() {
	        return correnteR2 >= 0 ? SENTIDO_DIREITA : SENTIDO_ESQUERDA;
	    }

	    public String getSentidoR3() {
	        return correnteR3 >= 0 ? SENTIDO_ESQUERDA : SENTIDO_DIREITA;
	    }

	    public double getE1() {
	        return e1;
	    }

	    public double getE2() {
	        return e2;
	    }

	    public double getR1() {
	        return r1;
	    }

	    public double getR2() {
	        return r2;
	    }

	    public double getR3() {
	        return r3;
	    }
}

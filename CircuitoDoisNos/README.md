# Calculadora de Circuito de Dois Nós (Problema 36)

Aplicação Java Swing que resolve o circuito da **Fig. 27.30** (Halliday –
2º Bimestre, Exercício 12/Problema 36): duas fontes ideais ℰ1 e ℰ2, cada uma
em série com um resistor (R2 e R3), ligando um barramento de terra (V = 0) a
um nó comum **A**. Um terceiro resistor, R1, liga o nó A diretamente à terra.

## Como importar no Eclipse

1. Abra o Eclipse.
2. `File > Import... > General > Existing Projects into Workspace`.
3. Em "Select root directory", aponte para a pasta `CircuitoDoisNos` (esta pasta).
4. Marque o projeto e clique em `Finish`.
5. Rode a classe `circuito.visao.Main` (botão direito > `Run As > Java Application`).

Requer JRE/JDK 8 ou superior.

## Estrutura dos pacotes

- **`circuito.controle`** – regras de negócio, sem nenhuma dependência de Swing:
  - `CircuitoDoisNos` — modelo do circuito; calcula a tensão no nó A e as
    correntes i1 (R1), i2 (R2) e i3 (R3) pelo método dos nós de Kirchhoff.
  - `ValidadorEntrada` — valida os limites de ℰ1, ℰ2, R1, R2 e R3.
  - `ValorInvalidoException` — exceção lançada quando um valor está fora do
    intervalo permitido.

- **`circuito.visao`** – interface gráfica (Swing):
  - `Main` — ponto de entrada da aplicação.
  - `JanelaPrincipal` — janela principal, com os blocos "Variáveis de Entrada",
    "Ações" e "Resultados do Cálculo", no mesmo espírito da imagem de
    referência fornecida.
  - `CampoEntrada` — componente reutilizável de entrada (slider + campo de
    texto sincronizados) usado para ℰ1, ℰ2, R1, R2 e R3.
  - `PainelDiagrama` — desenha o esquema elétrico correto do problema (nó A,
    R2/R3 horizontais, R1 vertical, ℰ1/ℰ2 e o terra) e, após o cálculo, as
    setas indicando o **sentido físico real** da corrente em cada resistor.

## Modelo físico (equações usadas)

Com o terra como referência (V = 0) e V_A a tensão no nó A:

```
i1 = V_A / R1                     (sentido positivo: de A para a terra, "para baixo")
i2 = (ℰ1 - V_A) / R2               (sentido positivo: de ℰ1 para A, "para a direita")
i3 = (ℰ2 - V_A) / R3               (sentido positivo: de ℰ2 para A, "para a esquerda")

Lei dos Nós em A:  i2 + i3 = i1

=>  V_A = (ℰ1/R2 + ℰ2/R3) / (1/R1 + 1/R2 + 1/R3)
```

Se o valor calculado de uma corrente for **negativo**, o sentido físico real é
o oposto do sentido positivo definido acima — é exatamente esse sinal que a
interface usa para decidir se a seta aponta para cima/baixo ou
esquerda/direita.

## Validação de entrada

- ℰ1 e ℰ2: devem ser **positivas**, maiores que 0 e no máximo **220 V**.
- R1, R2 e R3: devem ser **maiores que 0** e no máximo **10000 Ω**.

Os sliders permitem, de propósito, arrastar ou digitar valores fora desse
intervalo (até 300 V e 12000 Ω) — a validação real ocorre ao clicar em
**Calcular**, momento em que aparece uma caixa de diálogo avisando que o valor
não é válido e pedindo que outro valor seja informado.

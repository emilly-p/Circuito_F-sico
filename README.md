# Circuito_Físico

# Calculadora de Circuito

Aplicação em Java Swing que resolve, de forma interativa, o circuito
proposto no 12 da lista de exercícios, para o trabalho de física.

---

## 📷 Imagens

### Circuito do exercício


## 📋 Enunciado do exercício

> **36.** Na Fig. 27.30, ℰ₁ = 6,00 V, ℰ₂ = 12,0 V, R₁ = 100 Ω, R₂ = 200 Ω e
> R₃ = 300 Ω. Um ponto do circuito está ligado à terra (V = 0). Determine
> (a) o valor absoluto e (b) o sentido (para cima ou para baixo) da corrente
> na resistência 1, (c) o valor absoluto e (d) o sentido (para a esquerda ou
> para a direita) da corrente na resistência 2, (e) o valor absoluto e (f) o
> sentido (para a esquerda ou para a direita) da corrente na resistência 3.
> (g) Determine o potencial elétrico no ponto A.

O circuito é formado por **duas fontes ideais** (ℰ₁ e ℰ₂), cada uma em série
com um resistor (R₂ e R₃, respectivamente), ligando um barramento comum de
terra (V = 0) a um nó **A**. Um terceiro resistor, **R₁**, liga o nó A
diretamente à terra. Ou seja: três ramos em paralelo entre o nó A e a terra.

---

## 🧮 Resolução

O problema é resolvido pelo **método das tensões de nó** (análise nodal),
usando a terra como referência (V = 0) e V_A como a tensão desconhecida no
nó A.

### Correntes de cada ramo

Definindo como sentido positivo:

- **i₁** — de A para a terra, passando por R₁ (**para baixo**);
- **i₂** — de ℰ₁ para o nó A, passando por R₂ (**para a direita**);
- **i₃** — de ℰ₂ para o nó A, passando por R₃ (**para a esquerda**);

as três correntes podem ser escritas em função de V_A:

```
i1 = V_A / R1
i2 = (ℰ1 - V_A) / R2
i3 = (ℰ2 - V_A) / R3
```

### Lei dos Nós de Kirchhoff em A

A soma das correntes que entram no nó A é igual à que sai:

```
i2 + i3 = i1
```

Substituindo as expressões acima e isolando V_A:

```
ℰ1/R2 + ℰ2/R3 = V_A · (1/R1 + 1/R2 + 1/R3)

        ℰ1/R2 + ℰ2/R3
V_A = ───────────────────────
       1/R1 + 1/R2 + 1/R3
```

Com V_A calculado, basta substituir de volta em i₁, i₂ e i₃ para obter o
módulo e, pelo **sinal do resultado**, o sentido real de cada corrente: se o
valor obtido for positivo, a corrente segue o sentido definido acima como
positivo; se for negativo, o sentido físico real é o oposto.

### Exemplo com os valores do enunciado

Para ℰ₁ = 6,00 V, ℰ₂ = 12,0 V, R₁ = 100 Ω, R₂ = 200 Ω e R₃ = 300 Ω:

| Grandeza | Valor          | Sentido            |
|----------|----------------|---------------------|
| V_A      | ≈ 3,82 V       | —                   |
| i₁       | ≈ 38,2 mA      | para baixo          |
| i₂       | ≈ 10,9 mA      | para a direita      |
| i₃       | ≈ 27,3 mA      | para a esquerda     |

(Verificação: i₂ + i₃ = 10,9 + 27,3 ≈ 38,2 mA = i₁ ✔️)

---

## 💻 Sobre a aplicação

A aplicação permite ajustar ℰ₁, ℰ₂, R₁, R₂ e R₃ por meio de sliders/campos de
texto, calcular o circuito com um clique e visualizar:

- a tensão no nó A;
- o módulo e o sentido físico de i₁, i₂ e i₃;
- um diagrama do circuito com setas indicando o sentido real da corrente em
  cada resistor.

A interface valida as entradas (ℰ entre 0 e 220 V; R entre 0 e 10000 Ω) e
avisa quando um valor informado está fora do intervalo permitido.

### Estrutura do projeto

- `circuito.controle` — regras de negócio (cálculo do circuito e validação
  de entrada), sem dependência de interface gráfica.
- `circuito.visao` — interface gráfica em Swing (janela principal, campos de
  entrada e diagrama do circuito).

### Como executar

1. Importe o projeto no Eclipse (`File > Import > Existing Projects into
   Workspace`).
2. Execute a classe `circuito.visao.Main` como aplicação Java.

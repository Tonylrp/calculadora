import javax.swing.*;
import java.util.Stack;

public class CalculadoraLogica {
    private Stack<Double> numeros = new Stack<>();
    private Stack<String> operadores = new Stack<>();

    public void procesarEntrada(String comando, JTextField displayResultado, JTextField displayOperaciones) {
        try {
            if (comando.matches("[0-9.]")) {
                displayResultado.setText(displayResultado.getText() + comando);
            } else if (comando.matches("[+\\-*/]")) {
                if (!displayResultado.getText().isEmpty()) {
                    numeros.push(Double.parseDouble(displayResultado.getText()));
                    if (!operadores.isEmpty() && !tienePrioridad(comando, operadores.peek())) {
                        calcularOperacion(displayResultado, displayOperaciones);
                    }
                    displayOperaciones.setText(displayOperaciones.getText() + " " + displayResultado.getText() + " " + comando + " ");
                    displayResultado.setText("");
                } else {
                    if (!operadores.isEmpty()) {
                        operadores.pop();
                        displayOperaciones.setText(displayOperaciones.getText().substring(0, displayOperaciones.getText().length() - 3) + " " + comando + " ");
                    }
                }
                operadores.push(comando);
            } else if (comando.equals("=")) {
                if (!displayResultado.getText().isEmpty()) {
                    numeros.push(Double.parseDouble(displayResultado.getText()));
                }
                while (!operadores.isEmpty() && numeros.size() >= 2) {
                    calcularOperacion(displayResultado, displayOperaciones);
                }
                if (!numeros.isEmpty()) {
                    displayResultado.setText(String.format("%.2f", numeros.pop()));
                    displayOperaciones.setText("");
                } else {
                    displayResultado.setText("Error de sintaxis");
                }
            } else if (comando.equals("C")) {
                displayResultado.setText("");
                displayOperaciones.setText("");
                numeros.clear();
                operadores.clear();
            } else if (comando.equals("CE")) {
                String textoActual = displayResultado.getText();
                if (!textoActual.isEmpty()) {
                    displayResultado.setText(textoActual.substring(0, textoActual.length() - 1));
                }
            }
        } catch (NumberFormatException ex) {
            displayResultado.setText("Error de formato");
        } catch (ArithmeticException ex) {
            displayResultado.setText("Error matemático");
        }
    }

    private void calcularOperacion(JTextField displayResultado, JTextField displayOperaciones) {
        if (numeros.size() >= 2 && operadores.size() >= 1) {
            double num2 = numeros.pop();
            double num1 = numeros.pop();
            String operador = operadores.pop();
            double resultado = calcular(num1, num2, operador);
            numeros.push(resultado);
            if (!operadores.isEmpty()) {
                displayOperaciones.setText(displayOperaciones.getText() + " " + String.format("%.2f", resultado));
            } else {
                displayOperaciones.setText("");
            }
            displayResultado.setText(String.format("%.2f", resultado));
        }
    }

    private double calcular(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("División por cero");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operador inválido");
        }
    }

    private boolean tienePrioridad(String op1, String op2) {
        return (op1.equals("*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-"));
    }
}



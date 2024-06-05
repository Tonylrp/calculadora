import javax.swing.*;
import java.util.Stack;

class CalculadoraLogica {
    private Stack<Double> numeros = new Stack<>();
    private Stack<String> operadores = new Stack<>();

    public void actualizarDisplayOperaciones(String tecla, JTextField displayOperaciones) {
        String textoActual = displayOperaciones.getText();

        if (tecla.matches("[0-9]")) {
            if (textoActual.replaceAll("[^0-9]", "").length() < 12) {
                displayOperaciones.setText(textoActual + tecla);
            }
        } else if (tecla.equals(".") && !textoActual.endsWith(".") && !textoActual.contains(".")) {
            displayOperaciones.setText(textoActual + tecla);
        } else if (tecla.matches("[+\\-*/]")) {
            if (!textoActual.isEmpty() && !textoActual.matches("[+\\-*/]$")) {
                displayOperaciones.setText(textoActual + " " + tecla + " ");
            }
        } else if (tecla.equals("C")) {
            displayOperaciones.setText("");
        }
    }

    public void procesarEntrada(String tecla, JTextField displayResultado) {
        try {
            if (tecla.matches("[0-9]")) {
                if (displayResultado.getText().replaceAll("[^0-9]", "").length() < 12) {
                    displayResultado.setText(displayResultado.getText() + tecla);
                }
            } else if (tecla.equals(".") && !displayResultado.getText().contains(".")) {
                displayResultado.setText(displayResultado.getText() + tecla);
            } else if (tecla.matches("[+\\-*/]")) {
                if (!displayResultado.getText().isEmpty()) {
                    numeros.push(Double.parseDouble(displayResultado.getText()));
                    if (!operadores.isEmpty() && !tienePrioridad(tecla, operadores.peek())) {
                        calcularOperacion(displayResultado);
                    }
                    displayResultado.setText("");
                }
                operadores.push(tecla);
            } else if (tecla.equals("=")) {
                if (!displayResultado.getText().isEmpty()) {
                    numeros.push(Double.parseDouble(displayResultado.getText()));
                }
                while (!operadores.isEmpty() && numeros.size() >= 2) {
                    calcularOperacion(displayResultado);
                }
                if (!numeros.isEmpty()) {
                    displayResultado.setText(String.format("%.2f", numeros.pop()));
                } else {
                    displayResultado.setText("Error: Entrada incompleta");
                }
            } else if (tecla.equals("C")) {
                displayResultado.setText("");
                numeros.clear();
                operadores.clear();
            } else if (tecla.equals("CE")) {
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

    private void calcularOperacion(JTextField displayResultado) {
        if (numeros.size() >= 2 && operadores.size() >= 1) {
            double num2 = numeros.pop();
            double num1 = numeros.pop();
            String operador = operadores.pop();
            double resultado = calcular(num1, num2, operador);
            numeros.push(resultado);
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



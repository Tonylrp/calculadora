import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class Calculadora extends JFrame {
    private JPanel panelPantallas, panelBotones;
    private JTextField displayOperaciones, displayResultado;
    private CalculadoraLogica logica;

    public Calculadora() {
        setSize(500, 600);
        setMinimumSize(new Dimension(430, 500));
        setTitle("Calculadora");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        logica = new CalculadoraLogica();
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.DARK_GRAY);

        // Panel for the displays
        panelPantallas = new JPanel(new BorderLayout());
        panelPantallas.setBackground(Color.DARK_GRAY);
        panelPantallas.setPreferredSize(new Dimension(300, 130));

        displayOperaciones = new JTextField();
        displayOperaciones.setHorizontalAlignment(JTextField.RIGHT);
        displayOperaciones.setFont(new Font("Arial", Font.PLAIN, 18));
        displayOperaciones.setEditable(false);
        displayOperaciones.setBackground(Color.WHITE);
        panelPantallas.add(displayOperaciones, BorderLayout.NORTH);

        displayResultado = new JTextField();
        displayResultado.setBorder(new EmptyBorder(20, 10, 10, 10));
        displayResultado.setHorizontalAlignment(JTextField.RIGHT);
        displayResultado.setFont(new Font("Arial", Font.PLAIN, 52));
        displayResultado.setBackground(Color.WHITE);
        panelPantallas.add(displayResultado, BorderLayout.CENTER);

        this.add(panelPantallas, BorderLayout.NORTH);

        // Panel for the buttons
        panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        String[] etiquetasBotones = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "", ".", "+",
                "C", "CE", "=", ""
        };

        int i = 0;
        for (String etiqueta : etiquetasBotones) {
            JButton boton = new JButton(etiqueta);
            boton.setForeground(Color.BLACK);
            boton.setFont(new Font("Arial", Font.PLAIN, 24));

            if ("+-*/CCE".contains(etiqueta)) {
                boton.setBackground(Color.LIGHT_GRAY);
            } else if (etiqueta.equals("=")) {
                boton.setBackground(Color.BLUE);
                boton.setForeground(Color.WHITE);
            } else {
                boton.setBackground(Color.GRAY);
            }

            // Hover and click effect
            boton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    boton.setBackground(Color.DARK_GRAY);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if ("+-*/CCE".contains(etiqueta)) {
                        boton.setBackground(Color.LIGHT_GRAY);
                    } else if (etiqueta.equals("=")) {
                        boton.setBackground(Color.BLUE);
                    } else {
                        boton.setBackground(Color.GRAY);
                    }
                }
            });

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tecla = e.getActionCommand();
                    logica.procesarEntrada(tecla, displayResultado);
                    logica.actualizarDisplayOperaciones(tecla, displayOperaciones);
                    boton.setBackground(Color.DARK_GRAY);
                }
            });

            gbc.gridx = i % 4;
            gbc.gridy = i / 4;

            if (etiqueta.equals("0")) {
                gbc.gridwidth = 2;
                gbc.gridx = 0;
            } else if (etiqueta.equals("=")) {
                gbc.gridwidth = 2;
                gbc.gridx = 2;
            } else {
                gbc.gridwidth = 1;
            }

            panelBotones.add(boton, gbc);
            i++;
        }

        this.add(panelBotones, BorderLayout.CENTER);
    }
}






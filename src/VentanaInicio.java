import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Pantalla inicial: pide los nombres de los dos duelistas y lanza el duelo.
 */
public class VentanaInicio extends JFrame {

    private JTextField campoNombre1;
    private JTextField campoNombre2;

    public VentanaInicio() {
        setTitle("Yu-Gi-Oh! — Duelo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ── Panel principal ────────────────────────────────────────────────────
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(15, 15, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("⚔  DUELO DE YU-GI-OH!  ⚔", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 26));
        titulo.setForeground(new Color(255, 215, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        JLabel subtitulo = new JLabel("\"Confía en el corazón de las cartas\"", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Serif", Font.ITALIC, 13));
        subtitulo.setForeground(new Color(180, 180, 220));
        gbc.gridy = 1;
        panel.add(subtitulo, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 215, 0));
        gbc.gridy = 2; gbc.insets = new Insets(4, 0, 16, 0);
        panel.add(sep, gbc);
        gbc.insets = new Insets(8, 8, 8, 8);

        // Jugador 1
        gbc.gridwidth = 1; gbc.gridy = 3; gbc.gridx = 0;
        JLabel lbl1 = new JLabel("Jugador 1:");
        lbl1.setForeground(Color.WHITE);
        lbl1.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(lbl1, gbc);

        campoNombre1 = new JTextField("Yugi", 16);
        estilizarCampo(campoNombre1);
        gbc.gridx = 1;
        panel.add(campoNombre1, gbc);

        // Jugador 2
        gbc.gridy = 4; gbc.gridx = 0;
        JLabel lbl2 = new JLabel("Jugador 2:");
        lbl2.setForeground(Color.WHITE);
        lbl2.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(lbl2, gbc);

        campoNombre2 = new JTextField("Kaiba", 16);
        estilizarCampo(campoNombre2);
        gbc.gridx = 1;
        panel.add(campoNombre2, gbc);

        // Botón Iniciar
        JButton btnIniciar = new JButton("¡INICIAR DUELO!");
        btnIniciar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnIniciar.setBackground(new Color(180, 0, 0));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        btnIniciar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        panel.add(btnIniciar, gbc);

        btnIniciar.addActionListener(e -> iniciarDuelo());

        // Permitir Enter en los campos
        ActionListener enterAction = e -> iniciarDuelo();
        campoNombre1.addActionListener(enterAction);
        campoNombre2.addActionListener(enterAction);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBackground(new Color(30, 30, 60));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    private void iniciarDuelo() {
        String n1 = campoNombre1.getText().trim();
        String n2 = campoNombre2.getText().trim();
        if (n1.isEmpty()) n1 = "Jugador 1";
        if (n2.isEmpty()) n2 = "Jugador 2";

        Jugador j1 = new Jugador(n1);
        Jugador j2 = new Jugador(n2);
        CampoBatalla campo = new CampoBatalla(j1, j2);
        campo.iniciarDuelo();

        VentanaDuelo ventanaDuelo = new VentanaDuelo(campo);
        ventanaDuelo.setVisible(true);
        this.dispose();
    }
}

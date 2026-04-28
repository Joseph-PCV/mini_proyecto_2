import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Ventana principal del duelo.
 * Muestra campo, mano, LP de ambos jugadores y botones de acción.
 * Toda interacción ocurre mediante eventos Swing (sin Scanner).
 */
public class VentanaDuelo extends JFrame {

    // ── Modelo ────────────────────────────────────────────────────────────────
    private final CampoBatalla campo;

    // ── Paneles de información ────────────────────────────────────────────────
    private JLabel lblTurno;
    private JTextArea areaLog;

    // Panel jugador 1 (abajo)
    private JLabel lblNombreJ1, lblLpJ1, lblManoJ1, lblMazoJ1;
    private JPanel panelCampoJ1;

    // Panel jugador 2 (arriba)
    private JLabel lblNombreJ2, lblLpJ2, lblManoJ2, lblMazoJ2;
    private JPanel panelCampoJ2;

    // Panel de trampas
    private JLabel lblTrampasJ1, lblTrampasJ2;

    // Botones de acción
    private JButton btnJugarCarta;
    private JButton btnAtacar;
    private JButton btnActivarTrampa;
    private JButton btnCambiarPosicion;
    private JButton btnTerminarTurno;

    // ── Colores temáticos ─────────────────────────────────────────────────────
    private static final Color BG_DARK      = new Color(10, 10, 30);
    private static final Color BG_FIELD     = new Color(0, 60, 30);
    private static final Color COLOR_GOLD   = new Color(255, 215, 0);
    private static final Color COLOR_RED    = new Color(200, 20, 20);
    private static final Color COLOR_BLUE   = new Color(20, 100, 200);
    private static final Color COLOR_GREEN  = new Color(30, 160, 30);
    private static final Color COLOR_PURPLE = new Color(120, 30, 160);
    private static final Color COLOR_GRAY   = new Color(80, 80, 100);
    private static final Color FG_WHITE     = Color.WHITE;

    public VentanaDuelo(CampoBatalla campo) {
        this.campo = campo;
        construirUI();
        iniciarPrimerTurno();
    }

    // ── Construcción de la UI ─────────────────────────────────────────────────

    private void construirUI() {
        setTitle("Yu-Gi-Oh! — Duelo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(6, 6));
        getContentPane().setBackground(BG_DARK);

        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(construirPanelCampo(),   BorderLayout.CENTER);
        add(construirPanelAcciones(),BorderLayout.EAST);
        add(construirPanelLog(),     BorderLayout.SOUTH);

        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);
    }

    // ── Panel superior: turno + info jugadores ────────────────────────────────

    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        lblTurno = new JLabel("TURNO 0", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Serif", Font.BOLD, 18));
        lblTurno.setForeground(COLOR_GOLD);
        panel.add(lblTurno, BorderLayout.CENTER);
        return panel;
    }

    // ── Panel central: campo de batalla (J2 arriba, J1 abajo) ────────────────

    private JPanel construirPanelCampo() {
        JPanel panelTotal = new JPanel(new GridLayout(2, 1, 4, 4));
        panelTotal.setBackground(BG_DARK);
        panelTotal.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));

        // ── Jugador 2 (arriba) ────────────────────────────────────────────────
        JPanel zonaJ2 = new JPanel(new BorderLayout(4, 4));
        zonaJ2.setBackground(new Color(20, 20, 60));
        zonaJ2.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BLUE), " Oponente ",
            TitledBorder.LEFT, TitledBorder.TOP, null, COLOR_BLUE));

        JPanel infoJ2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        infoJ2.setBackground(new Color(20, 20, 60));
        lblNombreJ2 = infoLabel("", Font.BOLD, 14, COLOR_BLUE);
        lblLpJ2     = infoLabel("LP: 8000", Font.BOLD, 13, new Color(100, 200, 100));
        lblManoJ2   = infoLabel("Mano: 5", Font.PLAIN, 12, FG_WHITE);
        lblMazoJ2   = infoLabel("Mazo: 20", Font.PLAIN, 12, FG_WHITE);
        lblTrampasJ2= infoLabel("Trampas: 0", Font.PLAIN, 12, new Color(180, 100, 220));
        infoJ2.add(lblNombreJ2); infoJ2.add(lblLpJ2);
        infoJ2.add(lblManoJ2); infoJ2.add(lblMazoJ2); infoJ2.add(lblTrampasJ2);

        panelCampoJ2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelCampoJ2.setBackground(BG_FIELD);
        panelCampoJ2.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BG_FIELD), "Campo",
            TitledBorder.LEFT, TitledBorder.TOP, null, Color.LIGHT_GRAY));

        zonaJ2.add(infoJ2, BorderLayout.NORTH);
        zonaJ2.add(new JScrollPane(panelCampoJ2), BorderLayout.CENTER);
        panelTotal.add(zonaJ2);

        // ── Jugador 1 (abajo) ─────────────────────────────────────────────────
        JPanel zonaJ1 = new JPanel(new BorderLayout(4, 4));
        zonaJ1.setBackground(new Color(40, 10, 10));
        zonaJ1.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_RED), " Tu zona ",
            TitledBorder.LEFT, TitledBorder.TOP, null, COLOR_RED));

        JPanel infoJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        infoJ1.setBackground(new Color(40, 10, 10));
        lblNombreJ1 = infoLabel("", Font.BOLD, 14, COLOR_RED);
        lblLpJ1     = infoLabel("LP: 8000", Font.BOLD, 13, new Color(100, 200, 100));
        lblManoJ1   = infoLabel("Mano: 5", Font.PLAIN, 12, FG_WHITE);
        lblMazoJ1   = infoLabel("Mazo: 20", Font.PLAIN, 12, FG_WHITE);
        lblTrampasJ1= infoLabel("Trampas: 0", Font.PLAIN, 12, new Color(180, 100, 220));
        infoJ1.add(lblNombreJ1); infoJ1.add(lblLpJ1);
        infoJ1.add(lblManoJ1); infoJ1.add(lblMazoJ1); infoJ1.add(lblTrampasJ1);

        panelCampoJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelCampoJ1.setBackground(BG_FIELD);
        panelCampoJ1.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BG_FIELD), "Campo",
            TitledBorder.LEFT, TitledBorder.TOP, null, Color.LIGHT_GRAY));

        zonaJ1.add(infoJ1, BorderLayout.NORTH);
        zonaJ1.add(new JScrollPane(panelCampoJ1), BorderLayout.CENTER);
        panelTotal.add(zonaJ1);

        return panelTotal;
    }

    // ── Panel de acciones (derecha) ───────────────────────────────────────────

    private JPanel construirPanelAcciones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(20, 20, 50));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_GOLD),
            BorderFactory.createEmptyBorder(12, 10, 12, 10)));
        panel.setPreferredSize(new Dimension(170, 0));

        JLabel titulo = new JLabel("ACCIONES", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 15));
        titulo.setForeground(COLOR_GOLD);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(14));

        btnJugarCarta     = crearBotonAccion("🃏 Jugar Carta",    COLOR_GREEN);
        btnAtacar         = crearBotonAccion("⚔ Atacar",          COLOR_RED);
        btnActivarTrampa  = crearBotonAccion("🕳 Activar Trampa", COLOR_PURPLE);
        btnCambiarPosicion= crearBotonAccion("🔄 Cambiar Posición",COLOR_GRAY);
        btnTerminarTurno  = crearBotonAccion("✅ Terminar Turno",  new Color(80, 80, 20));

        panel.add(btnJugarCarta);      panel.add(Box.createVerticalStrut(8));
        panel.add(btnAtacar);          panel.add(Box.createVerticalStrut(8));
        panel.add(btnActivarTrampa);   panel.add(Box.createVerticalStrut(8));
        panel.add(btnCambiarPosicion); panel.add(Box.createVerticalStrut(8));
        panel.add(Box.createVerticalGlue());
        panel.add(btnTerminarTurno);

        btnJugarCarta.addActionListener(e -> accionJugarCarta());
        btnAtacar.addActionListener(e -> accionAtacar());
        btnActivarTrampa.addActionListener(e -> accionActivarTrampa());
        btnCambiarPosicion.addActionListener(e -> accionCambiarPosicion());
        btnTerminarTurno.addActionListener(e -> accionTerminarTurno());

        return panel;
    }

    // ── Panel de log (abajo) ──────────────────────────────────────────────────

    private JPanel construirPanelLog() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_GOLD), " Registro de batalla ",
            TitledBorder.LEFT, TitledBorder.TOP, null, COLOR_GOLD));

        areaLog = new JTextArea(6, 80);
        areaLog.setEditable(false);
        areaLog.setBackground(new Color(5, 5, 20));
        areaLog.setForeground(new Color(180, 220, 180));
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setPreferredSize(new Dimension(0, 130));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ── Helpers de construcción ───────────────────────────────────────────────

    private JLabel infoLabel(String texto, int estilo, int size, Color color) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", estilo, size));
        lbl.setForeground(color);
        return lbl;
    }

    private JButton crearBotonAccion(String texto, Color bg) {
        JButton btn = new JButton("<html><center>" + texto + "</center></html>");
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(150, 42));
        btn.setPreferredSize(new Dimension(150, 42));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    // ── Flujo de juego ────────────────────────────────────────────────────────

    private void iniciarPrimerTurno() {
        String log = campo.prepararTurno();
        agregarLog(log);
        actualizarUI();
    }

    private void actualizarUI() {
        Jugador activo   = campo.getJugadorActivo();
        Jugador oponente = campo.getOponente();
        Jugador j1       = campo.getJugador1();
        Jugador j2       = campo.getJugador2();

        // El jugador activo siempre se muestra abajo (J1 panel) para mayor claridad
        // Identificamos quién es activo y quién es rival:
        Jugador abajo  = activo;
        Jugador arriba = oponente;

        lblTurno.setText("Turno " + campo.getTurnoActual() + "  —  Turno de: " + activo.getNombre().toUpperCase());

        // Info abajo (activo)
        lblNombreJ1.setText("⚔ " + abajo.getNombre());
        lblLpJ1.setText("LP: " + abajo.getLp());
        lblManoJ1.setText("Mano: " + abajo.getMano().size());
        lblMazoJ1.setText("Mazo: " + abajo.getMazo().tamano());
        lblTrampasJ1.setText("Trampas: " + abajo.getZonaTrampas().size());

        // Info arriba (rival)
        lblNombreJ2.setText("👤 " + arriba.getNombre());
        lblLpJ2.setText("LP: " + arriba.getLp());
        lblManoJ2.setText("Mano: " + arriba.getMano().size());
        lblMazoJ2.setText("Mazo: " + arriba.getMazo().tamano());
        lblTrampasJ2.setText("Trampas: " + arriba.getZonaTrampas().size());

        // Color LP
        colorearLP(lblLpJ1, abajo.getLp());
        colorearLP(lblLpJ2, arriba.getLp());

        // Campos
        refrescarPanelCampo(panelCampoJ1, abajo.getCampo());
        refrescarPanelCampo(panelCampoJ2, arriba.getCampo());

        // Habilitar / deshabilitar botones
        boolean miTurno = true; // La GUI siempre muestra el jugador activo abajo
        boolean puedoJugar = !activo.isYaJugoCartaEsteTurno() && !activo.getMano().isEmpty();
        boolean hayAtacantes = activo.getCampo().stream().anyMatch(CartaMonstruo::puedeAtacar);
        Contexto ctx = new Contexto(activo, oponente, campo);
        boolean hayTrampas = activo.hayTrampaActivable(ctx);

        btnJugarCarta.setEnabled(puedoJugar);
        btnAtacar.setEnabled(hayAtacantes && !activo.isYaAtacoEsteTurno());
        btnActivarTrampa.setEnabled(hayTrampas);
        btnCambiarPosicion.setEnabled(!activo.getCampo().isEmpty());
        btnTerminarTurno.setEnabled(true);
    }

    private void colorearLP(JLabel lbl, int lp) {
        if (lp > 4000)      lbl.setForeground(new Color(80, 220, 80));
        else if (lp > 1500) lbl.setForeground(new Color(240, 200, 40));
        else                lbl.setForeground(new Color(240, 60, 60));
    }

    private void refrescarPanelCampo(JPanel panel, List<CartaMonstruo> monstruos) {
        panel.removeAll();
        if (monstruos.isEmpty()) {
            JLabel vacio = new JLabel("(campo vacío)");
            vacio.setForeground(Color.GRAY);
            panel.add(vacio);
        } else {
            for (int i = 0; i < monstruos.size(); i++) {
                panel.add(crearTarjetaMonstruo(monstruos.get(i), i));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private JPanel crearTarjetaMonstruo(CartaMonstruo m, int idx) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(105, 115));
        card.setMaximumSize(new Dimension(105, 115));

        Color borde = m.estaEnModoDefensa() ? new Color(80, 140, 220) : new Color(220, 80, 80);
        card.setBorder(BorderFactory.createLineBorder(borde, 2));
        card.setBackground(new Color(20, 40, 20));

        JLabel nombre = new JLabel("<html><center>" + m.getNombre() + "</center></html>", SwingConstants.CENTER);
        nombre.setFont(new Font("SansSerif", Font.BOLD, 10));
        nombre.setForeground(COLOR_GOLD);
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel stats = new JLabel("ATK:" + m.getAtk() + " DEF:" + m.getDef(), SwingConstants.CENTER);
        stats.setFont(new Font("Monospaced", Font.PLAIN, 10));
        stats.setForeground(Color.WHITE);
        stats.setAlignmentX(Component.CENTER_ALIGNMENT);

        String modoStr = m.estaEnModoDefensa() ? "DEF" : "ATK";
        Color modoColor = m.estaEnModoDefensa() ? new Color(80, 140, 220) : new Color(220, 80, 80);
        JLabel modo = new JLabel("[ " + modoStr + " ]", SwingConstants.CENTER);
        modo.setFont(new Font("SansSerif", Font.BOLD, 10));
        modo.setForeground(modoColor);
        modo.setAlignmentX(Component.CENTER_ALIGNMENT);

        String ataqueStr = m.puedeAtacar() ? "✔ puede atacar" : "✘ ya atacó";
        JLabel ataque = new JLabel(ataqueStr, SwingConstants.CENTER);
        ataque.setFont(new Font("SansSerif", Font.PLAIN, 9));
        ataque.setForeground(m.puedeAtacar() ? new Color(100, 200, 100) : Color.GRAY);
        ataque.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nivel = new JLabel("Lv" + m.getnivelCarta(), SwingConstants.CENTER);
        nivel.setFont(new Font("SansSerif", Font.PLAIN, 9));
        nivel.setForeground(new Color(200, 200, 100));
        nivel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(4));
        card.add(nombre);
        card.add(Box.createVerticalStrut(3));
        card.add(nivel);
        card.add(stats);
        card.add(modo);
        card.add(ataque);
        card.add(Box.createVerticalStrut(4));

        return card;
    }

    private void agregarLog(String texto) {
        if (texto == null || texto.isBlank()) return;
        areaLog.append(texto);
        areaLog.append("\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    // ── Acciones de botones ───────────────────────────────────────────────────

    private void accionJugarCarta() {
        Jugador activo   = campo.getJugadorActivo();
        Jugador oponente = campo.getOponente();
        Contexto ctx     = new Contexto(activo, oponente, campo);
        List<Carta> mano = activo.getMano();

        if (mano.isEmpty()) { agregarLog("No tienes cartas en la mano."); return; }
        if (activo.isYaJugoCartaEsteTurno()) { agregarLog("Ya jugaste una carta este turno."); return; }

        // Construir opciones para el JOptionPane
        String[] opciones = new String[mano.size() + 1];
        for (int i = 0; i < mano.size(); i++) opciones[i] = (i + 1) + ". " + mano.get(i).toString();
        opciones[mano.size()] = "Cancelar";

        String elegida = (String) JOptionPane.showInputDialog(
            this, "Elige una carta para jugar:",
            "🃏 Tu mano — " + activo.getNombre(),
            JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (elegida == null || elegida.equals("Cancelar")) return;

        int idx = java.util.Arrays.asList(opciones).indexOf(elegida);
        if (idx < 0 || idx >= mano.size()) return;

        Carta carta = mano.get(idx);

        // Si es monstruo nivel > 4, pedir sacrificio
        int indiceSacrificio = -1;
        if (carta.getTipo().equals("MONSTRUO")) {
            CartaMonstruo mon = (CartaMonstruo) carta;
            if (mon.getnivelCarta() > 4) {
                if (activo.getCampo().isEmpty()) {
                    agregarLog("Necesitas sacrificar un monstruo para invocar " + mon.getNombre()
                             + " (nivel " + mon.getnivelCarta() + "), pero no tienes monstruos en campo.");
                    return;
                }
                String[] opSacrificio = new String[activo.getCampo().size()];
                for (int i = 0; i < activo.getCampo().size(); i++) {
                    opSacrificio[i] = (i + 1) + ". " + activo.getCampo().get(i).getNombre();
                }
                String elegidoSac = (String) JOptionPane.showInputDialog(
                    this,
                    mon.getNombre() + " (Lv" + mon.getnivelCarta() + ") requiere un sacrificio.\nElige el monstruo a sacrificar:",
                    "⚰ Sacrificio requerido",
                    JOptionPane.WARNING_MESSAGE, null, opSacrificio, opSacrificio[0]);
                if (elegidoSac == null) return;
                indiceSacrificio = java.util.Arrays.asList(opSacrificio).indexOf(elegidoSac);
            }
        }

        boolean jugado = activo.jugarCarta(idx, ctx, indiceSacrificio);

        if (jugado) {
            agregarLog(activo.getNombre() + " jugó: " + carta.getNombre());
            verificarGanador();
        } else {
            agregarLog("No se pudo jugar la carta.");
        }
        actualizarUI();
    }

    private void accionAtacar() {
        Jugador activo   = campo.getJugadorActivo();
        Jugador oponente = campo.getOponente();

        // Filtrar monstruos que pueden atacar
        List<CartaMonstruo> disponibles = new java.util.ArrayList<>();
        for (CartaMonstruo m : activo.getCampo()) if (m.puedeAtacar()) disponibles.add(m);

        if (disponibles.isEmpty()) { agregarLog("Ningún monstruo puede atacar."); return; }
        if (activo.isYaAtacoEsteTurno()) { agregarLog("Ya atacaste este turno."); return; }

        // ── 1. Elegir atacante ────────────────────────────────────────────────
        String[] opAtacantes = new String[disponibles.size() + 1];
        for (int i = 0; i < disponibles.size(); i++) opAtacantes[i] = (i + 1) + ". " + disponibles.get(i);
        opAtacantes[disponibles.size()] = "Cancelar";

        String elegidoAtac = (String) JOptionPane.showInputDialog(
            this, "Elige el monstruo ATACANTE:",
            "⚔ Ataque — " + activo.getNombre(),
            JOptionPane.PLAIN_MESSAGE, null, opAtacantes, opAtacantes[0]);
        if (elegidoAtac == null || elegidoAtac.equals("Cancelar")) return;

        int idxAtac = java.util.Arrays.asList(opAtacantes).indexOf(elegidoAtac);
        if (idxAtac < 0 || idxAtac >= disponibles.size()) return;
        CartaMonstruo atacante = disponibles.get(idxAtac);

        // ── 2. Elegir defensor (si hay monstruos en campo rival) ──────────────
        CartaMonstruo defensor = null;

        if (!oponente.getCampo().isEmpty()) {
            List<CartaMonstruo> defensores = oponente.getCampo();
            String[] opDefensores = new String[defensores.size() + 1];
            for (int i = 0; i < defensores.size(); i++) opDefensores[i] = (i + 1) + ". " + defensores.get(i);
            opDefensores[defensores.size()] = "Cancelar";

            String elegidoDef = (String) JOptionPane.showInputDialog(
                this, "Elige el monstruo a atacar:",
                "🎯 Selecciona objetivo",
                JOptionPane.PLAIN_MESSAGE, null, opDefensores, opDefensores[0]);
            if (elegidoDef == null || elegidoDef.equals("Cancelar")) return;

            int idxDef = java.util.Arrays.asList(opDefensores).indexOf(elegidoDef);
            if (idxDef < 0 || idxDef >= defensores.size()) return;
            defensor = defensores.get(idxDef);
        }

        // ── 3. FASE DE RESPUESTA: el defensor puede activar trampas ──────────
        //
        // Se construye el contexto CON ROLES INVERTIDOS:
        //   ctx.getJugadorActivo() = oponente (dueño de las trampas)
        //   ctx.getOponente()      = activo   (el atacante)
        //
        // Esto hace que toda la lógica existente en las cartas trampa funcione
        // correctamente sin modificar ninguna otra clase.
        //
        Contexto ctxDefensa = new Contexto(oponente, activo, campo);
        ctxDefensa.setMonstruoAtacante(atacante);

        if (oponente.hayTrampaActivable(ctxDefensa)) {
            boolean activoTrampa = ofrecerRespuestaTrampas(oponente, ctxDefensa);

            if (activoTrampa) {
                verificarGanador();
                if (campo.hayGanador()) { actualizarUI(); return; }

                // ¿El atacante fue destruido por la trampa?
                if (!activo.getCampo().contains(atacante)) {
                    agregarLog("⚡ ¡" + atacante.getNombre() + " fue destruido! El ataque queda cancelado.");
                    activo.setYaAtacoEsteTurno(true);
                    actualizarUI();
                    return;
                }
            }
        }

        // ── 4. Resolver combate ───────────────────────────────────────────────
        String logCombate;

        if (oponente.getCampo().isEmpty()) {
            // Ataque directo (campo rival vacío desde el inicio o vaciado por trampa)
            logCombate = campo.ataqueDirecto(atacante, oponente);

        } else if (defensor != null && !oponente.getCampo().contains(defensor)) {
            // El defensor elegido fue destruido por una trampa → ataque directo
            agregarLog("El defensor fue destruido por la trampa. ¡Ataque directo!");
            logCombate = campo.ataqueDirecto(atacante, oponente);

        } else if (defensor != null) {
            // Combate normal contra el defensor elegido
            logCombate = campo.resolverCombate(atacante, defensor, activo, oponente);

        } else {
            // Fallback: ataque directo
            logCombate = campo.ataqueDirecto(atacante, oponente);
        }

        activo.setYaAtacoEsteTurno(true);
        agregarLog(logCombate);
        verificarGanador();
        actualizarUI();
    }

    /**
     * Muestra al jugador defensor la lista de trampas activables durante un ataque
     * y le permite elegir una para activar.
     *
     * El contexto recibido ya tiene los roles invertidos:
     *   ctx.getJugadorActivo() = defensor (dueño de las trampas)
     *   ctx.getOponente()      = atacante
     *
     * @return true si se activó alguna trampa, false si el jugador pasó.
     */
    private boolean ofrecerRespuestaTrampas(Jugador defensor, Contexto ctx) {
        List<CartaTrampa> trampas = defensor.getZonaTrampas();
        List<Integer>     indices = new java.util.ArrayList<>();
        List<String>      nombres = new java.util.ArrayList<>();

        for (int i = 0; i < trampas.size(); i++) {
            if (trampas.get(i).puedoActivarme(ctx)) {
                indices.add(i);
                nombres.add((indices.size()) + ". " + trampas.get(i).toString());
            }
        }
        nombres.add("⛔ No activar");

        String[] ops = nombres.toArray(new String[0]);

        // Preguntar al defensor — se coloca la opción "No activar" como selección por defecto
        String elegida = (String) JOptionPane.showInputDialog(
            this,
            "⚠  ¡ATAQUE DECLARADO!\n\n" +
            defensor.getNombre() + ", ¿deseas activar una trampa en respuesta?\n" +
            "(Si no activas nada, el combate se resuelve normalmente)",
            "🕳  Respuesta de trampas — " + defensor.getNombre(),
            JOptionPane.WARNING_MESSAGE, null, ops, ops[ops.length - 1]);

        // Canceló la ventana o eligió "No activar"
        if (elegida == null || elegida.equals("⛔ No activar")) return false;

        int posLista = nombres.indexOf(elegida);
        if (posLista < 0 || posLista >= indices.size()) return false;

        int idxReal = indices.get(posLista);
        CartaTrampa trampa = trampas.get(idxReal);

        agregarLog("🕳 " + defensor.getNombre() + " activó trampa en respuesta: " + trampa.getNombre());
        boolean ok = defensor.activarTrampa(idxReal, ctx);
        if (!ok) {
            agregarLog("La trampa no pudo activarse.");
            return false;
        }
        return true;
    }

    private void accionActivarTrampa() {
        Jugador activo   = campo.getJugadorActivo();
        Jugador oponente = campo.getOponente();
        Contexto ctx     = new Contexto(activo, oponente, campo);

        List<CartaTrampa> trampas = activo.getZonaTrampas();
        if (trampas.isEmpty()) { agregarLog("No tienes trampas colocadas."); return; }

        // Mostrar solo las activables
        java.util.List<Integer> indices = new java.util.ArrayList<>();
        java.util.List<String> nombres = new java.util.ArrayList<>();
        for (int i = 0; i < trampas.size(); i++) {
            if (trampas.get(i).puedoActivarme(ctx)) {
                indices.add(i);
                nombres.add((i + 1) + ". " + trampas.get(i).toString());
            }
        }
        nombres.add("Cancelar");

        if (indices.isEmpty()) { agregarLog("Ninguna trampa puede activarse ahora."); return; }

        String[] ops = nombres.toArray(new String[0]);
        String elegida = (String) JOptionPane.showInputDialog(
            this, "Elige la trampa a activar:",
            "🕳 Trampas — " + activo.getNombre(),
            JOptionPane.PLAIN_MESSAGE, null, ops, ops[0]);
        if (elegida == null || elegida.equals("Cancelar")) return;

        int posLista = nombres.indexOf(elegida);
        if (posLista < 0 || posLista >= indices.size()) return;
        int idxReal = indices.get(posLista);

        CartaTrampa trampa = trampas.get(idxReal);
        agregarLog(">>> " + activo.getNombre() + " activó trampa: " + trampa.getNombre());
        boolean ok = activo.activarTrampa(idxReal, ctx);
        if (!ok) agregarLog("La trampa no pudo activarse.");

        verificarGanador();
        actualizarUI();
    }

    private void accionCambiarPosicion() {
        Jugador activo = campo.getJugadorActivo();
        if (activo.getCampo().isEmpty()) { agregarLog("No tienes monstruos en campo."); return; }

        String[] ops = new String[activo.getCampo().size() + 1];
        for (int i = 0; i < activo.getCampo().size(); i++) ops[i] = (i + 1) + ". " + activo.getCampo().get(i);
        ops[activo.getCampo().size()] = "Cancelar";

        String elegida = (String) JOptionPane.showInputDialog(
            this, "Elige el monstruo para cambiar posición:",
            "🔄 Cambiar posición",
            JOptionPane.PLAIN_MESSAGE, null, ops, ops[0]);
        if (elegida == null || elegida.equals("Cancelar")) return;

        int idx = java.util.Arrays.asList(ops).indexOf(elegida);
        if (idx < 0 || idx >= activo.getCampo().size()) return;

        CartaMonstruo m = activo.getCampo().get(idx);
        m.cambiarPosicion();
        agregarLog(">>> " + m.getNombre() + " cambió a modo " + (m.estaEnModoDefensa() ? "DEFENSA" : "ATAQUE") + ".");
        actualizarUI();
    }

    private void accionTerminarTurno() {
        Jugador terminando = campo.getJugadorActivo();
        agregarLog("── " + terminando.getNombre() + " termina su turno ──");
        campo.terminarTurno();

        if (campo.hayGanador()) { mostrarGanador(); return; }

        String log = campo.prepararTurno();
        agregarLog(log);

        if (campo.hayGanador()) { mostrarGanador(); return; }

        actualizarUI();
        // Notificar de quién es el turno con un diálogo no bloqueante
        JOptionPane.showMessageDialog(this,
            "Es el turno de:\n" + campo.getJugadorActivo().getNombre().toUpperCase(),
            "Nuevo Turno", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Fin del juego ─────────────────────────────────────────────────────────

    private void verificarGanador() {
        if (campo.hayGanador()) mostrarGanador();
    }

    private void mostrarGanador() {
        // Deshabilitar todos los botones
        btnJugarCarta.setEnabled(false);
        btnAtacar.setEnabled(false);
        btnActivarTrampa.setEnabled(false);
        btnCambiarPosicion.setEnabled(false);
        btnTerminarTurno.setEnabled(false);

        Jugador ganador = campo.getGanador();
        String nombre = (ganador != null) ? ganador.getNombre() : "Nadie";

        actualizarUI();

        // Ventana de victoria
        JDialog dialogo = new JDialog(this, "¡DUELO TERMINADO!", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.getContentPane().setBackground(new Color(10, 10, 30));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(10, 10, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(8, 0, 8, 0);

        JLabel trofeo = new JLabel("🏆", SwingConstants.CENTER);
        trofeo.setFont(new Font("Serif", Font.PLAIN, 60));
        panel.add(trofeo, gbc);

        gbc.gridy = 1;
        JLabel lblGana = new JLabel("¡" + nombre.toUpperCase() + " GANA EL DUELO!", SwingConstants.CENTER);
        lblGana.setFont(new Font("Serif", Font.BOLD, 22));
        lblGana.setForeground(COLOR_GOLD);
        panel.add(lblGana, gbc);

        gbc.gridy = 2;
        JLabel cita = new JLabel("\"Confía en el corazón de las cartas\" — Yugi Muto", SwingConstants.CENTER);
        cita.setFont(new Font("Serif", Font.ITALIC, 13));
        cita.setForeground(new Color(180, 180, 220));
        panel.add(cita, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 8, 0);
        JButton btnNuevo = new JButton("Nueva partida");
        btnNuevo.setBackground(COLOR_GREEN);
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnNuevo.setFocusPainted(false);
        btnNuevo.addActionListener(e -> {
            dialogo.dispose();
            new VentanaInicio().setVisible(true);
            VentanaDuelo.this.dispose();
        });
        panel.add(btnNuevo, gbc);

        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }
}

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private int lp = 8000;
    private boolean yaJugoCartaEsteTurno = false;
    private boolean yaAtacoEsteTurno = false;
    private boolean bloqueadoProximoTurno = false;   // efecto de trampa DestinoInexorable
    private List<Carta> mano;
    private Mazo mazo;
    private List<CartaMonstruo> campo;
    private List<CartaTrampa> zonaTrampas; // trampas colocadas boca abajo

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mazo = new Mazo(false);
        this.lp = 8000;
        this.mano = new ArrayList<>();
        this.campo = new ArrayList<>();
        this.zonaTrampas = new ArrayList<>();
        this.yaJugoCartaEsteTurno = false;
        this.yaAtacoEsteTurno = false;
        this.bloqueadoProximoTurno = false;
    }

    // ── Getters / Setters ──────────────────────────────────────────────────────

    public String getNombre() { return nombre; }

    public int getLp() { return lp; }

    public void setLp(int lp) {
        this.lp = lp;
        if (this.lp < 0) this.lp = 0;
    }

    public List<Carta> getMano() { return mano; }

    public List<CartaMonstruo> getCampo() { return campo; }

    public Mazo getMazo() { return mazo; }

    public void setMazo(Mazo mazo) { this.mazo = mazo; }

    public List<CartaTrampa> getZonaTrampas() { return zonaTrampas; }

    // ── Mecánicas básicas ──────────────────────────────────────────────────────

    public void robarCarta() {
        if (mazo != null) {
            Carta c = mazo.robar();
            if (c != null) mano.add(c);
        }
    }

    public void recibirDanio(int pts) {
        lp -= pts;
        if (lp < 0) lp = 0;
    }

    public void curarDanio(int pts) {
        lp += pts;
    }

    public boolean tieneMonstruosEnCampo() { return !campo.isEmpty(); }

    public boolean tieneCartasEnMazo() { return mazo != null && !mazo.estaVacio(); }

    public boolean puedeJugarCarta() { return !yaJugoCartaEsteTurno; }

    // ── Bloqueo de turno (efecto trampa) ─────────────────────────────────────

    /** Llamado por DestinoInexorable: el próximo turno no puede jugar cartas. */
    public void bloquearJugarCartaProximoTurno() {
        bloqueadoProximoTurno = true;
    }

    public boolean isBloqueadoProximoTurno() { return bloqueadoProximoTurno; }

    // ── Reset de turno ─────────────────────────────────────────────────────────

    public void resetTurno() {
        if (bloqueadoProximoTurno) {
            // Aplica el bloqueo este turno y lo consume
            yaJugoCartaEsteTurno = true;
            bloqueadoProximoTurno = false;
        } else {
            yaJugoCartaEsteTurno = false;
        }
        yaAtacoEsteTurno = false;
        for (CartaMonstruo m : campo) {
            m.setPuedeAtacar(true);
        }
    }

    // ── Jugar carta (lógica pura, sin I/O – usada por la GUI) ─────────────────

    /**
     * Intenta jugar la carta en la posición `indice` de la mano.
     * Para monstruos nivel > 4 se requiere un sacrificio; el índice del
     * monstruo a sacrificar se pasa en `indiceSacrificio` (-1 = no se necesita).
     * Retorna true si la carta fue jugada con éxito.
     */
    public boolean jugarCarta(int indice, Contexto ctx, int indiceSacrificio) {
        if (indice < 0 || indice >= mano.size()) return false;
        if (yaJugoCartaEsteTurno) return false;

        Carta carta = mano.get(indice);

        if (carta.getTipo().equals("MONSTRUO")) {
            CartaMonstruo monstruo = (CartaMonstruo) carta;

            // Sacrificio obligatorio para nivel > 4
            if (monstruo.getnivelCarta() > 4) {
                if (campo.isEmpty()) return false; // no hay qué sacrificar
                if (indiceSacrificio < 0 || indiceSacrificio >= campo.size()) return false;
                CartaMonstruo sacrificado = campo.remove(indiceSacrificio);
                System.out.println(">>> " + nombre + " sacrificó a " + sacrificado.getNombre()
                        + " para invocar " + monstruo.getNombre() + ".");
            }

            campo.add(monstruo);
            mano.remove(indice);
            yaJugoCartaEsteTurno = true;
            monstruo.setPuedeAtacar(!ctx.getCampo().isEsPrimerTurno());
            return true;

        } else if (carta.getTipo().equals("MAGICA")) {
            if (carta instanceof Activable) {
                ((Activable) carta).activar(ctx);
                mano.remove(indice);
                yaJugoCartaEsteTurno = true;
                return true;
            }

        } else if (carta.getTipo().equals("TRAMPA")) {
            // Colocar trampa boca abajo en la zona de trampas
            CartaTrampa trampa = (CartaTrampa) carta;
            zonaTrampas.add(trampa);
            mano.remove(indice);
            yaJugoCartaEsteTurno = true;
            System.out.println(">>> " + nombre + " colocó una trampa boca abajo.");
            return true;
        }

        return false;
    }

    /**
     * Activa una trampa boca abajo desde la zona de trampas.
     * Retorna true si se activó con éxito.
     */
    public boolean activarTrampa(int indiceTrampa, Contexto ctx) {
        if (indiceTrampa < 0 || indiceTrampa >= zonaTrampas.size()) return false;
        CartaTrampa trampa = zonaTrampas.get(indiceTrampa);
        if (!trampa.puedoActivarme(ctx)) return false;
        trampa.activar(ctx);
        zonaTrampas.remove(indiceTrampa);
        return true;
    }

    /** Verifica si alguna trampa puede activarse en el contexto actual. */
    public boolean hayTrampaActivable(Contexto ctx) {
        for (CartaTrampa t : zonaTrampas) {
            if (t.puedoActivarme(ctx)) return true;
        }
        return false;
    }

    public boolean isYaAtacoEsteTurno() { return yaAtacoEsteTurno; }

    public void setYaAtacoEsteTurno(boolean v) { yaAtacoEsteTurno = v; }

    public boolean isYaJugoCartaEsteTurno() { return yaJugoCartaEsteTurno; }
}

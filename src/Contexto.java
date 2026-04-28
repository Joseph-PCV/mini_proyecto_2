public class Contexto {

    private Jugador jugActivo;
    private Jugador oponente;
    private CampoBatalla campo;

    /**
     * Monstruo que está declarando el ataque en este momento.
     * Solo se asigna durante la fase de respuesta de trampas en accionAtacar().
     * Null fuera de esa fase.
     */
    private CartaMonstruo monstruoAtacante;

    public Contexto(Jugador jugActivo, Jugador oponente, CampoBatalla campo) {
        this.jugActivo = jugActivo;
        this.oponente = oponente;
        this.campo = campo;
        this.monstruoAtacante = null;
    }

    public Jugador getJugadorActivo() {
        return jugActivo;
    }

    public Jugador getOponente() {
        return oponente;
    }

    public CampoBatalla getCampo() {
        return campo;
    }

    public int getTurno() {
        return campo.getTurnoActual();
    }

    /** Retorna el monstruo que está atacando. Null si no hay ataque en curso. */
    public CartaMonstruo getMonstruoAtacante() {
        return monstruoAtacante;
    }

    /** Asigna el monstruo atacante. Llamar antes de ofrecer respuesta de trampas. */
    public void setMonstruoAtacante(CartaMonstruo monstruoAtacante) {
        this.monstruoAtacante = monstruoAtacante;
    }
}

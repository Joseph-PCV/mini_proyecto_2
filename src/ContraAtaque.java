/**
 * Trampa: Contra-Ataque
 * Se activa DURANTE el ataque del oponente, antes de resolver el combate.
 * Niega el ataque y destruye al monstruo atacante.
 * Condición: debe haber un monstruo atacante declarado (ctx.getMonstruoAtacante() != null).
 */
public class ContraAtaque extends CartaTrampa {

    public ContraAtaque() {
        super("Contra-Ataque", "Niega un ataque del oponente y destruye al monstruo atacante.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        // Solo se puede activar si hay un ataque declarado en este momento
        return ctx.getMonstruoAtacante() != null;
    }

    @Override
    public void activar(Contexto ctx) {
        CartaMonstruo atacante = ctx.getMonstruoAtacante();
        if (atacante == null) return;

        // En el contexto de defensa: getOponente() es el jugador atacante
        // (los roles están invertidos: Contexto(defensor, atacante, campo))
        Jugador jugadorAtacante = ctx.getOponente();
        ctx.getCampo().eliminarMonstruo(atacante, jugadorAtacante);
    }
}

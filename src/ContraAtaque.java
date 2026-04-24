/**
 * Trampa: Contra-Ataque
 * Se activa cuando el oponente ataca. Niega el ataque y destruye al monstruo atacante.
 * Condición: el oponente tiene al menos un monstruo en campo.
 */
public class ContraAtaque extends CartaTrampa {

    public ContraAtaque() {
        super("Contra-Ataque", "Niega un ataque del oponente y destruye al monstruo atacante.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        // Se puede usar si el oponente tiene monstruos en campo (hay un ataque en curso)
        return !ctx.getOponente().getCampo().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        if (oponente.getCampo().isEmpty()) {
            return;
        }
        // Destruye el primer monstruo del oponente (el atacante)
        CartaMonstruo atacante = oponente.getCampo().get(0);
        ctx.getCampo().eliminarMonstruo(atacante, oponente);
    }
}

/**
 * Trampa: Robo Forzado
 * Fuerza al oponente a descartar la primera carta de su mano.
 * Condición: el oponente tiene al menos una carta en la mano.
 */
public class RoboForzado extends CartaTrampa {

    public RoboForzado() {
        super("Robo Forzado", "El oponente descarta 1 carta de su mano.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return !ctx.getOponente().getMano().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        if (!oponente.getMano().isEmpty()) {
            Carta descartada = oponente.getMano().remove(0);
            System.out.println(">>> " + oponente.getNombre() + " descartó: " + descartada.getNombre());
        }
    }
}

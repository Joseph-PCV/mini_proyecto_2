/**
 * Trampa: Reflejo Mágico
 * Inflige 500 LP de daño al oponente. Se activa en respuesta a una carta mágica.
 * Condición: siempre se puede activar (el jugador elige cuándo usarla en su turno de trampas).
 */
public class ReflejoMagico extends CartaTrampa {

    public ReflejoMagico() {
        super("Reflejo Mágico", "Inflige 500 LP de daño directo al oponente.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return true;
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        oponente.recibirDanio(500);
    }
}

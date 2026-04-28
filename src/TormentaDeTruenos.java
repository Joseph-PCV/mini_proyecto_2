/**
 * Trampa: Tormenta de Truenos
 * Inflige 300 LP de daño al oponente por cada monstruo que tenga en campo.
 */
public class TormentaDeTruenos extends CartaTrampa {

    public TormentaDeTruenos() {
        super("Tormenta de Truenos", "Inflige 300 de daño al oponente por cada monstruo en su campo.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return !ctx.getOponente().getCampo().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        int danio = 300 * oponente.getCampo().size();
        if (danio > 0) {
            oponente.recibirDanio(danio);
        }
    }
}

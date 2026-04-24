/**
 * Trampa: Renacer del Fénix
 * Otorga al jugador activo 1500 LP extra.
 * Se puede activar en cualquier momento.
 */
public class RenacerDelFenix extends CartaTrampa {

    public RenacerDelFenix() {
        super("Renacer del Fénix", "Recupera 1500 LP cuando tus puntos de vida bajen de 3000.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return ctx.getJugadorActivo().getLp() < 3000;
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador j = ctx.getJugadorActivo();
        j.setLp(j.getLp() + 1500);
    }
}

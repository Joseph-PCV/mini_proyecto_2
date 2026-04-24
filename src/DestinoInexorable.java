/**
 * Trampa: Destino Inexorable
 * El oponente pierde 800 LP y no puede jugar cartas en su próximo turno.
 * Se puede activar en cualquier momento.
 */
public class DestinoInexorable extends CartaTrampa {

    public DestinoInexorable() {
        super("Destino Inexorable", "El oponente pierde 800 LP y no puede jugar cartas el próximo turno.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return true;
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        oponente.recibirDanio(800);
        oponente.bloquearJugarCartaProximoTurno();
    }
}

/**
 * Trampa: Escudo Sagrado
 * Da +1000 DEF a todos los monstruos propios en campo este turno.
 * Condición: el jugador activo tiene monstruos en campo.
 */
public class EscudoSagrado extends CartaTrampa {

    public EscudoSagrado() {
        super("Escudo Sagrado", "+1000 DEF a todos tus monstruos en campo este turno.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return !ctx.getJugadorActivo().getCampo().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        for (CartaMonstruo m : ctx.getJugadorActivo().getCampo()) {
            m.aplicarBoostDef((short) 1000);
        }
    }
}

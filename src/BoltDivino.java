import java.util.Random;

/**
 * Trampa: Bolt Divino
 * Destruye un monstruo aleatorio del oponente.
 * Condición: el oponente tiene al menos un monstruo en campo.
 */
public class BoltDivino extends CartaTrampa {

    public BoltDivino() {
        super("Bolt Divino", "Destruye un monstruo aleatorio del oponente.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return !ctx.getOponente().getCampo().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        if (oponente.getCampo().isEmpty()) return;
        Random rnd = new Random();
        int idx = rnd.nextInt(oponente.getCampo().size());
        CartaMonstruo objetivo = oponente.getCampo().get(idx);
        ctx.getCampo().eliminarMonstruo(objetivo, oponente);
    }
}

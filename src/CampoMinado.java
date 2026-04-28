import java.util.ArrayList;
import java.util.List;

/**
 * Trampa: Campo Minado
 * Destruye todos los monstruos del oponente con ATK menor a 1000.
 * Condición: el oponente tiene monstruos en campo.
 */
public class CampoMinado extends CartaTrampa {

    public CampoMinado() {
        super("Campo Minado", "Destruye todos los monstruos del oponente con ATK < 1000.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return !ctx.getOponente().getCampo().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        List<CartaMonstruo> aDestruir = new ArrayList<>();
        for (CartaMonstruo m : oponente.getCampo()) {
            if (m.getAtk() < 1000) aDestruir.add(m);
        }
        for (CartaMonstruo m : aDestruir) {
            ctx.getCampo().eliminarMonstruo(m, oponente);
        }
        if (aDestruir.isEmpty()) {
            // Efecto fallido pero la carta se consume igual
        }
    }
}

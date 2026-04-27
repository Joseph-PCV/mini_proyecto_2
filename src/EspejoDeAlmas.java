/**
 * Trampa: Espejo de Almas
 * Copia el ATK del monstruo más fuerte del oponente e inflige ese valor como daño de LP.
 * Condición: el oponente tiene monstruos en campo.
 */
public class EspejoDeAlmas extends CartaTrampa {

    public EspejoDeAlmas() {
        super("Espejo de Almas", "Inflige daño igual al ATK del monstruo más fuerte del oponente.");
    }

    @Override
    public boolean puedoActivarme(Contexto ctx) {
        return !ctx.getOponente().getCampo().isEmpty();
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        if (oponente.getCampo().isEmpty()) return;
        int maxAtk = 0;
        for (CartaMonstruo m : oponente.getCampo()) {
            if (m.getAtk() > maxAtk) maxAtk = m.getAtk();
        }
        // inflige la mitad del ATK para no ser demasiado poderosa
        int danio = maxAtk / 2;
        oponente.recibirDanio(danio);
    }
}

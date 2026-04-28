import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica que produce exactamente 50 cartas:
 *   30 monstruos, 10 mágicas, 10 trampas.
 */
public class FabricaDeCartas {

    public static List<Carta> crearMazoCompleto() {
        List<Carta> mazo = new ArrayList<>();
        mazo.addAll(crearMonstruos());  // 30
        mazo.addAll(crearMagicas());    // 10
        mazo.addAll(crearTrampas());    // 10
        return mazo;                    // total = 50
    }

    /** 30 cartas monstruo */
    public static List<CartaMonstruo> crearMonstruos() {
        List<CartaMonstruo> lista = new ArrayList<>();

        // 6 × Guerrero De La Luz (nivel 3)
        for (int i = 0; i < 6; i++)
            lista.add(new CartaMonstruo("Guerrero De La Luz",  (byte) 3, (short) 1200, (short) 1000));

        // 6 × Bestia del Bosque (nivel 4)
        for (int i = 0; i < 6; i++)
            lista.add(new CartaMonstruo("Bestia del Bosque",   (byte) 4, (short) 1500, (short) 1200));

        // 5 × Guardián del Hierro (nivel 5 — requiere sacrificio)
        for (int i = 0; i < 5; i++)
            lista.add(new CartaMonstruo("Guardian del Hierro", (byte) 5, (short) 1000, (short) 2000));

        // 5 × Hechicero del Caos (nivel 4)
        for (int i = 0; i < 5; i++)
            lista.add(new CartaMonstruo("Hechicero del Caos",  (byte) 4, (short) 1800, (short) 1500));

        // 5 × Caballero Real (nivel 6 — requiere sacrificio)
        for (int i = 0; i < 5; i++)
            lista.add(new CartaMonstruo("Caballero Real",      (byte) 6, (short) 2300, (short) 2000));

        // 3 × Dragón Ancestral (nivel 8 — requiere sacrificio)
        for (int i = 0; i < 3; i++)
            lista.add(new CartaMonstruo("Dragon Ancestral",    (byte) 8, (short) 3000, (short) 2500));

        return lista; // 6+6+5+5+5+3 = 30
    }

    /** 10 cartas mágicas */
    public static List<CartaMagica> crearMagicas() {
        List<CartaMagica> lista = new ArrayList<>();
        lista.add(new PotOfGreed());
        lista.add(new PotOfGreed());
        lista.add(new EspadaDeZeus());
        lista.add(new EspadaDeZeus());
        lista.add(new EscudoDeAtenea());
        lista.add(new EscudoDeAtenea());
        lista.add(new CuraMilagrosa());
        lista.add(new CuraMilagrosa());
        lista.add(new Fisura());
        lista.add(new LlamadaDelAbismo());
        return lista; // 10
    }

    /** 10 cartas trampa */
    public static List<CartaTrampa> crearTrampas() {
        List<CartaTrampa> lista = new ArrayList<>();
        lista.add(new ContraAtaque());      // 1
        lista.add(new CampoMinado());       // 2
        lista.add(new ReflejoMagico());     // 3
        lista.add(new RenacerDelFenix());   // 4
        lista.add(new TormentaDeTruenos()); // 5
        lista.add(new DestinoInexorable()); // 6
        lista.add(new BoltDivino());        // 7
        lista.add(new RoboForzado());       // 8
        lista.add(new EscudoSagrado());     // 9  ← Solucion
        lista.add(new EspejoDeAlmas());     // 10 ← Solucion
        return lista; // 10
    }
}

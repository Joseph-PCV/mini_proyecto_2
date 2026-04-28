public class CartaMonstruo extends Carta {

    private byte nivelCarta;
    private short atk;
    private short def;
    private short boostAtk;
    private short boostDef;
    private boolean puedeAtacar;
    private boolean enModoDefensa;

    public CartaMonstruo(String nombre, byte nivelCarta, short atk, short def) {
        super(nombre);
        this.nivelCarta = nivelCarta;
        this.atk = atk;
        this.def = def;
        this.boostAtk = 0;
        this.boostDef = 0;
        this.puedeAtacar = false;
        this.enModoDefensa = false;
    }

    public int getnivelCarta() {
        return nivelCarta;
    }

    public int getAtk() {
        return atk + boostAtk;
    }

    public int getDef() {
        return def + boostDef;
    }

    public boolean puedeAtacar() {
        return puedeAtacar;
    }

    public void setPuedeAtacar(boolean puedeAtacar) {
        this.puedeAtacar = puedeAtacar;
    }

    public boolean estaEnModoDefensa() {
        return enModoDefensa;
    }

    public void cambiarPosicion() {
        this.enModoDefensa = !this.enModoDefensa;
        String modo = enModoDefensa ? "DEFENSA" : "ATAQUE";
        System.out.println(">>> " + getNombre() + " cambió a modo " + modo + ".");
    }

    public void aplicarBoostAtk(short incremento) {
        boostAtk = incremento;
    }

    public void aplicarBoostDef(short incremento) {
        boostDef = incremento;
    }

    public void resetBoosts() {
        boostAtk = 0;
        boostDef = 0;
    }

    public void reiniciarAtaques() {
        puedeAtacar = true;
        resetBoosts();
    }

    public void marcarComoAtacado() {
        puedeAtacar = false;
    }

    public void resetTurno() {
        puedeAtacar = true;
    }

    public void decrementarMejora() {
        boostAtk = 0;
        boostDef = 0;
    }

    @Override
    public String getTipo() {
        return "MONSTRUO";
    }

    @Override
    public String toString() {
        String modo = enModoDefensa ? "[DEF]" : "[ATK]";
        return modo + " " + getNombre()
            + " Lv" + nivelCarta
            + " ATK:" + getAtk()
            + " DEF:" + getDef();
    }
}

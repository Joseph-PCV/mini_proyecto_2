
public abstract class CartaTrampa extends Carta implements Activable {

    private String descripcion; // Texto que describe qué hace la trampa
    private boolean activada;   // Indica si la trampa ya fue usada (true) o no (false)

    public CartaTrampa(String nombre, String descripcion) {
        super(nombre);          // Le pasa el nombre a la clase padre "Carta"
        this.descripcion = descripcion;
        this.activada = false;  // Al crearse, la trampa empieza sin activar
    }

    // Método que cada trampa debe definir por su cuenta:
    // ¿Bajo qué condición puedo activarme?
    public abstract boolean puedoActivarme(Contexto ctx);

    // Método que cada trampa debe definir por su cuenta:
    // ¿Qué hago cuando me activan?
    @Override
    public abstract void activar(Contexto ctx);

   
    public String getDescripcion() { return descripcion; }
    public boolean isActivada()    { return activada; }
    public void setActivada(boolean activada) { this.activada = activada; }

    
    @Override
    public String getTipo() { return "TRAMPA"; }

    // Cuando imprimes la carta, muestra [TRAMPA] Campo Minado: destruye
    @Override
    public String toString() {
        return "[TRAMPA] " + getNombre() + ": " + descripcion;
    }
}
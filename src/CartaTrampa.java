/**
 * Clase abstracta base para todas las cartas de trampa.
 * Las trampas se activan bajo condiciones especiales durante el turno del oponente
 * o en respuesta a ciertos eventos. Implementan Activable para tener efecto.
 */
public abstract class CartaTrampa extends Carta implements Activable {

    private String descripcion;
    private boolean activada; // true = la trampa ya fue revelada/activada

    public CartaTrampa(String nombre, String descripcion) {
        super(nombre);
        this.descripcion = descripcion;
        this.activada = false;
    }

    /**
     * Condición que determina si la trampa puede dispararse en el contexto actual.
     * Cada subclase define cuándo se activa.
     */
    public abstract boolean puedoActivarme(Contexto ctx);

    @Override
    public abstract void activar(Contexto ctx);

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isActivada() {
        return activada;
    }

    public void setActivada(boolean activada) {
        this.activada = activada;
    }

    @Override
    public String getTipo() {
        return "TRAMPA";
    }

    @Override
    public String toString() {
        return "[TRAMPA] " + getNombre() + ": " + descripcion;
    }
}

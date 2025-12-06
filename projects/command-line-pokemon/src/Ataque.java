/**
 * Representa un movimiento o ataque que un Pokémon puede usar en batalla.
 * Cada ataque tiene un nombre, un elemento, potencia, puntos de poder (PP) y precisión.
 */
public class Ataque {
    private String nombre;               // Nombre del ataque
    private Elemento elementoAtaque;     // Tipo elemental del ataque
    private int potencia;                // Poder base del ataque
    private int ppMaximo;                // PP máximos
    private int ppActual;                // PP actuales
    private double precision;            // Precisión del ataque (0.0 a 1.0)

    /**
     * Constructor para inicializar un ataque con todos sus atributos.
     * @param nombre Nombre del ataque
     * @param elemento Tipo elemental del ataque
     * @param potencia Poder base del ataque
     * @param ppMaximo PP máximos del ataque
     * @param precision Precisión del ataque (0.0 a 1.0)
     */
    public Ataque(String nombre, Elemento elemento, int potencia, int ppMaximo, double precision){
        this.nombre = nombre;
        this.elementoAtaque = elemento;
        this.potencia = potencia;
        this.ppMaximo = ppMaximo;
        this.precision = precision;
        ppActual = ppMaximo;
    }

    public void setPpActual(int ppActual){
        this.ppActual = ppActual;
    }

    public String getNombre() {
        return nombre;
    }

    public Elemento getElementoAtaque() {
        return elementoAtaque;
    }

    public int getPotencia() {
        return potencia;
    }

    public int getPpMaximo() {
        return ppMaximo;
    }

    public int getPpActual() {
        return ppActual;
    }

    public double getPrecision() {
        return precision;
    }

}

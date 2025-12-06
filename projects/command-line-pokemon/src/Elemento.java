/**
 * Representa un tipo elemental en el sistema de combate.
 * Un elemento tiene nombre, fortalezas y debilidades.
 */
public class Elemento {
    /** Nombre del elemento (por ejemplo: "Fuego", "Agua") */
    private String nombre;

    /** Elementos que son fuertes contra este elemento (lo vencen) */
    private Elemento[] debilidades;

    /** Elementos contra los cuales este elemento es fuerte */
    private Elemento[] fortalezas;

    /**
     * Constructor para crear un elemento con sus relaciones.
     *
     * @param nombre Nombre del elemento.
     * @param debilidades Array de elementos que vencen a este elemento.
     * @param fortalezas Array de elementos contra los cuales este elemento es fuerte.
     */
    public Elemento(String nombre,Elemento[] debilidades,Elemento[] fortalezas){
        this.nombre=nombre;
        this.debilidades=debilidades;
        this.fortalezas=fortalezas;
    }

    /**
     * Obtiene el nombre del elemento.
     *
     * @return Nombre del elemento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la lista de elementos que son fuertes contra este elemento.
     *
     * @return Array de elementos que representan debilidades.
     */
    public Elemento[] getDebilidades() {
        return debilidades;
    }

    /**
     * Obtiene la lista de elementos contra los cuales este elemento es fuerte.
     *
     * @return Array de elementos que representan fortalezas.
     */
    public Elemento[] getFortalezas() {
        return fortalezas;
    }
}
